case class Point(x: Expression | Int, y: Expression | Int) extends Expression:
  override def evaluate(state: State): Point = (x, y) match {
    case (x: Expression, y: Expression) =>
      (x.evaluate(state), y.evaluate(state)) match {
        case (x: Int, y: Int) => Point(x, y)
        case _ =>
          throw new Exception(
            "Invalid type for Point: " + this.toString()
          )
      }
    case (x: Int, y: Int) => Point(x, y)
    case _ =>
      throw new Exception(
        "Invalid form of Point: " + this.toString()
      )
  }
  override def abstract_evaluate(state: State): TwoDInterval = (x, y) match {
    case (x: Expression, y: Expression) =>
      (x.abstract_evaluate(state), y.abstract_evaluate(state)) match {
        case (x: Interval, y: Interval) => TwoDInterval(x, y)
        case _ =>
          throw new Exception(
            "Invalid form of Interval: " + x.toString() + ", " + y.toString()
          )
      }
    case (x: Int, y: Int) =>
      TwoDInterval(
        Interval(x, true, x, true),
        Interval(y, true, y, true)
      ) // this is basically a concrete interval, hopefully would never get called
    case _ =>
      throw new Exception(
        "Invalid form of Point: " + this.toString()
      )
  }

  override def toString: String =
    "Point(" + x.toString() + ", " + y.toString() + ")"

  def +(other: Point): Point = (x, y, other.x, other.y) match {
    case (x: Expression, y: Expression, ox: Expression, oy: Expression) =>
      Point(Addition(x, ox), Addition(y, oy))
    case (x: Int, y: Int, ox: Int, oy: Int) => Point(x + ox, y + oy)
    case _ =>
      throw new Exception(
        "Cannot perfom operation(+) on this form of Point: " + this.toString()
      )
  }

  def -(other: Point): Point = (x, y, other.x, other.y) match {
    case (x: Expression, y: Expression, ox: Expression, oy: Expression) =>
      Point(Addition(x, ox, sub = true), Addition(y, oy, sub = true))
    case (x: Int, y: Int, ox: Int, oy: Int) => Point(x - ox, y - oy)
    case _ =>
      throw new Exception(
        "Cannot perfom operation(-) on this form of Point: " + this.toString()
      )
  }

  def *(num: Int): Point = (x, y) match {
    case (x: Expression, y: Expression) =>
      Point(Multiplication(x, Number(num)), Multiplication(y, Number(num)))
    case _ =>
      throw new Exception(
        "Cannot perfom operation(*) on this form of Point: " + this.toString()
      )
  }
