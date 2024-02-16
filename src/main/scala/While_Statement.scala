class While_Statement(
    cond: Expression,
    body: Expression | Function1[State, Unit]
) extends Expression {
  override def evaluate(state: State): Int | String | scala.Boolean = {
    var num = 0
    while (cond.evaluate(state).asInstanceOf[scala.Boolean]) {
      body match {
        case b: Expression      => b.evaluate(state)
        case f: Function1[_, _] => f.asInstanceOf[Function1[State, Unit]](state)
      }
    }
    return num // returns the number of times it looped, for now
  }

  // TODO: implement this
  // This is where the actual beef of abstract evaluation will have to live

  override def abstract_evaluate(state: State): Interval =
    Interval(0, true, 0, true)

  override def toString(): String =
    "While(" + cond.toString() + ") {" + body.toString() + "}"
}
