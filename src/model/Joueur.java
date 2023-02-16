
import plateau.*;
import mouvement.*;

public class Joueur {

    private String nom; 
    private int nbBille;
    private int nbBilleRougeCapturer;
    private boolean noire;


    public Joueur(String nom, Board board, boolean noire )
    {
        this.nom = nom;
        this.nbBille = 2*(board.getN()*board.getN());
        this.noire = noire;
        this.nbBilleRougeCapturer = 0;
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
    
    public boolean isWinner(Board board, Joueur adversaire){
        int n = board.getN();
        int nbTotalBilleRouge = (8*(n*n))- (12*n)+5;
        boolean condition1 = adversaire.emptyBille();
        boolean condition2 = this.getNbBilleRougeCapturer() == nbTotalBilleRouge/2 ;

        return condition1 || condition2;
    }
    
    public void move(Board board, Position pos, Direction dir, Joueur adversaire ){
       int nbActuRouge = board.countBille(Couleur.ROUGE);
       int nbActuNoire = board.countBille(Couleur.NOIR);
       int nbActuBlanc = board.countBille(Couleur.BLANC);
       board.bouger(pos, dir);    
        if(board.countBille(Couleur.ROUGE) < nbActuRouge){
            this.capturerBille();
        }
       if(isNoire()){ 
            if(board.countBille(Couleur.BLANC) < nbActuBlanc){
                adversaire.perdreBille();
            }
        }else{
            if(board.countBille(Couleur.NOIR) < nbActuNoire){
                adversaire.perdreBille();
            }
        }
    }
}
