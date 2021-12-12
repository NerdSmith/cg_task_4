package ru.vsu.cs;

import javax.swing.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setBounds(100, 100, 1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(new DrawPanel());
    }
}
