/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopoly.frames.Player;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author pablo
 */
public class Entity {
    public int x, y; // Se encarga de representar la posición de nuestro muñeco en el tablero. 
    public int speed; 

    public BufferedImage f1,f2,l1,l2,r1,r2,b1,b2; 
    public String direction; 
    
    public int spriteCounter = 0; 
    public int spriteNum = 1; 
   
}
