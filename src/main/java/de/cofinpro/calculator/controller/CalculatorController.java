package de.cofinpro.calculator.controller;

import de.cofinpro.calculator.view.DesktopCalculator;

public class CalculatorController {

    public CalculatorController() {
        var calculator = new DesktopCalculator();
        calculator.addSolveButtonListener(new SolveButtonListener(calculator));
    }
}
