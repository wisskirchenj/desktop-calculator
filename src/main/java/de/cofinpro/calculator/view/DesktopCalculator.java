package de.cofinpro.calculator.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 * JFrame based class representing the viewer window of the Desktop calculator.
 */
public class DesktopCalculator extends JFrame {

    private static final String TITLE = "Calculator";
    private static final String SOLVE_BUTTON = "Solve";
    private static final String EQUATION_TEXT_FIELD = "EquationTextField";
    private static final int SIDE_MARGIN = 75;
    private static final int TOP_BOTTOM_MARGIN = 50;
    private static final int PADDING = 150;

    private JTextField equationTextField;
    private JButton solveButton;

    public DesktopCalculator() {
        super(TITLE);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        decorateContentPane();
        pack();
        setResizable(false);
        setVisible(true);
    }

    public void addSolveButtonListener(ActionListener listener) {
        solveButton.addActionListener(listener);
    }

    public String getEquationText() {
        return equationTextField.getText();
    }

    public void setSolution(String text) {
        equationTextField.setText(text);
    }

    private void decorateContentPane() {
        var pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(Box.createVerticalStrut(TOP_BOTTOM_MARGIN));
        pane.add(createEquationPanel());
        pane.add(Box.createVerticalStrut(PADDING));
        pane.add(createSolveButton());
        pane.add(Box.createVerticalStrut(TOP_BOTTOM_MARGIN));
    }

    private Component createEquationPanel() {
        var equationPanel = new JPanel();
        equationPanel.setLayout(new BoxLayout(equationPanel, BoxLayout.X_AXIS));
        equationPanel.add(Box.createHorizontalStrut(SIDE_MARGIN));
        equationPanel.add(createEquationTextField());
        equationPanel.add(Box.createHorizontalStrut(SIDE_MARGIN));
        return equationPanel;
    }

    private Component createEquationTextField() {
        equationTextField = new JTextField(15);
        equationTextField.setName(EQUATION_TEXT_FIELD);
        equationTextField.setAlignmentX(CENTER_ALIGNMENT);
        equationTextField.setFont(new Font("Arial", Font.BOLD, 20));
        equationTextField.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return equationTextField;
    }

    private Component createSolveButton() {
        solveButton = new JButton(SOLVE_BUTTON);
        solveButton.setName(SOLVE_BUTTON);
        solveButton.setAlignmentX(CENTER_ALIGNMENT);
        return solveButton;
    }
}
