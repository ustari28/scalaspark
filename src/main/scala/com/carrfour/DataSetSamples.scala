package com.carrfour


import java.text.MessageFormat

import org.apache.avro.ipc.specific.Contexts
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkFiles, sql}
import org.apache.spark.sql.{Encoders, Row, SparkSession}



object DataSetSamples {

  def main(args: Array[String]): Unit = {

    val ss = SparkSession.builder().config(new SparkConf().setAppName("local").setMaster("local[*]")).getOrCreate()
    //@transient val ss = SparkSession.builder().getOrCreate()
    val dsSchools = ss.sqlContext.read.json("src/main/resources/schools.json").as(Encoders.bean(classOf[University]))
    val dsSchools1 = ss.sqlContext.read.json("src/main/resources/schools.json").as(Encoders.bean(classOf[University]))
    val dsStudents = ss.sqlContext.read.json("src/main/resources/students.json").as(Encoders.bean(classOf[Student]))

    //dsSchools.describe("name","numStudents").show
    //dsSchools.where("numStudents > 1500").show
    dsSchools.union(dsSchools1).distinct()
    val join = dsStudents join(dsSchools,dsSchools("idUniversity") === dsStudents("universityIdUniversity"))
    val count = join selectExpr("idStudent", "cast('1' as int) as t")
    val row = join.groupBy(join("idUniversity")) count()
    row show

    //ds.take(10).foreach(println)
  }
}

