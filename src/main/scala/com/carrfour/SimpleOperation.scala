package com.carrfour

import java.util.logging.Logger

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by alan-davila on 16/01/18.
  */
object SimpleOperation {

  val LOG = Logger.getLogger(getClass.getSimpleName)

  def main(args: Array[String]): Unit = {

    val sc = new SparkContext(new SparkConf().setMaster("local").setAppName("local"))
    var records: Seq[Tuple2[String, String]] = Seq.empty
    records = records :+ Tuple2.apply("v", "z")
    val lines = sc.textFile("src/main/resources/Don_Quijote_de_la_Mancha_cervantes_001.txt")
    val words = lines.flatMap(_.split(" "))
    val counts = words.map(w => (w, 1)).reduceByKey((x, y) => x + y)
    counts.sortBy(t => t._2, false).take(10).foreach(r => LOG.info("(" + r._1 + "," + r._2 + ")"))
    LOG.info("words:" + words.count())

    val rdds = sc.parallelize(records)
    rdds.collect().foreach(println)
    rdds.map(r => r._1 + ";" + r._2).saveAsTextFile("pruebasrdds")

  }
}
