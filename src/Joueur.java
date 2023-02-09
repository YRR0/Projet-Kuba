public class Joueur {

    private String nom; 
    private int nbBille;
    private int nbBilleRougeCapturer;
    private boolean noire;

    public Joueur(String nom, int taille, boolean noire )
    {
        this.nom = nom;
        this.nbBille = 2*(taille*taille);
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

    
}
