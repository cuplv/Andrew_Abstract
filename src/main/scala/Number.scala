case class Number(num: Int) extends Expression:
  override def evaluate(): Int = num
  override def toString: String = "Number(" + num.toString() + ")"
