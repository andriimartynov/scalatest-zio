ZioFlatSpec for ScalaTest
=========
[ ![Download](https://api.bintray.com/packages/andriimartynov/maven/scalatest-zio/images/download.svg) ](https://bintray.com/andriimartynov/maven/scalatest-zio/_latestVersion)
[![Build Status](https://travis-ci.org/andriimartynov/scalatest-zio.svg)](https://travis-ci.org/andriimartynov/scalatest-zio)
[![Apache License V.2](https://img.shields.io/badge/license-Apache%20V.2-blue.svg)](https://github.com/andriimartynov/scalatest-zio/blob/master/LICENSE)


[ScalaTest](http://www.scalatest.org/) spec for testing [zio](https://zio.dev/) monad.

## Getting ZioFlatSpec

To resolve artifacts through Artifactory, simply add the following code snippet to your build.sbt file:

```scala
resolvers += Resolver.jcenterRepo
```

The current version is 3.1.3, which is cross-built against Scala 2.11.x, 2.12.x and 2.13.x.

```scala
libraryDependencies += "org.github.andriimartynov" %% "scalatest-zio" % "3.1.3"
```

## Usage example
```scala
class ExampleSpec extends ZioFlatSpec {
  //For RIO[SomeService, Assertion]
  implicit protected val layer: Layer[Nothing, SomeService with AnowerService] = ...
    
  //For IO[ExceptionWrapper, Assertion]
  implicit protected val exceptionConverter: ExceptionWrapper => Throwable = ...
    
  it should "a RIO example" in {
    for {
      result <- SomeService.method() 
    } yield result
  }
      
  it should "a IO example" in {
    for {
      result <- IO.fromFunction(_ => ...) 
    } yield result
  }      
    
  it should "a Task example" in {
    for {
      result <- Task.fromFunction(_ => ...) 
    } yield result
  }
}
```