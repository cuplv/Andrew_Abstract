case class Multiplication(a: Expression, b: Expression) extends Expression:
  override def evaluate(state: State): Int | Point =
    val aval = a.evaluate(state)
    val bval = b.evaluate(state)
    (aval, bval) match {
      case (aval: Int, bval: Int) => aval * bval
      case (aval: Point, bval: Int) =>
        (a.asInstanceOf[Point] * bval).evaluate(state)
      case _ =>
        throw new Exception(
          "Cannot multipy " + aval.toString() + " and " + bval.toString()
        )
    }
  override def abstract_evaluate(state: State): Interval | TwoDInterval =
    (a.abstract_evaluate(state), b.abstract_evaluate(state)) match {
      case (a: Interval, b: Interval)     => a * b
      case (a: TwoDInterval, b: Interval) => a * b
      case _ =>
        throw new Exception(
          "Invalid multiplication: " + this.toString()
        )

    }
  override def toString: String =
    "Multiplication(" + a.toString() + ", " + b.toString() + ")"
