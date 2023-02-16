package model.plateau;

import model.Couleur;

public class Joueur {

    private String nom; 
    private int nbBille;
    private int nbBilleRougeCapturer;
    private boolean noire;
    private Couleur couleur;


    public Joueur(String nom, int taille, boolean noire )
    {
        this.nom = nom;
        this.nbBille = 2*(taille*taille);
        this.noire = noire;
        this.nbBilleRougeCapturer = 0;
        this.couleur = (noire) ? Couleur.NOIR : Couleur.BLANC;
    }

    public Couleur getCouleur(){
        return couleur;
    }

    public boolean emptyBille()
    {
        return nbBille == 0;
    }

    public void capturerBille()
    {
        this.nbBilleRougeCapturer++;
    }

    public void perdreBille()
    {
        this.nbBille--;
    }
    
    @Override 
    public String toString()
    {
        String bw =  this.noire == true ? "Noir" : "Blanc" ;
        return "Joueur : " + this.nom + " Nombre de billes : " + this.nbBille + " Nombre de billes rouge capturé : " + this.nbBilleRougeCapturer + " Couleur : " + bw;
    }

    public String getNom() {
        return nom;
    }

    public boolean isNoire() {
        return noire;
    }

    public int getNbBille() {
        return nbBille;
    }

    public int getNbBilleRougeCapturer() {
        return nbBilleRougeCapturer;
    }
    /* j'ai pensé a une methode update pour le nombre de bille, pas sûr de la conserver */
    public void update(Board board){
        int nbBille = 0;
        for(int i = 0; i < board.getBoard().length; i++){
            for(int j = 0; j < board.getBoard()[i].length; j++){
                if(!board.getBoard()[i][j].estVide()){
                    if(board.getBoard()[i][j].getBille().getColor() == Couleur.NOIR && this.noire){
                        this.nbBille++;
                    }
                    if(board.getBoard()[i][j].getBille().getColor() == Couleur.BLANC && !this.noire){
                        this.nbBille++;
                    }
                }
            }
        }
        this.nbBille = nbBille;
    }
    
    public void updateScore(Couleur c){
        if (c.equals(Couleur.ROUGE)){
            this.nbBilleRougeCapturer++;
        }
    }

}
