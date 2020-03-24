// Java implementation of Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.util.*;
import java.net.*;

// Server class
public class Server
{
    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket serverSocket = new ServerSocket(5056);

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket socket = null;

            try
            {
                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("A new client is connected : " + socket);

                // obtaining input and out streams
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("Assigning new thread for this client");

                // create a new thread object
                Thread thread = new ClientHandler(socket, dataInputStream, dataOutputStream);

                // Invoking the start() method
                thread.start();

            }
            catch (Exception e){
                socket.close();
                e.printStackTrace();
            }
        }
    }
}

// ClientHandler class
class ClientHandler extends Thread
{
    final DataInputStream dataInputStream;
    final DataOutputStream dataOutputStream;
    final Socket socket;


    // Constructor
    public ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream)
    {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run()
    {
        String filename;
        String output;
        while (true)
        {
            try {

                // Ask user what he wants
                dataOutputStream.writeUTF("What do you want to send..\n"+
                        "Type Exit to terminate connection.");

                // receive the answer from client
                filename = dataInputStream.readUTF();

                if(filename.equals("Exit"))
                {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                dataOutputStream.writeUTF("Ingegeven door Client: " + filename);

                BufferedReader outputReader = new BufferedReader(new FileReader(filename));

                while ((output = outputReader.readLine()) != null) {
                    dataOutputStream.writeUTF(output);
                }

                outputReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // closing resources
            this.dataInputStream.close();
            this.dataOutputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
