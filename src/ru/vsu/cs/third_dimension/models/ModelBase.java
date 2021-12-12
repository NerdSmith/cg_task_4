package ru.vsu.cs.third_dimension.models;

import ru.vsu.cs.third_dimension.PolyLine3D;

import java.util.List;

public interface ModelBase {
    List<PolyLine3D> getLines();
}
