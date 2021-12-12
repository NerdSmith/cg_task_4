package ru.vsu.cs.third_dimension;

import ru.vsu.cs.linear_alg.Vector3;

public interface CameraBase {
    Vector3 realToScreen(Vector3 vector);
}
