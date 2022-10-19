package org.movinganimation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DiagonalAnimator {
    final private JPanel panel;
    final private JLabel label;
    final private JLabel labelClone;
    final private Toolkit toolkit;
    final private double speed;
    private Timer animationTimer;
    private long startTime;

    public DiagonalAnimator(BufferedImage image, JPanel panel, Toolkit toolkit, double speed) {
        this.panel = panel;
        this.toolkit = toolkit;
        this.speed = speed;
        label = new JLabel(new ImageIcon(image));

        label.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        label.setSize(new Dimension(image.getWidth(), image.getHeight()));
        label.setLocation(0, 0);

        labelClone = new JLabel((new ImageIcon(image)));
        labelClone.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        labelClone.setSize(new Dimension(image.getWidth(), image.getHeight()));
        int diagonalLength = Math.min(panel.getWidth(), panel.getHeight());
        labelClone.setLocation(-diagonalLength, -diagonalLength);

        panel.setLayout(null);
        panel.add(label);
        panel.add(labelClone);

        initTimer();
    }

    public void run() throws RuntimeException {
        if (animationTimer == null) {
            throw new RuntimeException("Empty timer");
        }
        startTime = System.currentTimeMillis();
        animationTimer.restart();
    }

    private void initTimer() {
        animationTimer = new Timer(40, e -> animation());
    }

    private void animation() {
        int pixels = (int) Math.round((System.currentTimeMillis() - startTime) * speed);
        int diagonalLength = Math.min(panel.getWidth(), panel.getHeight());
        pixels %= diagonalLength;
        label.setLocation(pixels, pixels);
        labelClone.setLocation(pixels - diagonalLength, pixels - diagonalLength);
        toolkit.sync();
    }
}
