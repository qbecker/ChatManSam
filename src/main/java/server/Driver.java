package server;


import java.io.*;
import java.net.*;
 
class Driver
{
   public static void main(String argv[]) throws Exception
      {
   		 System.out.println(" Server started  " );
   		 
   		 Server server = new Server(8080);
   		 /*
         ServerSocket mysocket = new ServerSocket(8080);
 
         while(true)
         {
            Socket connectionSocket = mysocket.accept();
 
            BufferedReader reader =
            		new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            BufferedWriter writer= 
            		new BufferedWriter(new OutputStreamWriter(connectionSocket.getOutputStream()));
 
           
            writer.flush();
            String data1 = reader.readLine().trim();
 
            System.out.println(data1);
            connectionSocket.close();
            
   		 
         }
         */
      }
      
}
    
