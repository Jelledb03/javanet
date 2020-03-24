
import java.io.*;
import java.text.*;
import java.util.*;
import java.net.*;

public class Server {
    public static void main(String[] args) throws IOException {
        // server is listening on port 5056
        ServerSocket serverSocket = new ServerSocket(5000);

        // running infinite loop for getting
        // client request
        while (true) {
            Socket socket = null;

            try {
                // socket object to receive incoming client requests
                socket = serverSocket.accept();

                System.out.println("A new client is connected: " + socket);

                // obtaining input and out streams
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

                System.out.println("Creating new Thread for new Client...");

                // create a new thread object
                Thread thread = new ClientHandler(socket, inputStream, outputStream);

                // Invoking the start() method
                thread.start();

            } catch (Exception e) {
                assert socket != null;
                socket.close();
                e.printStackTrace();
            }
        }
    }
}

// ClientHandler class
class ClientHandler extends Thread {
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final Socket socket;


    // Constructor
    ClientHandler(Socket socket, DataInputStream inputStream, DataOutputStream outputStream) {
        this.socket = socket;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {
        String fileName;
        String output;
        while (true) {
            try {

                outputStream.writeUTF("Testje");

                PrintWriter printWriter = new PrintWriter(outputStream, true);

                // Ask user what he wants
                printWriter.println("Which file do you want?..\n" +
                        "Type Exit to terminate connection.");

                BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));

                // receive the answer from client
                fileName = fileReader.readLine();

                if (fileName.equals("Exit")) {
                    System.out.println("Client " + this.socket + " is exiting...");
                    System.out.println("Closing this connection.");
                    this.socket.close();
                    printWriter.close();
                    fileReader.close();
                    System.out.println("Connection closed");
                    break;
                }

                BufferedReader outputReader = new BufferedReader(new FileReader(fileName));


                while ((output = outputReader.readLine()) != null) {
                    printWriter.println(output);
                }

                outputReader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            // closing resources
            this.inputStream.close();
            this.outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}