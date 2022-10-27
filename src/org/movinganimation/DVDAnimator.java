package org.movinganimation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class DVDAnimator {
    private final BufferedImage image;
    private final JPanel panel;
    private JLabel label;
    private final Toolkit toolkit;
    private final Progress timer;
    private Color currentColor;
    private double positionX;
    private double positionY;
    private int directionX;
    private int directionY;
    private double lastProgress;
    private Rectangle2D.Double animationArea;

    public DVDAnimator(BufferedImage bufferedImage, JPanel panel, Toolkit toolkit, double progressSpeed) {
        image = resizeImage(bufferedImage);
        this.panel = panel;
        this.toolkit = toolkit;

        initPanel();
        initLabel();

        updateAnimationArea();
        updateImageColor();

        timer = new Progress(this::animation, progressSpeed);
    }

    public void run() {
        lastProgress = 0;
        timer.start();
    }

    private void animation(double progress) {
        double diffProgress = getDiffProgress(progress);

        positionX += directionX * diffProgress * panel.getWidth();
        positionY += directionY * diffProgress * panel.getHeight();

        processPositionOutOfArea();

        updateLabelPosition();
        toolkit.sync();
    }

    private double getDiffProgress(double progress) {
        if (lastProgress > progress) {
            lastProgress -= 1;
        }
        double diffProgress = progress - lastProgress;
        lastProgress = progress;
        return diffProgress;
    }

    private void initRandomPosition() {
        positionX = Math.random() * (panel.getWidth() - image.getWidth() + 1);
        positionY = Math.random() * (panel.getHeight() - image.getHeight() + 1);
        directionX = (int) Math.signum(Math.random() - 0.5);
        directionY = (int) Math.signum(Math.random() - 0.5);
    }

    private void initPanel() {
        panel.setLayout(null);
        panel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateAnimationArea();
            }
        });
    }

    private void initLabel() {
        label = new JLabel(new ImageIcon(image));
        label.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
        label.setSize(new Dimension(image.getWidth(), image.getHeight()));
        panel.add(label);

        initRandomPosition();
        updateLabelPosition();
    }

    private void updateAnimationArea() {
        animationArea = new Rectangle2D.Double(0, 0, panel.getWidth() - label.getWidth(), panel.getHeight() - label.getHeight());
    }

    private void updateLabelPosition() {
        label.setLocation((int) positionX, (int) positionY);
    }

    private void updateImageColor() {
        currentColor = getRandomColor();
        changeColor(currentColor);
    }

    private Color getRandomColor() {
        Color color;
        do {
            color = Color.getHSBColor((float) Math.random(), 1, 1);
        } while (color.equals(currentColor));
        return color;
    }

    private void changeColor(Color color) {
        WritableRaster raster = image.getRaster();
        int[] rgb = new int[4];
        for (int x = 0; x < raster.getWidth(); x++) {
            for (int y = 0; y < raster.getHeight(); y++) {
                raster.getPixel(x, y, rgb);
                if (rgb[3] != 0) {
                    rgb[0] = color.getRed();
                    rgb[1] = color.getGreen();
                    rgb[2] = color.getBlue();
                    raster.setPixel(x, y, rgb);
                }
            }
        }
    }

    private void processPositionOutOfArea() {
        int outcode = animationArea.outcode(positionX, positionY);
        if (outcode == 0) {
            return;
        }

        if ((outcode & Rectangle2D.OUT_LEFT) > 0) {
            directionX = 1;
            positionX = Math.abs(positionX);
        } else if ((outcode & Rectangle2D.OUT_RIGHT) > 0) {
            directionX = -1;
            positionX -= positionX - animationArea.getWidth();
        } else if ((outcode & Rectangle2D.OUT_TOP) > 0) {
            directionY = 1;
            positionY = Math.abs(positionY);
        } else if ((outcode & Rectangle2D.OUT_BOTTOM) > 0) {
            directionY = -1;
            positionY -= positionY - animationArea.getHeight();
        }

        updateImageColor();
    }

    private BufferedImage resizeImage(BufferedImage originImage) {
        int width = 200;
        int height = 88;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.drawImage(originImage, 0, 0, width, height, null);
        g2d.dispose();
        return bufferedImage;
    }
}
