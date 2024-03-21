sealed trait ComparisonOperator

case object LessThan extends ComparisonOperator
case object GreaterThan extends ComparisonOperator
case object Equals extends ComparisonOperator

case class Conditional(a: Expression, comp: ComparisonOperator, b: Expression)
    extends Expression {
  /* override def evaluate[T <: Domain](state: State[T]): T = {
    val aval = a.evaluate[T](state)
    val bval = b.evaluate[T](state)
    comp match {
      case LessThan    => (aval < bval).asInstanceOf[T]
      case GreaterThan => (aval > bval).asInstanceOf[T]
      case Equals      => (aval == bval).asInstanceOf[T]

    }
  } */

  // override def abstract_evaluate(state: State): TwoDInterval = ???

  // given an interval for the variable in the conditional, returns the (false, true) intervals
  def splitInterval(interval: Interval): (Interval, Interval) = {
    val bval = b.evaluate(State[Int]()).asInstanceOf[Int]
    comp match {
      case LessThan =>
        if (interval.high <= bval) {
          (Bottom(), interval) // false, true
        } else if (interval.low >= bval) {
          (interval, Bottom()) // false, true
        } else {
          (
            Interval(bval, true, interval.high, interval.highInc),
            Interval(interval.low, true, bval - 1, true)
          ) // false,true
        }
      case GreaterThan =>
        if (interval.low >= bval) {
          (Bottom(), interval) // false,true
        } else if (interval.high <= bval) {
          (interval, Bottom()) // false,true
        } else {
          (
            Interval(interval.low, interval.lowInc, bval, true),
            Interval(bval + 1, true, interval.high, interval.highInc)
          ) // false,true
        }

      // TODO this will likely involve split intervals??
      // TODO ensure this follows false, true
      case Equals =>
        if (interval.low > bval || interval.high < bval) {
          (Bottom(), Bottom())
        } else if (interval.low == bval && interval.high == bval) {
          (interval, interval)
        } else if (interval.low == bval) {
          (
            Interval(bval, true, bval, true),
            Interval(bval + 1, true, interval.high, interval.highInc)
          )
        } else if (interval.high == bval) {
          (
            Interval(interval.low, interval.lowInc, bval - 1, true),
            Interval(bval, true, bval, true)
          )
        } else {
          (
            Interval(interval.low, interval.lowInc, bval - 1, true),
            Interval(bval, true, interval.high, interval.highInc)
          )
        }
    }
  }

  override def toString(): String =
    "Conditional(" + a.toString() + ", " + comp.toString() + ", " + b
      .toString() + ")"
}
