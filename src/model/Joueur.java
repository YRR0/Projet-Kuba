public class Joueur 
{
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



























    public String getNom()
    {
        return this.nom;
    }

    public int getNbBille()
    {
        return this.nbBille;
    }

    public boolean getNoire()
    {
        return this.noire;
    }

    public void setNom(String nom)
    {
        this.nom = nom;
    }

    public void setNbBille(int nbBille)
    {
        this.nbBille = nbBille;
    }

    public void setNoire(boolean noire)
    {
        this.noire = noire;
    }

    public void setNbBilleRougeCapturer(int nbBilleRougeCapturer)
    {
        this.nbBilleRougeCapturer = nbBilleRougeCapturer;
    }

    public int getNbBilleRougeCapturer()
    {
        return this.nbBilleRougeCapturer;
    }





}