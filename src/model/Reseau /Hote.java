
/* Serveur en modele TCP(par flux) */

import java.net.InetAddress;
import java.net.UnknownHostException;

import model.plateau.Board;

import java.net.ServerSocket;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;


public class Hote extends Reseau {

    public static void main(String[] args) {
        try {

            ServerSocket server = new ServerSocket(getPort()); // le serveur passe par le port 2023
            System.out.println("Serveur en attente de connexion");

            Socket socket = server.accept(); // bloque le thread tant qu'une connexion n'a pas été établie
            System.out.println("Connexion établie");
           // InputStream is = socket.getInputStream();
           // OutputStream os = socket.getOutputStream();
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
          //  os.write(res);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
