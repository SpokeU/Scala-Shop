package validators

import controllers.Person

object Validators {

  def ageValidator(p: Person) = if (p.age == 12) "Age could not be 12" else ""
  def nameValidator(p: Person) = if (p.name == "Vasya") "Name could not be Vasya" else ""

}
