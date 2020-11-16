/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iago-
 */
public class Server implements Runnable{
    public ServerSocket server;
    public Socket[] clients = new Socket[2];
    private static String serverStatus = "Offline";
    
    public Server(String port){
        this.clients[0] = null;
        this.clients[1] = null;
        createServerSocket(port);
        setServerStatus("Online");
    }
    
    
    
    public void createServerSocket(String port){
        try {
            this.server = new ServerSocket(Integer.parseInt(port));
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not create server socket.");
        }
    }

    
    public void sendToAll(String msg){
        try{
            for(Socket client : this.clients){
                if(client != null){
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
                    msg += "\n";
                    writer.write(msg, 0, msg.length());
                    writer.flush();
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error on sendToAll");
        }
    }
    
    public void sendToOne(Socket socket, String msg){
        try{
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            msg += "\n";
            writer.write(msg, 0, msg.length());
            writer.flush();
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getServerStatus(){
        return serverStatus;
    }
    
    public static void setServerStatus(String msg){
        serverStatus = msg;
    }

    @Override
    public void run() {
        try{
            while(true){
                if(this.clients[0]==null){
                    this.clients[0] = this.server.accept();
                    this.sendToOne(this.clients[0], "Ply:1");
                    Thread thread1 = new Thread(new Handler(this, this.clients[0]));
                    thread1.start();
                }else if(this.clients[1] == null){
                    this.clients[1] = this.server.accept();
                    this.sendToOne(this.clients[1], "Ply:2");
                    Thread thread2 = new Thread(new Handler(this, this.clients[1]));
                    thread2.start();
                    //Both players connected, tell them who's turn it is.
                    this.sendToAll("Trn:1");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
