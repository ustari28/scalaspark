package com.carrfour

import java.io.{File, FileInputStream, InputStream}
import java.util.{Objects, Properties}

import org.slf4j.LoggerFactory

/**
  * Created by alan-davila on 23/11/17.
  * This class load the default file application.properties from classpath, if you want to load a different file you
  * have to set the property file.properties like java style <i>-Dfile.properties=fullPathToFile</i>.
  */
object PropertiesUtil {

  private val Logger = LoggerFactory.getLogger(getClass)
  private val properties = new Properties()
  init()

  /**
    * Init variables.
    */
  def init() = {
    var stream: InputStream = null
    if (scala.util.Properties.propIsSet("file.properties")) {
      loadProperties(scala.util.Properties.propOrNull("file.properties"), true)
    } else {
      loadProperties()
    }
  }

  /**
    * Load properties from classpath or system file.
    *
    * @param name     Relative or full path to file.
    * @param fullPath Flag to say if the path is full or relative.
    */
  private def loadProperties(name: String = "/application", fullPath: Boolean = false) = {

    var stream: InputStream = null
    if (fullPath) {
      stream = new FileInputStream(new File(name))
    } else {
      var isYaml = false
      stream = getClass.getResourceAsStream(name.concat(".properties"))
    }
    if (Objects.isNull(stream)) {
      Logger.warn(s"Properties file $name doesn't exist")
    } else {
      Logger.debug(s"Loading properties file $name")
      properties.load(stream)
      properties.list(System.out)
    }
  }

  /**
    * Get value of property.
    *
    * @param name Key of property.
    * @return Value of property.
    */
  def getProperty(name: String): String = properties.getProperty(name, "")

  def getPropertyList(pattern: String): Seq[String] = {
    var props = Seq.empty[String]
    props
  }
}
