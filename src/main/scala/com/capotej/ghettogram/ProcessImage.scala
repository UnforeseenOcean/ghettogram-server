package com.capotej.ghettogram

import org.apache.commons.exec._
import java.io._

object ProcessImage {

  def apply(src:String) = {
    val pi = new ProcessImage
    pi.src = Some(src)
    pi.bytes
  }

}

class ProcessImage {

  var inputPath:Option[String] = None
  var outputPath:Option[String] = None
  var src:Option[String] = None

  def rand = {
    Math.abs(scala.util.Random.nextInt)
  }

  def cmd(str: String) = {
    val cmdline = CommandLine.parse(str)
    val executor = new DefaultExecutor
    executor.setWorkingDirectory(new java.io.File("/tmp"))
    executor.execute(cmdline)
  }

  def applyFilter(f: Function2[String, String, Unit]) {
    inputPath foreach { path =>
      outputPath = Some("/tmp/" + rand)
      inputPath = outputPath
      println("outputing to " + outputPath.get)
      f(path, outputPath.get)
    }
  }

  def contrast {
    applyFilter((i,o) => cmd("convert " + i + " -sigmoidal-contrast 15x30% " + o))
  }

  def blurMap {
     applyFilter((i,o) => cmd("convert " + i + " -sparse-color Barycentric \'0,0 black 0,%h white\' -function polynomial 4,-4,1 " +  o))
  }

  def download {
    src foreach { url =>
      inputPath = Some("/tmp/" + rand)
      cmd("curl -o " + inputPath.get + " " + url)
    }
  }

  def bytes = {
    download
    contrast
    val fis = new FileInputStream(outputPath.get)
    Stream.continually(fis.read).takeWhile(-1 !=).map(_.toByte).toArray
  }

}