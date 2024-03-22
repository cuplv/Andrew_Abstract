import scala.util.Random

/* case class Concrete(value: Int) extends Domain:
  def +(other: Domain): Domain = other match {
    case other: Concrete => Concrete(other.value + value)
    case _ =>
      throw new Exception("Cannot add Concrete int and " + other.toString())
  }
  def -(other: Domain): Domain = other match {
    case other: Concrete => Concrete(value - other.value)
    case _ =>
      throw new Exception(
        "Cannot subtract Concrete int and " + other.toString()
      )
  }
  def *(other: Domain): Domain = other match {
    case other: Concrete => Concrete(other.value * value)
    case _ =>
      throw new Exception(
        "Cannot multiply Concrete int and " + other.toString()
      )
  }
  def <(other: Domain): Domain = other match {
    case other: Concrete => Concrete(if (value < other.value) 1 else 0)
    case _ =>
      throw new Exception("Cannot compare Concrete int and " + other.toString())
  }
  def >(other: Domain): Domain = other match {
    case other: Concrete => Concrete(if (value > other.value) 1 else 0)
    case _ =>
      throw new Exception("Cannot compare Concrete int and " + other.toString())
  }

  def rand(max: Int): Domain = Concrete(Random.nextInt(max))
  override def toString(): String = "Int" */

implicit object IntEvaluator extends Evaluator[Int] {
  def evaluate(expr: Expression, state: State[Int]): Int = expr match {
    case Addition(left, right, sub) =>
      if (sub) left.evaluate[Int](state) - right.evaluate[Int](state)
      else
        left.evaluate[Int](state) + right.evaluate[Int](state)
    case Rand(max)   => Random.nextInt(max)
    case Number(num) => num
    case Conditional(a, comp, b) =>
      if comp match {
          case LessThan =>
            (a.evaluate[Int](state) < b.evaluate[Int](state))
          case GreaterThan =>
            (a.evaluate[Int](state) > b.evaluate[Int](state))
          case Equals =>
            (a.evaluate[Int](state) == b.evaluate[Int](state))

        }
      then 1
      else 0
    case Variable(identifier) => state.variables(identifier)
    case _                    => null.asInstanceOf[Int]
  }

  def execute(stmt: Statement, state: State[Int]): State[Int] = stmt match {
    case Executable(body) => body(state).asInstanceOf[State[Int]]
    case stmt: While_Statement => {
      while (stmt.evalConditional[Int](state) != 0) {
        stmt.execBody[Int](state)
      }
      state
    }
    case _ => null
  }
}

/* implicit object BooleanEvaluator extends Evaluator[Boolean] {
  def evaluate(expr: Expression, state: State[Boolean]): Boolean = expr match {
    case Conditional(a, comp, b) =>
      if comp match {
          case LessThan =>
            (a.evaluate[Boolean](state) < b.evaluate[Boolean](state))
          case GreaterThan =>
            (a.evaluate[Boolean](state) > b.evaluate[Boolean](state))
          case Equals =>
            (a.evaluate[Boolean](state) == b.evaluate[Boolean](state))

        }
      then true
      else false
    case _ => null.asInstanceOf[Boolean]
  }
}
 */
