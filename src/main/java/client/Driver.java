package client;

import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.*;
public class Driver {
    public static void main(String[] args) {
        
        System.out.println("Hello world from client");
        BufferedReader inputRead;
        PrintWriter outputWriter;
         
        
        try{
            Socket soc = new Socket("localhost", 8080);
            inputRead = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            outputWriter = new PrintWriter(soc.getOutputStream());
            
            
            outputWriter.println("Hello poop from client");
            outputWriter.flush();
        
        
        }catch(Exception e){
            System.out.println(e);
        }
    }
}