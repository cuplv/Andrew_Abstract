//The idea is that every domain will have its own implementation of whatever operators/expression types
//that way to add new domains, you just add it as a domain and implement the operators, no need to mess with the expressions
/* trait Domain:
  def +(other: Domain): Domain
  def -(other: Domain): Domain
  def *(other: Domain): Domain
  def rand(max: Int): Domain
  def <(other: Domain): Domain
  def >(other: Domain): Domain
 */

trait Domain;

trait Operable:
  def +(other: Operable): Operable
  def -(other: Operable): Operable
  def *(other: Operable): Operable

trait Evaluator[T] {
  def evaluate(expr: Expression, state: State[T]): T
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
