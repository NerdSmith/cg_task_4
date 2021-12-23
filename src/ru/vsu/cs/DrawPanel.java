package ru.vsu.cs;

import ru.vsu.cs.draw_helper.DrawerBase;
import ru.vsu.cs.draw_helper.SimpleEdgeDrawer;
import ru.vsu.cs.linear_alg.Vector3;
import ru.vsu.cs.screen.ScreenConverter;
import ru.vsu.cs.third_dimension.Camera;
import ru.vsu.cs.third_dimension.Scene;
import ru.vsu.cs.third_dimension.models.ModelBase;
import ru.vsu.cs.third_dimension.models.Parallelepiped;
import ru.vsu.cs.third_dimension.models.Voxel;
import ru.vsu.cs.util.ModelLoader;
import ru.vsu.cs.util.VoxelService;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class DrawPanel extends JPanel implements CameraController.RepaintListener {
    private Scene scene;
    private ScreenConverter screenConverter;
    private Camera camera;
    private CameraController cameraController;

    public DrawPanel() {
        super();
        screenConverter = new ScreenConverter(-1, 1, 2, 2, 1, 1);
        camera = new Camera();
        cameraController = new CameraController(camera, screenConverter);
        scene = new Scene(Color.WHITE.getRGB());
        scene.showAxes();

//        scene.getModelsList().add(new Parallelepiped(new Vector3(0, 0, 0), 2));
//        scene.getModelsList().add(new Parallelepiped(
//                new Vector3(-0.5f, -0.5f, 0.5f),
//                new Vector3(0.5f, 0.5f, -0.5f)
//        ));

        scene.getModelsList().add(new ModelLoader().load(new File("resources/lowPolygon/humanoid.obj")));
        VoxelService voxelService = new VoxelService();

        for (Voxel voxel: voxelService.toVoxels(scene.getModelsList().get(0), 1f)) {
            scene.getModelsList().add(voxel);
        }


        cameraController.addRepaintListener(this);
        addMouseListener(cameraController);
        addMouseMotionListener(cameraController);
        addMouseWheelListener(cameraController);
    }

    @Override
    public void paint(Graphics g) {
        screenConverter.setScreenSize(getWidth(), getHeight());
        BufferedImage bi = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = (Graphics2D)bi.getGraphics();
        DrawerBase dr = new SimpleEdgeDrawer(screenConverter, graphics);
        scene.drawScene(dr, camera);
        g.drawImage(bi, 0, 0, null);
        graphics.dispose();
    }

    @Override
    public void forceRepaint() {
        repaint();
    }
}
