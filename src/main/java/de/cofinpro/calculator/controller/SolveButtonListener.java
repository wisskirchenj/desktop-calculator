package de.cofinpro.calculator.controller;

import de.cofinpro.calculator.model.EquationSolver;
import de.cofinpro.calculator.view.DesktopCalculator;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Slf4j
public class SolveButtonListener implements ActionListener {

    private final DesktopCalculator calculator;
    private final EquationSolver equationSolver;

    public SolveButtonListener(DesktopCalculator calculator) {
        this.calculator = calculator;
        this.equationSolver = new EquationSolver();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        log.debug("Solve Button clicked!");
        var solution = equationSolver.solve(calculator.getEquationText());
        calculator.setSolution(solution);
    }
}
