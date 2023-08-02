package de.cofinpro.calculator.model;

import java.util.Arrays;

public class EquationSolver {

    public String solve(String equation) {
        var tokens = equation.split("\\+");
        var result = Arrays.stream(tokens)
                .map(String::trim)
                .map(Double::parseDouble)
                .reduce(Double::sum).orElseThrow();
        return "%s = %.2f".formatted(equation, result);
    }
}
