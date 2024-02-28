case class MyString(str: String) extends Expression:
  override def evaluate(state: State): String = str
  // TODO: implement this
  override def abstract_evaluate(state: State): Interval =
    Interval(0, true, 0, true)
  override def toString: String = "MyString(\"" + str.toString() + "\")"
