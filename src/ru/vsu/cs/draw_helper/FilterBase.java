package ru.vsu.cs.draw_helper;

public interface FilterBase<T> {
    boolean permit(T value);
}
