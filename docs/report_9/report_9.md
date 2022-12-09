# Assignment 9

In this assignment, we performed mutation testing and analyzed the results in order to ensure the robustness of our tests and the overall code quality. First we had to fix some tests and the code related to them, so we could run the PIT library and generate the mutation report, because, as a pre-condition, all tests must pass before executing it.

## Mutation Score

In the configuration, we excluded the classes related to the GUI. The report and its main packages under test are as follows:

![](https://i.imgur.com/8vuYbvo.png)

---

![](https://i.imgur.com/T4EFEkX.png)

---

![](https://i.imgur.com/tUr9LSc.png)

---

![](https://i.imgur.com/Hom4wBg.png)


## Mutants Analysis

We found many instances where the conditional boundary mutant survived, this mutant changes the conditional operator to a boundary counterpart:

![](https://i.imgur.com/KT2goiO.png)

![](https://i.imgur.com/Spp59KW.png)


In some cases, the mutants survived to the replacement of the return value, which means that part of our tests could be testing some internal logic but were not checking the final return value, or did not explore all the return value possibilities.

![](https://i.imgur.com/JXOhrSf.png)


Other major part of mutants that survived are from negated conditionals. It replaces the conditionals for inverse ones, for instance, `!=` becomes `==`.

![](https://i.imgur.com/fLUCEVN.png)

![](https://i.imgur.com/YO0F7QU.png)


Also, a considerable amount of coverage was lost due to unreachable statements. That's the case of the `RelativeDate` class, where most of the code has no coverage, thus a high survivability compared with other parts of the source code.

Equivalent mutants are mutants that change some part of the code, but it still behaves as expected. They should be manually identified and ignored if possible.
This conditional boundary mutant found in the `CursorPositionCalculator` is equivalent and behaves as the original code. Replacing `<` for `<=` will still assign the integer 0:

![](https://i.imgur.com/LvClRWV.png)

The same equivalent style happens in the `FilterFactory` class, where independently of the operator being `>` or `>=` it maintains the same behaviour if there are contexts or not, because if the `ByContextFilter` receives an empty list it will always evaluate to true when being applied.

![](https://i.imgur.com/pPo8aEX.png)


## Increasing mutation score and tests developed

In the end, we obtained a total of 66% of mutation coverage. Although it is not very high, there are some reasons: Firstly, there are a significant amount of NO_COVERAGE entries, and simply covering those lines would not be easy:  

![](https://i.imgur.com/M7wA06N.png)  

In the above example, we can see that the RelativeDate Class is practically not covered. However, developing tests for these lines would require a lot of mocking, and since this class's usage is only tied with our developed tests, these tests would serve no real purpose.  

![](https://i.imgur.com/17x9pYz.png)  

Another obstacle that we faced, as explained in a previous assignment, is that there are a lot of private methods with and without coverage, and we have no easy way to develop a test for these methods, since we would need to either change the code or try our luck with reflection-based tests or try to test functions that indirectly call these methods.

![](https://i.imgur.com/91d7Hqc.png)

Overall, the tests created were to cover parts of the code that were reachable but were missing tests. We have also added tests to handle some mutants such as the conditional boundary mutants and thus increase our Mutation Coverage by only 5% as seen in the data above.

