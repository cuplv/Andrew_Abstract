class Executable(body: State[_] => Unit) extends Expression {
  override def evaluate[T](using
      evaluator: Evaluator[T]
  )(state: State[T]): T =
    body(state)
    null.asInstanceOf[T]

  // return 0;
  /* override def abstract_evaluate(state: State[]): Interval =
    // somehow "run" the code abstractly, storing new intervals in the state
    Interval(0, true, 0, true) */

  override def toString(): String = "Executable"
}

//this class is only concrete increment(the += operator), does not rly work
/*case class IncrementVar(x: Variable[Int], c: Domain)
    extends Executable((state: State[Domain]) => {
      x.addAssign[Domain](c, state)
    }) {

  override def executeInterval(interval: Interval): Interval =
    (interval + c).asInstanceOf[Interval]
} */
