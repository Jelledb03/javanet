// Java implementation of  Server side
// It contains two classes : Server and ClientHandler
// Save file as Server.java

import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

// Server class
public class Server
{
    public static void main(String[] args) throws IOException
    {
        // server is listening on port 5056
        ServerSocket serverSocket = new ServerSocket(5000);

        // running infinite loop for getting
        // client request
        while (true)
        {
            Socket socket = null;

            try
            {
                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("A new client is connected: " + socket);

                // obtaining input and out streams
                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("Creating new Thread for new Client...");

                // create a new thread object
                Thread thread = new ClientHandler(socket, dataInputStream, dataOutputStream);

                // Invoking the start() method
                thread.start();

            }
            catch (Exception e){
                assert socket != null;
                socket.close();
                e.printStackTrace();
            }
        }
    }
}

// ClientHandler class
class ClientHandler extends Thread
{
    private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    private DateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final Socket socket;


    // Constructor
    ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream)
    {
        this.socket = socket;
        this.dataInputStream = dataInputStream;
        this.dataOutputStream = dataOutputStream;
    }

    @Override
    public void run()
    {
        String input;
        String output;
        while (true)
        {
            try {

                // Ask user what he wants
                dataOutputStream.writeUTF("Which file do you want?..\n"+
                        "Type Exit to terminate connection.");

                // receive the answer from client
                input = dataInputStream.readUTF();

                if(input.equals("Exit"))
                {
                    System.out.println("Client " + this.socket + " is exiting...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                // creating Date object
                Date date = new Date();

                // write on output stream based on the
                // answer from the client
                switch (input) {

                    case "Date" :
                        output = dateFormat.format(date);
                        dataOutputStream.writeUTF(output);
                        break;

                    case "Time" :
                        output = timeFormat.format(date);
                        dataOutputStream.writeUTF(output);
                        break;

                    default:
                        dataOutputStream.writeUTF("Getting file " + input + "...");
                        try{
                            sendFile(input);
                        }catch(Exception e){
                            dataOutputStream.writeUTF("This file is not accessible:\n");
                            e.printStackTrace();
                        }
                        break;
                }
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

    private void sendFile(String fileName){

    }
}