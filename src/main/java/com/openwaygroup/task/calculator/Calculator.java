package com.openwaygroup.task.calculator;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static com.openwaygroup.task.calculator.Main.logger;

public class Calculator implements SimpleCalculator {

    LinkedList<String> resultList = new LinkedList<>();
    LinkedList<LinkedList<String>> expressionList;

    @Override
    public void calculate(Path file, Path resultFile) {
        try {
            logger.info("Start of the session");

            expressionList = SAXParserImplementation.SAXParser(file);

            for (LinkedList<String> list : expressionList)
                resultList.add(evaluate(list));

            XMLWriter.writeResultToXML(resultFile, resultList);

            logger.info("End of the session\n");
        } catch (ParserConfigurationException e) {
            logger.error("ParserConfigurationException\n", e);
        } catch (TransformerException e) {
            logger.error("TransformerException\n", e);
        } catch (IOException e) {
            logger.error("IOException\n", e);
        } catch (SAXException e) {
            logger.error("SAXException\n", e);
        }
    }

    private static String evaluate(LinkedList<String> stringTokenList) {

        List<Token> prefixTokenList = new ArrayList<>();

        for (String token : stringTokenList)
            prefixTokenList.add(new Token(token));

        return calculator(prefixTokenList);
    }

    /**
     * Implementation of a prefix calculator
     *
     * @param prefixTokenList: list of tokens in a prefix form
     * @return String result of prefix calculations
     */

    private static String calculator(List<Token> prefixTokenList) {
        Stack<Double> stack = new Stack<>();
        Double A, B, result;
        try {
            Collections.reverse(prefixTokenList);
            for (Token token : prefixTokenList) {
                if (!token.isType()) {
                    A = stack.pop();
                    B = stack.pop();
                    try {
                        if ((result = basicOperation(token.getOperator(), A, B)) == null)
                            throw new NullPointerException();
                        stack.push(result);
                    } catch (NullPointerException e) {
                        return "Division by 0 occurred";
                    } catch (Exception e) {
                        return "Unknown operation";
                    }
                } else
                    stack.push(token.getValue());
            }
        } catch (EmptyStackException e) {
            logger.error("EmptyStackException", e);
            return null;
        }
        logger.info("Expression's calculated");
        return String.valueOf(stack.pop());
    }

    private static Double basicOperation(String operator, double A, double B) throws Exception {

        switch (operator) {
            case "SUM":
                return A + B;
            case "SUB":
                return A - B;
            case "MUL":
                return A * B;
            case "DIV":
                try {
                    if (B == 0)
                        throw new ArithmeticException();
                    return A / B;
                } catch (ArithmeticException e) {
                    logger.warn("Division by 0");
                    return null;
                }
            default:
                logger.error("Unknown operation");
                throw new Exception();
        }
    }
}