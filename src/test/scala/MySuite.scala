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
    println("Expression: "+ test.toString)
    println("Interval Result: "+test.abstract_evaluate())
    println("Concrete Result: "+test.evaluate())
    println("--------------------")
    val obtained = test.abstract_evaluate()
    val expected = Interval(2, true, 2, true)
    assertEquals(obtained, expected)
  }

  test("Abstract analysis of Rand()") {
    val test = Rand();
    println("Expression: "+ test.toString)
    println("Interval Result: "+test.abstract_evaluate())
    println("Concrete Result: "+test.evaluate())
    println("--------------------")

    val obtained = test.abstract_evaluate()
    val expected = Interval(0, true, Infinity(false), false)
    assertEquals(obtained, expected)
  }

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
  }

  test("Abstract analysis of 2*(1+Rand())") {
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

  test("Abstract analysis of ((Rand(),1)*4)-(-5,10)") {
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

}
