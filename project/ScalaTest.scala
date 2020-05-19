import sbt._

object ScalaTest {
  lazy val SCALATEST_VERSION = "3.1.1"

  def dependency: ModuleID =
    "org.scalatest" %% "scalatest" % SCALATEST_VERSION

}
