package com.gao.gomoku.game;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import static java.util.Optional.*;
import static javax.imageio.ImageIO.read;

/**
 * PicturePanel
 * kep megmutatasi szukseges osztqly
 * a kep classpath-on szerepel, ezert felhasznalas soran tobb munka lesz, mint multkor
 * imageIcon: hasznalott kepmegmutato
 */
public class PicturePanel extends JPanel {

    private ImageIcon imageIcon;

    /**
     * konstruktor, betolti a megadott kepet
     * ha a kep nem letezik, akkor a kep helyett semmi nem mutat meg
     * @param imagePath: a megadott kep
     */
    public PicturePanel(String imagePath) {
        this.imageIcon = readIcon(imagePath).orElseGet(ImageIcon::new);
        setPreferredSize(new Dimension(120, 120));
    }

    /**
     * paintComponent metodus felulirasa
     * @param g: a megadott rajzpalya
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imageIcon.getImage(), 0, 0, 120, 120, null);
    }

    /**
     * betoltesi metodus 1
     * ha kep letezik, akkor betolti a kepet
     * @param fileName a megadott fajl neve
     * @return Optional status, hogy sikerul-e betolteni
     */
    private Optional<ImageIcon> readIcon(String fileName) {
        return ofNullable(getClass().getResourceAsStream("/" + fileName))
                .flatMap(this::createIcon);
    }

    /**
     * betoltesi metodus 2
     * a fenti metodus eredmenyvel dolgozik, es visszaad a feltoltesi eredmenyt
     * @param is: a most dolgozott stream
     * @returnfetoltesi eredmeny, ha sikerul, akkor visszaad a betoltott streamnek adatot
     * ha nem, akkor visszaad Optional.empty() statust
     */
    private Optional<ImageIcon> createIcon(InputStream is) {
        try {
            return of(new ImageIcon(read(is)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return empty();
    }

}
