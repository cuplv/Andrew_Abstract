trait Expression:
  def evaluate(state: State): Int | String | scala.Boolean | Point
  def abstract_evaluate(state: State): Interval | TwoDInterval
end Expression

case class Infinity(neg: Boolean):
  override def toString(): String = if neg then "-Infinity" else "Infinity"
  def *(num: Int): Infinity = if num < 0 then Infinity(!neg) else this
  def <(num: Int): Boolean = if neg then true else false
  def >(num: Int): Boolean = if neg then false else true
  def >(num: Infinity): Boolean = if neg then false else true
  def <(num: Infinity): Boolean = if neg then true else false
  def ==(num: Infinity): Boolean = neg == num.neg
  def ==(num: Int): Boolean = false

case class Interval(
    low: Int | Infinity,
    lowInc: Boolean,
    high: Int | Infinity,
    highInc: Boolean
):
  override def toString(): String =
    (if lowInc then "[" else "(") + low.toString() + ", " + high
      .toString() + (if highInc then "]" else ")")

  def *(num: Int): Interval =
    if (num == 0) {
      throw new Exception("Multiplying Interval by 0 is not allowed")
    }
    Interval(
      high match {
        case h: Int      => h * num
        case h: Infinity => h * (num / (num.abs))
      },
      highInc,
      low match {
        case l: Int      => l * num
        case l: Infinity => l * (num / (num.abs))
      },
      lowInc
    )

  def *(other: Interval): Interval =
    var low = this.low
    var high = this.high
    var lowInc = this.lowInc
    var highInc = this.highInc
    // lower bound
    if (this.low.isInstanceOf[Infinity] || other.low.isInstanceOf[Infinity]) {
      low = Infinity(true)
      lowInc = false
    } else {
      low = this.low.asInstanceOf[Int] * other.low.asInstanceOf[Int]
      lowInc = this.lowInc && other.lowInc
    }

    // upper bound
    if (this.high.isInstanceOf[Infinity] || other.high.isInstanceOf[Infinity]) {
      high = Infinity(false)
      highInc = false
    } else {
      high = this.high.asInstanceOf[Int] * other.high.asInstanceOf[Int]
      highInc = this.highInc && other.highInc
    }

    Interval(low, lowInc, high, highInc)

  def union(other: Interval): Interval =
    var min = this.low
    var max = this.high
    var minInc, maxInc = true
    if (this.low.isInstanceOf[Infinity] || other.low.isInstanceOf[Infinity])
    then
      min = Infinity(true)
      minInc = false
    else
      if (this.low.asInstanceOf[Int] < other.low.asInstanceOf[Int])
      then
        min = this.low
        minInc = this.lowInc
      else if (this.low.asInstanceOf[Int] > other.low.asInstanceOf[Int])
        min = other.low
        minInc = other.lowInc
      else
        min = this.low
        minInc = this.lowInc && other.lowInc
      end if
    end if

    if (this.high.isInstanceOf[Infinity] || other.high.isInstanceOf[Infinity])
    then
      max = Infinity(false)
      maxInc = false
    else
      if (this.high.asInstanceOf[Int] > other.high.asInstanceOf[Int])
      then
        max = this.high
        maxInc = this.highInc
      else if (this.high.asInstanceOf[Int] < other.high.asInstanceOf[Int])
        max = other.high
        maxInc = other.highInc
      else
        max = this.high
        maxInc = this.highInc && other.highInc
      end if
    end if

    Interval(min, minInc, max, maxInc)

  def +(other: Interval): Interval =
    var min = this.low
    var max = this.high
    var minInc, maxInc = true
    if (this.low.isInstanceOf[Infinity] || other.low.isInstanceOf[Infinity])
    then
      min = Infinity(true)
      minInc = false
    else
      min = this.low.asInstanceOf[Int] + other.low.asInstanceOf[Int]
      minInc = this.lowInc && other.lowInc
    end if

    if (this.high.isInstanceOf[Infinity] || other.high.isInstanceOf[Infinity])
    then
      max = Infinity(false)
      maxInc = false
    else
      max = this.high.asInstanceOf[Int] + other.high.asInstanceOf[Int]
      maxInc = this.highInc && other.highInc
    end if

    Interval(min, minInc, max, maxInc)
  def +(other: Int): Interval =
    this + Interval(other, true, other, true)
  def -(other: Interval): Interval =
    this + other * -1

class Bottom() extends Interval(0, false, 0, false):
  override def toString(): String = "\u22A5"
  override def *(num: Int): Interval = this
  override def *(other: Interval): Interval = this
  override def union(other: Interval): Interval = other
  override def +(other: Interval): Interval = this
  override def -(other: Interval): Interval = this

case class TwoDInterval(x: Interval, y: Interval):
  override def toString(): String =
    "(" + x.toString() + ", " + y.toString() + ")"
  def union(other: TwoDInterval): TwoDInterval =
    TwoDInterval(x union other.x, y union other.y)
  def +(other: TwoDInterval): TwoDInterval =
    TwoDInterval(x + other.x, y + other.y)
  def -(other: TwoDInterval): TwoDInterval =
    TwoDInterval(x - other.x, y - other.y)
  def *(other: Interval): TwoDInterval = TwoDInterval(x * other, y * other)
  def *(num: Int): TwoDInterval = TwoDInterval(x * num, y * num)

  // TODO allow input for rand so it can be within a range
case class Rand(max: Int = Int.MaxValue) extends Expression:
  override def evaluate(state: State): Int =
    scala.util.Random.nextInt(max)
  override def abstract_evaluate(state: State): Interval =
    Interval(
      0,
      true,
      if max == Int.MaxValue then Infinity(false) else max,
      if max == Int.MaxValue then false else true
    )
  override def toString(): String = "Rand()"

//think about potential uses/extensions

//could you integrate abstract analyiss to gps/mapping? perhaps making sure someone stays inside a certain area/range no matter what path they take?
//i feel like theres something with gps we could do here
//look into r3 and algabraic analysis as well

//models?
//2d visualizations for non-deterministic steps
//states in xy plane - give it increasing number of possible moves
//chance of if in certain interval new behaviour is introduced
//interaction of two states?

//should add in more operations/expressions like if, while. these should help with non determinism

// type IntOrInfinity = Int | Infinity
//extension to allow comparison operators on Int | Infinity
extension (x: Int | Infinity)
  def <(y: Int | Infinity): Boolean = (x, y) match {
    case (x: Infinity, y: Infinity) => x < y
    case (x: Int, y: Int)           => x < y
    case (x: Infinity, y: Int)      => x < y
    case (x: Int, y: Infinity)      => x < y
    case _                          => false // should be unreachable
  }
  def >(y: Int | Infinity): Boolean = (x, y) match {
    case (x: Infinity, y: Infinity) => x > y
    case (x: Int, y: Int)           => x > y
    case (x: Infinity, y: Int)      => x > y
    case (x: Int, y: Infinity)      => x > y
    case _                          => false // should be unreachable
  }
  def ==(y: Int | Infinity): Boolean = (x, y) match {
    case (x: Infinity, y: Infinity) => x == y
    case (x: Int, y: Int)           => x == y
    case (x: Infinity, y: Int)      => false
    case (x: Int, y: Infinity)      => false
    case _                          => false // should be unreachable
  }
  def <=(y: Int | Infinity): Boolean = (x, y) match {
    case (x: Infinity, y: Infinity) => x < y
    case (x: Int, y: Int)           => x <= y
    case (x: Infinity, y: Int)      => x < y
    case (x: Int, y: Infinity)      => x < y
    case _                          => false // should be unreachable
  }
  def >=(y: Int | Infinity): Boolean = (x, y) match {
    case (x: Infinity, y: Infinity) => x > y
    case (x: Int, y: Int)           => x >= y
    case (x: Infinity, y: Int)      => x > y
    case (x: Int, y: Infinity)      => x > y
    case _                          => false // should be unreachable
  }
