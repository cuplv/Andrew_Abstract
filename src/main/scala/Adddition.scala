class Addition(var a: Expression, var b: Expression, val sub: Boolean = false) extends Expression:
  override def evaluate(): Int | String | scala.Boolean | Point=

    if(a.isInstanceOf[Point]&& b.isInstanceOf[Point]){
      if(sub) then return (a.asInstanceOf[Point]-b.asInstanceOf[Point]).evaluate()
      else return (a.asInstanceOf[Point]+b.asInstanceOf[Point]).evaluate()
    }
    val aval = a.evaluate()
    val bval = b.evaluate()
    
    (aval, bval, sub) match{
      case (a:Int, b:Int, false) => a+b
      case (a:Int, b:Int, true) => a-b
      case (a:String, b:String, _) => a+b
      case (a:Boolean, b:Boolean, _) => a || b
      case (a:Point, b:Point, true) => (a-b).evaluate()
      case (a:Point, b:Point, false) => (a+b).evaluate()
      case _ => throw new Exception(
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

  end evaluate

  override def abstract_evaluate(): Interval|TwoDInterval= 
    val aval = a.abstract_evaluate()
    val bval = b.abstract_evaluate()
    (aval, bval) match{
      case (a: Interval, b:Interval) => if sub then a-b else a+b
      case (a: TwoDInterval, b:TwoDInterval) => if sub then a-b else a+b
      case _ => throw new Exception(
        "Cannot add " + aval.toString() + " and " + bval.toString()
      )
    }

  override def toString: String = {
    "Addition(" + a.toString() + ", " + b.toString() + (if !sub then ")" else (", sub: " + sub.toString() + ")"))
  }