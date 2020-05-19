import sbt._

object ScalaTest {
  lazy val SCALATEST_VERSION = "3.3.0-SNAP2"

  def dependency: ModuleID =
    "org.scalatest" %% "scalatest" % SCALATEST_VERSION

}
