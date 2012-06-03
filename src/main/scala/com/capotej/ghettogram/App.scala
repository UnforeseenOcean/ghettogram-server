package com.capotej.ghettogram

import com.posterous.finatra._

object App {

  case class ImageViewer(var original: String, var transformed: String)

  class GhettoGram extends FinatraApp{

    get("/") { r =>
      val src = r.params.get("src").getOrElse("http://i.imgur.com/D3XNI.jpg")
      val img = new ImageViewer(src, ProcessImage(src))
      render(path="index.mustache", exports=img)
    }

  }

  def main(args: Array[String]) {
    val gg = new GhettoGram

    FinatraServer.register(gg)
    FinatraServer.start()
  }

}


