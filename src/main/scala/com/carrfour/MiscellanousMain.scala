package com.carrfour

import org.apache.spark.SparkConf

/**
  * Hello world!
  *
  */
object MiscellanousMain {
  private val POS_INI_CENTRO = 6
  private val LON_CENTRO = 10
  private val POS_INI_FECHA = 0
  private val LON_FECHA = 6
  private val POS_INI_HORA = 6
  private val LON_HORA = 10
  private val POS_INI_COD = 10
  private val LON_COD = 12
  private val POS_INI_DES = 12
  private val LON_DES = 14
  private val POS_INI_TRANID = 14
  private val LON_TRANID = 18

  def main(args: Array[String]): Unit = {
    val runtime1 = Runtime.getRuntime
    println(runtime1.freeMemory)
    println(runtime1.totalMemory)
    println(runtime1.maxMemory)
    val conf = new SparkConf().setAppName("Spark-Streaming").setMaster("local[2]")
    val xx = "180131043720.V6277"
    val sbNuevaLinea = StringBuilder.newBuilder.append("CUPAGOAAAA.ASV".substring(POS_INI_CENTRO, LON_CENTRO))
      .append(Constants.DEFAULT_SEPARATOR)
      .append(xx.substring(POS_INI_FECHA, LON_FECHA)).append(Constants.DEFAULT_SEPARATOR)
      .append(xx.substring(POS_INI_HORA, LON_HORA)).append(Constants.DEFAULT_SEPARATOR)
      .append(xx.substring(POS_INI_COD, LON_COD)).append(Constants.DEFAULT_SEPARATOR)
      .append(xx.substring(POS_INI_DES, LON_DES)).append(Constants.DEFAULT_SEPARATOR)
      .append(xx.substring(POS_INI_TRANID, LON_TRANID))
    println(sbNuevaLinea.toString)
    TestObject().test
    val runtime = Runtime.getRuntime
    println(runtime.freeMemory)
    println(runtime.totalMemory)
    println(runtime.maxMemory)

    var x= "rr|@|rrr|@|ttt|@||@|ttt"
    var xa = x.split("\\|@\\|")
    xa.foreach(println)
  }
}
