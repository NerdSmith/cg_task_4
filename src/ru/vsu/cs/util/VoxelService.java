package ru.vsu.cs.util;

import ru.vsu.cs.linear_alg.Axis;
import ru.vsu.cs.linear_alg.Vector2;
import ru.vsu.cs.linear_alg.Vector3;
import ru.vsu.cs.third_dimension.PolyLine3D;
import ru.vsu.cs.third_dimension.models.ModelBase;
import ru.vsu.cs.third_dimension.models.Parallelepiped;
import ru.vsu.cs.third_dimension.models.Voxel;

import java.util.ArrayList;
import java.util.List;

import static ru.vsu.cs.util.CoordinateUtils.getMaxCoordinate;
import static ru.vsu.cs.util.CoordinateUtils.getMinCoordinate;

public class VoxelService {

    public List<Voxel> toVoxels(ModelBase model, float voxelSize) {
        List<Voxel> voxels = new ArrayList<>();
        Parallelepiped bounds = getBounds(model, voxelSize);
//        for (float currX = bounds.getLTF().getX(); currX <= bounds.getRBN().getX(); currX += voxelSize) {
//            for (float currY = bounds.getLTF().getY(); currY <= bounds.getRBN().getY(); currY += voxelSize) {
//                for (float currZ = bounds.getRBN().getZ(); currZ <= bounds.getLTF().getZ(); currZ += voxelSize) {
//                    if (isPointInsideModel(new Vector3(currX, currY, currZ))) {
//                        voxels.add(new Voxel())
//                    }
//                }
//            }
//        }
        float boundsEdgeLen = bounds.getEdgeLen();
        float halfBoundsEdgeLen = boundsEdgeLen / 2;
//        System.out.println(bounds.getCenter().getX() + " " +  bounds.getCenter().getX() + " " + bounds.getCenter().getX() + " " + bounds.getEdgeLen());
//        voxels.add(bounds.asVoxel());
        for (
                float currX = bounds.getCenter().getX() - halfBoundsEdgeLen;
                currX <= bounds.getCenter().getX() + halfBoundsEdgeLen;
                currX += voxelSize
        ) {
            for (
                    float currY = bounds.getCenter().getY() - halfBoundsEdgeLen;
                    currY <= bounds.getCenter().getY() + halfBoundsEdgeLen;
                    currY += voxelSize
            ) {
                for (
                        float currZ = bounds.getCenter().getZ() - halfBoundsEdgeLen;
                        currZ <= bounds.getCenter().getZ() + halfBoundsEdgeLen;
                        currZ += voxelSize
                ) {
                    //System.out.println("Checking: " + currX + " " + currY + " " + currZ);
                    if (isPointInsideModel(new Vector3(currX, currY, currZ), model)) {
                        voxels.add(new Voxel(new Vector3(currX, currY, currZ), voxelSize));
                        //System.out.println("passed");
                    }
                }
            }
        }

        return voxels;
    }

    private boolean isPointInsideModel(Vector3 point, ModelBase model) {
        int xRayIntersects = numberOfPolygonIntersectionsByAxis(point, model, Axis.Y, Axis.Z);
        int yRayIntersects = numberOfPolygonIntersectionsByAxis(point, model, Axis.X, Axis.Z);
        int zRayIntersects = numberOfPolygonIntersectionsByAxis(point, model, Axis.X, Axis.Y);
//        if (xRayIntersects != 0 || yRayIntersects != 0 || zRayIntersects != 0)
//            System.out.println("Intersects "+ xRayIntersects + " " + yRayIntersects + " " + zRayIntersects);

        return xRayIntersects != 0 && yRayIntersects != 0 && zRayIntersects != 0;
    }

    private int numberOfPolygonIntersectionsByAxis(Vector3 point, ModelBase model, Axis firstAxis, Axis secondAxis) {
        int intersections = 0;

        for (PolyLine3D polygon: model.getLines()) {
            List<Vector2> projectedPolygon = new ArrayList<>();

            for (Vector3 point3d: polygon.getPoints()) {
                projectedPolygon.add(new Vector2(point3d.getByAxis(firstAxis), point3d.getByAxis(secondAxis)));
            }
//            if (firstAxis == Axis.X && secondAxis == Axis.Y)
//                System.out.print(projectedPolygon + " ");

            float square = MathUtils.getPolygon2dSquare(projectedPolygon);
//            if (firstAxis == Axis.X && secondAxis == Axis.Y)
//                System.out.println(square);
            if (square != 0) {
                Vector2 projectedPoint = new Vector2(point.getByAxis(firstAxis), point.getByAxis(secondAxis));
//                intersections += polygon2dContainsPoint(projectedPolygon, projectedPoint) ? 1 : 0;
                intersections += pointInPolygon(projectedPolygon, projectedPoint) ? 1 : 0;
            }
        }
        return intersections;
    }

    public boolean pointInPolygon(List<Vector2> polygon, Vector2 point) {
        boolean odd = false;

        for (int i = 0, j = polygon.size() - 1; i < polygon.size(); i++) {

            if (((polygon.get(i).getSecond() > point.getSecond()) != (polygon.get(j).getSecond() > point.getSecond()))
                    && (point.getFirst() < (polygon.get(j).getFirst() - polygon.get(i).getFirst()) * (point.getSecond() - polygon.get(i).getSecond()) /
                    (polygon.get(j).getSecond() - polygon.get(i).getSecond()) + polygon.get(i).getFirst())) {

                odd = !odd;
            }

            j = i;
        }

        return odd;
    }


//    private boolean polygon2dContainsPoint(List<Vector2> projectedPolygon, Vector2 projectedPoint) {
//        boolean result = false;
//        int j = projectedPolygon.size() - 1;
//
//        for (int i = 0; i < projectedPolygon.size(); i++) {
//            if (
//                    (
//                            projectedPolygon.get(i).getSecond() < projectedPoint.getSecond() &&
//                                    projectedPolygon.get(j).getSecond() >= projectedPoint.getSecond() ||
//                                    projectedPolygon.get(j).getSecond() < projectedPoint.getSecond() &&
//                                            projectedPolygon.get(i).getSecond() >= projectedPoint.getSecond()
//                    ) &&
//                    (
//                            projectedPolygon.get(i).getFirst() +
//                                    (projectedPoint.getSecond() - projectedPolygon.get(i).getSecond()) /
//                                            (projectedPolygon.get(j).getSecond() - projectedPolygon.get(i).getSecond()) *
//                                            (projectedPolygon.get(j).getFirst() - projectedPolygon.get(i).getFirst()) < projectedPoint.getFirst()
//                    )
//            )
//                result = !result;
//            j = i;
//        }
//
//        return result;
//    }

    private Parallelepiped getBounds(ModelBase model, float voxelSize) {
        float xMax = getMaxCoordinate(model, Axis.X) + voxelSize*10;
        float yMax = getMaxCoordinate(model, Axis.Y) + voxelSize*10;
        float zMax = getMaxCoordinate(model, Axis.Z) + voxelSize*10;
        float xMin = getMinCoordinate(model, Axis.X) - voxelSize*10;
        float yMin = getMinCoordinate(model, Axis.Y) - voxelSize*10;
        float zMin = getMinCoordinate(model, Axis.Z) - voxelSize*10;

        float maxLen = Float.MIN_VALUE;
        maxLen = Math.max(maxLen, xMax - xMin);
        maxLen = Math.max(maxLen, yMax - yMin);
        maxLen = Math.max(maxLen, zMax - zMin);

        float centerX = (xMax - xMin)/2 + xMin;
        float centerY = (yMax - yMin)/2 + yMin;
        float centerZ = (zMax - zMin)/2 + zMin;

        return new Parallelepiped(
                new Vector3(centerX, centerY, centerZ),
                maxLen
        );
    }

}
