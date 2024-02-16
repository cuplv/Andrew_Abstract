case class Number(num: Int) extends Expression:
  override def evaluate(sttae: State): Int = num
  override def abstract_evaluate(state: State): Interval =
    Interval(num, true, num, true)
  override def toString: String = "Number(" + num.toString() + ")"
