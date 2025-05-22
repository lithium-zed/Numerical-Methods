import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GaussSeidelTableModel extends AbstractTableModel {
    ArrayList<GaussSeidelContent> gaussSeidelContents;
    int currentDecimalPlaces;
    String x1,x2,x3;
    String[] header = {"Iteration", "x", "y", "z", "x'", "y'", "z'", "EA,x'", "EA,y'","EA,z'"};

    public GaussSeidelTableModel() {
        this.x1 = "x";
        this.x2 = "y";
        this.x3 = "z";
        gaussSeidelContents = new ArrayList<>();
    }
    public void addToTable(GaussSeidelContent gaussSeidelContent){
        gaussSeidelContents.add(gaussSeidelContent);
        this.fireTableDataChanged();
    }
    public void computeGaussSeidel(String eq1Str, String eq2Str, String eq3Str, double tolerance, int decimalPlaces, JLabel xResultLabel, JLabel yResultLabel, JLabel zResultLabel) {
        currentDecimalPlaces = decimalPlaces;
        gaussSeidelContents.clear();
        fireTableDataChanged();

        // Basic parsing to extract coefficients and constants (very limited)
        double a11 = 0, a12 = 0, a13 = 0, b1 = 0;
        double a21 = 0, a22 = 0, a23 = 0, b2 = 0;
        double a31 = 0, a32 = 0, a33 = 0, b3 = 0;

        // **Very Basic and Incomplete Parsing - Needs Improvement for Robustness**
        try {
            String[] terms1 = eq1Str.replace(" ", "").split("=");
            String[] coeffs1 = terms1[0].split("(?=[+-])");
            b1 = Double.parseDouble(terms1[1]);
            for (String term : coeffs1) {
                if (term.contains("x")) a11 = parseCoefficient(term, 'x');
                else if (term.contains("y")) a12 = parseCoefficient(term, 'y');
                else if (term.contains("z")) a13 = parseCoefficient(term, 'z');
            }

            String[] terms2 = eq2Str.replace(" ", "").split("=");
            String[] coeffs2 = terms2[0].split("(?=[+-])");
            b2 = Double.parseDouble(terms2[1]);
            for (String term : coeffs2) {
                if (term.contains("x")) a21 = parseCoefficient(term, 'x');
                else if (term.contains("y")) a22 = parseCoefficient(term, 'y');
                else if (term.contains("z")) a23 = parseCoefficient(term, 'z');
            }

            String[] terms3 = eq3Str.replace(" ", "").split("=");
            String[] coeffs3 = terms3[0].split("(?=[+-])");
            b3 = Double.parseDouble(terms3[1]);
            for (String term : coeffs3) {
                if (term.contains("x")) a31 = parseCoefficient(term, 'x');
                else if (term.contains("y")) a32 = parseCoefficient(term, 'y');
                else if (term.contains("z")) a33 = parseCoefficient(term, 'z');
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error parsing equations. Please use the format like 'ax + by + cz = d'.", "Parsing Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double x = 0; // Initial guess for x
        double y = 0; // Initial guess for y
        double z = 0; // Initial guess for z

        int iteration = 0;
        double eaX = Double.MAX_VALUE;
        double eaY = Double.MAX_VALUE;
        double eaZ = Double.MAX_VALUE;

        do {
            System.out.println("P");
            iteration++;
            double oldX = x;
            double oldY = y;
            double oldZ = z;

            // Gauss-Seidel iterative formulas (assuming diagonally dominant and solvable)
            double newX = roundToDecimalPlaces((b1 - a12 * y - a13 * z) / a11, decimalPlaces);
            double newY = roundToDecimalPlaces((b2 - a21 * newX - a23 * z) / a22, decimalPlaces);
            double newZ = roundToDecimalPlaces((b3 - a31 * newX - a32 * newY) / a33, decimalPlaces);

            if (Math.abs(a11) < 1e-9 || Math.abs(a22) < 1e-9 || Math.abs(a33) < 1e-9) {
                JOptionPane.showMessageDialog(null, "Diagonal coefficient is zero. Gauss-Seidel may not work.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            eaX = Math.abs((newX - oldX) / Math.max(1e-9, newX)) * 100; // Avoid division by zero
            eaY = Math.abs((newY - oldY) / Math.max(1e-9, newY)) * 100;
            eaZ = Math.abs((newZ - oldZ) / Math.max(1e-9, newZ)) * 100;

            addToTable(new GaussSeidelContent(iteration, oldX, oldY, oldZ, newX, newY, newZ,
                    roundToDecimalPlaces(eaX, decimalPlaces),
                    roundToDecimalPlaces(eaY, decimalPlaces),
                    roundToDecimalPlaces(eaZ, decimalPlaces)));

            x = newX;
            y = newY;
            z = newZ;

            if (iteration > 100) { // Safety break to prevent infinite loops
                JOptionPane.showMessageDialog(null, "Maximum iterations reached. Convergence may not have occurred.", "Warning", JOptionPane.WARNING_MESSAGE);
                break;
            }
        } while (Math.abs(eaX) > tolerance || Math.abs(eaY) > tolerance || Math.abs(eaZ) > tolerance);
        xResultLabel.setText(String.valueOf(roundToDecimalPlaces(x, currentDecimalPlaces)));
        yResultLabel.setText(String.valueOf(roundToDecimalPlaces(y, currentDecimalPlaces)));
        zResultLabel.setText(String.valueOf(roundToDecimalPlaces(z, currentDecimalPlaces)));
    }

    // Helper function for basic coefficient parsing
    private double parseCoefficient(String term, char variable) {
        String coeffStr = term.replace(String.valueOf(variable), "");
        if (coeffStr.equals("+") || coeffStr.isEmpty()) return 1.0;
        if (coeffStr.equals("-")) return -1.0;
        return Double.parseDouble(coeffStr);
    }
    private double roundToDecimalPlaces(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
    private int getDecimalPlaces() {
        return currentDecimalPlaces;
    }

    @Override
    public int getRowCount() {
        return gaussSeidelContents.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= 0 && rowIndex < gaussSeidelContents.size()) {
            GaussSeidelContent gsc = gaussSeidelContents.get(rowIndex);
            switch (columnIndex) {
                case 0: return gsc.getIteration();
                case 1: return roundToDecimalPlaces(gsc.getX(), getDecimalPlaces());
                case 2: return roundToDecimalPlaces(gsc.getY(), getDecimalPlaces());
                case 3: return roundToDecimalPlaces(gsc.getZ(), getDecimalPlaces());
                case 4: return roundToDecimalPlaces(gsc.getpX(), getDecimalPlaces());
                case 5: return roundToDecimalPlaces(gsc.getpY(), getDecimalPlaces());
                case 6: return roundToDecimalPlaces(gsc.getpZ(), getDecimalPlaces());
                case 7: return roundToDecimalPlaces(gsc.getEaX(), getDecimalPlaces());
                case 8: return roundToDecimalPlaces(gsc.getEaY(), getDecimalPlaces());
                case 9: return roundToDecimalPlaces(gsc.getEaZ(), getDecimalPlaces());
                default: return null;
            }
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }
}
