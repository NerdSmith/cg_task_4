package ru.vsu.cs.third_dimension;


import ru.vsu.cs.linear_alg.Matrix4;
import ru.vsu.cs.linear_alg.Vector3;
import ru.vsu.cs.linear_alg.Vector4;

public class Camera implements CameraBase {
    private Matrix4 translate, rotate, scale, projection;

    public Camera() {
        translate = Matrix4.one();
        rotate = Matrix4.one();
        scale = Matrix4.one();
        projection = Matrix4.one();
    }

    @Override
    public Vector3 realToScreen(Vector3 v) {
        return projection.mul(
            translate.mul(
                rotate.mul(
                    scale.mul(
                        new Vector4(v, 1)
                    )
                )
            )
        ).asVector3();
    }
    
    public void modifyProjection(Matrix4 matrix) {
        this.projection = matrix.mul(this.projection);
    }

    public Matrix4 getProjection() {
        return projection;
    }

    public void setProjection(Matrix4 projection) {
        this.projection = projection;
    }

    public void modifyRotate(Matrix4 matrix) {
        this.rotate = matrix.mul(this.rotate);
    }
    
    public Matrix4 getRotate() {
        return rotate;
    }

    public void setRotate(Matrix4 rotate) {
        this.rotate = rotate;
    }

    public void modifyScale(Matrix4 matrix) {
        this.scale = matrix.mul(this.scale);
    }
    
    public Matrix4 getScale() {
        return scale;
    }

    public void setScale(Matrix4 scale) {
        this.scale = scale;
    }

    public void modifyTranslate(Matrix4 matrix) {
        this.translate = matrix.mul(this.translate);
    }
    
    public Matrix4 getTranslate() {
        return translate;
    }

    public void setTranslate(Matrix4 translate) {
        this.translate = translate;
    }
}
