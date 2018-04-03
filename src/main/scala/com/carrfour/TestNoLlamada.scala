package com.carrfour

class TestNoLlamada {

  val x = 'ffffffffffffffffffffffffffffffffffffffff
  val y = 7

  def test : Unit = {println(x)}
}

object TestNoLlamada{
  def apply(): TestNoLlamada = new TestNoLlamada()
}