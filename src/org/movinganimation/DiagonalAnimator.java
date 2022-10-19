package org.movinganimation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DiagonalAnimator {
    private final JPanel panel;
    private JLabel label;
    private JLabel labelClone;
    private final Toolkit toolkit;
    private final Progress timer;

    public DiagonalAnimator(BufferedImage image, JPanel panel, Toolkit toolkit, double speed) {
        this.panel = panel;
        this.toolkit = toolkit;

        panel.setLayout(null);

        initLabel(image);
        initLabelCopy(image);

        timer = new Progress(this::animation, speed);
    }

    private void initLabel(BufferedImage image) {
        label = new JLabel(new ImageIcon(image));

        label.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        label.setSize(new Dimension(image.getWidth(), image.getHeight()));
        label.setLocation(0, 0);

        panel.add(label);
    }

    private void initLabelCopy(BufferedImage image) {
        int diagonalLength = Math.min(panel.getWidth(), panel.getHeight());

        labelClone = new JLabel((new ImageIcon(image)));
        labelClone.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        labelClone.setSize(new Dimension(image.getWidth(), image.getHeight()));
        labelClone.setLocation(-diagonalLength, -diagonalLength);

        panel.add(labelClone);
    }

    private void animation(double progress) {
        int diagonalLength = Math.min(panel.getWidth(), panel.getHeight());
        int pixels = (int) Math.round(diagonalLength * progress);
        label.setLocation(pixels, pixels);
        labelClone.setLocation(pixels - diagonalLength, pixels - diagonalLength);
        toolkit.sync();
    }

    public void run() throws RuntimeException {
        timer.start();
    }
}
