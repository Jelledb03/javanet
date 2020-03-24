import java.net.*;
import java.io.*;

public class BasicServer {
    public static void main(String[] args) {

        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server started");

            System.out.println("Waiting for a client ...");

            //socket
            Socket socket = server.accept();
            System.out.println("Client accepted");

            //input and output stream
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            while (true) {
                try {

                    dataOutputStream.writeUTF("What do you want to send..\n" +
                            "Type Exit to terminate connection.");

                    // get filename from client
                    String filename = dataInputStream.readUTF();
                    System.out.println(filename);

                    //break out of while loop
                    if (filename.equals("Exit")) {
                        System.out.println("Client sends exit...");
                        break;
                    }

                    dataOutputStream.writeUTF("Entered by Client: " + filename);


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            // close connection
            System.out.println("Closing connection");
            socket.close();
            dataInputStream.close();
            dataOutputStream.close();
            System.out.println("Closed connection");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}