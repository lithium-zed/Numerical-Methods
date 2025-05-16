import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class GaussSeidelTableModel extends AbstractTableModel {
    ArrayList<GaussSeidelContent> gaussSeidelContents;
    String x1,x2,x3;
    String[] header = {"Iteration", "x1", "x2", "x3", "x'1", "x'2", "x'3", "EA,x'1", "EA,x'2","EA,x'3"};

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
        return null;
    }

    @Override
    public String getColumnName(int column) {
        return header[column];
    }
}
