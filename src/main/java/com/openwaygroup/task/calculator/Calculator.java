package com.openwaygroup.task.calculator;

import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class Calculator implements SimpleCalculator {
    private static final Logger log = Logger.getLogger(Main.class);
    LinkedList<String> resultList = new LinkedList<>();
    LinkedList<LinkedList<String>> expressionList;

    @Override
    public void calculate(Path input, Path output) {
        try {
            log.info("Start of the session");

            expressionList = SAXParserImplementation.SAXParser(input, log);

            for (LinkedList<String> list : expressionList)
                resultList.add(evaluate(list));


            XMLWriter.writeResultToXML(output, resultList, log);

            log.info("End of the session\n");
        } catch (ParserConfigurationException e) {
            log.error("ParserConfigurationException\n", e);
        } catch (TransformerException e) {
            log.error("TransformerException\n", e);
        } catch (IOException e) {
            log.error("IOException\n", e);
        } catch (SAXException e) {
            log.error("SAXException\n", e);
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
                        result = basicOperation(token.getOperator(), A, B);
                        if (result == null)
                            throw new NullPointerException();
                    } catch (NullPointerException e) {
                        return "Division by 0 occurred";
                    }
                    stack.push(result);
                } else
                    stack.push(token.getValue());
            }
        } catch (EmptyStackException e) {
            log.error("EmptyStackException", e);
            return null;
        }
        log.info("Expression's calculated");
        return String.valueOf(stack.pop());
    }

    private static Double basicOperation(String operator, double A, double B) {

        Double result = 0.0;

        switch (operator) {
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
                    log.error("Division by 0");
                    return null;
                }
        }
        return result;
    }
}