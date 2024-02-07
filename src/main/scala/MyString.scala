case class MyString(str: String) extends Expression:
  override def evaluate(): String = str
  //TODO: implement this
  override def abstract_evaluate(): Interval = Interval(0, true, 0, true)
  override def toString: String = "MyString(\"" + str.toString() + "\")"
