public class SimpsonsContent {
    private double xFunction, xValue, iValue;
    private String x;

    public SimpsonsContent(String x,double xValue, double xFunction){
        this.x=x;
        this.xValue=xValue;
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

    public double getxValue() {
        return xValue;
    }

    public void setxValue(double xValue) {
        this.xValue = xValue;
    }
}
