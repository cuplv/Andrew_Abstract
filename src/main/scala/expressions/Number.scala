case class Number(num: Int) extends Expression:
  /* override def evaluate[T <: Domain](state: State[T]): Concrete = Concrete(
    num
  ) */
  // don't love this evaluate, would love for it to return interval or whatever else the T domain is

  // override def abstract_evaluate(state: State): Interval =
  // Interval(num, true, num, true)
  override def toString: String = "Number(" + num.toString() + ")"
