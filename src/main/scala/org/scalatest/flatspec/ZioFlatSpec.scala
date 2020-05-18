package org.scalatest.flatspec

import org.scalatest.Assertion
import zio._

import scala.concurrent.Future

abstract class ZioFlatSpec extends AsyncFlatSpec {

  implicit protected val runtime: Runtime[ZEnv] =
    zio.Runtime.global

  implicit protected val taskToFuture: Task[Assertion] => Future[Assertion] =
    task =>
      runtime.unsafeRunToFuture(task)

  implicit protected def ioToFuture[E](
                                        io: IO[E, Assertion]
                                      )(
                                        implicit exceptionConverter: E => Throwable
                                      ): Future[Assertion] =
    io.mapError(exceptionConverter)

  implicit protected def rioToFuture[R](
                                         rio: RIO[Has[R], Assertion]
                                       )(
                                         implicit layer: Layer[Nothing, Has[R]]
                                       ): Future[Assertion] =
    rio.provideLayer(layer)

}
