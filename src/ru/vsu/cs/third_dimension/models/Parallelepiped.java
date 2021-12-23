/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.vsu.cs.third_dimension.models;


import ru.vsu.cs.linear_alg.Vector3;
import ru.vsu.cs.third_dimension.PolyLine3D;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Описывает параллелипипед по двум диагональным точкам
 * @author Alexey
 */
public class Parallelepiped implements ModelBase {
    private final Vector3 LTF;
    private final Vector3 RBN;

    private Vector3 center;
    private float edgeLen;

    /**
     * Создаёт экземпляр параллелипипеда
     * @param LTF Левая Верхняя Дальняя точка (Left Top Far)
     * @param RBN Правая Нижняя Ближняя точка (Right Bottom Near)
     */
    public Parallelepiped(Vector3 LTF, Vector3 RBN) {
        this.LTF = LTF;
        this.RBN = RBN;

        this.edgeLen = Math.abs(LTF.getX() * 2);
//        System.out.println(edgeLen);

        this.center = new Vector3(
                this.LTF.getX() + this.edgeLen / 2,
                this.LTF.getY() + this.edgeLen / 2,
                this.LTF.getZ() - this.edgeLen / 2
        );
//        System.out.println(LTF.getX() + " " + LTF.getY() + " " + LTF.getZ());
//        System.out.println(center.getX() + " " + center.getY() + " " + center.getZ());

    }

    public Parallelepiped(Vector3 center, float edgeLen) {
        this.center = center;
        this.edgeLen = edgeLen;

        this.RBN = new Vector3(
                center.getX() + edgeLen / 2,
                center.getY() + edgeLen / 2,
                center.getZ() - edgeLen / 2
        );
        this.LTF = new Vector3(
                center.getX() - edgeLen / 2,
                center.getY() - edgeLen / 2,
                center.getZ() + edgeLen / 2
        );
    }
    

    @Override
    public List<PolyLine3D> getLines() {
        LinkedList<PolyLine3D> lines = new LinkedList<>();
        /*Дальняя сторона (Z фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ())), true));
        /*Ближняя сторона  (Z фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), RBN.getZ())), true));
        
        /*Верхняя сторона (Y фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), LTF.getZ())), true));
        /*Нижняя сторона (Y фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3(LTF.getX(), RBN.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ())), true));
        
        /*Левая сторона (X фиксирован и вязт у LTF)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3(LTF.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(LTF.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(LTF.getX(), RBN.getY(), LTF.getZ())), true));
        /*Правая сторона (X фиксирован и вязт у RBN)*/
        lines.add(new PolyLine3D(Arrays.asList(new Vector3(RBN.getX(), LTF.getY(), LTF.getZ()),
                new Vector3(RBN.getX(), LTF.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), RBN.getZ()),
                new Vector3(RBN.getX(), RBN.getY(), LTF.getZ())), true));
        
        return lines;
    }

    public Vector3 getLTF() {
        return LTF;
    }

    public Vector3 getRBN() {
        return RBN;
    }

    public Vector3 getCenter() {
        return center;
    }

    public float getEdgeLen() {
        return edgeLen;
    }

    public Voxel asVoxel() {
        return new Voxel(this.center, this.edgeLen);
    }
}
