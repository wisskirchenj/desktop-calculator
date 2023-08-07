package de.cofinpro.calculator.controller;

import de.cofinpro.calculator.view.DesktopCalculator;

public class CalculatorController {

    public CalculatorController() {
        var buttonListener = new ButtonListener();
        var calculator = new DesktopCalculator(buttonListener);
        buttonListener.setCalculator(calculator);
    }
}
