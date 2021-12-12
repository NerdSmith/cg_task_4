package ru.vsu.cs.third_dimension;

import ru.vsu.cs.draw_helper.IDrawer;
import ru.vsu.cs.linear_alg.Vector3;
import ru.vsu.cs.third_dimension.models.Line3D;
import ru.vsu.cs.third_dimension.models.ModelBase;

import java.util.*;

public class Scene {
    private List<ModelBase> models = new ArrayList<>();

    public List<ModelBase> getModelsList() {
        return models;
    }
    
    private int backgroundColor;

    public Scene(int backgroundColorRGB) {
        this.backgroundColor = backgroundColorRGB;
        this.showAxes = false;
    }
    
    private boolean showAxes;

    public boolean isShowAxes() {
        return showAxes;
    }

    public void setShowAxes(boolean showAxis) {
        this.showAxes = showAxis;
    }
    
    public void showAxes() {
        this.showAxes = true;
    }
    
    public void hideAxes() {
        this.showAxes = false;
    }
    
    private static final List<Line3D> axes = Arrays.asList(
            new Line3D(new Vector3(0, 0, 0), new Vector3(1, 0, 0)),
            new Line3D(new Vector3(0, 0, 0), new Vector3(0, 1, 0)),
            new Line3D(new Vector3(0, 0, 0), new Vector3(0, 0, 1))
    );

    public void drawScene(IDrawer drawer, CameraBase camera) {
        List<PolyLine3D> lines = new LinkedList<>();
        LinkedList<Collection<? extends ModelBase>> allModels = new LinkedList<>();
        allModels.add(models);
        if (isShowAxes())
            allModels.add(axes);
        for (Collection<? extends ModelBase> mc : allModels)
            for (ModelBase m : mc) {
                for (PolyLine3D pl : m.getLines()) {
                    List<Vector3> points = new LinkedList<>();
                    for (Vector3 v : pl.getPoints()) {
                        points.add(camera.realToScreen(v));
                    }
                    lines.add(new PolyLine3D(points, pl.isClosed()));
                }
            }
        drawer.clear(backgroundColor);
        drawer.draw(lines);
    }
}
