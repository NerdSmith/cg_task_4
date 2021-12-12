package ru.vsu.cs.draw_helper;

import ru.vsu.cs.screen.ScreenConverter;
import ru.vsu.cs.third_dimension.PolyLine3D;

import java.awt.*;
import java.util.*;
import java.util.List;

public abstract class ScreenGraphicsDrawer implements DrawerBase {
    private ScreenConverter screenConverter;
    private Graphics2D gr2d;

    public ScreenGraphicsDrawer(ScreenConverter screenConverter, Graphics2D gr2d) {
        this.screenConverter = screenConverter;
        this.gr2d = gr2d;
    }

    public Graphics2D getGraphics() {
        return gr2d;
    }

    public ScreenConverter getScreenConverter() {
        return screenConverter;
    }
    
    @Override
    public void draw(Collection<PolyLine3D> polylines) {
        List<PolyLine3D> lines = new LinkedList<>();
        FilterBase<PolyLine3D> filter = getFilter();
        for (PolyLine3D pl : polylines) {
            if (filter.permit(pl))
                lines.add(pl);
        }
        PolyLine3D[] arr = lines.toArray(new PolyLine3D[0]);
        Arrays.sort(arr, getComparator());
        for (PolyLine3D pl : arr) {
            oneDraw(pl);
        }
    }
    
    @Override
    public void clear(int color) {
        Graphics2D g = getGraphics();
        Color c = g.getColor();
        g.setColor(new Color(color));
        g.fillRect(0, 0, screenConverter.getwScreen(), screenConverter.getwScreen());
        g.setColor(c);
    }

    protected abstract void oneDraw(PolyLine3D polyline);

    protected abstract FilterBase<PolyLine3D> getFilter();

    protected abstract Comparator<PolyLine3D> getComparator();
}
