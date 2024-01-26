case class Boolean(value: scala.Boolean) extends Expression {
    override def evaluate(): Any = value

  override def toString: String = "Boolean("+value.toString+")";
}