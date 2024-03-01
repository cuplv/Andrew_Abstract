# Abstract Interpretation

This project is an attempt to learn static analysis and abstract interpretation from the ground up.

Andrew Fox

Spring 2024

## Usage

The best way to run this project so far is with `sbt test`

## Weekly Updates

Because why not keep a log this can show my timeline and yeah.

Week 1(main branch):

- Simple AST structure for addition of strings and ints

Week 2(logic branch):

- Added if statments, booleans
- Started adding in structure for variables and while loops

Week 3:

- Added in simple implementation for subtraction(with `Number` and `Point` only)
- added in abstract_evaluate nodes: these will report the interval of a given expression, for now only really functional with `Number` and `Point`
- added in `Rand`
- started thinking about direction of project and other cases that could happen
- thought about combination of the evaluate and abstract_evaluate
  - My thought process is same function but as its parameters its taking a bool that says if it is abstract or not, however this seems too simple
- Added in `Multiplication` which supports `Number`x`Number` and `Point`x`Number`
- Added in `Point` class, alongside support for abstraction(the `TwoDInterval` class)

Week 4(analysis branch):

- Added in `State` and functionality for it so I can track variables across multiple expression calls.
- Added in real functionality for while loops which now can run
- Started abstract interpretation for a loop(picking up each additional state per pass through the loop and merging them together)

Week 5(analysis branch):

- Learned process for simple interpretation of a loop
- Added in simple `abstract_evaluate()` for while loops
  - Added in `Bottom`, extension for `Int|Interval` comparisons
  - Added in `IncrementVar` as a sublass of `Executable`
- Created `Conditional` class to hold comparisons
- Changed `Rand` to have a max value to create more specific ranges

Week 6(combine branch):

- Worked in [dual_evaluate.worksheet.scala](src/main/scala/dual_evaluate.worksheet.sc) to try to find a awy to call the same evaluate function for any domain(concrete, abstract, etc.)
