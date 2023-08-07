package de.cofinpro.calculator.controller;

import de.cofinpro.calculator.model.EquationSolver;
import de.cofinpro.calculator.model.EquationTextResolver;
import de.cofinpro.calculator.view.DesktopCalculator;
import lombok.Setter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 * button listener to all calculator buttons, that calls appropriate resolver methods on different key presses and
 * uses an EquationSolver for solving the equation. The results are finally transferred to the DesktopCalculator.
 */
public class ButtonListener implements ActionListener {

    private static final String EMPTY_EQUATION = " ";
    private final EquationSolver equationSolver = new EquationSolver();
    private final EquationTextResolver equationTextResolver = new EquationTextResolver();

    @Setter
    private DesktopCalculator calculator;

    @Override
    public void actionPerformed(ActionEvent e) {
        var clicked = ((JButton) e.getSource()).getText();
        calculator.resetEquationError();
        var processedText = switch (clicked) {
            case "=" -> solve();
            case "C" -> clear();
            case "Del" -> del();
            case "( )" -> parentheses();
            case "√" -> sqrt();
            case "x²" -> powerOperator("^(2)");
            case "xʸ" -> powerOperator("^(");
            case "±" -> plusMinus();
            default -> processKey(clicked);
        };
        calculator.setEquationText(processedText);
    }

    private String plusMinus() {
        return equationTextResolver.resolveSign(calculator.getEquationText());
    }

    private String powerOperator(String powerOperator) {
        return equationTextResolver.resolvePowerOperator(calculator.getEquationText(), powerOperator);
    }

    private String sqrt() {
        return equationTextResolver.resolveSqrt(calculator.getEquationText());
    }

    private String parentheses() {
        return equationTextResolver.resolveParentheses(calculator.getEquationText());
    }

    private String processKey(String clicked) {
        var equationText = calculator.getEquationText();
        if (equationTextResolver.isOperator(clicked)) {
            equationText = equationTextResolver.resolveOperator(equationText, clicked);
        } else {
            equationText = equationTextResolver.resolveDotOrDigit(equationText, clicked);
        }
        return equationText;
    }

    private String clear() {
        calculator.setResultText("0");
        return EMPTY_EQUATION;
    }

    private String del() {
        var content = calculator.getEquationText();
        return EMPTY_EQUATION.equals(content) ? EMPTY_EQUATION : content.substring(0, content.length() - 1);
    }

    private String solve() {
        var equationText = equationTextResolver.resolveLastNumber(calculator.getEquationText());
        var solution = equationSolver.solve(equationText);

        if (EquationSolver.ERROR.equals(solution)) {
            calculator.markEquationAsError();
        } else {
            calculator.setResultText(solution);
        }
        return equationText;
    }
}
