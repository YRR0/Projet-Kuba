package plateau;

import Bille;

class Cell {

    private Bille bille;

    void setBille(Bille bille) {
        this.bille = bille;
    }

    public Bille getBille() {
        return bille;
    }

    boolean estVide() {
        return bille == null;
    }

    void clear() {
        bille = null;
    }

    @Override
    public String toString() {
        if(estVide()) return "_";
        return bille.toString();
    }

}
