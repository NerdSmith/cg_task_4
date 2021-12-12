package ru.vsu.cs.util;

import ru.vsu.cs.linear_alg.Vector3;
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

    private class Polygon {
        private ArrayList<Integer> verticesNumbers;

        Polygon(int size) {
            verticesNumbers = new ArrayList<>(size);
        }

        public void addVertexNb(int vertexNb) {
            verticesNumbers.add(vertexNb);
        }

        public ArrayList<Integer> getVerticesNumbers() {
            return verticesNumbers;
        }
    };

    public Model load(File file) {
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
            e.printStackTrace();
        }

        ArrayList<PolyLine3D> lines = new ArrayList<>();
        ArrayList<Vector3> vertices = new ArrayList<>();
        ArrayList<Polygon> polygons = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String currStr = scanner.nextLine();
            VectorType currVectorType = getVectorType(currStr);
            fillVectors(currStr, currVectorType, vertices, polygons);
        }

        for (Polygon polygon: polygons) {
            ArrayList<Vector3> currVertices = new ArrayList<>();

            for (int vertexNb: polygon.getVerticesNumbers()) {
                currVertices.add(vertices.get(vertexNb - 1));
            }

            lines.add(
                    new PolyLine3D(currVertices, true)
            );
        }

        return new Model(lines);
    }

    private void fillVectors(
            String str,
            VectorType vectorType,
            ArrayList<Vector3> vertices,
            ArrayList<Polygon> faces
    ) {
        if (vectorType == VectorType.VERTEX) {
            vertices.add(parseVertex(str));
        }
        else if (vectorType == VectorType.FACE) {
            faces.add(parsePolygons(str));
        }
    }

    private VectorType getVectorType(String str) {
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

    private Vector3 parseVertex(String str) { //v 1.000000 -1.000000 -1.000000
        String[] splittedStr = str.split("\\s+");
        float[] val = new float[3];

        for (int elIdx = 1; elIdx < 4; elIdx++) {
            val[elIdx - 1] = Float.parseFloat(splittedStr[elIdx]);
        }
        return new Vector3(val[0], val[1], val[2]);
    }

    private Polygon parsePolygons(String str) { //f 2//1 4//1 1//1
        String[] splittedStr = str.split("\\s+");
        Polygon polygon = new Polygon(splittedStr.length - 1);

        for (int elIdx = 1; elIdx < splittedStr.length; elIdx++) {
            String[] currElements = splittedStr[elIdx].split("/");

            String vertexNb = currElements[0];
            polygon.addVertexNb(Integer.parseInt(vertexNb));
        }
        return polygon;
    }

    public static void main(String[] args) {
        Model model = new ModelLoader().load(new File("resources/pyromid.obj"));
        System.out.println("asd");
    }
}
