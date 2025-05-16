public class GaussSeidelContent {
    private int iteration;
    private double x,y,z,pX,pY,pZ,eaX,eaY,eaZ;

    public GaussSeidelContent(int iteration, double x, double y, double z, double pX, double pY, double pZ, double eaX, double eaY, double eaZ) {
        this.iteration = iteration;
        this.x = x;
        this.y = y;
        this.z = z;
        this.pX = pX;
        this.pY = pY;
        this.pZ = pZ;
        this.eaX = eaX;
        this.eaY = eaY;
        this.eaZ = eaZ;
    }

    public GaussSeidelContent() {
    }

    public int getIteration() {
        return iteration;
    }

    public void setIteration(int iteration) {
        this.iteration = iteration;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getpX() {
        return pX;
    }

    public void setpX(double pX) {
        this.pX = pX;
    }

    public double getpY() {
        return pY;
    }

    public void setpY(double pY) {
        this.pY = pY;
    }

    public double getpZ() {
        return pZ;
    }

    public void setpZ(double pZ) {
        this.pZ = pZ;
    }

    public double getEaX() {
        return eaX;
    }

    public void setEaX(double eaX) {
        this.eaX = eaX;
    }

    public double getEaY() {
        return eaY;
    }

    public void setEaY(double eaY) {
        this.eaY = eaY;
    }

    public double getEaZ() {
        return eaZ;
    }

    public void setEaZ(double eaZ) {
        this.eaZ = eaZ;
    }
}
