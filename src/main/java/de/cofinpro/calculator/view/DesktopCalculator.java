package de.cofinpro.calculator.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import static java.util.Map.entry;

/**
 * JFrame based class representing the viewer window of the Desktop calculator.
 */
public class DesktopCalculator extends JFrame {

    private static final String ARIAL = "Arial";
    private static final String TITLE = "Calculator";
    private static final String EQUATION_LABEL = "EquationLabel";
    private static final String RESULT_LABEL = "ResultLabel";
    private static final int SIDE_MARGIN = 20;
    private static final int TOP_BOTTOM_MARGIN = 20;
    private static final int VERT_PADDING = 30;
    private static final int BUTTON_PADDING = 5;
    private static final List<List<Map.Entry<String, String>>> BUTTONS = List.of(
            List.of(
                    entry("Parentheses", "( )"),
                    entry("Clear", "C"),
                    entry("Delete", "Del")
            ), List.of(
                    entry("PowerTwo", "x²"),
                    entry("PowerY", "xʸ"),
                    entry("SquareRoot", "√"),
                    entry("Divide", "÷")
            ), List.of(
                    entry("Seven", "7"),
                    entry("Eight", "8"),
                    entry("Nine", "9"),
                    entry("Multiply", "×")
            ), List.of(
                    entry("Four", "4"),
                    entry("Five", "5"),
                    entry("Six", "6"),
                    entry("Subtract", "-")
            ), List.of(
                    entry("One", "1"),
                    entry("Two", "2"),
                    entry("Three", "3"),
                    entry("Add", "+")
            ), List.of(
                    entry("PlusMinus", "±"),
                    entry("Zero", "0"),
                    entry("Dot", "."),
                    entry("Equals", "=")
            ));
    private static final String WHITE_REGEXP = "[0-9±.]";
    private static final Color LIGHT_GRAYISH = new Color(230, 230, 230);

    private final transient ActionListener buttonListener;
    private JLabel equationLabel;
    private JLabel resultLabel;

    public DesktopCalculator(ActionListener buttonListener) {
        super(TITLE);
        this.buttonListener = buttonListener;
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        decorateContentPane();
        pack();
        setResizable(false);
        setVisible(true);
    }

    public String getEquationText() {
        return equationLabel.getText();
    }

    public void setEquationText(String text) {
        equationLabel.setText(text);
    }

    public void markEquationAsError() {
        equationLabel.setForeground(Color.RED.darker());
    }

    public void resetEquationError() {
        equationLabel.setForeground(Color.GREEN.darker());
    }

    public void setResultText(String text) {
        resultLabel.setText(text);
    }

    private void decorateContentPane() {
        var pane = getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(Box.createVerticalStrut(TOP_BOTTOM_MARGIN));
        pane.add(createLabelPanel(createResultLabel()));
        pane.add(Box.createVerticalStrut(VERT_PADDING));
        pane.add(createLabelPanel(createEquationLabel()));
        pane.add(Box.createVerticalStrut(VERT_PADDING));
        addButtonRows();
        pane.add(Box.createVerticalStrut(TOP_BOTTOM_MARGIN));
    }

    private Component createLabelPanel(Component label) {
        var equationPanel = new JPanel();
        equationPanel.setLayout(new BoxLayout(equationPanel, BoxLayout.X_AXIS));
        equationPanel.add(Box.createHorizontalGlue());
        equationPanel.add(label);
        equationPanel.add(Box.createHorizontalStrut(SIDE_MARGIN + 10));
        return equationPanel;
    }

    private Component createEquationLabel() {
        equationLabel = new JLabel(" ");
        equationLabel.setName(EQUATION_LABEL);
        equationLabel.setForeground(Color.GREEN.darker());
        equationLabel.setFont(new Font(ARIAL, Font.BOLD, 20));
        return equationLabel;
    }

    private Component createResultLabel() {
        resultLabel = new JLabel("0");
        resultLabel.setName(RESULT_LABEL);
        resultLabel.setFont(new Font(ARIAL, Font.BOLD, 50));
        return resultLabel;
    }

    private void addButtonRows() {
        BUTTONS.forEach(this::addButtonRow);
    }

    private void addButtonRow(List<Map.Entry<String, String>> buttonLine) {
        getContentPane().add(Box.createVerticalStrut(BUTTON_PADDING));
        var buttonLinePanel = new JPanel();
        buttonLinePanel.setLayout(new BoxLayout(buttonLinePanel, BoxLayout.X_AXIS));
        buttonLinePanel.add(Box.createHorizontalStrut(SIDE_MARGIN));
        buttonLinePanel.add(Box.createHorizontalGlue());
        buttonLine.forEach(entry -> addButton(buttonLinePanel, entry));
        buttonLinePanel.add(Box.createHorizontalStrut(SIDE_MARGIN - BUTTON_PADDING));
        getContentPane().add(buttonLinePanel);
    }

    private void addButton(JPanel buttonLinePanel, Map.Entry<String, String> entry) {
        var button = new JButton(entry.getValue());
        button.setName(entry.getKey());
        button.setFont(new Font(ARIAL, Font.BOLD, 25));
        button.setPreferredSize(new Dimension(80, 60));
        button.setMargin(new Insets(10, 0, 10, 0));
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setBackground(entry.getValue().matches(WHITE_REGEXP) ? Color.WHITE : LIGHT_GRAYISH);
        button.addActionListener(buttonListener);
        buttonLinePanel.add(button);
        buttonLinePanel.add(Box.createHorizontalStrut(BUTTON_PADDING));
    }
}
