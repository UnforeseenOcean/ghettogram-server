package com.capotej.ghettogram

import org.apache.commons.exec._
import java.io._

object ProcessImage {

  def apply(src:String, filter:String) = {
    val pi = new ProcessImage
    pi.src = Some(src)
    pi.filter = Some(filter)
    pi.path
  }

}

class ProcessImage {

  var inputPath:Option[String] = None
  var outputPath:Option[String] = None
  var src:Option[String] = None
  var filter:Option[String] = None

  def rand = {
    Math.abs(scala.util.Random.nextInt)
  }

  def cmd(str: String) {
    if (str.startsWith("convert")) {
      println("running " + str)
      val cmdline = CommandLine.parse(str)
      val executor = new DefaultExecutor
      executor.setWorkingDirectory(new java.io.File("."))
      executor.execute(cmdline)
    }
  }

  def applyFilter(f: Function2[String, String, Unit]) {
    inputPath foreach { path =>
      outputPath = Some("public/" + rand + ".gif")
      inputPath = outputPath
      f(path, outputPath.get)
    }
  }

  // def contrast {
  //   applyFilter((i,o) => cmd("convert " + i + " -quality 15 -sigmoidal-contrast 15x30% " + o))
  // }

  // def vignette {
  //   applyFilter((i,o) => cmd("convert " + i + " -quality 15 -matte -background none -vignette 0x3 " + o))
  // }

  // def painting {
  //   applyFilter((i,o) => cmd("convert " + i + " -quality 15 -paint 1 " + o))
  // }

  def download {
    src foreach { rawUrl =>
      val url = rawUrl.toLowerCase
      if(url.contains("jpg") || url.contains("png") || url.contains("jpeg")){
        inputPath = Some("/tmp/" + rand)
        val cmdline = CommandLine.parse("curl -o " + inputPath.get + " " + rawUrl)
        val executor = new DefaultExecutor
        executor.setWorkingDirectory(new java.io.File("."))
        executor.execute(cmdline)
      }
    }
  }

  def path = {
    download
    val name = filter.get
    println("geting " + name + " from db")
    val content = ImageRecipes.db.get(name)
    if (content != null) {
      try {
        applyFilter((i,o) => cmd(content.replace("{{i}}", i).replace("{{o}}", o)))
      } catch {
        case e: Exception => println(e)
      }
      outputPath.get.split("/").last.mkString
    } else {
      inputPath.get
    }
  }

}