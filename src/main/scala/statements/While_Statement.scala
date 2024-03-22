import java.util.concurrent.locks.Condition
class While_Statement(
    cond: Conditional,
    body: Executable
) extends Statement {
  /* override def evaluate[T <: Domain](state: State[T]): T = {
    var num = 0
    while (cond.evaluate[T](state).asInstanceOf[Boolean]) {
      body.evaluate[T](state)
    }
    return Concrete(num).asInstanceOf[T]
    // returns the number of times it looped, for now
  } */

  /* override def evaluate[T](using evaluator: Evaluator[T])(state: State): T =
    while (cond.evaluate[T](state).asInstanceOf[Boolean]) {
      body.evaluate[T](state)
    } */

  // TODO: change this to invterval analysis through evaluate
  // This is where the actual beef of abstract evaluation will have to live
  /* def abstract_evaluate(state: State[Interval]): Interval =
    // this maps each position to the next possible ones
    val topologicalMap = Map(
      0 -> List(1),
      1 -> List(3, 2),
      2 -> List(0, 3),
      3 -> List() // No outgoing transitions from position 4
    )

    // maps each position to the intervals there
    var intervals = collection.mutable.Map(
      0 -> Interval(0, true, 0, true), // TODO redo this,
      1 -> Bottom(),
      2 -> Bottom(),
      3 -> Bottom()
    )
    // need some way to keep track of what was changed
    var oldIntervals = intervals.clone()

    // what happens at each position
    val transfers =
      Map(
        0 -> (() => {
          if (!intervals(2).isInstanceOf[Bottom]) {
            val together = intervals(0) union intervals(2)
            var high = together.high
            var low = together.low
            var highInc = together.highInc
            var lowInc = together.lowInc
            // for now just if it has increased, then assume it will always increase
            if (together.high > intervals(1).high) {
              high = Infinity(false)
              highInc = false
            }
            // same with decrease
            if (together.low < intervals(1).low) {
              low = Infinity(true)
              lowInc = false
            }
            intervals(0) = Interval(low, lowInc, high, highInc)
          }
        }), // merges 0 and 2 if possible(with fake wideing operator)
        1 -> (() => {
          intervals(1) =
            cond.splitInterval(intervals(0))._2 // 2 is the true branch
        }), // splits 0 at b, 1 holds true branch
        2 -> (() => {
          intervals(2) = body match {
            case inc: IncrementVar => inc.executeInterval(intervals(1))
            case _                 => intervals(1)
          }
        }), // call the executable with 1
        3 -> (() => {
          intervals(3) =
            cond.splitInterval(intervals(0))._1 // 1 is false branch

        }) // holds false branch
      )

    // queue to keep track of the positions
    var queue =
      scala.collection.mutable.Queue(1) // start the queue with position one
    var position = 0

    // makes everything look nicer
    println(Console.GREEN + "Start of Anaylsis" + Console.RESET)
    println("--------------------")
    println("Current position: " + position)
    println("Current intervals" + intervals.toString())
    println("Current queue: " + queue.toString())

    // loop to go through queue
    while (queue.nonEmpty) {
      position = queue.dequeue()

      transfers(position)()
      // at the end, if there was a change we add next places onto new queue
      if (!intervals.equals(oldIntervals)) {
        oldIntervals = intervals.clone()
        topologicalMap(position).foreach(queue.enqueue(_))
      }
      println("--------------------")
      println("Current position: " + position)
      println("Current intervals" + intervals.toString())
      println("Current queue: " + queue.toString())
    }

    // if position 3 is bottom, then we try to call 3 one time to see if there is any change
    if (position != 3 && intervals(3).isInstanceOf[Bottom]) {
      transfers(3)()
      println("--------------------")
      println("Current position: " + position)
      println("Current intervals" + intervals.toString())
      println("Current queue: " + queue.toString())
    }

    println("--------------------")
    println(Console.GREEN + "End of Analysis" + Console.RESET)
    println("--------------------")

    // return the interval at the end
    return intervals(3)
   */

  def evalConditional[T](using evaluator: Evaluator[T])(state: State[T]): T =
    evaluator.evaluate(cond, state)
  def execBody[T](using evaluator: Evaluator[T])(state: State[T]): State[T] =
    evaluator.execute(body, state)
  def getCond(): Conditional = cond
}

//if statement return two intervals (false, true)
