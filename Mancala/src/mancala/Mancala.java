/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala;
import client.Client;
import java.net.Socket;
/**
 *
 * @author iago-
 */
public class Mancala extends Client{

    /**
     * @param args the command line arguments
     */
    public String player;
    public String chatMessage;
    public int turn;
    private Board game;
    
    public Mancala(Socket in, Board game){
        super(in);
        this.game = game;
    }
    
    public String getPlayer(){
        return this.player;
    }
    
    public int getTurn(){
        return this.turn;
    }
    
    public void sendChatMsg(String msg){
        this.sendToServer("Cht:"+msg);
    }
    
    public void sendPlayerType(String msg){
        this.sendToServer("Ply:"+msg);
    }
    
    public void sendTurn(String msg){
        this.sendToServer("Trn:"+msg);
    }
    
    public void sendBoardMovement(String msg){
        this.sendToServer("Brd:"+msg);
    }
    
    public String getHostName(){
        return this.socket.getInetAddress().getHostAddress() +"/"+ this.socket.getPort();
    }
    
    
    public void moveButton(String msg){
        String msgs[] = msg.split(",");
        int pc = Integer.parseInt(msgs[0]);
        int x = Integer.parseInt(msgs[1]);
        int y = Integer.parseInt(msgs[2]);
        this.game.setPiece(pc, x, y);
    }
    
    public void changeTurn(){
        if(this.getPlayer().equals("1")){
            this.sendTurn("2");
        }else{
            this.sendTurn("1");
        }
    }

    @Override
    public void decideFromServerMessage(String msg) {
        if(msg.length()>0){
            String typeMsg = msg.substring(0,4);
            String realMsg = msg.substring(4);
            switch(typeMsg){
                case "Ply:" -> {
                    //Recebendo do servidor qual jogador sou eu.
                    this.player = realMsg;
                    this.game.setPlayerName(this.getPlayer());
                }
                case "Cht:" -> this.game.appendChat(realMsg);
                case "Trn:" -> {    
                    if(realMsg.equals(this.player)){
                        this.turn = 1;
                    }else{
                        this.turn = 0;
                    }
                    this.game.setTurn(realMsg);
                }
                case "Brd:" -> this.moveButton(realMsg);
            }
            //Decidir turno
            //Se a mensagem do servidor == meu player, então é meu turno.
                    }

    }
    
}
