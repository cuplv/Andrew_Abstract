class If_Statement(cond: Expression, tbranch: Expression, fbranch: Expression)
    extends Expression {
  override def evaluate(): Int | String | scala.Boolean | Point = {
    if (cond.evaluate().asInstanceOf[scala.Boolean]) {
      tbranch.evaluate()
    } else {
      fbranch.evaluate()
    }
  }

  // TODO: implement this
  def abstract_evaluate(): Interval = Interval(0, true, 0, true)

  override def toString(): String =
    "If(" + cond.toString() + ") {" + tbranch.toString() + "} else {" + fbranch
      .toString() + "}"
}
