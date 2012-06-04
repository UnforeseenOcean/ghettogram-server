package com.capotej.ghettogram

case class Recipe(name: String)

class RecipeAdmin(recipe: String, src: String) {

  def currentRecipeName = {
    recipe
  }

  def currentSrc = {
    src
  }

  def transformedImage = {
    ProcessImage(src, currentRecipeName)
  }

  def currentRecipe = {
    ImageRecipes.db.get(recipe)
  }

  def recipes = {
    ImageRecipes.db.keySet
  }

}