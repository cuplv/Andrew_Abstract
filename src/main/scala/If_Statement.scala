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

case class LessThan(a: Expression, b: Expression) extends Expression {
  override def evaluate(state: State): scala.Boolean = {
    a.evaluate(state).asInstanceOf[Int] < b.evaluate(state).asInstanceOf[Int]
  }

  override def abstract_evaluate(state: State): Interval =
    Interval(0, true, 0, true)

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
