package com.capotej.ghettogram

object ProcessImage {

  def apply(src:String) = {
    val pi = new ProcessImage
    pi.src = src
    pi.bytes
  }

}

class ProcessImage {
  var src:String = null

  def bytes = {
    "lol".getBytes
  }

}