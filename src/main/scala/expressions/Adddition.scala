case class Addition(a: Expression, b: Expression, sub: Boolean = false)
    extends Expression:
  /* override def evaluate[T <: Domain](state: State[T]): T =

    val aval = a.evaluate[T](state)
    val bval = b.evaluate[T](state)

    (aval, bval, sub) match {
      case (a: T, b: T, false) => (a + b).asInstanceOf[T]
      case (a: T, b: T, true)  => (a - b).asInstanceOf[T]
      // case (a: String, b: String, _)   => a + b
      // case (a: Boolean, b: Boolean, _) => a || b
      case _ =>
        throw new Exception(
          "Cannot add " + a.toString() + " and " + b.toString()
        )
    }
    /* if aval.isInstanceOf[Int] && bval.isInstanceOf[Int] then
      if sub then
        aval.asInstanceOf[Int] - bval.asInstanceOf[Int]
      else
        aval.asInstanceOf[Int] + bval.asInstanceOf[Int]

    else if aval.isInstanceOf[String] && bval.isInstanceOf[String] then
      aval.asInstanceOf[String] + bval.asInstanceOf[String]
    else if aval.isInstanceOf[scala.Boolean] && bval.isInstanceOf[scala.Boolean] then
      aval.asInstanceOf[scala.Boolean] || bval.asInstanceOf[scala.Boolean]
    else
      throw new Exception(
        "Cannot add " + aval.toString() + " and " + bval.toString()
      ) */

  end evaluate */

  /* override def abstract_evaluate(state: State): Interval | TwoDInterval =
    val aval = a.abstract_evaluate(state)
    val bval = b.abstract_evaluate(state)
    (aval, bval) match {
      case (a: Interval, b: Interval)         => if sub then a - b else a + b
      case (a: TwoDInterval, b: TwoDInterval) => if sub then a - b else a + b
      case _ =>
        throw new Exception(
          "Cannot add " + aval.toString() + " and " + bval.toString()
        )
    }
   */
  override def toString: String = {
    "Addition(" + a.toString() + ", " + b.toString() + (if !sub then ")"
                                                        else
                                                          (", sub: " + sub
                                                            .toString() + ")"
                                                        ))
  }
