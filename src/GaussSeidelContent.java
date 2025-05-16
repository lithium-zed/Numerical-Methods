public class GaussSeidelContent {
    private int iteration;
    private double x1,x2,x3,px1,px2,px3,eax1,eax2,eax3;

    public GaussSeidelContent(int iteration, double x1, double x2, double x3, double px1, double px2, double px3, double eax1, double eax2, double eax3) {
        this.iteration = iteration;
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.px1 = px1;
        this.px2 = px2;
        this.px3 = px3;
        this.eax1 = eax1;
        this.eax2 = eax2;
        this.eax3 = eax3;
    }

    public GaussSeidelContent() {
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
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

    public double getX3() {
        return x3;
    }

    public void setX3(double x3) {
        this.x3 = x3;
    }

    public double getPx1() {
        return px1;
    }

    public void setPx1(double px1) {
        this.px1 = px1;
    }

    public double getPx2() {
        return px2;
    }

    public void setPx2(double px2) {
        this.px2 = px2;
    }

    public double getPx3() {
        return px3;
    }

    public void setPx3(double px3) {
        this.px3 = px3;
    }

    public double getEax1() {
        return eax1;
    }

    public void setEax1(double eax1) {
        this.eax1 = eax1;
    }

    public double getEax2() {
        return eax2;
    }

    public void setEax2(double eax2) {
        this.eax2 = eax2;
    }

    public double getEax3() {
        return eax3;
    }

    public void setEax3(double eax3) {
        this.eax3 = eax3;
    }
}
