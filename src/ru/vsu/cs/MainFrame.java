package ru.vsu.cs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame {
    private DrawPanel drawPanel;
    private JPanel panelMain;
    private JPanel sidePanel;
    private JButton loadFileBtn;
    private JButton removeBtn;
    private JCheckBox deleteCheckBox;
    private JButton voxelizeBtn;
    private JTextField sizeTextField;


    public MainFrame() {
        setBounds(100, 100, 1000, 800);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.panelMain = new JPanel();
        this.panelMain.setLayout(new BoxLayout(this.panelMain, BoxLayout.X_AXIS));

        this.drawPanel = new DrawPanel();

        this.sidePanel = new JPanel();
        this.sidePanel.setLayout(new BoxLayout(this.sidePanel, BoxLayout.Y_AXIS));

        this.loadFileBtn = new JButton("Load file");
        this.loadFileBtn.addActionListener(new LoadFileBtnListener(this.drawPanel));

        this.deleteCheckBox = new JCheckBox("All?");

        this.removeBtn = new JButton("Remove");
        this.removeBtn.addActionListener(new RemoveBtnListener(this.drawPanel, this.deleteCheckBox));

        this.voxelizeBtn = new JButton("Voxelize");
        this.sizeTextField = new JTextField();
        this.sizeTextField.setFont(new Font("Arial", Font.PLAIN, 20));
        this.sizeTextField.setMaximumSize(new Dimension(200, 40));

        this.voxelizeBtn.addActionListener(new VoxelizeBtnListener(this.drawPanel, this.sizeTextField));


//        this.inputLabel = new JLabel();
//        this.inputLabel.setFont(new Font("Arial", Font.PLAIN, 20));
//        this.inputLabel.setText("y=");
//
//        this.inputTextField = new JTextField();
//        this.inputTextField.setFont(new Font("Arial", Font.PLAIN, 20));
//        this.inputTextField.setMaximumSize(new Dimension(600, 60));
//
//        this.inputPanel.add(this.inputLabel);
//        this.inputPanel.add(this.inputTextField);

        this.sidePanel.add(loadFileBtn);
        this.sidePanel.add(removeBtn);
        this.sidePanel.add(deleteCheckBox);
        this.sidePanel.add(voxelizeBtn);
        this.sidePanel.add(sizeTextField);

        this.panelMain.add(this.drawPanel);
        this.panelMain.add(this.sidePanel);

        this.getContentPane().add(this.panelMain, BorderLayout.CENTER);

    }

    private static class LoadFileBtnListener implements ActionListener {
        private final DrawPanel drawPanel;

        public LoadFileBtnListener(DrawPanel drawPanel) {
            this.drawPanel = drawPanel;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("."));

            int res = fileChooser.showOpenDialog(null);

            if (res == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                this.drawPanel.addModel(file);
            }


        }
    }

    private static class RemoveBtnListener implements ActionListener {
        private final DrawPanel drawPanel;
        private final JCheckBox checkBox;

        public RemoveBtnListener(DrawPanel drawPanel, JCheckBox checkBox) {
            this.drawPanel = drawPanel;
            this.checkBox = checkBox;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            this.drawPanel.removeModel(checkBox.isSelected());
        }
    }

    private static class VoxelizeBtnListener implements ActionListener {
        private final DrawPanel drawPanel;
        private final JTextField sizeTextField;

        public VoxelizeBtnListener(DrawPanel drawPanel, JTextField sizeTextField) {
            this.drawPanel = drawPanel;
            this.sizeTextField = sizeTextField;
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            this.drawPanel.voxelize(Float.parseFloat(sizeTextField.getText()));
        }
    }
}
