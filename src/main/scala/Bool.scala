case class Bool(value: scala.Boolean) extends Expression {
  override def evaluate(): scala.Boolean = value
  override def abstract_evaluate(): Interval = 
    val as_int = if value then 1 else 0
    Interval(as_int, true, as_int, true)

  override def toString: String = "Boolean("+value.toString+")";
}