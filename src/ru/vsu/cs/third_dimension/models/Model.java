package ru.vsu.cs.third_dimension.models;

import ru.vsu.cs.third_dimension.PolyLine3D;

import java.util.ArrayList;
import java.util.List;

public class Model implements ModelBase {
    private ArrayList<PolyLine3D> lines;

    public Model(ArrayList<PolyLine3D> lines) {
        this.lines = lines;
    }

    @Override
    public List<PolyLine3D> getLines() {
        return lines;
    }
}
