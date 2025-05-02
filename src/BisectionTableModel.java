import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class BisectionTableModel extends AbstractTableModel {
    ArrayList<BisectionContent> bisectionContents;
    String[] header = {"Iteration", "x0", "f(x0)", "x1", "f(x1)", "x2", "f(x2)", "EA"};
    public BisectionTableModel() {
        this.bisectionContents = new ArrayList<>();
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
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }
}
