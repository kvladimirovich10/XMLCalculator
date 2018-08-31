package com.openwaygroup.task.calculator;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Calculator implements SimpleCalculator {

    @Override
    public void calculate(Path input,
                          Path output) {
        try {
            LinkedList<LinkedList<String>> expressionList = SAXParserImplementation.SAXParser(input);
            LinkedList<String> resultList = new LinkedList<>();

            for (LinkedList<String> list : expressionList) {
                resultList.add(evaluate(list));
            }

            XMLWriter.writeResultToXML(output, resultList);
        } catch (ParserConfigurationException | TransformerException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private static String evaluate(LinkedList<String> stringTokenList) {

        List<Token> prefixTokenList = new ArrayList<>();

        for (String token : stringTokenList) {
            prefixTokenList.add(new Token(token));
        }

        return calculator(prefixTokenList);
    }

    /**
     * Implementation of a prefix calculator
     *
     * @param prefixTokenList: list of tokens in a prefix form
     * @return String result of prefix calculations
     */

    private static String calculator(List<Token> prefixTokenList) throws EmptyStackException {
        Stack<Double> stack = new Stack<>();
        Double A, B, result;
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
                try {
                    if (B == 0)
                        throw new ArithmeticException();
                    result = A / B;
                } catch (ArithmeticException e) {
                    return null;
                }
        }
        return result;
    }
}