
/* Serveur en modele TCP(par flux) */

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.ServerSocket;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;
import com.kuba.model.player.Joueur;
import com.kuba.model.plateau.Board;


public class Serveur {

    private ServerSocket ss;
    private int numPlayers;

    public Serveur(){
        System.out.println("--------- Serveur ----------");
        numPlayers = 0;
        try{
            ss = new ServerSocket(2023, numPlayers);
        }catch(IOException e){
            System.out.println("IOException sur le constucteur du serveur");
        }
    }

    public void acceptConenctions(){
        try{
            System.out.println("En attente de connexion");
            while(numPlayers<2){
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("Connexion établie avec le joueur " + numPlayers);
            }
            System.out.println("Deux joueurs sont connectés");
        }catch(IOException e){
            System.out.println("IOException sur la méthode acceptConnections");
        }
    }
    public static void main(String[] args) {
        try {

            ServerSocket server = new ServerSocket(2023,2); // le serveur passe par le port 2023
            System.out.println("Serveur en attente de connexion");

            server.setSoTimeout(10000); // timeout de 10 secondes

            Socket socket = server.accept(); // bloque le thread tant qu'une connexion n'a pas été établie
            System.out.println("Connexion établie");





            server.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Erreur : " + e.getMessage());
        }

    }

    


}

           /* InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();
            Board board = null;
            while(true){
            Scanner sc = new Scanner(System.in);
            System.out.println("Quel taille de plateau voulez vous ?");
            int nb = sc.nextInt();

            if(nb >0){
            board = new Board(nb);
            sc.close();
            break;
            }else{
                System.out.println("Erreur, veuillez saisir un nombre positif");
            }
            }
        
            System.out.println("En attente de message");
           // int nb = is.read(); // bloque le thread tant qu'un message n'a pas été reçu
            System.out.println("Message reçu");
           // int res = nb*2;
            System.out.println("Envoi du message");
          //  os.write(res); */