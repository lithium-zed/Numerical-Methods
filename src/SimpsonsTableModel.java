import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class SimpsonsTableModel extends AbstractTableModel {

    //create objects
    ArrayList<SimpsonsContent> simpsonsContents;
    String iValue;
    String[] columns = {"","x","f(x)"};

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
    public void computeSimpsons(String function, double a, double b, double n, int decimalPlaces){
        SimpsonsContent sc=new SimpsonsContent();
        MathJSAPIConnection functionOfX=new MathJSAPIConnection();

        double step=(b-a)/n;
        double roundedStep=roundToDecimalPlaces(step,decimalPlaces);

        int iter=-1;
        double iValueAddition=0;

        //for loop for the simpsons rule method
        double lastXValue=0;
        for(double i=a;i<=b;i+=roundedStep){
            i=roundToDecimalPlaces(i,decimalPlaces);
            iter++;
            lastXValue=i;
            System.out.println(i);
            double getFunctionOfX=roundToDecimalPlaces(Double.parseDouble(functionOfX.evaluateFunctionAtValue(function,i)),decimalPlaces);
            System.out.println("x_"+iter+" = "+getFunctionOfX);

            //addition of I value
            if(iter==0||i==b){
                iValueAddition+=getFunctionOfX;
                System.out.println("x_"+iter+" * 1 = "+getFunctionOfX);
            }else{
                if(iter%2==1){
                    iValueAddition+=getFunctionOfX*4;
                    System.out.println("x_"+iter+" * 4 = "+getFunctionOfX*4);
                }else{
                    iValueAddition+=getFunctionOfX*2;
                    System.out.println("x_"+iter+" * 2 = "+getFunctionOfX*2);
                }
            }
            addToTable(new SimpsonsContent("x_"+iter,lastXValue,getFunctionOfX));
            this.fireTableDataChanged();
        }
        if(lastXValue<b){
            iter++;
            double getFunctionOfX=roundToDecimalPlaces(Double.parseDouble(functionOfX.evaluateFunctionAtValue(function,b)),decimalPlaces);
            System.out.println("x_"+iter+" = "+getFunctionOfX);
            iValueAddition+=getFunctionOfX;
            System.out.println("x_"+iter+" * 1 = "+getFunctionOfX);
            addToTable(new SimpsonsContent("x_"+iter,lastXValue,getFunctionOfX));
            this.fireTableDataChanged();
        }
        double iValue= roundToDecimalPlaces((roundedStep/3)*iValueAddition,decimalPlaces);
        this.iValue = String.valueOf(iValue);
        sc.setiValue(iValue);
        System.out.println(iValue);
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
        SimpsonsContent content = simpsonsContents.get(rowIndex);
        if(columnIndex==0){
            return content.getX();
        }else if(columnIndex==1){
            return content.getxValue();
        }else {
            return content.getxFunction();
        }
    }

    public static void main(String[] args) {
        SimpsonsTableModel tableModel = new SimpsonsTableModel();
        MathJSAPIConnection func = new MathJSAPIConnection();
        System.out.println(func.evaluateFunctionAtValue("e^x",-1));
        //tableModel.computeSimpsons("e^x", -1, 1, 2, 2);
    }

}