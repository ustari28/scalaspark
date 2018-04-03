package com.carrfour

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util
import java.util.Optional
import java.util.logging.Logger

import kafka.serializer.{DefaultDecoder, StringDecoder}
import org.apache.commons.lang3.StringUtils
import org.apache.hadoop.fs.Path
import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{Get, HTable, Put}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}


object KafkaHbaseStreaming {
  val LOG = Logger.getLogger(getClass.getName)

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("kafkastreaming").setMaster("local[*]")
    val ssc = new StreamingContext(conf, Seconds(5))
    val params: Map[String, String] = Map(
      "metadata.broker.list" -> "localhost:9092",
      "zookeeper.connect" -> "localhost:2182",
      "group.idUniversity" -> "spark-streaming",
      "zookeeper.connection.timeout.ms" -> "1000"
    )
    val topics = List("spark")
    val table = hbase()
    LOG.warning("hbase connected")
    val kafkaStream = KafkaUtils.createStream[Array[Byte], String, DefaultDecoder, StringDecoder](ssc, params, topics.map((_, 1)).toMap, StorageLevel.MEMORY_ONLY_SER).map(_._2)
    kafkaStream.flatMap(linea => linea.split(" ")).map((_, 1)).reduceByKey((x, y) => x + y).foreachRDD(rdd => {
      val puts = new util.ArrayList[Put]()
      val date = LocalDate.now.format(DateTimeFormatter.ofPattern("yyyyMMdd"))
      rdd.filter(r => StringUtils.isNotEmpty(r._1)).collect().foreach(r => {
        val g = new Get(Bytes.toBytes(r._1))
        val result = table.get(g)
        val p = new Put(Bytes.toBytes(r._1))
        val previous = Bytes.toInt(Optional.ofNullable(result.getValue(Bytes.toBytes("count"), Bytes.toBytes("count"))).orElse(Bytes.toBytes(0)))
        p.add(Bytes.toBytes("word"), Bytes.toBytes("word"), Bytes.toBytes(r._1))
        p.add(Bytes.toBytes("count"), Bytes.toBytes("count"), Bytes.toBytes(r._2 + previous))
        p.add(Bytes.toBytes("date"), Bytes.toBytes("date"), Bytes.toBytes(date))
        puts.add(p)
      })
      table.put(puts)
    })
    ssc.start()
    ssc.awaitTermination()
  }

  def hbase(): HTable = {
    val confHbase = HBaseConfiguration.create()
    val path = "hbase-site.xml"
    confHbase.addResource(new Path(KafkaHbaseStreaming.getClass.getClassLoader.getResource(path).getPath))

    new HTable(confHbase, "words")
  }
}
