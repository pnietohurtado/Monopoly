/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopoly.frames;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JTextArea;

/**
 *
 * @author pablo
 */
public class Display extends JTextArea{
    
    final int originalTileSize = 19; // Lo que significa que cada elemento que encontremos en el juego va a medir 19x19
    final int scale = 3;         
    
    public final int tileSize = originalTileSize * scale;  // 57x57
    public final int maxScreenCol = 5; 
    public final int maxScreenRow = 6; 
    public final int screenWidth = tileSize * maxScreenCol; // 76
    public final int screenHeight = tileSize * maxScreenRow; // 114
    
    
    public Display()
    {
        this.setPreferredSize(new Dimension(this.screenWidth, this.screenHeight)); 
        this.setMinimumSize(new Dimension(this.screenWidth, this.screenHeight));
        this.setBackground(Color.white); 
        this.setDoubleBuffered(true); 
        this.setFocusable(true); 
        this.setEditable(false); //Para hacer que el area de texto no sea editable
        this.setLineWrap(true);
        this.setWrapStyleWord(true);
        
        this.setText("hola que tal"); 
        
    }
    
    
    
    
}
