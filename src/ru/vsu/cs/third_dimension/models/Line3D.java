package ru.vsu.cs.third_dimension.models;


import ru.vsu.cs.linear_alg.Vector3;
import ru.vsu.cs.third_dimension.PolyLine3D;

import java.util.Arrays;
import java.util.List;

public class Line3D implements ModelBase {
    private Vector3 p1, p2;

    public Line3D(Vector3 p1, Vector3 p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    @Override
    public List<PolyLine3D> getLines() {
        return Arrays.asList(new PolyLine3D(
                Arrays.asList(p1, p2),
                false)
        );
    }
    
}
