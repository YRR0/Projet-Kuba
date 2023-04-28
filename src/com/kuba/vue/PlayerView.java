package com.kuba.vue;

import java.awt.*;
import javax.swing.*;
import com.kuba.model.player.Joueur;

public class PlayerView extends JPanel {
    private JLabel billesCapturesR;
    private JLabel billesRestantes;
    private final Joueur joueur;

    public PlayerView(Joueur j, int WIDTH, int HEIGHT){
        setBackground(new Color(0,0,0,0));
        setLayout(null);
        joueur = j;
        
        // Réglage des titres
        JLabel nom = new JLabel(j.getNom());
        nom.setFont(new Font("Serif", Font.BOLD, (int)(WIDTH*0.016)+(int)(HEIGHT*0.008)));
        JLabel lab1 = new JLabel("Billes restantes");
        lab1.setFont(new Font("Serif", Font.BOLD, (int)(WIDTH*0.012)+(int)(HEIGHT*0.006)));
        JLabel lab2 = new JLabel("Billes rouges capturées");
        lab2.setFont(new Font("Serif", Font.BOLD, (int)(WIDTH*0.012)+(int)(HEIGHT*0.006)));

        // Réglage des données de la partie
        billesRestantes = new JLabel(j.getNbAdversaireCapturee()+"");
        billesRestantes.setFont(new Font("Serif", Font.BOLD, (int)(WIDTH*0.014)+(int)(HEIGHT*0.007)));
        billesCapturesR = new JLabel(j.getNbBilleRougeCapturee()+"");
        billesCapturesR.setFont(new Font("Serif", Font.BOLD, (int)(WIDTH*0.014)+(int)(HEIGHT*0.007)));

        // Placement des composants
        nom.setBounds((int)(WIDTH*0.018), (int)(HEIGHT*0.016), (int)(WIDTH*0.2), (int)(HEIGHT*0.057));
        lab1.setBounds(18, 73,200, 30);
        lab2.setBounds(18,113,200, 30);
        billesRestantes.setBounds(228, 78, 20, 20);
        billesCapturesR.setBounds(228,118, 20, 20);

        // Ajout des composants
        add(nom);
        add(lab1);
        add(billesRestantes);
        add(lab2);
        add(billesCapturesR);
    }

    // Mise à jour des données affichées
    public void update(){
        billesRestantes = new JLabel(joueur.getNbAdversaireCapturee()+"");
        billesCapturesR = new JLabel(joueur.getNbBilleRougeCapturee()+"");
        this.repaint();
    }
}
