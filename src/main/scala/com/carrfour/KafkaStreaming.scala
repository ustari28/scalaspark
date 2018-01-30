package com.carrfour

import java.util.logging.Logger

import kafka.serializer.{DefaultDecoder, StringDecoder}
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


/**
  * Created by alan-davila on 6/11/17.
  */
object KafkaStreaming {

  val LOG = Logger.getLogger(getClass.getName)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("kafkastreaming").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(5))
    val params: Map[String, String] = Map(
      "metadata.broker.list" -> "localhost:9092",
      "zookeeper.connect" -> "localhost:2181",
      "group.id" -> "spark-streaming",
      "zookeeper.connection.timeout.ms" -> "1000"
    )
    val topics = List("spark")
    val kafkaStream = KafkaUtils.createStream[Array[Byte], String, DefaultDecoder, StringDecoder](ssc, params, topics.map((_, 1)).toMap, StorageLevel.MEMORY_ONLY_SER).map(_._2)
    kafkaStream.flatMap(linea => linea.split(" ")).map((_, 1)).reduceByKey((x, y) => x + y).foreachRDD(rdd => {
      val topList = rdd.sortBy(reg => reg._2, false).take(50)
      topList.foreach(reg => LOG.warning(s"$reg"))
    })
    ssc.start()
    ssc.awaitTermination()
  }
}
