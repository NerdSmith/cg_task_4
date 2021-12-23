package ru.vsu.cs.util;

import ru.vsu.cs.linear_alg.Axis;
import ru.vsu.cs.linear_alg.Vector3;
import ru.vsu.cs.third_dimension.PolyLine3D;
import ru.vsu.cs.third_dimension.models.ModelBase;

import java.util.List;

public class CoordinateUtils {
    public static float getMaxCoordinate(ModelBase model, Axis axis) {
        List<PolyLine3D> polyLines = model.getLines();
        float maxCoordinate = Float.MIN_VALUE;

        for (PolyLine3D polyLine3D: polyLines) {
            for (Vector3 point: polyLine3D.getPoints()) {
                maxCoordinate = getMaxByAxis(maxCoordinate, point, axis);
            }
        }
        return maxCoordinate;
    }

    public static float getMaxByAxis(float maxCoordinate, Vector3 point, Axis axis) {
        switch (axis) {
            case X:
                return Math.max(maxCoordinate, point.getX());
            case Y:
                return Math.max(maxCoordinate, point.getY());
            case Z:
                return Math.max(maxCoordinate, point.getZ());
        }
        return 0;
    }

    public static float getMinCoordinate(ModelBase model, Axis axis) {
        List<PolyLine3D> polyLines = model.getLines();
        float minCoordinate = Float.MAX_VALUE;

        for (PolyLine3D polyLine3D: polyLines) {
            for (Vector3 point: polyLine3D.getPoints()) {
                minCoordinate = getMinByAxis(minCoordinate, point, axis);
            }
        }
        return minCoordinate;
    }

    public static float getMinByAxis(float minCoordinate, Vector3 point, Axis axis) {
        switch (axis) {
            case X:
                return Math.min(minCoordinate, point.getX());
            case Y:
                return Math.min(minCoordinate, point.getY());
            case Z:
                return Math.min(minCoordinate, point.getZ());
        }
        return 0;
    }
}
