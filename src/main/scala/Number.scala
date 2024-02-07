case class Number(num: Int) extends Expression:
  override def evaluate(): Int = num
  override def abstract_evaluate(): Interval = Interval(num, true, num, true)
  override def toString: String = "Number(" + num.toString() + ")"
