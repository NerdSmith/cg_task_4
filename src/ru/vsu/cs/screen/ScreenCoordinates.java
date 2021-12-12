package ru.vsu.cs.screen;

import java.util.Collection;

public class ScreenCoordinates {
    private int[] xx, yy;

    public ScreenCoordinates(Collection<ScreenPoint> points) {
        xx = new int[points.size()];
        yy = new int[points.size()];
        int i = 0;
        for (ScreenPoint p : points) {
            xx[i] = p.getX();
            yy[i] = p.getY();
            i++;
        }
    }

    public int[] getXx() {
        return xx;
    }

    public int[] getYy() {
        return yy;
    }

    public int size() {
        return xx.length;
    }
}
