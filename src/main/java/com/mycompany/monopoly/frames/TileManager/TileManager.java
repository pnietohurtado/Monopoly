/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopoly.frames.TileManager;

import com.mycompany.monopoly.frames.GamePanel;
import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;

/**
 *
 * @author pablo
 */
public class TileManager {
    GamePanel gp ;  
    Tile[] tile; 
    int mapTileNum[][]; 
    
    public TileManager(GamePanel gp){
        this.gp = gp; 
        tile = new Tile[10]; 
        mapTileNum = new int[gp.maxScreenCol][gp.maxScreenRow]; 
        
        getTileImage(); 
        loadMap("/Map/Mapa.txt"); 
    }
    
    
    public void getTileImage(){
        try{
            
            tile[0] = new Tile(); 
            tile[0].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Grass1.png")); 
            
            
            tile[1] = new Tile(); 
            tile[1].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Grass2.png")); 
            
            tile[2] = new Tile(); 
            tile[2].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Grass3.png")); 
            
            tile[3] = new Tile(); 
            tile[3].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Carcel.png")); 
            
            tile[4] = new Tile(); 
            tile[4].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Salida1.png")); 
            
            tile[5] = new Tile(); 
            tile[5].image = ImageIO.read(getClass().getResourceAsStream("/Tile/Salida2.png")); 
            
            tile[6] = new Tile(); 
            tile[6].image = ImageIO.read(getClass().getResourceAsStream("/Tile/AvenidaAmerica.png")); 
            
            tile[7] = new Tile(); 
            tile[7].image = ImageIO.read(getClass().getResourceAsStream("/Tile/CalleMariaMolina.png")); 
            
            tile[8] = new Tile(); 
            tile[8].image = ImageIO.read(getClass().getResourceAsStream("/Tile/CalleCeaBermudez.png")); 
            
 
            
        }catch(IOException e){
            
        }
    }
    
     public void loadMap(String path){
        try{
            
            InputStream is = getClass().getResourceAsStream(path);
            BufferedReader br = new BufferedReader(new InputStreamReader(is)); 
            
            int col = 0; 
            int row = 0; 
            
            while(col < gp.maxScreenCol && row < gp.maxScreenRow ){
                String line = br.readLine(); 
                
                while(col < gp.maxScreenCol ){
                    String numbers[] = line.split(" "); 
                    
                    int num = Integer.parseInt(numbers[col]); 
                    mapTileNum[col][row] = num; 
                    col++; 
                }
                
                if(col == gp.maxScreenCol){
                    col = 0; 
                    row++; 
                }
            }
            
            br.close(); 
        }catch(Exception e){
            
        }
    }
    
     
     
      public void draw(Graphics2D g2)
    {
        int col = 0; 
        int row = 0; 
        int x = 0; 
        int y = 0; 
        
        while(col < gp.maxScreenCol && row < gp.maxScreenRow)
        {
            int tileNum = mapTileNum[col][row]; 
            
            g2.drawImage(tile[tileNum].image, x,y,gp.tileSize,gp.tileSize,null); 
            col++; 
            x += gp.tileSize; 
            
            if(col == gp.maxScreenCol){
                col = 0; 
                x = 0; 
                row++; 
                y += gp.tileSize; 
            }
        }
        
        
    }
    
    
}
