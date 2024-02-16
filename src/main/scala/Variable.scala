case class Variable(identifier: String) extends Expression {
  override def evaluate(state: State): Int | String | scala.Boolean | Point =
    state.variables.get(identifier).get.evaluate(state)
  override def abstract_evaluate(state: State): Interval | TwoDInterval =
    state.variables.get(identifier).get.abstract_evaluate(state)

  // choose not to statically type variable types
  // assignment will be the way to update scope's intervals

  def assign(newValue: Expression, state: State): Unit =
    if (state.variables.contains(identifier)) then
      state.variables(identifier) = newValue
      // every time a variable is updated, the state will pick up the new interval
      state.intervals(identifier) =
        (state.intervals(identifier), newValue.abstract_evaluate(state)) match {
          case (o: Interval, n: Interval)         => o union n
          case (o: TwoDInterval, n: TwoDInterval) => o union n
          case _ =>
            throw new Exception(
              "Invalid interval type"
            ) // should be impossible to reach
        }
    else {
      state.variables += (identifier -> newValue)
      state.intervals += (identifier -> newValue.abstract_evaluate(state))
    }

  def addAssign(value: Expression, state: State): Unit =
    if (state.variables.contains(identifier)) then
      state.variables(identifier) = Addition(state.variables(identifier), value)
      // every time a variable is updated, the state will pick up the new interval
      state.intervals(identifier) =
        (state.intervals(identifier), value.abstract_evaluate(state)) match {
          case (o: Interval, n: Interval)         => o union (o + n)
          case (o: TwoDInterval, n: TwoDInterval) => o union (o + n)
          case _ =>
            throw new Exception(
              "Invalid interval type"
            ) // should be impossible to reach
        }
    else throw new Exception("Variable not intialized")

  override def toString: String = "Variable(" + identifier.toString + ")";
}

//assignment currently can't handle holding itself, would need
//to evalute before storing but that defeats whole purpose of abstract evaluation
