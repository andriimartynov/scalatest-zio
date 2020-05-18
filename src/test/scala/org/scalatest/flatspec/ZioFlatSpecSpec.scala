package org.scalatest.flatspec

import org.scalatest.flatspec.TestService.TestService
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.{Args, ParallelTestExecution}
import zio.{IO, Layer, Task}

import scala.util.Try

class ZioFlatSpecSpec extends AnyFunSpec {

  describe("ZioFlatSpec") {

    it("can be used for tests that return IO under parallel async test execution") {

      class ExampleSpec extends ZioFlatSpec with ParallelTestExecution {

        protected case class ExceptionWrapper(t: Throwable)

        implicit protected val exceptionConverter: ExceptionWrapper => Throwable =
          (w: ExceptionWrapper) => w.t

        val a = 1

        it should "test 1" in {
          tryToIO {
            Try(assert(a == 1))
          }
        }

        it should "test 2" in {
          tryToIO {
            Try(assert(a == 2))
          }
        }

        it should "test 3" in {
          tryToIO {
            Try(pending)
          }
        }

        it should "test 4" in {
          tryToIO {
            Try(cancel)
          }
        }

        it should "test 5" ignore {
          tryToIO {
            Try(cancel)
          }
        }

        override def newInstance = new ExampleSpec

        private def tryToIO[A](t: Try[A]): IO[ExceptionWrapper, A] =
          IO.fromTry(t).mapError(ExceptionWrapper)
      }

      val rep = new EventRecordingReporter
      val spec = new ExampleSpec
      val status = spec.run(None, Args(reporter = rep))
      // SKIP-SCALATESTJS,NATIVE-START
      status.waitUntilCompleted()
      // SKIP-SCALATESTJS,NATIVE-END
      assert(rep.testStartingEventsReceived.length == 4)
      assert(rep.testSucceededEventsReceived.length == 1)
      assert(rep.testSucceededEventsReceived.head.testName == "should test 1")
      assert(rep.testFailedEventsReceived.length == 1)
      assert(rep.testFailedEventsReceived.head.testName == "should test 2")
      assert(rep.testPendingEventsReceived.length == 1)
      assert(rep.testPendingEventsReceived.head.testName == "should test 3")
      assert(rep.testCanceledEventsReceived.length == 1)
      assert(rep.testCanceledEventsReceived.head.testName == "should test 4")
      assert(rep.testIgnoredEventsReceived.length == 1)
      assert(rep.testIgnoredEventsReceived.head.testName == "should test 5")
    }
  }

  it("can be used for tests that return RIO under parallel async test execution") {

    class ExampleSpec extends ZioFlatSpec with ParallelTestExecution {

      implicit protected val layer: Layer[Nothing, TestService] = TestService.live

      val a = 1

      it should "test 1" in {
        for {
          result <- TestService.assert(a == 1)
        } yield result
      }

      it should "test 2" in {
        for {
          result <- TestService.assert(a == 2)
        } yield result
      }

      it should "test 3" in {
        for {
          result <- TestService.pending()
        } yield result
      }

      it should "test 4" in {
        for {
          result <- TestService.cancel()
        } yield result
      }

      it should "test 5" ignore {
        for {
          result <- TestService.cancel()
        } yield result
      }

      override def newInstance = new ExampleSpec
    }

    val rep = new EventRecordingReporter
    val spec = new ExampleSpec
    val status = spec.run(None, Args(reporter = rep))
    // SKIP-SCALATESTJS,NATIVE-START
    status.waitUntilCompleted()
    // SKIP-SCALATESTJS,NATIVE-END
    assert(rep.testStartingEventsReceived.length == 4)
    assert(rep.testSucceededEventsReceived.length == 1)
    assert(rep.testSucceededEventsReceived.head.testName == "should test 1")
    assert(rep.testFailedEventsReceived.length == 1)
    assert(rep.testFailedEventsReceived.head.testName == "should test 2")
    assert(rep.testPendingEventsReceived.length == 1)
    assert(rep.testPendingEventsReceived.head.testName == "should test 3")
    assert(rep.testCanceledEventsReceived.length == 1)
    assert(rep.testCanceledEventsReceived.head.testName == "should test 4")
    assert(rep.testIgnoredEventsReceived.length == 1)
    assert(rep.testIgnoredEventsReceived.head.testName == "should test 5")
  }

  it("can be used for tests that return Task under parallel async test execution") {

    class ExampleSpec extends ZioFlatSpec with ParallelTestExecution {

      val a = 1

      it should "test 1" in {
        Task.fromTry {
          Try(assert(a == 1))
        }
      }

      it should "test 2" in {
        Task.fromTry {
          Try(assert(a == 2))
        }
      }

      it should "test 3" in {
        Task.fromTry {
          Try(pending)
        }
      }

      it should "test 4" in {
        Task.fromTry {
          Try(cancel)
        }
      }

      it should "test 5" ignore {
        Task.fromTry {
          Try(cancel)
        }
      }

      override def newInstance = new ExampleSpec
    }

    val rep = new EventRecordingReporter
    val spec = new ExampleSpec
    val status = spec.run(None, Args(reporter = rep))
    // SKIP-SCALATESTJS,NATIVE-START
    status.waitUntilCompleted()
    // SKIP-SCALATESTJS,NATIVE-END
    assert(rep.testStartingEventsReceived.length == 4)
    assert(rep.testSucceededEventsReceived.length == 1)
    assert(rep.testSucceededEventsReceived.head.testName == "should test 1")
    assert(rep.testFailedEventsReceived.length == 1)
    assert(rep.testFailedEventsReceived.head.testName == "should test 2")
    assert(rep.testPendingEventsReceived.length == 1)
    assert(rep.testPendingEventsReceived.head.testName == "should test 3")
    assert(rep.testCanceledEventsReceived.length == 1)
    assert(rep.testCanceledEventsReceived.head.testName == "should test 4")
    assert(rep.testIgnoredEventsReceived.length == 1)
    assert(rep.testIgnoredEventsReceived.head.testName == "should test 5")
  }

}
