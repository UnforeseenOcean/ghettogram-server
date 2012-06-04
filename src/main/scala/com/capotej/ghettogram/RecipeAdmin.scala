package com.capotej.ghettogram

case class Recipe(name: String)

class RecipeAdmin(recipe: String) {

  def currentRecipeName = {
    recipe
  }

  def src = {
    ProcessImage("http://i.imgur.com/Pmt0B.jpg", currentRecipeName)
  }

  def currentRecipe = {
    ImageRecipes.db.get(recipe)
  }

  def recipes = {
    ImageRecipes.db.keySet
  }

}