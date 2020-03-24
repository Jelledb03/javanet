// Java implementation for a client
// Save file as Client.java

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client class
public class Client {
    public static void main(String[] args) throws IOException {
        try {
            Scanner scanner = new Scanner(System.in);

            // getting localhost ip
            InetAddress ipaddress = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket socket = new Socket(ipaddress, 5056);

            // obtaining input and out streams
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            while (true) {
                System.out.println(dataInputStream.readUTF());
                String filename = scanner.nextLine();
                dataOutputStream.writeUTF(filename);

                // If client sends exit,close this connection
                // and then break from the while loop
                if (filename.equals("Exit")) {
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                // printing date or time as requested by client
                String received = dataInputStream.readUTF();

                while (!(received = dataInputStream.readUTF()).equals("")) {
                    System.out.println(received);
                    try {
                        File file = new File("testje.txt");
                        if (file.createNewFile()) {
                            System.out.println("File created");
                        } else {
                            System.out.println("File already exists.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try{
                        FileWriter fileWriter = new FileWriter("testje.txt");
                        fileWriter.write(received);
                        fileWriter.close();
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                }
                System.out.println("Finished writing file: " + filename);
            }

            // closing resources
            scanner.close();
            dataInputStream.close();
            dataOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
