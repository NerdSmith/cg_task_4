package ru.vsu.cs.third_dimension.models;

import ru.vsu.cs.linear_alg.Vector3;

public class Voxel extends Parallelepiped {
    public Voxel(Vector3 center, float edgeLen) {
        super(center, edgeLen);
    }
}
