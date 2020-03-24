// A Java program for a Server 

import java.net.*;
import java.io.*;

public class BasicServer {

    // constructor with port 
    public static void main(String[] args) {
        // starts server and waits for a connection
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            //initialize socket and input stream
            Socket socket = server.accept();
            System.out.println("Client accepted");

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while (true) {
                try {

                    // Ask user what he wants
                    dataOutputStream.writeUTF("What do you want to send..\n" +
                            "Type Exit to terminate connection.");

                    // receive the answer from client
                    String filename = dataInputStream.readUTF();
                    System.out.println(filename);

                    if (filename.equals("Exit")) {
                        System.out.println("Client " + socket + " sends exit...");
                        System.out.println("Closing this connection.");
                        //socket.close();
                        System.out.println("Connection closed");
                        break;
                    }

                    dataOutputStream.writeUTF("Ingegeven door Client: " + filename);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Closing connection");
            // close connection
            socket.close();
            dataInputStream.close();
            dataOutputStream.close();
            System.out.println("Closed connection");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}