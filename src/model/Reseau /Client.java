
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import model.Joueur;
import model.plateau.Board;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Client implements Reseau {
   
    private Joueur player;
    private Board board; 
    
    public Client(Joueur player, Board board){
        this.player = player;
        this.board = board;
    }
    public static void main(String[] args) {
    try {
        Socket socket = new Socket("localhost", 2023); // choix arbitraire
        InputStream is = socket.getInputStream();
        OutputStream os = socket.getOutputStream();
      
        while(true){
        Scanner sc = new Scanner(System.in);
        System.out.println("Quel ");
        int nb = sc.nextInt();
        os.write(nb);
        System.out.println("En attente de réponse");
        int res = is.read();
        System.out.println("Réponse reçue : " + res);
        sc.close();
        }
    
    }   catch (Exception e) { 
       e.printStackTrace();
    }
    
   }
}
