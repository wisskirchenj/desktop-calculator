package de.cofinpro.calculator;

import de.cofinpro.calculator.controller.CalculatorController;

import java.util.Locale;
import javax.swing.SwingUtilities;

public class ApplicationRunner {

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        SwingUtilities.invokeLater(CalculatorController::new);
    }
}