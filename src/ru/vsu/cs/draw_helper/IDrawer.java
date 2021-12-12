package ru.vsu.cs.draw_helper;

import ru.vsu.cs.third_dimension.PolyLine3D;

import java.util.Collection;

public interface IDrawer {
    public void clear(int color);

    public void draw(Collection<PolyLine3D> polyline);
}
