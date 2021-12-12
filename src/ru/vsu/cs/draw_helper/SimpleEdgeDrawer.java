package ru.vsu.cs.draw_helper;

import ru.vsu.cs.linear_alg.Vector3;
import ru.vsu.cs.screen.ScreenConverter;
import ru.vsu.cs.screen.ScreenCoordinates;
import ru.vsu.cs.screen.ScreenPoint;
import ru.vsu.cs.third_dimension.PolyLine3D;

import java.awt.*;
import java.util.Comparator;
import java.util.LinkedList;

public class SimpleEdgeDrawer extends ScreenGraphicsDrawer {

    public SimpleEdgeDrawer(ScreenConverter sc, Graphics2D g) {
        super(sc, g);
    }

    @Override
    protected void oneDraw(PolyLine3D polyline) {
        LinkedList<ScreenPoint> points = new LinkedList<>();

        for (Vector3 v : polyline.getPoints())
            points.add(getScreenConverter().realToScreen(v));
        getGraphics().setColor(Color.BLACK);

        if (points.size() < 2) {
            if (points.size() > 0)
                getGraphics().fillRect(points.get(0).getX(), points.get(0).getY(), 1, 1);
            return;
        }

        ScreenCoordinates crds = new ScreenCoordinates(points);

        if (polyline.isClosed())
            getGraphics().drawPolygon(crds.getXx(), crds.getYy(), crds.size());
        else
            getGraphics().drawPolyline(crds.getXx(), crds.getYy(), crds.size());
    }

    @Override
    protected IFilter<PolyLine3D> getFilter() {
        return new IFilter<PolyLine3D>() {
            @Override
            public boolean permit(PolyLine3D line) {
                return true;
            }
        };
    }

    @Override
    protected Comparator<PolyLine3D> getComparator() {
        return new Comparator<PolyLine3D>() {
            private static final float EPSILON = 1e-10f;
            @Override
            public int compare(PolyLine3D o1, PolyLine3D o2) {
                float d = o1.avgZ() - o2.avgZ();
                if (-EPSILON < d && d < EPSILON)
                    return 0;
                return d < 0 ? -1 : 1;
            }
        };
    }
    
}
