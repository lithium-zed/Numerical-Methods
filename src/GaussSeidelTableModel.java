import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class GaussSeidelTableModel extends AbstractTableModel {
    ArrayList<GaussSeidelContent> gaussSeidelContents;
    String x1,x2,x3;
    String[] header = {"Iteration", "x", "y", "z", "x'", "y'", "z'", "EA,x'", "EA,y'","EA,z'"};

    public GaussSeidelTableModel() {
        this.x1 = "";
        this.x2 = "";
        this.x3 = "";
        gaussSeidelContents = new ArrayList<>();
    }
    public void addToTable(GaussSeidelContent gaussSeidelContent){
        gaussSeidelContents.add(gaussSeidelContent);
        this.fireTableDataChanged();
    }
    public void computeGaussSeidel(String eq1, String eq2, String eq3, double tolerance, int decimalPlaces){

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
        return gaussSeidelContents.size();
    }

    @Override
    public int getColumnCount() {
        return header.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        GaussSeidelContent gsc = new GaussSeidelContent();
        if(columnIndex == 0){
            return gsc.getIteration();
        } else if (columnIndex == 1) {
            return gsc.getX();
        } else if (columnIndex == 2) {
            return gsc.getY();
        } else if (columnIndex == 3) {
            return gsc.getZ();
        } else if (columnIndex == 4) {
            return gsc.getpX();
        } else if (columnIndex == 5) {
            return gsc.getpY();
        } else if (columnIndex == 6) {
            return gsc.getpZ();
        } else if (columnIndex == 7) {
            return gsc.getEaX();
        }else if (columnIndex == 8) {
            return gsc.getEaY();
        }else if (columnIndex == 9) {
            return gsc.getEaZ();
        }
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }
}
