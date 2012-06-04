package com.capotej.ghettogram

import jdbm._

object ImageRecipes {
  val recMan = RecordManagerFactory.createRecordManager("db/recipes")
  val db:PrimaryHashMap[String, String] = recMan.hashMap("recipes")

  def save = {
    recMan.commit
  }
}