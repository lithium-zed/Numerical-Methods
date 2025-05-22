import javax.swing.*;
import java.awt.*;
import java.util.regex.*;

public class DooLittleUI extends JFrame {
    private JTextField sizeField;
    private JPanel matrixPanel, bVectorPanel;
    private JTextField[] functionFields;
    private JTextField[] bVectorFields;
    private JTextArea resultArea;
    private JComboBox<String> precisionSelector;
    private int decimalPlaces = 2;

    public DooLittleUI() {
        setTitle("Doolittle LU Decomposition Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 600);
        setLayout(new BorderLayout());

        // Top Panel: Matrix size
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Matrix Size (n x n):"));
        sizeField = new JTextField(5);
        topPanel.add(sizeField);
        JButton generateButton = new JButton("Generate Fields");
        generateButton.addActionListener(e -> generateMatrix());
        topPanel.add(generateButton);

        add(topPanel, BorderLayout.NORTH);

        // Center Panel: Matrix & b-vector
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        matrixPanel = new JPanel();
        JScrollPane matrixScroll = new JScrollPane(matrixPanel);
        centerPanel.add(matrixScroll);

        bVectorPanel = new JPanel();
        JScrollPane bScroll = new JScrollPane(bVectorPanel);
        centerPanel.add(bScroll);

        add(centerPanel, BorderLayout.CENTER);

        // Bottom Panel: Compute + Results
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton computeButton = new JButton("Compute");
        computeButton.addActionListener(e -> computeLU());
        bottomPanel.add(computeButton, BorderLayout.NORTH);

        resultArea = new JTextArea(15, 50);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setEditable(false);
        JScrollPane resultScroll = new JScrollPane(resultArea);
        bottomPanel.add(resultScroll, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void generateMatrix() {
        int n;
        try {
            n = Integer.parseInt(sizeField.getText());
            if (n <= 1 || n > 10) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a valid size (2 to 10).", "Invalid Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        matrixPanel.removeAll();
        matrixPanel.setLayout(new GridLayout(n, 1, 5, 5));
        functionFields = new JTextField[n];

        for (int i = 0; i < n; i++) {
            functionFields[i] = new JTextField(30);
            functionFields[i].setToolTipText("e.g., 2*x1 + 3*x2 - 4*x3");
            matrixPanel.add(functionFields[i]);
        }

        bVectorPanel.removeAll();
        bVectorPanel.setLayout(new GridLayout(n + 1, 1, 5, 5));
        bVectorFields = new JTextField[n];
        for (int i = 0; i < n; i++) {
            bVectorPanel.add(new JLabel("b" + (i + 1)));
            bVectorFields[i] = new JTextField(5);
            bVectorFields[i].setHorizontalAlignment(JTextField.CENTER);
            bVectorPanel.add(bVectorFields[i]);
        }

        // Add precision selector
        String[] precisions = { "0", "1", "2", "3", "4" };
        precisionSelector = new JComboBox<>(precisions);
        precisionSelector.setSelectedIndex(2);
        precisionSelector.addActionListener(e -> decimalPlaces = Integer.parseInt((String) precisionSelector.getSelectedItem()));
        bVectorPanel.add(new JLabel("Decimal Places:"));
        bVectorPanel.add(precisionSelector);

        matrixPanel.revalidate();
        matrixPanel.repaint();
        bVectorPanel.revalidate();
        bVectorPanel.repaint();
    }

    private void computeLU() {
        if (functionFields == null || bVectorFields == null) {
            JOptionPane.showMessageDialog(this, "Please generate the matrix and vector first.", "Missing Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int n = functionFields.length;
        double[][] A = new double[n][n];
        double[] b = new double[n];

        try {
            // Parse b values
            for (int i = 0; i < n; i++) {
                b[i] = Double.parseDouble(bVectorFields[i].getText());
            }

            // Parse function strings
            for (int i = 0; i < n; i++) {
                String func = functionFields[i].getText().replaceAll("\\s+", "");
                for (int j = 0; j < n; j++) {
                    String variable = "x" + (j + 1);
                    A[i][j] = extractCoefficient(func, variable);
                }
            }

            double[][] L = new double[n][n];
            double[][] U = new double[n][n];

            for (int i = 0; i < n; i++) {
                L[i][i] = 1.0;
                for (int j = i; j < n; j++) {
                    double sum = 0.0;
                    for (int k = 0; k < i; k++) {
                        sum += L[i][k] * U[k][j];
                    }
                    U[i][j] = A[i][j] - sum;
                }

                for (int j = i + 1; j < n; j++) {
                    double sum = 0.0;
                    for (int k = 0; k < i; k++) {
                        sum += L[j][k] * U[k][i];
                    }
                    L[j][i] = (A[j][i] - sum) / U[i][i];
                }
            }

            // Forward substitution
            double[] y = new double[n];
            for (int i = 0; i < n; i++) {
                double sum = 0.0;
                for (int j = 0; j < i; j++) {
                    sum += L[i][j] * y[j];
                }
                y[i] = b[i] - sum;
            }

            // Backward substitution
            double[] x = new double[n];
            for (int i = n - 1; i >= 0; i--) {
                double sum = 0.0;
                for (int j = i + 1; j < n; j++) {
                    sum += U[i][j] * x[j];
                }
                x[i] = (y[i] - sum) / U[i][i];
            }

            // Format output
            String format = "%8." + decimalPlaces + "f";
            StringBuilder result = new StringBuilder();
            result.append("Original Matrix A:\n").append(formatMatrix(A, format)).append("\n");
            result.append("L Matrix:\n").append(formatMatrix(L, format)).append("\n");
            result.append("U Matrix:\n").append(formatMatrix(U, format)).append("\n");
            result.append("Solution Vector x:\n");
            for (int i = 0; i < n; i++) {
                result.append("x").append(i + 1).append(" = ").append(String.format("%." + decimalPlaces + "f", x[i])).append("\n");
            }

            resultArea.setText(result.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error in input or parsing. Check syntax and values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private double extractCoefficient(String equation, String variable) {
        String regex = "([+-]?\\d*\\.?\\d*)\\*?" + variable;
        Matcher matcher = Pattern.compile(regex).matcher(equation);
        if (matcher.find()) {
            String coeff = matcher.group(1);
            if (coeff.isEmpty() || coeff.equals("+")) return 1.0;
            if (coeff.equals("-")) return -1.0;
            return Double.parseDouble(coeff);
        }
        return 0.0;
    }

    private String formatMatrix(double[][] M, String format) {
        StringBuilder sb = new StringBuilder();
        for (double[] row : M) {
            for (double val : row) {
                sb.append(String.format(format, val)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DooLittleUI::new);
    }
}

