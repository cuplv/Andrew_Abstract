import scala.quoted.Expr
trait Expression:
  def evaluate(): Int | String | scala.Boolean
end Expression
