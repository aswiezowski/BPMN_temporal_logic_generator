/*
 * Copyright (c) 2015, pl.edu.agh
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package pl.edu.agh.kis.core.panels;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.openide.util.Exceptions;

/**
 *
 * @author Jakub Piotrowski
 * Draws main application image
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
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream(IMAGE));
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

}
