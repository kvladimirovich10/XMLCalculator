package com.openwaygroup.task.calculator;

import java.nio.file.Path;

@FunctionalInterface
public interface SimpleCalculator {
    void calculate(Path file, Path resultFile);
}