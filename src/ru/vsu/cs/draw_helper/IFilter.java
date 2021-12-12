package ru.vsu.cs.draw_helper;

public interface IFilter<T> {
    boolean permit(T value);
}
