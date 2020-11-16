/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mancala;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JLabel;

/**
 *
 * @author iago-
 */
public class Movement implements MouseListener, MouseMotionListener{
    
    private int x, y;
    private Mancala client;
    
    public Movement(Component[] panel, Mancala client){
        this.client = client;
        for(Component piece : panel){
            if(piece instanceof JLabel){
                piece.addMouseListener(this);
                piece.addMouseMotionListener(this);
            }
        }
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(this.client.getTurn() == 1){
            this.x = e.getX();
            this.y = e.getY();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
//        System.out.println("Mouse Released");
//        System.out.println(e.getComponent().getName());
//        System.out.println(e.getX());
//        System.out.println(e.getY());
        if(this.client.getTurn() == 1){
            String name = e.getComponent().getName();
            String xd = Integer.toString((e.getX()+e.getComponent().getX())-x);
            String yd = Integer.toString((e.getY()+e.getComponent().getY())-y);
            this.client.sendBoardMovement(name+","+xd+","+yd);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if(this.client.getTurn() == 1){
            e.getComponent().setLocation((e.getX()+e.getComponent().getX())-x, (e.getY()+e.getComponent().getY())-y);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
    
}
