package de.cofinpro.calculator.model;

import java.util.regex.Pattern;

/**
 * resolver for the equation label to apply all syntax restrictions and rules specified for accepting
 * and resolving calculator button presses in the current equation context.
 */
public class EquationTextResolver {

    private static final Pattern EQUATION_ENDS_WITH_NUMBER = Pattern.compile("(.*[ +÷×-])([0-9.]+)");
    private static final Pattern SIGNED_NUMBER_AT_END = Pattern.compile("(\\(-)?[0-9.]+$");

    public boolean isOperator(String toTest) {
        return toTest.matches("[+÷×-]");
    }

    /**
     * resolves the equation label after operator was pressed according to following rules:
     * -> override operator at the end of the display if applies
     * -> don't accept operator key if term is expected (isTermStart)
     * -> resolve any number before the operator regarding "."
     */
    public String resolveOperator(String equation, String operator) {
        if (isOperator(equation.substring(equation.length() - 1))) {
            return equation.substring(0, equation.length() - 1) + operator;
        }
        if (isTermStart(equation)) {
            return equation;
        }
        return resolveLastNumber(equation) + operator;
    }

    /**
     * smart choice and append of parenthesis, that fits the current equation context (if any).
     */
    public String resolveParentheses(String equation) {
        if (isTermStart(equation)) {
            return equation + "(";
        }
        var leftParentheses = equation.chars().filter(c -> c == '(').count();
        var rightParentheses = equation.chars().filter(c -> c == ')').count();
        if (leftParentheses != rightParentheses) {
            return resolveLastNumber(equation) + ")";
        }
        return equation;
    }

    /**
     * appends an exponentiation start to the equation, iff the adding is valid (i.e. there is a term to lift).
     */
    public String resolvePowerOperator(String equation, String operationText) {
        if (!isTermStart(equation)) {
            return resolveLastNumber(equation) + operationText;
        }
        return equation;
    }

    /**
     * appends an open sqrt term to the equation, iff the adding is valid (e.g. "2√(" is not valid).
     */
    public String resolveSqrt(String equation) {
        if (isTermStart(equation)) {
            return equation + "√(";
        }
        return equation;
    }

    public String resolveSign(String equation) {
        if (equation.endsWith("(-")) { // 2 ±-presses should annihilate -> stupid requirement...
            return equation.substring(0, equation.length() - 2);
        }
        if (isTermStart(equation)) {
            return equation + "(-";
        }
        var matcher = SIGNED_NUMBER_AT_END.matcher(equation);
        if (!matcher.find()) {
            return equation;
        }
        var signToggled = matcher.group().startsWith("(-")
                ? matcher.group().substring(2)
                : "(-" + matcher.group();
        return matcher.replaceFirst(signToggled);
    }

    /**
     * appends given dot or digit to the equation, iff the adding is valid (e.g. "(2+3)2" is not valid).
     */
    public String resolveDotOrDigit(String equation, String dotOrDigit) {
        if (equation.endsWith(")")) {
            return equation;
        }
        return equation + dotOrDigit;
    }

    /**
     * Iff equation ends with a number, resolve it (e.g. ".2" -> "0.2").
     */
    public String resolveLastNumber(String equation) {
        var matcher = EQUATION_ENDS_WITH_NUMBER.matcher(equation);
        return matcher.matches() ? matcher.group(1) + resolveNumber(matcher.group(2)) : equation;
    }

    private String resolveNumber(String numberInput) {
        String resolvedNumber = numberInput;
        if (numberInput.startsWith(".")) {
            resolvedNumber = "0" + numberInput;
        }
        if (resolvedNumber.endsWith(".")) {
            resolvedNumber += "0";
        }
        return resolvedNumber;
    }

    /**
     * checks the equation, iff it ends with " " (empty), an open parenthesis or a standard operator.
     * In this case a new term starts.
     */
    private boolean isTermStart(String equation) {
        var lastChar = equation.substring(equation.length() - 1);
        return lastChar.matches("[( +÷×-]");
    }
}
