lazy val Scala211 = "2.11.12"
lazy val Scala212 = "2.12.12"
lazy val Scala213 = "2.13.3"

organization := "org.github.andriimartynov"
name := "scalatest-zio"
licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))
scalaVersion := Scala212
startYear := Some(2020)
version := ScalaTest.SCALATEST_VERSION

crossScalaVersions := Seq(Scala211, Scala212, Scala213)

libraryDependencies += ScalaTest.dependency

libraryDependencies += Zio.dependency

credentials += Credentials(
  "GnuPG Key ID",
  "gpg",
  sys.env.getOrElse("GPG_PUBLIC_KEY", ""), // key identifier
  "ignored" // this field is ignored; passwords are supplied by pinentry
)