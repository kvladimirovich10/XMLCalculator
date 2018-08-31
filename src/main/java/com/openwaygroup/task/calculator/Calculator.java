package com.openwaygroup.task.calculator;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Calculator implements SimpleCalculator {

    @Override
    public void calculate(Path file,
                          Path resultFile) {
        try {
            LinkedList<LinkedList<String>> expressionList = SAXImplementation.SAXParser(file.toString());

            for (LinkedList<String> list : expressionList) {
                System.out.println(evaluate(list));
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String evaluate(LinkedList<String> stringTokens) {

        List<Token> prefixTokenList = new ArrayList<>();

        for (String token : stringTokens) {
            prefixTokenList.add(new Token(token));
        }

        return calculator(prefixTokenList);
    }

    /**
     * Implementation of a prefix calculator
     *
     * @param prefixTokenList: list of tokens in a prefix form
     * @return String result of prefix calculations
     * @throws {@link EmptyStackException} in case of division by 0
     */

    private static String calculator(List<Token> prefixTokenList) throws EmptyStackException {
        Stack<Double> stack = new Stack<>();
        double A, B, result;
        try {
            Collections.reverse(prefixTokenList);
            for (Token token : prefixTokenList) {
                if (!token.isType()) {
                    A = stack.pop();
                    B = stack.pop();
                    try {
                        result = basicOperation(token.getOp(), A, B);
                    } catch (NullPointerException e) {
                        return null;
                    }
                    stack.push(result);
                } else
                    stack.push(token.getValue());
            }
        } catch (EmptyStackException e) {
            return null;
        }

        result = stack.pop();

        return String.valueOf(result);
    }

    /**
     * Executes basic math operations
     *
     * @param op: '+', '-', '*', '/'
     * @param A:  first operand
     * @param B:  second operand
     * @return Double result of basic math operations
     */

    private static Double basicOperation(String op,
                                         double A,
                                         double B) {

        Double result = 0.0;

        switch (op) {
            case "SUM":
                result = A + B;
                break;
            case "SUB":
                result = A - B;
                break;
            case "MUL":
                result = A * B;
                break;
            case "DIV":
                if (B == 0)
                    return null;
                result = A / B;
        }
        return result;
    }
}