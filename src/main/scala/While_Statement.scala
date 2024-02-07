class While_Statement(cond:Expression, body:Expression) extends Expression{
    override
    def evaluate(): Int | String | scala.Boolean = {
        while (cond.evaluate().asInstanceOf[scala.Boolean]) {
            body.evaluate()
        }
        return 0 //what to return from here?
    }

    override def toString(): String = "While(" + cond.toString() + ") {" + body.toString() + "}"
}