import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class SecantTableModel extends AbstractTableModel {
    ArrayList<BisectionContent> bisectionContents;
    String estimated_root;
    String[] header = {"Iteration", "x0", "f(x0)", "x1", "f(x1)", "x2", "f(x2)", "EA"};
    public SecantTableModel() {
        this.estimated_root = "";
        this.bisectionContents = new ArrayList<>();
    }

    public void addToTable(BisectionContent bisectionContent){
        bisectionContents.add(bisectionContent);
        this.fireTableDataChanged();
    }

    public void computeSecant(String function, double x0, double x1, double tolerance, int decimalPlaces){
        BisectionContent SecantContent = new BisectionContent(); // reused from BisectionContent
        MathJSAPIConnection functionofx0 = new MathJSAPIConnection();
        MathJSAPIConnection functionofx1 = new MathJSAPIConnection();
        MathJSAPIConnection functionofx2 = new MathJSAPIConnection();


        double roundedX0 = roundToDecimalPlaces(x0, decimalPlaces);
        double roundedX1 = roundToDecimalPlaces(x1, decimalPlaces);
        double roundedTolerance = roundToDecimalPlaces(tolerance, decimalPlaces);

        int iter = 0;
        double oldX2 = 0;
        double ea = 0;
        double tempx1 = 0;

        do {
            iter++;

            double getFunctionOfx0 = roundToDecimalPlaces(Double.parseDouble(functionofx0.evaluateFunctionAtValue(function,roundedX0)),decimalPlaces);
            double getFunctionOfx1 = roundToDecimalPlaces(Double.parseDouble(functionofx1.evaluateFunctionAtValue(function,roundedX1)),decimalPlaces);
            double roundedx2 = roundToDecimalPlaces(
                    (roundedX1 - getFunctionOfx1 * ((roundedX1 - roundedX0) / (getFunctionOfx1 - getFunctionOfx0))),
                    decimalPlaces
            );
            double getFunctionofx2 = roundToDecimalPlaces(Double.parseDouble(functionofx2.evaluateFunctionAtValue(function,roundedx2)),decimalPlaces);

            if (iter > 1) {
                ea = roundToDecimalPlaces(Math.abs(roundedx2 - oldX2),decimalPlaces);
            }
            SecantContent.setEa(ea);

            addToTable(new BisectionContent(iter,roundedX0,getFunctionOfx0,roundedX1,getFunctionOfx1,roundedx2,getFunctionofx2, SecantContent.getEa()));
            
            tempx1 = roundedX1;
            roundedX1 = roundedx2;
            roundedX0 = tempx1;

            //x1 = x2
            //x0 = x1
            oldX2 = roundedx2;



            System.out.println("x2: " + roundedx2 + " x1: " + roundedX1 + " x0: "+ roundedX0 + " ea: " + ea);
            if(iter > 1 && ea <= roundedTolerance){
                estimated_root = String.valueOf(roundedx2);
                break;
            }


        }while(true);

    }
    private double roundToDecimalPlaces(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public int getRowCount() {
        return bisectionContents.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BisectionContent bsc = bisectionContents.get(rowIndex);
        if(columnIndex == 0){
            return bsc.getIteration();
        } else if (columnIndex == 1) {
            return bsc.getX0();
        } else if (columnIndex == 2) {
            return bsc.getFunc_x0();
        } else if (columnIndex == 3) {
            return bsc.getX1();
        } else if (columnIndex == 4) {
            return bsc.getFunc_x1();
        } else if (columnIndex == 5) {
            return bsc.getX2();
        } else if (columnIndex == 6) {
            return bsc.getFunc_x2();
        } else if (columnIndex == 7) {
            return bsc.getEa();
        }

        return null;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }

    public static void main(String[] args) {
        SecantTableModel model = new SecantTableModel();
    }
    //testing
}

