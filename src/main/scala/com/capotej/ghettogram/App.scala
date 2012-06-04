package com.capotej.ghettogram

import com.posterous.finatra._

object App {

  case class ImageViewer(var original: String, var transformed: String)

  class GhettoGram extends FinatraApp{

    get("/") { r =>
      val recipe = r.params.get("recipe").getOrElse("contrast")
      val src = r.params.get("src").getOrElse("http://i.imgur.com/Pmt0B.jpg")
      val ra = new RecipeAdmin(recipe, src)
      render(path="index.mustache", exports=ra)
    }

    post("/saveRecipe") { r =>
      val name = r.params.get("name")
      val content = r.params.get("content")
      val src = r.params.get("src")
      if (name.isDefined && content.isDefined && src.isDefined) {
        ImageRecipes.db.put(name.get, content.get)
        ImageRecipes.save
        redirect("/?recipe=" + name.get + "&src=" + src.get)
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


