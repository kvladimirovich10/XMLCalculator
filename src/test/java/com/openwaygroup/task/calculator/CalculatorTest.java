package com.openwaygroup.task.calculator;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class CalculatorTest {

    private Calculator calculator = new Calculator();
    private final Path input = Paths.get(Paths.get(".").toAbsolutePath() + "/src/test/resources/inputTest.xml");
    private final Path multyLevelInput = Paths.get(Paths.get(".").toAbsolutePath() + "/src/test/resources/multyLevelExpressionTest.xml");
    private final Path output = Paths.get(Paths.get(".").toAbsolutePath() + "/src/test/resources/outputTest.xml");

    @Test
    public void calculatorTest1() {

        calculator.calculate(input, output);
        LinkedList assertResult = new LinkedList<String>() {
            {
                add("Division by 0 occurred");
                add("90.0");
                add("14.378460686600219");
            }
        };

        Assert.assertEquals(assertResult.size(), calculator.resultList.size());
    }

    @Test
    public void calculatorTest2() {

        calculator.calculate(input, output);

        LinkedList resultAssertion = new LinkedList<String>() {
            {
                add("14.378460686600219");
                add("Division by 0 occurred");
                add("90.0");
            }
        };

        Iterator iterator = resultAssertion.listIterator();

        for (String result : calculator.resultList) {
            Assert.assertEquals(iterator.next(), result);
        }
    }

    @Test
    public void calculatorTest3() {

        List<String> tokenAssertion = new ArrayList<>(Arrays.asList("DIV", "MUL", "DIV", "13", "43", "DIV", "3995", "2", "SUM", "SUM", "20", "22", "MUL", "1", "0"));

        calculator.calculate(input, output);

        Iterator iterator = tokenAssertion.listIterator();

        for (String token : calculator.expressionList.get(0)) {
            Assert.assertEquals(iterator.next(), token);
        }

    }

    @Test
    public void calculatorTest4() {
        calculator.calculate(multyLevelInput, output);

        String resultAssertion = "-6927361.808722123";

        Assert.assertEquals(resultAssertion, calculator.resultList.getFirst());

    }
}