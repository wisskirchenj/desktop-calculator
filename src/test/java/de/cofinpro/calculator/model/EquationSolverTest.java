package de.cofinpro.calculator.model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            equation,                           result
            2+2,                                4
            -2+3,                               1
            2+3+4+4,                            13
            2.3+3,                              5.3
            0.1222+3,                           3.1222
            (25+9÷3-8×8),                       -36
            (8+(7-1+5)),                        19
            3+8×((4+3)×2+1)-6÷(2+1),            121
            9.2÷2.3×12÷2.4,                     20
            √(16)+36^(2)÷5,                     263.2
            25+9^(2)×3-8÷8×√(49),               261
            (-2-2),                             -4
            2-17+5,                             -10
            """)
    void whenValidEquation_solveTestHasCorrectResult(String equation, String expected) {
        assertEquals(expected, equationSolver.solve(equation));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "2+",
            "5÷0",
            "2+7÷(6-2×3)",
            "√((-9))"
    })
    void whenInvalidEquation_solveTestReturnsError(String equation) {
        assertEquals(EquationSolver.ERROR, equationSolver.solve(equation));
    }
}