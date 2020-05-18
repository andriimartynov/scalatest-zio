lazy val Scala211 = "2.11.12"
lazy val Scala212 = "2.12.11"
lazy val Scala213 = "2.13.2"

organization := "org.github.andriimartynov.scalatest"
name := "scalatest-zio"
scalaVersion := Scala212
version := ScalaTest.SCALATEST_VERSION

crossScalaVersions := Seq(Scala211, Scala212, Scala213)

libraryDependencies += ScalaTest.dependency

libraryDependencies += Zio.dependency

startYear := Some(2020)

licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))

credentials += Credentials(
  "Artifactory Realm",
  "oss.jfrog.org",
  sys.env.getOrElse("OSS_JFROG_USER", "andriimartynov"),
  sys.env.getOrElse("OSS_JFROG_PASS", "2ed547fb736295dcd9c5b61569193fe9c20f626a")
)

publishTo := {
  val jfrog = "https://oss.jfrog.org/artifactory/"
  if (isSnapshot.value)
    Some("OJO Snapshots" at jfrog + "oss-snapshot-local")
  else
    Some("OJO Releases" at jfrog + "oss-release-local")
}