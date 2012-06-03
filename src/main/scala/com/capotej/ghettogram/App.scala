package com.capotej.ghettogram

import com.posterous.finatra._

object App {

  class GhettoGram extends FinatraApp{

    // get /img?src=http://www.example.com/a.jpg
    get("/img") { r =>
      r.params.get("src") match {
        case Some(src) => rawResponse(body=ProcessImage(src))
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


