import scala.quoted.Expr
abstract class Expression:
  def evaluate(): Any
end Expression
