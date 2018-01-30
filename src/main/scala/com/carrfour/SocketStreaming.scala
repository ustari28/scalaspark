package com.carrfour

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Hello world!
 *
 */
object SocketStreaming {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("Spark-Streaming").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(1))
    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    wordCounts.print()
    ssc.start()             // Start the computation
    ssc.awaitTermination()  // Waiting for signals
  }
}
