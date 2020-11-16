/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iago-
 */
public abstract class Client implements Runnable{

    public Socket socket = null;
    public String message = "";
    private BufferedReader reader;
    private BufferedWriter writer;
    
    abstract public void decideFromServerMessage(String msg);
    
    public Client(Socket in){
        this.socket = in;
        try {
            this.reader = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
        } catch (IOException ex) {
            System.out.println("Error creating reader or writer");
            System.out.println(ex);
        }
    }
    
    @Override
    public void run() {
        if(this.socket != null){
            System.out.println("Entrei no run do client");
            while(true){
                this.getFromServer();
            }
        }else{
            System.out.println("Socket is null");
            System.exit(1);
        }
    }
    
    public void getFromServer(){
        String msg;
        try {
            while((msg = this.reader.readLine())!= null){
                this.decideFromServerMessage(msg);
            }
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error");
        }
    }
    
    public void sendToServer(String msg){
        System.out.println("Send to server inside: "+msg);
        try {
            msg += "\n";
            this.writer.write(msg,0,msg.length());
            this.writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error sending to server");
        }
    }
    
}
