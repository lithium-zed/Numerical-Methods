import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;

public class DooLittleUI extends JFrame{
    private JPanel matrixPanel, bVectorPanel;
    private JTextField sizeField;
    private JButton generateButton, computeButton;
    private JTextField[][] matrixFields;
    private JTextField[] bVectorFields;
    private JTextArea resultArea;

    public DooLittleUI(){
        setTitle("Doolittle LU Decomposition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 700);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null); // Center window

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.setBorder(new TitledBorder("Matrix Size"));

        topPanel.add(new JLabel("Enter size (n x n):"));
        sizeField = new JTextField(3);
        topPanel.add(sizeField);

        generateButton = new JButton("Generate Matrix");
        topPanel.add(generateButton);
        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));

        matrixPanel = new JPanel();
        matrixPanel.setBorder(new TitledBorder("Enter Matrix Elements"));
        centerPanel.add(matrixPanel, BorderLayout.CENTER);



        bVectorPanel = new JPanel();
        bVectorPanel.setBorder(new TitledBorder("Enter Vector b (Ax = b)"));
        centerPanel.add(bVectorPanel, BorderLayout.EAST);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));

        computeButton = new JButton("Compute LU");
        computeButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        bottomPanel.add(computeButton, BorderLayout.NORTH);

        resultArea = new JTextArea(15, 40);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createTitledBorder("Results"));
        bottomPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        generateButton.addActionListener(e -> generateMatrix());
        computeButton.addActionListener(e -> computeLU());

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
        matrixPanel.setLayout(new GridLayout(n, n, 5, 5));
        matrixFields = new JTextField[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrixFields[i][j] = new JTextField(5);
                matrixFields[i][j].setHorizontalAlignment(JTextField.CENTER);
                matrixPanel.add(matrixFields[i][j]);
            }
        }

        bVectorPanel.removeAll();
        bVectorPanel.setLayout(new GridLayout(n, 1, 5, 5));
        bVectorFields = new JTextField[n];
        for (int i = 0; i < n; i++) {
            bVectorFields[i] = new JTextField(5);
            bVectorFields[i].setHorizontalAlignment(JTextField.CENTER);
            bVectorPanel.add(bVectorFields[i]);
        }

        matrixPanel.revalidate();
        matrixPanel.repaint();
        bVectorPanel.revalidate();
        bVectorPanel.repaint();
    }

    private void computeLU() {
        if (matrixFields == null || bVectorFields == null) {
            JOptionPane.showMessageDialog(this, "Please generate the matrix first.", "Missing Matrix", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int n = matrixFields.length;
        double[][] A = new double[n][n];
        double[] b = new double[n];

        try {
            // Read matrix values
            for (int i = 0; i < n; i++) {
                b[i] = Double.parseDouble(bVectorFields[i].getText());
                for (int j = 0; j < n; j++) {
                    A[i][j] = Double.parseDouble(matrixFields[i][j].getText());
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

            double[] y = new double[n];
            for (int i = 0; i < n; i++) {
                double sum = 0.0;
                for (int j = 0; j < i; j++) {
                    sum += L[i][j] * y[j];
                }
                y[i] = b[i] - sum;
            }

            double[] x = new double[n];
            for (int i = n - 1; i >= 0; i--) {
                double sum = 0.0;
                for (int j = i + 1; j < n; j++) {
                    sum += U[i][j] * x[j];
                }
                x[i] = (y[i] - sum) / U[i][i];
            }

            // Display result
            StringBuilder result = new StringBuilder();
            result.append("L Matrix:\n").append(formatMatrix(L)).append("\n");
            result.append("U Matrix:\n").append(formatMatrix(U)).append("\n");

            result.append("Solution Vector x:\n");
            for (int i = 0; i < n; i++) {
                result.append(String.format("x%d = %.2f\n", i + 1, x[i]));
            }

            resultArea.setText(result.toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values in all fields.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private String formatMatrix(double[][] matrix) {
        StringBuilder sb = new StringBuilder();
        for (double[] row : matrix) {
            for (double val : row) {
                sb.append(String.format("%8.2f", val)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(DooLittleUI::new);
    }

}