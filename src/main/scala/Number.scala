case class Number(num: Int) extends Expression:
  override def evaluate(): Any = num
  override def toString: String = "Number(" + num.toString() + ")"
