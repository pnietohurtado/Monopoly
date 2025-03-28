/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopoly.modelos;


import com.mycompany.monopoly.conexionBBDD.Conexion;
import static com.mycompany.monopoly.conexionBBDD.Conexion.getConnection;
import com.mycompany.monopoly.conexionBBDD.interfaces.ICasillasRepositorio;
import com.mycompany.monopoly.conexionBBDD.ropositorios.CasillasRepositorio;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author pablo
 */
public class Tablero {
    private Jugadores j1, j2; 
    private List<Casilla> casillasJ1; //NO TOCAR 
    private List<Casilla> casillasJ2; //NO TOCAR 
    private List<Casilla> casillasDisponibles; //NO TOCAR 
    
    ICasillasRepositorio cas = new CasillasRepositorio();  //NO TOCAR 
    
    
    public Connection getConnectio() throws SQLException{
        return Conexion.getConnection(); 
    }
    
    public Tablero() throws SQLException, Exception{
        this.casillasJ1 = new ArrayList<>(); 
        this.casillasJ2 = new ArrayList<>();
        this.casillasDisponibles = new ArrayList<>();
        //cas.cargarCasillasCasilla(this);
    }
    
    public Tablero(Jugadores j1, Jugadores j2) throws SQLException ,Exception{
        this(); 
        this.j1 = j1; 
        this.j2 = j2; 
    }
    
    
    public static double getDineroJ1(ResultSet rs)throws SQLException{
        double dineroActual = rs.getDouble("J1_Dinero"); 
        return dineroActual; 
    }
    
    public static double getDineroJ2(ResultSet rs)throws SQLException{
        double dineroActual = rs.getDouble("J2_Dinero"); 
        return dineroActual; 
    }
    
    /***********************Inicio de nuestro servidor******************************/
    
    public void inicioPartida(Long id, int numJug) throws SQLException, Exception{
        
        PreparedStatement pt2 = getConnection().prepareStatement("call llamarTurnoInicio()");  
        pt2.executeUpdate(); 
        pt2.close(); 
        
        if(numJug == 1){
            PreparedStatement pt = getConnection().prepareStatement("call borrarJugador1()"); 
            pt.executeUpdate(); 
            pt.close(); 
            
            PreparedStatement pt3 = getConnection().prepareStatement("INSERT INTO jugador?(J1_Id, J1_IdUser, J1_IdCasilla) VALUES (1,?,100)"); 
            pt3.setInt(1, numJug); 
            pt3.setLong(2, id); 
            pt3.executeUpdate(); 
            pt3.close(); 
        }else if(numJug == 2){
            PreparedStatement pt = getConnection().prepareStatement("call borrarJugador2()"); 
            pt.executeUpdate(); 
            pt.close(); 
            
            System.out.println("Jugador2");
            PreparedStatement pt3 = getConnection().prepareStatement("INSERT INTO jugador?(J2_Id, J2_IdUser, J2_IdCasilla) VALUES (1,?,100)"); 
            pt3.setInt(1, numJug); 
            pt3.setLong(2, id); 
            pt3.executeUpdate(); 
            pt3.close(); 
        }
    }   
    
   
    
    
    /*******************************************************************************/
    
    
    
    public void CargarCasillaJ1(Long id, Jugador1 j) throws SQLException,Exception{
       
        
        PreparedStatement pt = getConnection().prepareStatement("update casilla set CAS_Disponibilidad = 1, CAS_IdPropietario = ?, CAS_Propietario = ? where CAS_Id = ?"); 
        pt.setLong(1, j.getJ1_IdUser());
        pt.setString(2, "jugador1"); 
        pt.setLong(3, id); 
        pt.executeUpdate(); 
        pt.close(); 
        
        /*De esta forma cada vez que un jugador compre alguna de las casillas no solo se va a actualizar en 
        nuestro programa sino que también se va a ir actualizando en nuestra base de datos. */
        String sql = "call actualizaJ1()"; 
        PreparedStatement pt2 = getConnection().prepareStatement(sql); 
        pt2.executeUpdate(); 
        pt2.close(); 
        
        
        PreparedStatement pt3 = getConnection().prepareStatement("update jugador1 set J1_Dinero = J1_Dinero - (select CAS_Precio from casilla where CAS_Id = ?) where J1_IdCasilla = ? "); 
        pt3.setLong(1, id); 
        pt3.setLong(2, j.getJ1_IdCasilla());
        pt3.executeUpdate(); 
        pt.close(); 
        
        double saldo = 0.0d; 
        
        PreparedStatement pt4 = getConnection().prepareStatement("select J1_Dinero from jugador1 order by J1_Dinero asc  limit ?");
        pt4.setInt(1, 1);
        ResultSet rs = pt4.executeQuery(); 
        if(rs.next()){
            saldo = getDineroJ1(rs); 
            j.setJ1_Dinero(saldo);
        }
        //System.out.println("Saldo actual "+saldo);
        rs.close(); 
        
        Casilla c = porId(id); 
        c.setCAS_Disponibilidad(1);
        //System.out.println("Aquiiiiiiaisdiaisdasdasd:  "+c );
        casillasJ1.add(c); 
        //System.out.println(casillasJ1);
        EliminarCasillaDisponible(id); 
        
    }
    
    public void CargarCasillaJ2(Long id, Jugador2 j) throws SQLException,Exception{
        
        
        PreparedStatement pt = getConnection().prepareStatement("update casilla set CAS_Disponibilidad = 1, CAS_IdPropietario = ?, CAS_Propietario = ? where CAS_Id = ?"); 
        pt.setLong(1, j.getJ2_IdUser());
        pt.setString(2, "jugador2"); 
        pt.setLong(3, id); 
        pt.executeUpdate(); 
        
        /*De esta forma cada vez que un jugador compre alguna de las casillas no solo se va a actualizar en 
        nuestro programa sino que también se va a ir actualizando en nuestra base de datos. */
        String sql = "call actualizaJ2()"; 
        PreparedStatement pt2 = getConnection().prepareStatement(sql); 
        pt2.executeUpdate(); 
        
        PreparedStatement pt3 = getConnection().prepareStatement("update jugador2 set J2_Dinero = J2_Dinero - (select CAS_Precio from casilla where CAS_Id = ?) where J2_IdCasilla = ? "); 
        pt3.setLong(1, id); 
        pt3.setLong(2, j.getJ2_IdCasilla());
        pt3.executeUpdate(); 
        
        double saldo = 0.0d; 
        
        PreparedStatement pt4 = getConnection().prepareStatement("select J2_Dinero from jugador2 order by J2_Dinero asc  limit ?");
        pt4.setInt(1, 1);
        ResultSet rs = pt4.executeQuery(); 
        if(rs.next()){
            saldo = getDineroJ2(rs); 
            j.setJ2_Dinero(saldo);
        }
        //System.out.println("Saldo actual "+saldo);
        
        Casilla c = porId(id); 
        c.setCAS_Disponibilidad(1);
        casillasJ2.add(c); 
        EliminarCasillaDisponible(id); 
        
        
    }
    
    public Casilla porId(Long id) {
        
        Iterator<Casilla> it = casillasDisponibles.iterator(); 
        while(it.hasNext()){
            Casilla c = it.next(); 
            if(c.getCAS_Id().equals(id)){
                return  c; 
            }
        }
        return null; 
    }
    
    
    public void ActualizarCasillasDisponibles() {
        casillasDisponibles.clear(); /*Borra todas las casillas dentro de casillas disponibles*/
    }
    
    public void EliminarCasillaDisponible(Long id) throws SQLException,Exception{
        Casilla c = porId(id); 
        casillasDisponibles.remove(c); 
    }
    
    public void CargarCasillasJuego(Casilla c) throws SQLException,Exception
               /*Esta función va a ser la encargada de añadir todos los datos de las casillas a 
            nuestro programa de java desde la Base de Datos, haciendo los datos aún más manejables*/
    {
        
        casillasDisponibles.add(c); 
    }
    
    //Vaciar las Casillas Disponibles para poder ir a la par con la BBDD 
    
    
    
    public void limiparCasillasJugador1(){
        casillasJ1.clear(); 
    }
    
    public void limiparCasillasJugador2(){
        casillasJ2.clear(); 
    } 
    
    public List<Casilla> addCasillasJugador1() throws SQLException,Exception{
       
        
        PreparedStatement pt = getConnection().prepareStatement("select * from casilla where CAS_Id != 100 and CAS_Disponibilidad != 0 AND CAS_Propietario = 'jugador1'"); 
        ResultSet rs = pt.executeQuery(); 
        
        while(rs.next()){
            Casilla c = new Casilla(); 
            c.setCAS_Id(rs.getLong("CAS_Id")); 
            c.setCAS_Nombre(rs.getString("CAS_Nombre"));
            c.setCAS_Precio(rs.getDouble("CAS_Precio"));
            c.setCAS_Disponibilidad(rs.getInt("CAS_Disponibilidad"));
            c.setCAS_Propietario(rs.getString("CAS_Propietario"));
            c.setCAS_Tipo(rs.getString("CAS_Tipo")); 
            c.setCAS_Color(rs.getString("CAS_Color")); 
            casillasJ1.add(c); 
        }
        return casillasJ1; 
    }
    
    public List<Casilla> addCasillasJugador2() throws SQLException,Exception{
       
        
        PreparedStatement pt = getConnection().prepareStatement("select * from casilla where CAS_Id != 100 and CAS_Disponibilidad != 0 AND CAS_Propietario = 'jugador2'"); 
        ResultSet rs = pt.executeQuery(); 
        
        while(rs.next()){
            Casilla c = new Casilla(); 
            c.setCAS_Id(rs.getLong("CAS_Id")); 
            c.setCAS_Nombre(rs.getString("CAS_Nombre"));
            c.setCAS_Precio(rs.getDouble("CAS_Precio"));
            c.setCAS_Disponibilidad(rs.getInt("CAS_Disponibilidad"));
            c.setCAS_Propietario(rs.getString("CAS_Propietario"));
            c.setCAS_Tipo(rs.getString("CAS_Tipo")); 
            c.setCAS_Color(rs.getString("CAS_Color")); 
            casillasJ2.add(c); 
        }
        return casillasJ2; 
    }
    
    public List<Casilla> casillasDisponibles() throws SQLException,Exception{
        return this.casillasDisponibles; 
    }
    /*
    public String recorrerCasillasDisponibles() throws SQLException,Exception{
        Iterator<Casilla> it = casillasDisponibles.iterator(); 
        String linea = ""; 
        while(it.hasNext()){
            Casilla c = it.next(); 
            
        
        }
    }*/
    

 
    /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
    
    public void actualizarSaldoJ1(Long id, Jugador1 j, int numero) throws SQLException,Exception{
        
        double saldo = 0.0d; 
        PreparedStatement pt = getConnection().prepareStatement("select J1_Dinero from jugador1 order by J1_Dinero asc  limit ?");
        pt.setInt(1, 1);
        ResultSet rs = pt.executeQuery(); 
        if(rs.next()){
            saldo = getDineroJ1(rs); 
            j.setJ1_Dinero(saldo);
        }
        if(numero == 1){

            double dinero_actual_jugador2 = 0d; 
            PreparedStatement pt5 = getConnection().prepareStatement("SELECT J2_Dinero FROM jugador2 WHERE J2_Id=1"); 
            ResultSet rs3 = pt5.executeQuery(); 

            if(rs3.next()){
                dinero_actual_jugador2 = rs3.getDouble("J2_Dinero"); 
            }

            Casilla casilla = cas.porId(id);
            String color_de_la_casilla = casilla.getCAS_Color(); 

            if(casilla.getCAS_Propietario() != null){
                if(casilla.getCAS_Propietario().equals("jugador1")){


                    System.out.println("Esta ya es tu propiedad!");


                }else if(casilla.getCAS_Propietario().equals("jugador2")){
                    Connection conn = getConnection(); 

                    int numero_propiedades = 0; 

                    System.out.println("Paga multa");
                    PreparedStatement pt3 = conn.prepareStatement("SELECT COUNT(Cas_Id) FROM casilla WHERE Cas_Color = ? AND CAS_Propietario = 'jugador2'"); 
                    pt3.setString(1, color_de_la_casilla);
                    ResultSet rs2 = pt3.executeQuery(); 

                    if(rs2.next()){
                        numero_propiedades = rs2.getInt(1); 
                    }


                    double multa = 0.0d; 
                    if(numero_propiedades == 1){
                        multa = casilla.getCAS_Precio() * 0.35; 
                    }else if(numero_propiedades == 2){
                        multa = casilla.getCAS_Precio() * 0.40; 
                    }else if(numero_propiedades == 3){
                        multa = casilla.getCAS_Precio()*0.45; 
                    }

                    saldo -= multa; 

                    j.setJ1_Dinero(saldo);

                    PreparedStatement pt4 = getConnection().prepareStatement("UPDATE jugador1 SET J1_Dinero = ? WHERE J1_Id = 1"); 
                    pt4.setDouble(1, saldo); 
                    pt4.executeUpdate(); 

                    dinero_actual_jugador2 += multa; 
                    PreparedStatement pt2 = conn.prepareStatement("UPDATE jugador2 SET J2_Dinero = ? WHERE J2_Id = 1") ; 
                    pt2.setDouble(1, dinero_actual_jugador2); 
                    pt2.executeUpdate(); 
                }else{
                    System.out.println("aimaiiiiiiii");
                }
            }    
        }
        
        
        /*Podría hacer una parte la cual comprueba el número de casillas de ese mismo 
        tipo de su rival "SELECT COUNT(Cas_Id) FROM casilla WHERE Cas_Color = ?" Y en función si el 
        resultado es 1 (Se multiplica por 0.35), 2 (Se multiplica por 0.40) y si es 3 (Se multiplica por 
        0.45). */
      
    }
    
    
    public void actualizarSaldoJ2(Long id, Jugador2 j,int numero) throws SQLException,Exception{
        
        double saldo = 0.0d; 
        
        PreparedStatement pt = getConnection().prepareStatement("select J2_Dinero from jugador2 order by J2_Dinero asc  limit ?");
        pt.setInt(1, 1);
        ResultSet rs = pt.executeQuery(); 
        if(rs.next()){
            saldo = getDineroJ2(rs); 
            j.setJ2_Dinero(saldo);
        }
        
        Casilla casilla = cas.porId(id); 
        String color_de_la_casilla = casilla.getCAS_Color(); 
        
        if(numero == 1){

                if(casilla.getCAS_Propietario() != null){
                    if(casilla.getCAS_Propietario().equals("jugador2")){

                    System.out.println("Esta ya es tu propiedad!");

                    }else if(casilla.getCAS_Propietario().equals("jugador1")){

                        System.out.println("Paga multa");

                        int numero_propiedades = 0; 

                        PreparedStatement pt3 = getConnection().prepareStatement("SELECT COUNT(Cas_Id) FROM casilla WHERE Cas_Color = ? AND CAS_Propietario = 'jugador1'"); 
                        pt3.setString(1, color_de_la_casilla);
                        ResultSet rs2 = pt3.executeQuery(); 

                        if(rs2.next()){
                            numero_propiedades = rs2.getInt(1); 
                        }

                        double dinero_actual_jugador1 = 0d; 
                        PreparedStatement pt5 = getConnection().prepareStatement("SELECT J1_Dinero FROM jugador1 WHERE J1_Id=1"); 
                        ResultSet rs3 = pt5.executeQuery(); 

                        if(rs3.next()){
                            dinero_actual_jugador1 = rs3.getDouble("J1_Dinero"); 
                        }

                        double multa = 0.0d; 
                        if(numero_propiedades == 1){
                            multa = casilla.getCAS_Precio() * 0.35; 
                        }else if(numero_propiedades == 2){
                            multa = casilla.getCAS_Precio() * 0.40; 
                        }else if(numero_propiedades == 3){
                            multa = casilla.getCAS_Precio()*0.45; 
                        }


                        //double multa = casilla.getCAS_Precio() * 0.35; 

                        saldo -= multa; 

                        j.setJ2_Dinero(saldo);

                        PreparedStatement pt4 = getConnection().prepareStatement("UPDATE jugador2 SET J2_Dinero = ? WHERE J2_Id = 1"); 
                        pt4.setDouble(1, saldo); 
                        pt4.executeUpdate(); 

                        dinero_actual_jugador1 += multa; 
                        PreparedStatement pt2 = getConnection().prepareStatement("UPDATE jugador1 SET J1_Dinero = ? WHERE J1_Id = 1") ; 
                        pt2.setDouble(1, dinero_actual_jugador1);
                        pt2.executeUpdate(); 
                }else{
                    System.out.println("aimaiiiiiiii");
                }
            }
        }
      
    }
    
    public void vueltaCompletada(Long id, Jugadores j, int num) throws SQLException, Exception{
        double saldo = 0.0d; 
        
        if(num == 1){
            Jugador1 x = (Jugador1) j; 
            PreparedStatement pt = getConnection().prepareStatement("update jugador1 set J1_Dinero = (? + 20) where J1_Id = 1");            
            pt.setDouble(1, x.getJ1_Dinero()); 

            pt.executeUpdate(); 
        }else if (num == 2){
            Jugador2 x = (Jugador2) j; 
            PreparedStatement pt = getConnection().prepareStatement("update jugador2 set J2_Dinero = (? + 20) where J2_Id = 1");
            pt.setDouble(1, x.getJ2_Dinero()); 
            pt.executeUpdate(); 
        }
    }
    

    
    
    /*Casillas de la suerte o mala suerte para cada usurio*/ 
    public void suerte1(Jugador1 j1) throws SQLException, Exception{
        PreparedStatement pt = getConnection().prepareStatement("update jugador1 set J1_Dinero = (? + 10) where J1_Id = 1");
        //pt.setInt(1, 1);
        pt.setDouble(1, j1.getJ1_Dinero()); 
        
        pt.executeUpdate(); 
        
    }
    public void suerte2(Jugador2 j2) throws SQLException, Exception{
        PreparedStatement pt = getConnection().prepareStatement("update jugador2 set J2_Dinero = (? + 10) where J2_Id = 1");
        //pt.setInt(1, 1);
        pt.setDouble(1, j2.getJ2_Dinero()); 
        
        pt.executeUpdate(); 
        
    }
    
    @Override 
    public String toString(){
        StringBuilder sb = new StringBuilder(); 
        sb.append(" Jugador 1 { ").append(this.j1).append(" } Jugador 2 { ").append(this.j2).append(" }").append("\n"); 
        return sb.toString(); 
    }
}
