class While_Statement(cond: Expression, body: Expression) extends Expression {
  override def evaluate(): Int | String | scala.Boolean = {
    while (cond.evaluate().asInstanceOf[scala.Boolean]) {
      body.evaluate()
    }
    return 0 // what to return from here?
  }

  // TODO: implement this
  override def abstract_evaluate(): Interval = Interval(0, true, 0, true)

  override def toString(): String =
    "While(" + cond.toString() + ") {" + body.toString() + "}"
}
