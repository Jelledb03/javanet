import java.io.*;
import java.net.*;
import java.util.Scanner;

// Client class
public class Client {
    public static void main(String[] args) throws IOException {
        try {
            Scanner scanner = new Scanner(System.in);

            InetAddress ipaddress = InetAddress.getByName("localhost");

            Socket socket = new Socket(ipaddress, 5000);

            // input and out streams
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.println(dataInputStream.readUTF());
                String filename = scanner.nextLine();
                dataOutputStream.writeUTF(filename);

                // Close connection if filename is Exit
                if (filename.equals("Exit")) {
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                //print filename
                String received = dataInputStream.readUTF();
                System.out.println(received);

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
