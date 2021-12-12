package ru.vsu.cs.util;

import ru.vsu.cs.linear_alg.Vector3;
import ru.vsu.cs.linear_alg.Vector4;
import ru.vsu.cs.third_dimension.PolyLine3D;
import ru.vsu.cs.third_dimension.models.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Вспомогательный класс, преобразующий файлы .obj в модели для программы
 * @author Nikita NS Merzlyakov
 */
public class ModelLoader {
    private enum VectorType {VERTEX, FACE}

    public static Model load(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }

        ArrayList<PolyLine3D> lines = new ArrayList<>();
        ArrayList<Vector3> vertices = new ArrayList<>();
        ArrayList<Vector4> faces = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String currStr = scanner.nextLine();
            VectorType currVectorType = getVectorType(currStr);
            fillVectors(currStr, currVectorType, vertices, faces);
        }

        for (Vector4 face: faces) {
            ArrayList<Vector3> polygon = new ArrayList<>(Arrays.asList(
                    vertices.get(((int) face.getX()) - 1),
                    vertices.get(((int) face.getY()) - 1),
                    vertices.get(((int) face.getZ()) - 1)
            ));
            if (face.getW() != Integer.MAX_VALUE) {
                polygon.add(vertices.get(((int) face.getW()) - 1));
            }
            lines.add(
                    new PolyLine3D(polygon, true)
            );
        }

        return new Model(lines);
    }

    private static void fillVectors(
            String str,
            VectorType vectorType,
            ArrayList<Vector3> vertices,
            ArrayList<Vector4> faces
    ) {
        if (vectorType == VectorType.VERTEX) {
            vertices.add(parseVertex(str));
        }
        else if (vectorType == VectorType.FACE) {
            faces.add(parseFace(str));
        }
    }

    private static VectorType getVectorType(String str) {
        if (str.startsWith("v ")) {
            return VectorType.VERTEX;
        }
        else if (str.startsWith("f ")) {
            return VectorType.FACE;
        }
        else {
            return null;
        }
    }

    private static Vector3 parseVertex(String str) { //v 1.000000 -1.000000 -1.000000
        String[] splittedStr = str.split("\\s+");
        float[] val = new float[3];

        for (int elIdx = 1; elIdx < 4; elIdx++) {
            val[elIdx - 1] = Float.parseFloat(splittedStr[elIdx]);
        }
        return new Vector3(val[0], val[1], val[2]);
    }

    private static Vector4 parseFace(String str) { //f 2//1 4//1 1//1
        String[] splittedStr = str.split("\\s+");
        int[] val;
        if (splittedStr.length > 4) {
            val = new int[4];
        }
        else {
            val = new int[3];
        }


        for (int elIdx = 1; elIdx < splittedStr.length; elIdx++) {
            String[] currElements = splittedStr[elIdx].split("/");

            String vertexNb = currElements[0];
            val[elIdx - 1] = Integer.parseInt(vertexNb);
        }
        if (splittedStr.length > 4) {
            return new Vector4(val[0], val[1], val[2], val[3]);
        }
        else {
            return new Vector4(val[0], val[1], val[2], Integer.MAX_VALUE);
        }

    }

    public static void main(String[] args) {
        Model model = ModelLoader.load(new File("resources/pyromid.obj"));
        System.out.println("asd");
    }
}
