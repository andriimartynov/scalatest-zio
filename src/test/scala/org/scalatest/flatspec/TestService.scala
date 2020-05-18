package org.scalatest.flatspec

import org.scalactic.source
import org.scalactic.source.Position
import org.scalatest.exceptions.{StackDepthException, TestCanceledException, TestPendingException}
import org.scalatest.{Assertion, PendingStatement, Succeeded}
import zio._

object TestService {

  type TestService = Has[TestService.Service]

  trait Service {
    def assert(condition: Boolean): Task[Assertion]

    def pending(): Task[Assertion with PendingStatement]

    def cancel()(implicit pos: source.Position): Task[Nothing]
  }

  object Service {
    lazy val live: Service = new Service {

      private val messageFun: StackDepthException => Option[String] =
        _ => None

      override def assert(
                           condition: Boolean
                         ): Task[Assertion] =
        if (condition) Task.fromFunction(_ => Succeeded)
        else Task.fromEither(Left(new Throwable("condition is false")))

      override def pending(): Task[Assertion with PendingStatement] =
        Task.fromEither(Left(new TestPendingException))

      override def cancel()(implicit pos: Position): Task[Nothing] =
        Task.fromEither(Left(new TestCanceledException(messageFun, None, pos, None)))
    }
  }

  lazy val live: Layer[Nothing, TestService] =
    ZLayer.fromFunction(_ => Service.live)

  def assert(
              condition: Boolean
            ): RIO[TestService, Assertion] =
    ZIO.accessM(_.get.assert(condition))

  def pending(): RIO[TestService, Assertion with PendingStatement] =
    ZIO.accessM(_.get.pending())

  def cancel()(implicit pos: Position): RIO[TestService, Nothing] =
    ZIO.accessM(_.get.cancel())

}
