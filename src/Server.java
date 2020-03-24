import java.io.*;
import java.net.*;

public class Server
{
    public static void main(String[] args) throws IOException
    {
        ServerSocket serverSocket = new ServerSocket(5000);
        System.out.println("Server started");

        System.out.println("Waiting for a client ...");

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
                //Close socket here too
                socket.close();
                e.printStackTrace();
            }
        }
    }
}


class ClientHandler extends Thread
{
    private final DataInputStream dataInputStream;
    private final DataOutputStream dataOutputStream;
    private final Socket socket;


    ClientHandler(Socket socket, DataInputStream dataInputStream, DataOutputStream dataOutputStream)
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

                dataOutputStream.writeUTF("What do you want to send..\n"+
                        "Type Exit to terminate connection.");

                // Get filename from client
                filename = dataInputStream.readUTF();
                System.out.println(filename);

                //break out of while loop and close socket
                if(filename.equals("Exit"))
                {
                    System.out.println("Client " + this.socket + " sends exit...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                dataOutputStream.writeUTF("Ingegeven door Client: " + filename);



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            // close input and output
            this.dataInputStream.close();
            this.dataOutputStream.close();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
