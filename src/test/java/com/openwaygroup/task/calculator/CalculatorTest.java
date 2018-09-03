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

        List<String> tokenAssertion = new ArrayList<>(Arrays.asList("DIV", "DIV","MUL", "SUB", "SUB", "SUM", "DIV", "MUL", "SUB", "SUB", "MUL", "DIV", "DIV", "43", "4", "DIV", "14", "41", "DIV", "92", "17", "DIV", "10000", "42", "DIV", "14232", "4455", "DIV", "1254", "75", "DIV", "1", "45", "DIV", "156", "4625", "DIV", "651", "4655", "DIV", "2341", "4096705", "DIV", "901", "45", "DIV", "71", "45645", "DIV", "98451", "45"));

        calculator.calculate(multyLevelInput, output);

        Iterator iterator = tokenAssertion.listIterator();

        for (String token : calculator.expressionList.get(0)) {
            Assert.assertEquals(iterator.next(), token);
        }

    }

    @Test
    public void calculatorTest4() {
        calculator.calculate(multyLevelInput, output);

        String resultAssertion = "-313929.98850796255";

        Assert.assertEquals(resultAssertion, calculator.resultList.getFirst());
    }
}