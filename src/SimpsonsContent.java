public class SimpsonsContent {
    private double xFunction, iValue;
    private String x;

    public SimpsonsContent(String x, double xFunction){
        this.x=x;
        this.xFunction=xFunction;
    }

    public SimpsonsContent(){

    }

    public double getxFunction() {
        return xFunction;
    }

    public void setxFunction(double xFunction) {
        this.xFunction = xFunction;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public double getiValue() {
        return iValue;
    }

    public void setiValue(double iValue) {
        this.iValue = iValue;
    }
}
