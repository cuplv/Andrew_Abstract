case class Rand(max: Int = Int.MaxValue) extends Expression:

  /* override def abstract_evaluate(state: State): Interval =
    Interval(
      0,
      true,
      if max == Int.MaxValue then Infinity(false) else max,
      if max == Int.MaxValue then false else true
    )
   */
  override def toString(): String = "Rand()"
