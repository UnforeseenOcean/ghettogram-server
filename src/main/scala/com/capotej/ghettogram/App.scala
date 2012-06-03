package com.capotej.ghettogram

import com.posterous.finatra._

object App {

  case class ImageViewer(var original: String)

  class GhettoGram extends FinatraApp{


    get("/") { r =>
      val img = new ImageViewer("http://i.imgur.com/jXHX1.jpg")
      render(path="index.mustache", exports=img)
    }

    // get /img?src=http://www.example.com/a.jpg
    get("/img") { r =>
      r.params.get("src") match {
        case Some(src) => rawResponse(body=ProcessImage(src), headers=Map("Content-Type" -> "image/jpeg"))
        case None => response(status=500, body="please supply an src param")
      }
    }

  }

  def main(args: Array[String]) {
    val gg = new GhettoGram

    FinatraServer.register(gg)
    FinatraServer.start()
  }

}


