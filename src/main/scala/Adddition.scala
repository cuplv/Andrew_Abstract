class Addition(var a: Expression, var b: Expression) extends Expression:
  override def evaluate(): Int | String | scala.Boolean =
    val aval = a.evaluate()
    val bval = b.evaluate()
    
    if aval.isInstanceOf[Int] && bval.isInstanceOf[Int] then
      aval.asInstanceOf[Int] + bval.asInstanceOf[Int]
    else if aval.isInstanceOf[String] && bval.isInstanceOf[String] then
      aval.asInstanceOf[String] + bval.asInstanceOf[String]
    else if aval.isInstanceOf[scala.Boolean] && bval.isInstanceOf[scala.Boolean] then
      aval.asInstanceOf[scala.Boolean] || bval.asInstanceOf[scala.Boolean]
    else
      throw new Exception(
        "Cannot add " + aval.toString() + " and " + bval.toString()
      )

  end evaluate

  override def toString: String = {
    "Addition(" + a.toString() + ", " + b.toString() + ")"
  }


  //look into union types? make sure you know stuff about boxing etc.
  //look into case classes(they only hold one variable?)
  //Int or String as definite evaluate type to make it a little easier
  //add in variables and assignment and lookup
  // maybe add in conditionals and loops
  //should make a statement to control if, loop, assignment and each would have expression's as parameters
  //variables are an expression tho
  //unsure if variables are worth it, if and loops may be better places to start

  //what makes an expression parametric?

  //scala object vs class?
  //trait vs abstract classs