package com.openwaygroup.task.calculator;import org.apache.commons.lang3.EnumUtils;/** * Class Token: contains information of all token in the expression */enum Operators {    SUM, SUB, MUL, DIV}class Token {    private boolean type;   // 0 - operator, 1 - value    private String operator;      // stores the operator as String    private double value;   // stores double value of the operand    Token(String token) {        if (isNumeric(token)) {            this.value = Double.parseDouble(token);            this.type = true;        } else if (EnumUtils.isValidEnum(Operators.class, token)) {            this.operator = token;        }    }    private static boolean isNumeric(String str) {        return str.matches("-?\\d+(\\.\\d+)?");    }    boolean isType() {        return type;    }    String getOperator() {        return operator;    }    double getValue() {        return value;    }}