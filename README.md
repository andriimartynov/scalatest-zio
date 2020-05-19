ZioFlatSpec for ScalaTest
=========
[![Build Status](https://travis-ci.org/andriimartynov/scalatest-zio.svg)](https://travis-ci.org/andriimartynov/scalatest-zio)

[ScalaTest](http://www.scalatest.org/) spec for testing [zio](https://zio.dev/) monad.

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