package de.cofinpro.calculator.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class EquationSolverTest {

    EquationSolver equationSolver;

    @BeforeAll
    static void setLocale() {
        Locale.setDefault(Locale.US);
    }

    @BeforeEach
    void setup() {
        equationSolver = new EquationSolver();
    }

    @ParameterizedTest
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            equation,   result
            2+2,        4.00
            -2 + 3 ,    1.00
            2+3+4+4,    13.00
            2.3 +3,     5.30
            0.1222+3,   3.12
            """)
    void solveTest(String equation, double expectedResult) {
        var expected = "%s = %.2f".formatted(equation, expectedResult);
        assertEquals(expected, equationSolver.solve(equation));
    }

}