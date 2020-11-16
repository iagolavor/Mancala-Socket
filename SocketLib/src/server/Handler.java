/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author iago-
 */
public class Handler implements Runnable{
    
    protected Server socketServer;
    protected Socket client;
    private BufferedReader reader;
    
    public Handler(Server server, Socket socket){
        this.socketServer = server;
        this.client = socket;
        try{
            this.reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException ex) {
            Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro criação dos buffer do handler");
        }
    }

    @Override
    public void run() {
        while(true){
            String msg;
            try{
                msg = this.reader.readLine();
                if(!msg.isEmpty()){
                    this.socketServer.sendToAll(msg);
                    msg = "";
                }
                
            } catch (IOException ex) {
                Logger.getLogger(Handler.class.getName()).log(Level.SEVERE, null, ex);
                System.exit(1);
                System.out.println("Handler error");
            }
        }
        
    }
    
}
