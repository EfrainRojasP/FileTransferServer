/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package filetransferserver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Rojas Piña Efrain Ulises <al2172001457@azc.uam.mx>
 * Fuente: https://heptadecane.medium.com/file-transfer-via-java-sockets-e8d4f30703a5
 */
public class Server {

   /**
    * @param args the command line arguments
    */
   private static DataOutputStream dataOutputStream = null;
   private static DataInputStream dataInputStream = null;

   public static void main(String[] args) {

      System.out.println("Tam integer " + Integer.MAX_VALUE);

      try (ServerSocket serverSocket = new ServerSocket(5000)) {
         //Abrimos el socket en el puereto 5000 del localhot y esperamos
         //a que llegue un cliente
         System.out.println("Escuchando en el puerto:5000");
         //Acpectamos al cliente
         Socket clientSocket = serverSocket.accept();
         System.out.println("Cliente: " + "\n\t" + clientSocket + " conectado.");

         //Inicializamos el flujo de entrada y de salida
         dataInputStream = new DataInputStream(clientSocket.getInputStream());
         dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());

         //Recibimos el archivo y lo nombramos
         receiveFile("HolaRecibido.txt");

         dataInputStream.close();
         dataOutputStream.close();
         clientSocket.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   private static void receiveFile(String fileName) throws Exception {

      /*
      byte[] buffer = new byte[Integer.MAX_VALUE];
      int bytes = dataInputStream.read(buffer,0,buffer.length);
      fileOutputStream = new FileOutputStream(newFileName);
      fileOutputStream.write(buffer,0,bytes);
       */
      int bytes = 0;
      FileOutputStream fileOutputStream = new FileOutputStream(fileName);

      //Leemos el tamaño del archivo
      long size = dataInputStream.readLong();
      byte[] buffer = new byte[4 * 1024];
      while (size > 0 && (bytes = dataInputStream.read(buffer, 0, (int) Math.min(buffer.length, size))) != -1) {
         fileOutputStream.write(buffer, 0, bytes);
         // Leemos hasta que se envie el archivo por completo
         size -= bytes;
      }
      fileOutputStream.close();
   }

}
