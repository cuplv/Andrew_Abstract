class Executable(body: Function1[State, Unit]) extends Expression {
  override def evaluate(state: State): Int | String | scala.Boolean =
    body(state)
    return 0;
  override def abstract_evaluate(state: State): Interval =
    // somehow "run" the code abstractly, storing new intervals in the state
    Interval(0, true, 0, true)

  def executeInterval(interval: Interval): Interval = {
    return interval
  }
  override def toString(): String = "Executable"
}

case class IncrementVar(x: Variable, c: Int)
    extends Executable((state: State) => {
      x.addAssign(Number(c), state)
    }) {

  override def executeInterval(interval: Interval): Interval = interval + c
}
