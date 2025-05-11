import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class SimpsonsTableModel extends AbstractTableModel {

    //create objects
    ArrayList<SimpsonsContent> simpsonsContents;
//    String estimated_root;
    String[] columns = {"x","f(x)"};

    //instantiate
    public SimpsonsTableModel(){
        //this.estimated_root="";
        simpsonsContents=new ArrayList<>();
    }

    public void addToTable(SimpsonsContent simpsonsContent){
        simpsonsContents.add(simpsonsContent);
        this.fireTableDataChanged();
    }
    public void deleteFromTable(int[] index){
        if(this.getRowCount()>1){
            for(int i=index.length-1;i>=0;i--){
                simpsonsContents.remove(index[i]);
            }
        }else{
            simpsonsContents.remove(index[0]);
        }
        this.fireTableDataChanged();
    }

    //algorithms
    public void computeSimpsons(String function, double a, double b, double tolerance, int decimalPlaces){

    }
    private double roundToDecimalPlaces(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    //overrides
    @Override
    public int getRowCount() {
        return simpsonsContents.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column){
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return null;
    }
}