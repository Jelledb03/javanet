// A Java program for a Server 

import java.net.*;
import java.io.*;

public class BasicServer {

    // constructor with port 
    public BasicServer(int port) {
        // starts server and waits for a connection
        try {
            ServerSocket server = new ServerSocket(port);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            //initialize socket and input stream
            Socket socket = server.accept();
            System.out.println("Client accepted");

            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                try {

                    //dataOutputStream.writeUTF("What do you want to send..\n" +
                    //        "Type Exit to terminate connection.");

                    String filename = dataInputStream.readUTF();
                    System.out.println(filename);

                    if(filename.equals("Exit"))
                    {
                        break;
                    }

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

    public static void main(String[] args) {
        BasicServer server = new BasicServer(5000);
    }
}