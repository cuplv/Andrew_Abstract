# Abstract Interpretation

This project is an attempt to learn static analysis and abstract interpretation from the ground up.

Andrew Fox

Spring 2024

## Usage

The best way to run this project so far is with `sbt test`

## Weekly Updates?

Because why not keep a log this can show my timeline and yeah.

Week 1(main branch):

- Simple AST structure for addition of strings and ints

Week 2(logic branch):

- Added if statments, booleans
- Started adding in structure for variables and while loops

Week 3:

- Added in simple implementation for subtraction(with `Number`s only)
- added in abstract_evaluate nodes: these will report the interval of a given expression, for now only really functional with `Number`'s
- added in `Rand`
- started thinking about direction of project and other cases that could happen
- thought about combination of the evaluate and abstract_evaluate
  - My thought process is same function but as its parameters its taking a bool that says if it is abstract or not, however this seems too simple
