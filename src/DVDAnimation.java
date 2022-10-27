import org.movinganimation.DVDAnimator;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DVDAnimation {
    public static void main(String[] args) {
        JFrame.setDefaultLookAndFeelDecorated(false);
        JFrame frame = new JFrame("DVD video Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(800, 800));
        panel.setBackground(Color.GRAY);

        frame.add(panel);
        frame.pack();

        DVDAnimator animator = new DVDAnimator(getImage(), panel, frame.getToolkit(), 25);
        animator.run();

        frame.setVisible(true);
    }

    private static BufferedImage getImage() {
        BufferedImage image;
        try {
            image = ImageIO.read(new File("src", "DVD_Video_(Old).png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }
}
