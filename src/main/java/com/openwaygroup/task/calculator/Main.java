package com.openwaygroup.task.calculator;import java.nio.file.*;public class Main {    public static void main(String[] args) {        Calculator c = new Calculator();        Path input = Paths.get("/Users/kirillkiselev1/IdeaProjects/SimpleCalculator/src/main/resources/SampleTest.xml");        Path output = Paths.get("/Users/kirillkiselev1/IdeaProjects/SimpleCalculator/src/main/resources/result.xml");        c.calculate(input, output);    }}