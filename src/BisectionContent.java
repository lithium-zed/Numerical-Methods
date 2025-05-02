public class BisectionContent {
    private int iteration;
    private double x0, func_x0, x1, func_x1, x2,  func_x2, ea;

    public BisectionContent(int iteration, double x0, double func_x0, double x1, double func_x1, double x2, double func_x2, double ea) {
        this.iteration = iteration;
        this.x0 = x0;
        this.func_x0 = func_x0;
        this.x1 = x1;
        this.func_x1 = func_x1;
        this.x2 = x2;
        this.func_x2 = func_x2;
        this.ea = ea;
    }

    public BisectionContent() {
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getFunc_x0() {
        return func_x0;
    }

    public void setFunc_x0(double func_x0) {
        this.func_x0 = func_x0;
    }

    public double getFunc_x1() {
        return func_x1;
    }

    public void setFunc_x1(double func_x1) {
        this.func_x1 = func_x1;
    }

    public double getFunc_x2() {
        return func_x2;
    }

    public void setFunc_x2(double func_x2) {
        this.func_x2 = func_x2;
    }

    public double getEa() {
        return ea;
    }

    public void setEa(double ea) {
        this.ea = ea;
    }
}
