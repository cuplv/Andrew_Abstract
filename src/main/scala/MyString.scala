case class MyString(str: String) extends Expression:
  override def evaluate(): String = str
  override def toString: String = "MyString(\"" + str.toString() + "\")"
