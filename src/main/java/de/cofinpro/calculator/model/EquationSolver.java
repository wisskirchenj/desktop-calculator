package de.cofinpro.calculator.model;

import java.util.Map;
import java.util.Objects;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;

/**
 * equations solver class, that bundles all the rules for parsing and simplifying / calculating the equations.
 */
public class EquationSolver {

    public static final String ERROR = "Error";
    public static final String SIGNED = "(-?[0-9.]+)";
    private static final Pattern INNER_PARENTHESIS = Pattern.compile("\\([^()]+\\)");
    private static final Pattern POWER_OPERATION = Pattern.compile("√%1$s|%1$s\\^%1$s".formatted(SIGNED));
    private static final Pattern DOT_OPERATION = Pattern.compile("%1$s([÷×])%1$s".formatted(SIGNED));
    private static final Pattern LINE_OPERATION = Pattern.compile("%1$s([+-])%1$s".formatted(SIGNED));
    private final Map<String, BinaryOperator<Float>> operations = Map.of(
            "+", Float::sum,
            "-", (x, y) -> x - y,
            "×", (x, y) -> x * y,
            "÷", this::divideZeroChecked
    );

    private float divideZeroChecked(float x, float y) {
        if (y != 0) {
            return x / y;
        }
        throw new ArithmeticException("div by zero");
    }

    /**
     * only public entry point to this class, that solves the equation to a result string.
     * @return the result of the equation if it is a valid float or else EquationSolver.ERROR.
     */
    public String solve(String equation) {
        try {
            var result = simplify(equation);
            return result == (long) result ? "%d".formatted((long) result) : "%s".formatted(result);
        } catch (NumberFormatException | ArithmeticException ex) {
            return ERROR;
        }
    }

    /**
     * simplify the equation by:
     * 1.) simplifying all parentheses - starting from inner ones (using recursion calls to simplify on the content)
     * 2.) doing all power operations - i.e. x^y and √ from left to right
     * 3.) doing all dot operations - i.e. multiply and divide from left to right
     * 4.) doing all line operations - i.e. add and subtract from left to right
     * The right-to-left processing is implemented by end-recursion and Matcher::find.
     * @param equation the equation string to simplify
     * @return the result
     * @throws NumberFormatException if some number format is invalid (Float::parseFloat)
     * @throws ArithmeticException if division by zero attempted or power-raising yields NaN.
     */
    private float simplify(String equation) {
        equation = simplifyParentheses(equation);
        equation = simplifyPowerOperations(equation);
        equation = simplifyOperations(equation, DOT_OPERATION);
        equation = simplifyOperations(equation, LINE_OPERATION);
        return Float.parseFloat(equation);
    }

    private String simplifyPowerOperations(String equation) {
        var matcher = POWER_OPERATION.matcher(equation);
        if (!matcher.find()) {
            return equation;
        }
        var isSquareRoot = Objects.nonNull(matcher.group(1));
        var base = isSquareRoot ? Float.parseFloat(matcher.group(1)) : Float.parseFloat(matcher.group(2));
        var exponent = isSquareRoot ? 0.5f : Float.parseFloat(matcher.group(3));
        var result = powNaNChecked(base, exponent);
        return simplifyPowerOperations(matcher.replaceFirst("%s".formatted(result)));
    }

    private String simplifyParentheses(String equation) {
        var matcher = INNER_PARENTHESIS.matcher(equation);
        if (!matcher.find()) {
            return equation;
        }
        var matched= matcher.group();
        var result = simplify(matched.substring(1, matched.length() - 1));
        return simplifyParentheses(matcher.replaceFirst("%s".formatted(result)));
    }

    private String simplifyOperations(String equation, Pattern operationPattern) {
        var matcher = operationPattern.matcher(equation);
        if (!matcher.find()) {
            return equation;
        }
        var left = Float.parseFloat(matcher.group(1));
        var right = Float.parseFloat(matcher.group(3));
        var result = operations.get(matcher.group(2)).apply(left, right);
        return simplifyOperations(matcher.replaceFirst("%s".formatted(result)), operationPattern);
    }

    private float powNaNChecked(float base, float exponent) {
        var result = Math.pow(base, exponent);
        if (Double.isNaN(result)) {
            throw new ArithmeticException("negative base raised to fractional power");
        }
        return (float) result;
    }
}
