public class Main {
    public static void main(String[] args) {
        Joueur j1 = new Joueur("Joueur 1", 15, false);
        Joueur j2 = new Joueur("Joueur 2", 2, true);
        System.out.println(j1.toString());
        System.out.println(j2.toString());
    }
}