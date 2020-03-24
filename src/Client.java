

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client
{
    public static void main(String[] args) throws IOException
    {
        try
        {
            Scanner scanner = new Scanner(System.in);

            // getting localhost ip
            InetAddress ipaddress = InetAddress.getByName("localhost");

            // establish the connection with server port 5056
            Socket socket = new Socket(ipaddress, 5000);

            // creating bufferedReader(for filename) & inputStream from Server
            BufferedReader clientBufferReader = new BufferedReader(new InputStreamReader(System.in));
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());

            // the following loop performs the exchange of
            // information between client and client handler
            while (true)
            {
                System.out.println(dataInputStream.readUTF());

                //System.out.println(dataInputStream.readUTF());

                //Sending file name to server with PrintWriter
                String filename = scanner.nextLine();
                //PrintWriter printWriter = new PrintWriter(outputStream, true);

                dataOutputStream.writeUTF(filename);
                //printWriter.println(filename);
                //dos.writeUTF(filename);

                // If client sends exit,close this connection
                // and then break from the while loop
                if(filename.equals("Exit"))
                {
                    System.out.println("Closing this connection : " + socket);
                    socket.close();
                    System.out.println("Connection closed");
                    break;
                }

                // recieve content from file from server
                BufferedReader serverBufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
                String textFromFile;
                while((textFromFile = serverBufferedReader.readLine()) != null){
                    System.out.println(textFromFile);
                }
                //printWriter.close();
                serverBufferedReader.close();
            }

            // closing resources
            scanner.close();
            dataInputStream.close();
            dataOutputStream.close();
            clientBufferReader.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}