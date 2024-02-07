class If_Statement(cond: Expression,  tbranch:Expression, fbranch: Expression) extends Expression{
    override
    def evaluate(): Int | String | scala.Boolean = {
        if (cond.evaluate().asInstanceOf[scala.Boolean]) {
            tbranch.evaluate()
        } else {
            fbranch.evaluate()
        }
    }

    override def toString(): String = "If(" + cond.toString() + ") {" + tbranch.toString() + "} else {" + fbranch.toString()+"}"
}