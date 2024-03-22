trait Operable:
  def +(other: Operable): Operable
  def -(other: Operable): Operable
  def *(other: Operable): Operable

trait Evaluator[T] {
  def evaluate(expr: Expression, state: State[T]): T
  def execute(stmt: Statement, state: State[T]): State[T]
}
trait Expression:
  def evaluate[T](using evaluator: Evaluator[T])(state: State[T]): T =
    evaluator.evaluate(this, state)
  // def abstract_evaluate(state: State): Interval | TwoDInterval
end Expression

extension (x: Operable | Int)
  def +(y: Operable | Int): Operable | Int = (x, y) match {
    case (x: Int, y: Int)           => x + y
    case (x: Int, y: Operable)      => null
    case (x: Operable, y: Int)      => null
    case (x: Operable, y: Operable) => x + y
  }
