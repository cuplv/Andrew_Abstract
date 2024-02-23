import scala.compiletime.ops.int
class If_Statement(cond: Expression, tbranch: Expression, fbranch: Expression)
    extends Expression {
  override def evaluate(state: State): Int | String | scala.Boolean | Point = {
    if (cond.evaluate(state).asInstanceOf[scala.Boolean]) {
      tbranch.evaluate(state)
    } else {
      fbranch.evaluate(state)
    }
  }

  // TODO: implement this, how would you abstractly evaluate an if statement? probably merge the intervals of both branches
  def abstract_evaluate(state: State): Interval = Interval(0, true, 0, true)

  override def toString(): String =
    "If(" + cond.toString() + ") {" + tbranch.toString() + "} else {" + fbranch
      .toString() + "}"
}

sealed trait ComparisonOperator

case object LessThan extends ComparisonOperator
case object GreaterThan extends ComparisonOperator
case object Equals extends ComparisonOperator

case class Conditional(a: Variable, comp: ComparisonOperator, b: Number)
    extends Expression {
  override def evaluate(state: State): Boolean = {
    val aval = a.evaluate(state).asInstanceOf[Int]
    val bval = b.evaluate(state)
    comp match {
      case LessThan    => aval < bval
      case GreaterThan => aval > bval
      case Equals      => aval == bval

    }
  }

  override def abstract_evaluate(state: State): TwoDInterval = ???

  // given an interval for the variable in the conditional, returns the (false, true) intervals
  def splitInterval(interval: Interval): (Interval, Interval) = {
    val bval = b.evaluate(State()).asInstanceOf[Int]
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
/*
case class LessThan(a: Expression, b: Expression) extends Conditional {
  override def evaluate(state: State): scala.Boolean = {
    a.evaluate(state).asInstanceOf[Int] < b.evaluate(state).asInstanceOf[Int]
  }

  override def abstract_evaluate(state: State): TwoDInterval = ???

  override def toString(): String =
    "lessThan(" + a.toString() + ", " + b.toString() + ")"
}

case class GreaterThan(a: Expression, b: Expression) extends Expression {
  override def evaluate(state: State): scala.Boolean = {
    a.evaluate(state).asInstanceOf[Int] > b.evaluate(state).asInstanceOf[Int]
  }

  override def abstract_evaluate(state: State): Interval =
    Interval(0, true, 0, true)

  override def toString(): String =
    "greaterThan(" + a.toString() + ", " + b.toString() + ")"
}

case class Equals(a: Expression, b: Expression) extends Expression {
  override def evaluate(state: State): scala.Boolean = {
    val aval = a.evaluate(state)
    val bval = b.evaluate(state)

    (aval, bval) match {
      case (aval: Int, bval: Int)                     => aval == bval
      case (aval: String, bval: String)               => aval == bval
      case (aval: scala.Boolean, bval: scala.Boolean) => aval == bval
      // case (aval: Point, bval: Point) => aval == bval
      case _ => false
    }
  }

  override def abstract_evaluate(state: State): Interval =
    Interval(0, true, 0, true)

  override def toString(): String =
    "equals(" + a.toString() + ", " + b.toString() + ")"
}

case class Or(a: Expression, b: Expression) extends Expression {
  override def evaluate(state: State): scala.Boolean = {
    val aval = a.evaluate(state)
    val bval = b.evaluate(state)

    (aval, bval) match {
      // case (aval: Int, bval: Int)                     => aval || bval
      // case (aval: String, bval: String)               => aval || bval
      case (aval: scala.Boolean, bval: scala.Boolean) => aval || bval
      // case (aval: Point, bval: Point) => aval || bval
      case _ => false
    }
  }

  override def abstract_evaluate(state: State): Interval =
    Interval(0, true, 0, true)

  override def toString(): String =
    "or(" + a.toString() + ", " + b.toString() + ")"
}

case class And(a: Expression, b: Expression) extends Expression {
  override def evaluate(state: State): scala.Boolean = {
    val aval = a.evaluate(state)
    val bval = b.evaluate(state)

    (aval, bval) match {
      // case (aval: Int, bval: Int)                     => aval && bval
      // case (aval: String, bval: String)               => aval && bval
      case (aval: scala.Boolean, bval: scala.Boolean) => aval && bval
      // case (aval: Point, bval: Point) => aval && bval
      case _ => false
    }
  }

  override def abstract_evaluate(state: State): Interval =
    Interval(0, true, 0, true)

  override def toString(): String =
    "and(" + a.toString() + ", " + b.toString() + ")"
}
 */
