package controllers

import model.Category
import play.api.mvc.Action
import play.api.mvc.Controller
import util.UserModel
import myLibrary._


object Application extends Controller {

  case class Person(name: String, age: Int)
  
  def index = Action {
    //testMacro(Person("name", 1))
    HelloTest.doStuff("It WoRKS!")
    Ok("")
  }
  
/*  def testMacro[T: Mappable](p: T) = {
    
    println(mapper.toMap(p))
    println("From map: " + mapper.fromMap(Map("name" -> "And", "age" -> 1)))
  }*/

}