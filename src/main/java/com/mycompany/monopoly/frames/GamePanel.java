/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopoly.frames;

import com.mycompany.monopoly.frames.Jugador2.Player2.PlayerP2;
import com.mycompany.monopoly.frames.JugadorUno.Boton;
import com.mycompany.monopoly.frames.JugadorUno.Player1.Player;
import com.mycompany.monopoly.frames.TileManager.TileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import javax.swing.JTextArea;

/**
 *
 * @author pablo
 */
public class GamePanel extends JPanel implements Runnable{
    
    final int originalTileSize = 19; 
    final int scale = 3; 
    
    public final int tileSize = originalTileSize * scale; // 57x57
    public final int maxScreenCol = 15; // Estos son los ajuts de la pantalla NO TOCAR 
    public final int maxScreenRow = 13; // NO TOCAR 
    public final int screenWidth = tileSize * maxScreenCol; // NO TOCAR
    public final int screenHeight = tileSize * maxScreenRow; // NO TOCAR 
    
    // Instanciamos
    TileManager tileM = new TileManager(this);
    KeyHandler keyH = new KeyHandler(); 
    public Player player = new Player(this, keyH); 
    public PlayerP2 player2 = new PlayerP2(this, keyH); 
    public Sound sound = new Sound(); 
    Thread gameThread; 
    
    int playerX = 100; 
    int playerY = 100; 
    int playerSpeed = 4; 
    
    Boton b = new Boton(); 
    
    public GamePanel()
    {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight)); 
        this.setBackground(Color.white);
        this.setDoubleBuffered(true); 
        this.addKeyListener(keyH); 
        this.setFocusable(true);
        this.setLayout(null); 
        
        b.setBounds(100,100,200,30); 
        b.setText("asdasdasd"); 
        this.add(b); 
        
    }
    
    public void setUpGame()
            /*To print the object in the map*/
    {
        
    }
    
    public void startThread(){
        gameThread = new Thread(this); 
        gameThread.start(); 
    }
    
    public final int FPS = 60; 
    
    @Override 
    public void run(){
        double drawInterval = 1000000000/FPS; 
        double nextDrawInterval = System.nanoTime() + drawInterval; 
        
        while(gameThread != null){
            
            update(); 
            repaint(); 
            //System.out.println("Player: "+player.getX() + " "+player.getY());
            try{

                double remainingTime = nextDrawInterval - System.nanoTime(); 
                remainingTime = remainingTime/1000000; 
                
                if(remainingTime < 0){
                    remainingTime = 0; 
                }
                
                Thread.sleep((long) remainingTime); 
                
                nextDrawInterval += drawInterval; 
                
            }catch(InterruptedException e){
                
            }
        }
    }
    
    public void update(){
        player.update(); 
        player2.update();
    }
    
    
    public void paintComponent(Graphics g){
    
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g; 
  
        tileM.draw(g2); 
        player.draw(g2); 
        player2.draw(g2);
        
        g2.dispose(); 
    }
    
    
    public void playMusic(int i){
        sound.setFile(i); 
        System.out.println(sound.clip);
        sound.play(); 
        sound.loop();
    }
    
    public void stopMusic(){
        sound.stop(); 
    }
    
    public void playSE(int i){
        sound.setFile(i); 
        sound.play(); 
    }
}
