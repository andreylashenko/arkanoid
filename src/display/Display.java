package display;

import javax.swing.*;
import java.awt.*;

public class Display {

    private Canvas canvas;

    private String title;
    private int height, width;
    public JFrame jFrame;

    public Display(String title, int width, int height) {
        this.title = title;
        this.height = height;
        this.width = width;

        createDisplay();
    }

    private void createDisplay() {
        jFrame = new JFrame(title);
        jFrame.setSize(width, height);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setMaximumSize(new Dimension(width, height));
        canvas.setMinimumSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.setBackground(Color.BLACK);

        jFrame.add(canvas);
        jFrame.pack();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JFrame getFrame() {
        return jFrame;
    }
}