import org.movinganimation.SquareAnimator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SquareAnimation {
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame frame = new JFrame("Square Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 800));
        panel.setBackground(Color.GRAY);

        frame.add(panel);
        frame.pack();

        SquareAnimator animator = new SquareAnimator(getImage(), panel, frame.getToolkit(), 5);
        animator.run();

        frame.setVisible(true);
    }

    private static BufferedImage getImage() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("src", "egg.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
