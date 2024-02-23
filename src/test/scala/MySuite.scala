// For more information on writing tests, see
// https://scalameta.org/munit/docs/getting-started.html
class MySuite extends munit.FunSuite {
  /* test("1+1") {
    val test = Addition(Number(1), Number(1));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = 2
    assertEquals(obtained, expected)
  }

  test("1+1+1") {
    val test = Addition(Addition(Number(1), Number(1)), Number(1));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = 3
    assertEquals(obtained, expected)
  }

  test("Hello+World") {
    val test = Addition(MyString("Hello, "), MyString("World"));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = "Hello, World"
    assertEquals(obtained, expected)
  }

  test("true+false") {
    val test = Addition(Bool(true), Bool(false));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = true
    assertEquals(obtained, expected)
  }

  test("If(true){1}else{0}") {
    val test = If_Statement(Bool(true), Number(1), Number(0));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = 1
    assertEquals(obtained, expected)
  } */

  // Addition abstract analysis tests
  /* test("Abstract analysis of 1+1") {
    val test = Addition(Number(1), Number(1));
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = Interval(2, true, 2, true)
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of Rand()") {
    val test = Rand();
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")

    val obtained = test.abstract_evaluate()
    val expected = Interval(0, true, Infinity(false), false)
    assertEquals(obtained, expected)
  } */
  /*
  test("Abstract analysis of Rand()+1") {
    val test = Addition(Rand(), Number(1));
    println("Expression: "+ test.toString)
    println("Interval Result: "+test.abstract_evaluate())
    println("Concrete Result: "+test.evaluate())
    println("--------------------")

    val obtained = test.abstract_evaluate()
    val expected = Interval(1, true, Infinity(false), false)
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of Rand()+Rand()") {
    val test = Addition(Rand(), Rand());
    println("Expression: "+ test.toString)
    println("Interval Result: "+test.abstract_evaluate())
    println("Concrete Result: "+test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = Interval(0, true, Infinity(false), false)
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of Rand()-Rand()") {
    val test = Addition(Rand(), Rand(),sub = true);
    println("Expression: "+ test.toString)
    println("Interval Result: "+test.abstract_evaluate())
    println("Concrete Result: "+test.evaluate())
    val obtained = test.abstract_evaluate()
    val expected = Interval(Infinity(true), false, Infinity(false), false)
    assertEquals(obtained, expected)
  } */

  // multiplication tests
  /* test("1*1") {
    val test = Multiplication(Number(1), Number(1));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = 1
    assertEquals(obtained, expected)
  }

  test("1*2") {
    val test = Multiplication(Number(1), Number(2));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = 2
    assertEquals(obtained, expected)
  }

  test("-1*2") {
    val test = Multiplication(Number(-1), Number(2));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = -2
    assertEquals(obtained, expected)
  } */

  // abstract tests of multiplication
  /* test("Abstract analysis of 1*1") {
    val test = Multiplication(Number(1), Number(1));
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = Interval(1, true, 1, true)
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of 2*2") {
    val test = Multiplication(Number(2), Number(2));
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = Interval(4, true, 4, true)
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of 2*Rand()") {
    val test = Multiplication(Number(2), Rand());
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = Interval(0, true, Infinity(false), false)
    assertEquals(obtained, expected)
  }*/

  /* test("Abstract analysis of 2*(1+Rand())") {
    val test = Multiplication(Number(2), Addition(Number(1), Rand()));
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = Interval(2, true, Infinity(false), false)
    assertEquals(obtained, expected)
  } */

  // interval multiplication tests
  /*  test("Interval multiplication of (1,2] * [3,4]") {
    val test =
      Interval(1, false, 2, true) * Interval(3, true, 4, true);
    println(test.toString)
    val obtained = test
    val expected = Interval(3, false, 8, true)
    assertEquals(obtained, expected)
  }

  test("Interval multiplication of (1,Infinity) * [3,4]") {
    val test =
      Interval(1, false, Infinity(false), false) *
        Interval(3, true, 4, true);
    println(test.toString)
    val obtained = test
    val expected = Interval(3, false, Infinity(false), false)
    assertEquals(obtained, expected)
  } */

  // testing points
  /*  test("(1,1) + (1,1)") {
    val test =
      Addition(Point(Number(1), Number(1)), Point(Number(1), Number(1)));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = Point(Number(2), Number(2)).evaluate()
    assertEquals(obtained, expected)
  }

  test("(1,1) + (5,-10)") {
    val test = Addition(
      Point(Number(1), Number(1)),
      Point(Number(5), Number(-10))
    );
    println(test.toString)
    val obtained = test.evaluate()
    val expected = Point(Number(6), Number(-9)).evaluate()
    assertEquals(obtained, expected)
  }

  test("(1,1) * 1") {
    val test = Multiplication(Point(Number(1), Number(1)), Number(1));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = Point(Number(1), Number(1)).evaluate()
    assertEquals(obtained, expected)
  }

  test("(2,1) * 3") {
    val test = Multiplication(Point(Number(2), Number(1)), Number(3));
    println(test.toString)
    val obtained = test.evaluate()
    val expected = Point(Number(6), Number(3)).evaluate()
    assertEquals(obtained, expected)
  } */

  // abstract analysis of points tests
  /* test("Abstract analysis of (1,1)") {
    val test = Point(Number(1), Number(1))
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = TwoDInterval(
      Interval(1, true, 1, true),
      Interval(1, true, 1, true)
    )
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of (1,Rand())") {
    val test = Point(Number(1), Rand())
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = TwoDInterval(
      Interval(1, true, 1, true),
      Interval(0, true, Infinity(false), false)
    )
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of (1,1) + (1,1)") {
    val test =
      Addition(Point(Number(1), Number(1)), Point(Number(1), Number(1)));
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = TwoDInterval(
      Interval(2, true, 2, true),
      Interval(2, true, 2, true)
    )
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of (1,Rand()) + (1,1)") {
    val test =
      Addition(Point(Number(1), Rand()), Point(Number(1), Number(1)));
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = TwoDInterval(
      Interval(2, true, 2, true),
      Interval(1, true, Infinity(false), false)
    )
    assertEquals(obtained, expected)
  }

  test("Abstract anaylsis of (1,1) * 2") {
    val test =
      Multiplication(Point(Number(1), Number(1)), Number(2));
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = TwoDInterval(
      Interval(2, true, 2, true),
      Interval(2, true, 2, true)
    )
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of (1,Rand()) * 2") {
    val test =
      Multiplication(Point(Number(1), Rand()), Number(2));
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = TwoDInterval(
      Interval(2, true, 2, true),
      Interval(0, true, Infinity(false), false)
    )
    assertEquals(obtained, expected)
  }
   */

  /* test("Abstract analysis of ((Rand(),1)*4)-(-5,10)") {
    val test = Addition(
      Multiplication(Point(Rand(), Number(1)), Number(4)),
      Point(Number(-5), Number(10)),
      sub = true
    )
    println("Expression: " + test.toString)
    println("Interval Result: " + test.abstract_evaluate())
    println("Concrete Result: " + test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = TwoDInterval(
      Interval(5, true, Infinity(false), false), // x
      Interval(-6, true, -6, true) // y
    )
    assertEquals(obtained, expected)
  } */

  // Variable Assignment tests
  /* test("Variable assignment: x=1") {
    var state = State()
    var test = Variable("x")
    test.assign(Number(1), state)
    val obtained = test.evaluate(state)
    val expected = 1
    assertEquals(obtained, expected)

  }

  test("Variable assignment: x=2") {
    var state = State()
    var test = Variable("x")
    test.assign(Number(1), state)
    test.assign(Number(2), state)
    val obtained = test.evaluate(state)
    val expected = 2
    assertEquals(obtained, expected)
  }

  test("Variable assignment: x=1+1") {
    var state = State()
    var test = Variable("x")
    test.assign(Addition(Number(1), Number(1)), state)
    val obtained = test.evaluate(state)
    val expected = 2
    assertEquals(obtained, expected)
  }

  test("Variable assignment: x=1+1, y=x") {
    var state = State()
    var test = Variable("x")
    test.assign(Addition(Number(1), Number(1)), state)
    var test2 = Variable("y")
    test2.assign(Variable("x"), state)
    val obtained = test2.evaluate(state)
    val expected = 2
    assertEquals(obtained, expected)
  } */

  /*  test("x=1; x=x+1") {
    var state = State()
    var test = Variable("x")
    test.assign(Number(1), state)
    test.addAssign(Number(1), state)
    val obtained = test.evaluate(state)
    val expected = 2
    assertEquals(obtained, expected)
  } */

  /* // Variable abstract analysis tests
  test("x=1") {
    var state = State()
    var test = Variable("x")
    test.assign(Number(1), state)
    val obtained = test.abstract_evaluate(state)
    val expected = Interval(1, true, 1, true)
    assertEquals(obtained, expected)
  }

  test("x=Rand()") {
    var state = State()
    var test = Variable("x")
    test.assign(Rand(), state)
    val obtained = test.abstract_evaluate(state)
    val expected = Interval(0, true, Infinity(false), false)
    assertEquals(obtained, expected)
  }

  test("x=5; x=x+5") {
    var state = State()
    var test = Variable("x")
    test.assign(Number(5), state)
    test.assign(Addition(Variable("x"), Number(5)), state)
    val obtained = test.abstract_evaluate(state)
    val expected = Interval(10, true, 10, true)
    assertEquals(obtained, expected)
  } */

  // While statement tests
  /* test("While(x<1){x+=1}") {
    var state = State()
    Variable("x").assign(Number(0), state)
    val body = (s: State) => {
      // println("I got called!")
      Variable("x").addAssign(Number(1), s)
    }
    var test = While_Statement(
      LessThan(Variable("x"), Number(1)),
      body
    )
    val obtained = Variable("x").evaluate(state)
    val expected = 1
    assertEquals(obtained, expected)
  } */

  // Abstract while statement tests

  test("x=Rand(4);While(x<3){x+=2}") { // normal loop
    var state = State()
    Variable("x").assign(Rand(4), state)
    var test = While_Statement(
      Conditional(Variable("x"), LessThan, Number(3)),
      IncrementVar(Variable("x"), 2)
    )

    val obtained = test.abstract_evaluate(state)
    val expected = Interval(3, true, Infinity(false), false)

    assertEquals(obtained, expected)
  }

  test("x=Rand(2);While(x>2){x+=-2}") { // never enters loop
    var state = State()
    Variable("x").assign(Rand(2), state)
    var test = While_Statement(
      Conditional(Variable("x"), GreaterThan, Number(2)),
      IncrementVar(Variable("x"), -2)
    )

    val obtained = test.abstract_evaluate(state)
    val expected = Interval(0, true, 2, true)

    assertEquals(obtained, expected)
  }

  /* test("x=Rand(2)-1;While(x<2){x+=1}") {
    var state = State()
    Variable("x").assign(Addition(Rand(2), Number(-1)), state)
    var test = While_Statement(
      Conditional(Variable("x"), LessThan, Number(2)),
      IncrementVar(Variable("x"), 1)
    )

    val obtained = test.abstract_evaluate(state)
    val expected = Interval(2, true, Infinity(false), false)

    assertEquals(obtained, expected)
  } */
  //

  test("x=Rand(3)+1;While(x>0){x+=1}") { // try to test an infinite loop

    var state = State()
    Variable("x").assign(Addition(Rand(3), Number(1)), state)
    var test = While_Statement(
      Conditional(Variable("x"), GreaterThan, Number(0)),
      IncrementVar(Variable("x"), 1)
    )

    val obtained = test.abstract_evaluate(state)
    val expected =
      Interval(0, false, 0, false) // sequivalent to bottom

    assertEquals(obtained, expected)
  }

  // Interval union tests
  /* test("Interval union of (1,2] and [3,4]") {
    val test = Interval(1, false, 2, true) union Interval(3, true, 4, true)
    println(test.toString)
    val obtained = test
    val expected = Interval(1, false, 4, true)
    assertEquals(obtained, expected)
  }

  test("Interval union of (1,Infinity) and [3,4]") {
    val test =
      Interval(1, false, Infinity(false), false) union Interval(
        3,
        true,
        4,
        true
      )
    println(test.toString)
    val obtained = test
    val expected = Interval(1, false, Infinity(false), false)
    assertEquals(obtained, expected)
  } */
}

//start every time with x+y<=c1, x<=c2, y<=c3
//after these intials, every time there is a new operation can adjust these inequalities as needed
//the extra sides come from ?? ... magic?
