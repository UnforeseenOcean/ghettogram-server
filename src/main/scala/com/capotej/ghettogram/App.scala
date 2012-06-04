package com.capotej.ghettogram

import com.posterous.finatra._

object App {

  case class ImageViewer(var original: String, var transformed: String)

  class GhettoGram extends FinatraApp{

    get("/") { r =>
      val ra = new RecipeAdmin(r.params.get("recipe").getOrElse("contrast"))
      render(path="index.mustache", exports=ra)
    }

    post("/saveRecipe") { r =>
      val name = r.params.get("name")
      val content = r.params.get("content")
      if (name.isDefined && content.isDefined) {
        ImageRecipes.db.put(name.get, content.get)
        ImageRecipes.save
        redirect("/?recipe=" + name.get)
      } else {
        response(status=500, body="sucks")
      }
    }

    get("/remove") { r =>
      val name = r.params.get("recipe")
      if (name.isDefined) {
        ImageRecipes.db.remove(name.get)
        ImageRecipes.save
      }
      redirect("/")
    }

    get("/img") { r =>
      val src = r.params.get("src").getOrElse("http://i.imgur.com/D3XNI.jpg")
      val filter = r.params.get("filter").getOrElse("contrast")
      val img = new ImageViewer(src, ProcessImage(src, filter))
      render(path="index.mustache", exports=img)
    }

  }

  def main(args: Array[String]) {
    val gg = new GhettoGram

    FinatraServer.register(gg)
    FinatraServer.start()
  }

}


