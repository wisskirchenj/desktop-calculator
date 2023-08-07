package de.cofinpro.calculator.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class EquationTextResolverTest {

    EquationTextResolver resolver;

    @BeforeAll
    static void setLocale() {
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    void setup() {
        resolver = new EquationTextResolver();
    }

    @ParameterizedTest
    @ValueSource(strings = {"×", "÷", "-", "+"})
    void whenOperatorPressedEmptyEquation_NoChange(String operator) {
        assertEquals(" ", resolver.resolveOperator(" ", operator));
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            equation,           operator,   result
            2,                  ×,          2×
            2×,                 ÷,          2÷
            2×(,                ÷,          2×(
            2×.2,               ÷,          2×0.2÷
            2×.2,               ÷,          2×0.2÷
            2×2.,               -,          2×2.0-
            2×.,                ×,          2×0.0×
            """)
    void whenOperatorPressed_resolveWorks(String equation, String operator, String expected) {
        assertEquals(expected, resolver.resolveOperator(equation, operator));
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            equation,           result
            2,                  2
            2×,                 2×(
            2×(,                2×((
            ((,                 (((
            3+4,                3+4
            (3+,                (3+(
            (3+4,               (3+4)
            (2-3),              (2-3)
            ((2-3),             ((2-3))
            (5÷(3+5)+6),        (5÷(3+5)+6)
            (5÷(3+5)+.2,        (5÷(3+5)+0.2)
            """)
    void whenParenthesisPressed_resolveWorks(String equation, String expected) {
        assertEquals(expected, resolver.resolveParentheses(equation));
    }

    @Test
    void whenEmptyAndParenthesisPressed_openParenthesisReturned() {
        assertEquals(" (", resolver.resolveParentheses(" "));
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            equation,           result
            2,                  2^(
            2×,                 2×
            2×(,                2×(
            ((,                 ((
            3+4,                3+4^(
            (3+,                (3+
            (3+4,               (3+4^(
            (2-3),              (2-3)^(
            ((2-3),             ((2-3)^(
            (5÷(3+5)+6),        (5÷(3+5)+6)^(
            (5÷(3+5)+.2,        (5÷(3+5)+0.2^(
            """)
    void whenPowerOperator_resolveWorks(String equation, String expected) {
        assertEquals(expected, resolver.resolvePowerOperator(equation, "^("));
    }

    @Test
    void whenEmptyAndPowerOperator_noChange() {
        assertEquals(" ", resolver.resolvePowerOperator(" ", "^(2)"));
        assertEquals(" ", resolver.resolvePowerOperator(" ", "^("));
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            equation,           result
            2,                  2
            2×,                 2×√(
            2×(,                2×(√(
            ((,                 ((√(
            3+4,                3+4
            (2-3),              (2-3)
            ((2-3),             ((2-3)
            """)
    void whenSqrt_resolveWorks(String equation, String expected) {
        assertEquals(expected, resolver.resolveSqrt(equation));
    }

    @Test
    void whenEmpty_SqrtWorks() {
        assertEquals(" √(", resolver.resolveSqrt(" "));
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            equation,           dotordigit, result
            2,                  .,          2.
            2×,                 2,          2×2
            2×(,                3,          2×(3
            2×.2,               0,          2×.20
            (2×2),              9,          (2×2)
            """)
    void whenDotOrDigit_resolveWorks(String equation, String dotOrDigit, String expected) {
        assertEquals(expected, resolver.resolveDotOrDigit(equation, dotOrDigit));
    }

    @Test
    void whenEmpty_dotOrDigitWoks() {
        assertEquals(" .", resolver.resolveDotOrDigit(" ", "."));
        assertEquals(" 3", resolver.resolveDotOrDigit(" ", "3"));
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            equation,           result
            2,                  (-2
            2×,                 2×(-
            2×(,                2×((-
            ((,                 (((-
            3+4,                3+(-4
            (3+4),              (3+4)
            (-2.3,              2.3
            (-.1,               .1
            .04,                (-.04
            """)
    void whenSignPressed_resolveWorks(String equation, String expected) {
        assertEquals(expected, resolver.resolveSign(equation));
    }

    @Test
    void whenEmpty_resolveSignToggles() {
        assertEquals(" (-", resolver.resolveSign(" "));
        assertEquals(" ", resolver.resolveSign(" (-"));
    }
}