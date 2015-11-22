/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.edu.agh.kis.core.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.openide.util.Exceptions;

/**
 *
 * @author Jakub Piotrowski
 */
public class ImagePanel extends JPanel {

    private static final String IMAGE = "/pl/edu/agh/kis/core/panels/splash.gif";
    private BufferedImage image;

    public ImagePanel() {
        readImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        readImage();
        int widthDiff = (int) (this.getWidth() * 0.4);
        int heightDiff = (int) (this.getHeight()* 0.05);
        Image tmp = image.getScaledInstance(this.getWidth() - widthDiff, this.getHeight() - heightDiff, Image.SCALE_SMOOTH);
        image = new BufferedImage(this.getWidth() - widthDiff, this.getHeight() - heightDiff, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        g2d = (Graphics2D) g;
        int x = (this.getWidth() - image.getWidth(null)) / 2;
        int y = (this.getHeight() - image.getHeight(null)) / 2;
        g2d.drawImage(image, x, y, null);
    }

    private void readImage() {
        try {
            image = ImageIO.read(new File(getClass().getClassLoader().getResource(IMAGE).toURI()));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        } catch (URISyntaxException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

}
