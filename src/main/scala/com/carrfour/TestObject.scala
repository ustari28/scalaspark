package com.carrfour

class TestObject {

  val x = 'ffffffffffffffffffffffffffffffffffffffff
  val y = 7

  def test : Unit = {println(x)}
}

object TestObject{
  def apply(): TestObject = new TestObject()
}