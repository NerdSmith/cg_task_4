package ru.vsu.cs.screen;

import ru.vsu.cs.linear_alg.Vector3;

public class ScreenConverter {
    double xReal, yReal, wReal, hReal;
    int wScreen, hScreen;

    public ScreenConverter(double xReal, double yReal, double wReal, double hReal, int wScreen, int hScreen) {
        this.xReal = xReal;
        this.yReal = yReal;
        this.wReal = wReal;
        this.hReal = hReal;
        this.wScreen = wScreen;
        this.hScreen = hScreen;
    }

    public ScreenPoint realToScreen(Vector3 vector) {
        int i = (int)((vector.getX() - xReal) * wScreen / wReal);
        int j = (int)((yReal - vector.getY()) * hScreen / hReal);
        return new ScreenPoint(i, j);
    }

    public Vector3 screenToReal(ScreenPoint p, float z) {
        double x = xReal + p.getX() * wReal / wScreen;
        double y = yReal - p.getY() * hReal / hScreen;
        return new Vector3((float)x, (float)y, z);
    }

    public Vector3 screenToReal(ScreenPoint p) {
        return screenToReal(p, 0);
    }

    public void setScreenSize(int w, int h) {
        setwScreen(w);
        sethScreen(h);
    }

    public double gethReal() {
        return hReal;
    }

    public void sethReal(double hReal) {
        this.hReal = hReal;
    }

    public int gethScreen() {
        return hScreen;
    }

    public void sethScreen(int hScreen) {
        this.hScreen = hScreen;
    }

    public double getwReal() {
        return wReal;
    }

    public void setwReal(double wReal) {
        this.wReal = wReal;
    }

    public int getwScreen() {
        return wScreen;
    }

    public void setwScreen(int wScreen) {
        this.wScreen = wScreen;
    }

    public double getxReal() {
        return xReal;
    }

    public void setxReal(double xReal) {
        this.xReal = xReal;
    }

    public double getyReal() {
        return yReal;
    }

    public void setyReal(double yReal) {
        this.yReal = yReal;
    }
    
}
