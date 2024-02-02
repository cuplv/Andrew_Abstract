case class Bool(value: scala.Boolean) extends Expression {
  override def evaluate(): scala.Boolean = value

  override def toString: String = "Boolean("+value.toString+")";
}