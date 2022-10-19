package org.movinganimation;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SquareAnimator {
    final private JPanel panel;
    final private JLabel label;
    final private Toolkit toolkit;
    private final Progress timer;

    public SquareAnimator(BufferedImage image, JPanel panel, Toolkit toolkit, double progressSpeed) {
        this.panel = panel;
        this.toolkit = toolkit;

        label = new JLabel(new ImageIcon(image));
        label.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        label.setSize(new Dimension(image.getWidth(), image.getHeight()));
        label.setLocation(0, 0);

        panel.setLayout(null);
        panel.add(label);

        timer = new Progress(this::animation, progressSpeed);
    }

    public void run() throws RuntimeException {
        timer.start();
    }

    private void animation(double progress) {
        if (progress < 0.25) {
            int x = (int) Math.round(progress * 4 * (panel.getWidth() - label.getWidth()));
            label.setLocation(x, 0);
        } else if (progress < 0.5) {
            int y = (int) Math.round((progress - 0.25) * 4 * (panel.getHeight() - label.getHeight()));
            label.setLocation(panel.getWidth() - label.getWidth(), y);
        } else if (progress < 0.75) {
            int x = (int) Math.round((0.75 - progress) * 4 * (panel.getWidth() - label.getWidth()));
            label.setLocation(x, panel.getHeight() - label.getHeight());
        } else {
            int y = (int) Math.round((1 - progress) * 4 * (panel.getHeight() - label.getHeight()));
            label.setLocation(0, y);
        }
        toolkit.sync();
    }
}
