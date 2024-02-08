import scala.quoted.Expr
trait Expression:
  def evaluate(): Int | String | scala.Boolean | Point
  def abstract_evaluate(): Interval | TwoDInterval
end Expression


case class Infinity(neg: Boolean):
  override def toString(): String = if neg then "-Infinity" else "Infinity"
  def *(num:Int): Infinity = if num <0 then Infinity(!neg) else this

case class Interval(low: Int|Infinity, lowInc: Boolean, high: Int|Infinity, highInc: Boolean):
  override def toString(): String = (if lowInc then "[" else "(") + low.toString() + ", " + high.toString() + (if highInc then "]" else ")")
  
  def *(num:Int): Interval = 
    if(num ==0) {
      throw new Exception("Multiplying Interval by 0 is not allowed")
    }
      Interval(high match{
        case h: Int => h* num
        case h: Infinity => h * (num/(num.abs))
      }, highInc, low match{
        case l: Int => l* num
        case l: Infinity => l * (num/(num.abs))
      }, lowInc)
  
  def *(other: Interval): Interval = 
    var low = this.low
    var high = this.high
    var lowInc = this.lowInc
    var highInc = this.highInc
    //lower bound
    if(this.low.isInstanceOf[Infinity] || other.low.isInstanceOf[Infinity]){
      low = Infinity(true)
      lowInc = false
    } else {
      low = this.low.asInstanceOf[Int] * other.low.asInstanceOf[Int]
      lowInc = this.lowInc && other.lowInc
    }

    //upper bound
    if(this.high.isInstanceOf[Infinity] || other.high.isInstanceOf[Infinity]){
      high = Infinity(false)
      highInc = false
    } else {
      high = this.high.asInstanceOf[Int] * other.high.asInstanceOf[Int]
      highInc = this.highInc && other.highInc
    }

    Interval(low, lowInc, high, highInc)

  def +(other: Interval): Interval =
    var min = this.low
    var max = this.high
    var minInc, maxInc = true
    if(this.low.isInstanceOf[Infinity] || other.low.isInstanceOf[Infinity]) then
      min = Infinity(true)
      minInc = false
    else
      min = this.low.asInstanceOf[Int] + other.low.asInstanceOf[Int]
      minInc = this.lowInc && other.lowInc
    end if

    if(this.high.isInstanceOf[Infinity] || other.high.isInstanceOf[Infinity]) then
      max = Infinity(false)
      maxInc = false
    else
      max = this.high.asInstanceOf[Int] + other.high.asInstanceOf[Int]
      maxInc = this.highInc && other.highInc
    end if

    Interval(min, minInc, max, maxInc)

  def -(other: Interval): Interval =
    this + other * -1

case class TwoDInterval(x: Interval, y: Interval):
  override def toString(): String = "(" + x.toString() + ", " + y.toString() + ")"
  def +(other: TwoDInterval): TwoDInterval = TwoDInterval(x + other.x, y + other.y)
  def -(other: TwoDInterval): TwoDInterval = TwoDInterval(x - other.x, y - other.y)
  def *(other: Interval): TwoDInterval = TwoDInterval(x * other, y * other)
  def *(num: Int): TwoDInterval = TwoDInterval(x * num, y * num)



case class Rand() extends Expression:
  override def evaluate(): Int = scala.util.Random.nextInt(Int.MaxValue)
  override def abstract_evaluate(): Interval = Interval(0, true, Infinity(false), false)
  override def toString(): String = "Rand()"

//think about potential uses/extensions

//could you integrate abstract analyiss to gps/mapping? perhaps making sure someone stays inside a certain area/range no matter what path they take?
//i feel like theres something with gps we could do here
//look into r3 and algabraic analysis as well