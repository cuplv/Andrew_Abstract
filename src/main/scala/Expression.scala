import scala.quoted.Expr
trait Expression:
  def evaluate(): Int | String | scala.Boolean
  def abstract_evaluate(): Interval
end Expression


case class Infinity(neg: Boolean):
  override def toString(): String = if neg then "-Infinity" else "Infinity"
  def *(num:Int): Infinity = if num <0 then Infinity(!neg) else this

case class Interval(low: Int|Infinity, lowInc: Boolean, high: Int|Infinity, highInc: Boolean):
  override def toString(): String = (if lowInc then "[" else "(") + low.toString() + ", " + high.toString() + (if highInc then "]" else ")")
  
  def *(num:Int): Interval =
    if num <0 then
      Interval(high match{
        case h: Int => h* -1
        case h: Infinity => h * -1
      }, highInc, low match{
        case l: Int => l* -1
        case l: Infinity => l * -1
      }, lowInc)
    else
      this
  
  def +(other: Interval): Interval =
    var min = this.low
    var max = this.high
    var minInc, maxInc = true
    if(this.low.isInstanceOf[Infinity] || other.low.isInstanceOf[Infinity]) then
      min = Infinity(true)
      minInc = false
    else
      min = this.low.asInstanceOf[Int] + other.low.asInstanceOf[Int]
      minInc = this.lowInc || other.lowInc
    end if

    if(this.high.isInstanceOf[Infinity] || other.high.isInstanceOf[Infinity]) then
      max = Infinity(false)
      maxInc = false
    else
      max = this.high.asInstanceOf[Int] + other.high.asInstanceOf[Int]
      maxInc = this.highInc || other.highInc
    end if

    Interval(min, minInc, max, maxInc)

  def -(other: Interval): Interval =
    this + other * -1

case class Rand() extends Expression:
  override def evaluate(): Int = scala.util.Random.nextInt(Int.MaxValue)
  override def abstract_evaluate(): Interval = Interval(0, true, Infinity(false), false)
  override def toString(): String = "Rand()"

//think about potential uses/extensions

//could you integrate abstract analyiss to gps/mapping? perhaps making sure someone stays inside a certain area/range no matter what path they take?
//i feel like theres something with gps we could do here
//look into r3 and algabraic analysis as well