case class Multiplication(a: Expression, b: Expression) extends Expression:
  override def evaluate(): Int|Point =
    val aval = a.evaluate()
    val bval = b.evaluate()
    (aval, bval) match{
        case (aval:Int, bval:Int) => aval*bval
        case (aval: Point, bval: Int) => (a.asInstanceOf[Point]*bval).evaluate()
        case _ => throw new Exception(
        "Cannot multipy " + aval.toString() + " and " + bval.toString()
      )
    }
  override def abstract_evaluate(): Interval|TwoDInterval = (a.abstract_evaluate(), b.abstract_evaluate()) match {
    case (a: Interval, b:Interval) => a*b 
    case (a: TwoDInterval, b:Interval) => a*b
    case _ => throw new Exception(
        "Invalid multiplication: " + this.toString()
    )
    
  }
  override def toString: String = "Multiplication(" + a.toString() + ", " + b.toString() + ")"