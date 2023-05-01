package com.kuba.vue;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.kuba.model.player.Joueur;

public class PlayerView extends JPanel {
    private final JLabel nom;
    private final JLabel billesRouges;
    private final JLabel billesAdversaire;
    private final Joueur joueur;
    public static final Font f1;
    static {
        InputStream is = PlayerView.class.getResourceAsStream("/resources/fonts/Amatic-Bold.ttf");
        Font font, derived_font;
        try {
            assert is != null;
            font = Font.createFont(Font.TRUETYPE_FONT, is);
            derived_font = font.deriveFont((float) (0.02 * GameView.WIDTH));
        } catch (FontFormatException | IOException e) {
            derived_font = new Font("Arial", Font.BOLD, 23);
        }

        f1 = derived_font;
    }

    public PlayerView(Joueur j){
        joueur = j;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        nom = new JLabel(j.getNom());
        billesRouges = new JLabel("Billes rouges capturées: " + j.getNbBilleRougeCapturee());
        billesAdversaire = new JLabel("Billes adversaires capturées: " + j.getNbAdversaireCapturee());

        add(Box.createGlue());
        add(nom);
        add(Box.createGlue());
        add(billesRouges);
        add(Box.createGlue());
        add(billesAdversaire);
        add(Box.createGlue());

        style();
    }

    private void style() {
        nom.setFont(f1);
        billesRouges.setFont(f1);
        billesAdversaire.setFont(f1);

        nom.setAlignmentX(CENTER_ALIGNMENT);
    }

    public void update(){
        billesRouges.setText("Billes rouges capturées: " + joueur.getNbBilleRougeCapturee());
        billesAdversaire.setText("Billes adversaires capturées: " + joueur.getNbAdversaireCapturee());
    }
}
