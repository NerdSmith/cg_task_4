package ru.vsu.cs.util;

import ru.vsu.cs.linear_alg.Vector2;

import java.util.List;

public class MathUtils {
    private final static float EPSILON = 10e-6f;

    public static float getPolygon2dSquare(List<Vector2> polygon2dSquare) {

        double area = 0.0;

        int j = polygon2dSquare.size() - 1;
        for (int i = 0; i < polygon2dSquare.size(); i++)
        {
            area += (polygon2dSquare.get(j).getFirst() + polygon2dSquare.get(i).getFirst()) *
                    (polygon2dSquare.get(j).getSecond() - polygon2dSquare.get(i).getFirst());

            j = i;
        }
        float res = (float) Math.abs(area / 2.0);

        if (res < EPSILON) {
            return 0;
        }
        return res;
    }
}
