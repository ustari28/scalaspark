package com.carrfour

object EitherSamples {

  def main(args: Array[String]): Unit = {
    eitherString match {
      case Right(x) => println(s"Int value $x")
      case Left(x) => println(s"String value $x")
    }
    eitherInt() match {
      case Right(x) => println(s"Int value $x")
      case Left(x) => println(s"String value $x")
    }
  }

  def eitherString(): Either[String, Int] = {
    Left("test")
  }

  def eitherInt(): Either[String, Int] = {
    Right(2)
  }
}
