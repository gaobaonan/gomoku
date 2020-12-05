package com.gao.gomoku.game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static java.util.Optional.*;
import static javax.imageio.ImageIO.read;

public class PicturePanel extends JPanel {

    private ImageIcon imageIcon;

    PicturePanel(String imagePath) {
        this.imageIcon = readIcon(imagePath).orElseGet(ImageIcon::new);
        setPreferredSize(new Dimension(120, 120));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(), 0, 0, 120, 120, null);
    }

    private Optional<ImageIcon> readIcon(String fileName) {
        return ofNullable(getClass().getResourceAsStream("/" + fileName))
                .flatMap(this::createIcon);
    }

    private Optional<ImageIcon> createIcon(InputStream is) {
        try {
            return of(new ImageIcon(read(is)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empty();
    }

}
