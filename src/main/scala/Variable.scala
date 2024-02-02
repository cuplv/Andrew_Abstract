class Variable(var value: Number|MyString|Bool) extends Expression {
  override def evaluate(): Int|String|scala.Boolean = value.evaluate()

  //choose not to statically type variable types
  def update(newValue: Number|MyString|Bool): Unit = value = newValue

  override def toString: String = "Variable("+value.toString+")";
}

//how to add in variables without adding in statements?
//like how would i modify a variable in the code?
//I could add in a state or scope that holds variables and stores them in a map with their identifiers

//should the variable contain its own identifier? or would that be handled by the state
