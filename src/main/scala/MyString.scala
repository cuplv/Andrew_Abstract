case class MyString(str: String) extends Expression:
  override def evaluate(): Any = str
  override def toString: String = "MyString(\"" + str.toString() + "\")"
