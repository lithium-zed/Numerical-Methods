import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class BisectionTableModel extends AbstractTableModel {
    ArrayList<BisectionContent> bisectionContents;
    String[] header = {"Iteration", "x0", "f(x0)", "x1", "f(x1)", "x2", "f(x2)", "EA"};
    public BisectionTableModel() {
        this.bisectionContents = new ArrayList<>();
    }

    public void addToTable(BisectionContent bisectionContent){
        bisectionContents.add(bisectionContent);
        this.fireTableDataChanged();
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
}
