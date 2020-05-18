name := "scalatest-zio"

lazy val Scala211 = "2.11.12"
lazy val Scala212 = "2.12.11"
lazy val Scala213 = "2.13.2"

ThisBuild / organization := "org.github.andriimartynov"
ThisBuild / scalaVersion := Scala212
ThisBuild / version := ScalaTest.SCALATEST_VERSION

crossScalaVersions := Seq(Scala211, Scala212, Scala213)

libraryDependencies += ScalaTest.dependency

libraryDependencies += Zio.dependency

startYear := Some(2020)

licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))
