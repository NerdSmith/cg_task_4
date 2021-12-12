package ru.vsu.cs;

import ru.vsu.cs.linear_alg.Axis;
import ru.vsu.cs.linear_alg.Matrix4Factories;
import ru.vsu.cs.linear_alg.Vector3;
import ru.vsu.cs.linear_alg.Vector4;
import ru.vsu.cs.screen.ScreenConverter;
import ru.vsu.cs.screen.ScreenPoint;
import ru.vsu.cs.third_dimension.Camera;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class CameraController implements MouseListener, MouseMotionListener, MouseWheelListener {

    public interface RepaintListener {
        void forceRepaint();
    }

    private Set<RepaintListener> listeners = new HashSet<>();

    public void addRepaintListener(RepaintListener listener) {
        listeners.add(listener);
    }

    public void removeRepaintListener(RepaintListener listener) {
        listeners.remove(listener);
    }

    protected void onRepaint() {
        for (RepaintListener listener : listeners)
            listener.forceRepaint();
    }

    private Camera camera;
    private ScreenConverter screenConverter;

    public CameraController(Camera camera, ScreenConverter screenConverter) {
        this.camera = camera;
        this.screenConverter = screenConverter;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    /*Здесь запоминаем последнее положение мыши, для которого обрабатывали событие*/
    private Point last;
    /*Флаг, фиксирующий, зажата ли сейчас левая кнопка мыши*/
    private boolean leftFlag = false;
    /*Флаг, фиксирующий, зажата ли сейчас правая кнопка мыши*/
    private boolean rightFlag = false;
    /*Флаг, фиксирующий, зажата ли сейчас средняя кнопка мыши*/
    private boolean middleFlag = false;

    @Override
    public void mousePressed(MouseEvent e) {
        /*Устанавливаем флаги кнопок мыши*/
        if (SwingUtilities.isLeftMouseButton(e))
            leftFlag = true;
        if (SwingUtilities.isRightMouseButton(e))
            rightFlag = true;
        if (SwingUtilities.isMiddleMouseButton(e))
            middleFlag = true;
        last = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        /*Снимаем флаги кнопок мыши*/
        if (SwingUtilities.isLeftMouseButton(e))
            leftFlag = false;
        if (SwingUtilities.isRightMouseButton(e))
            rightFlag = false;
        if (SwingUtilities.isMiddleMouseButton(e))
            middleFlag = false;

        /*Если оба сняты, то забываем точку*/
        if (!leftFlag && !rightFlag && !middleFlag)
            last = null;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point current = e.getPoint();
        if (last != null) {
            /*Вычисляем разницу в пикселях*/
            int dx = current.x - last.x;
            int dy = current.y - last.y;
            /*Если двигаем с зажатой левой кнопкой мыши, то вращаем камеру*/
            if (leftFlag) {
                double da = dx * Math.PI / 180;
                double db = dy * Math.PI / 280;
                camera.modifyRotate(
                        Matrix4Factories.rotationXYZ(da, Axis.Y)
                                .mul(
                                        Matrix4Factories.rotationXYZ(db, Axis.X)
                                )
                );
            }
            /*Если двигаем с зажатой правой кнопкой мыши, то перемещаем камеру вдоль осей X и Y*/
            if (rightFlag) {
                Vector4 zero = new Vector4(screenConverter.screenToReal(new ScreenPoint(0, 0)), 0);
                Vector4 cur = new Vector4(screenConverter.screenToReal(new ScreenPoint(dx, dy)), 0);

                /*Вектор смещения в реальных координатах с точки зрения камеры*/
                Vector3 delta = cur.add(zero.mul(-1)).asVector3();
                camera.modifyTranslate(Matrix4Factories.translation(delta));
            }
            /* Если двигаем с зажатой средней кнопкой мыши, то перемещаем камеру
             * вдоль оси Z на расстояние равное изменению положения мыши в реальных координатах.
             * Направление выбирается положительное при движении вверх.
             */
            if (middleFlag && dy != 0) {
                Vector4 zero = new Vector4(screenConverter.screenToReal(new ScreenPoint(0, 0)), 0);
                Vector4 cur = new Vector4(screenConverter.screenToReal(new ScreenPoint(dx, dy)), 0);
                /*Длина вектор смещения в реальных координатах с точки зрения камеры*/
                float length = cur.add(zero.mul(-1)).asVector3().length();
                if (dy < 0)
                    length = -length;
                System.out.println(length);
                camera.modifyTranslate(Matrix4Factories.translation(0, 0, length));
            }
        }
        last = current;
        onRepaint(); /*Оповещаем всех, что мы изменили камеру и её надо перерисовать*/
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int delta = e.getWheelRotation();
        /*Если зажат Control, то будем менять параметры перспективы, иначе - масштаба*/
        if (e.isControlDown()) {
            /*delta*5f - экспериментально подобранное число. Чем меньше, тем быстрее будет изменяться точка схода*/
            camera.modifyProjection(Matrix4Factories.centralProjection(delta*5f, Axis.Z));
        } else {
            /*Вычислим коэффициент масштаба*/
            float factor = 1;
            float scale = delta < 0 ? 0.9f : 1.1f;
            int counter = delta < 0 ? -delta : delta;
            while (counter-- > 0)
                factor *= scale;
            camera.modifyScale(Matrix4Factories.scale(factor));
        }
        onRepaint();
    }
}
