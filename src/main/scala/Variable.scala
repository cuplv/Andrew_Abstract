case class Variable[T](identifier: String) extends Expression {
  /* override def evaluate[T <: Domain](state: State[T]): T =
    state.variables.get(identifier).get */
  /* override def abstract_evaluate(state: State): Interval | TwoDInterval =
    state.variables.get(identifier).get.abstract_evaluate(state)
   */
  // choose not to statically type variable types
  // assignment will be the way to update scope's intervals

  def assign[T](using
      evaluator: Evaluator[T]
  )(newValue: Expression, state: State[T]): Unit =
    if (state.variables.contains(identifier)) then
      state.variables(identifier) = newValue.evaluate[T](state)
      // every time a variable is updated, the state will pick up the new interval
      /* state.intervals(identifier) =
        (state.intervals(identifier), newValue.abstract_evaluate(state)) match {
          case (o: Interval, n: Interval)         => o union n
          case (o: TwoDInterval, n: TwoDInterval) => o union n
          case _ =>
            throw new Exception(
              "Invalid interval type"
            ) // should be impossible to reach
        } */
    else {
      state.variables += (identifier -> newValue.evaluate[T](state))
      // state.intervals += (identifier -> newValue.abstract_evaluate(state))
    }

    // TODO work on making an addAssign method that will work for all types
  def addAssign[T <: Operable | Int](value: T, state: State[T]): State[T] =
    if (state.variables.contains(identifier)) then
      state.variables(identifier) =
        (state.variables(identifier) + value).asInstanceOf[T]
    else throw new Exception("Variable not intialized")
    return state

  override def toString: String = "Variable(" + identifier.toString + ")";
}
