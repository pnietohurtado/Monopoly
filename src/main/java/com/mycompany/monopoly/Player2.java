/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.monopoly;

import static com.mycompany.monopoly.Player1.ESTADO_DE_TURNO;
import static com.mycompany.monopoly.Player1.getConnection;
import com.mycompany.monopoly.conexionBBDD.Conexion;
import com.mycompany.monopoly.conexionBBDD.interfaces.ICasillasRepositorio;
import com.mycompany.monopoly.conexionBBDD.interfaces.IJugadoresRepositorio;
import com.mycompany.monopoly.conexionBBDD.interfaces.IPosicionRepositorio;
import com.mycompany.monopoly.conexionBBDD.interfaces.IUsuarioRepositorio;
import com.mycompany.monopoly.conexionBBDD.ropositorios.CasillasRepositorio;
import com.mycompany.monopoly.conexionBBDD.ropositorios.Jugador2Repositorio;
import com.mycompany.monopoly.conexionBBDD.ropositorios.PosicionJ1Repositorio;
import com.mycompany.monopoly.conexionBBDD.ropositorios.PosicionJ2Repositorio;
import com.mycompany.monopoly.conexionBBDD.ropositorios.UsuarioIRepositorio;
import com.mycompany.monopoly.modelos.Casilla;
import com.mycompany.monopoly.modelos.Jugador2;
import com.mycompany.monopoly.modelos.PosicionJ1;
import com.mycompany.monopoly.modelos.PosicionJ2;
import com.mycompany.monopoly.modelos.Tablero;
import com.mycompany.monopoly.modelos.UsuarioI;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
//import static com.mycompany.monopoly.pruebas.dado;
//import static com.mycompany.monopoly.pruebas.listarTablero;
//import static com.mycompany.monopoly.pruebas.resetearJugador;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author pablo
 */
public class Player2 {
    
    public static int ESTADO_DE_TURNO = 0; 
    
    public static  Connection getConnection() throws SQLException{
        return Conexion.getConnection(); 
    }
    
    
    public static String menuJugador(){
        
        Scanner sc = new Scanner(System.in); 
        String opcion = ""; 
        System.out.println(" [1] Tirar el dado. ");
        System.out.println(" [2] Mostrar casillas disponibles. ");
        System.out.println(" [3] Mostrar casillas en propiedad. ");
        System.out.println(" [4] Mostrar dinero. ");
        System.out.println(" [5] Iniciar intercambio. ");
        System.out.println(" [6] RENDIRSE. ");
        System.out.print("Elección -> ");
        opcion = sc.nextLine(); 
        //sc.close(); 
        return opcion; 
    }
    
    
   public static void resetearJugador(String[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                if (tablero[i][j].equals(" 1 ") || tablero[i][j].equals(" 2 ")) {
                    tablero[i][j] = "   ";
                }
            }
        }
    }

    public static int dado() {
        return (int) (Math.random() * 6) + 1;
    }

    public static void listarTablero(String[][] tablero) {
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[0].length; j++) {
                System.out.print(tablero[i][j]);
            }
            System.out.println("");
        }
    }
    
    /*Función que mire si los usuarios están en banca rota o no*/
    
    public static void ganador( Jugador2 jug2) throws SQLException, Exception{
        String ganador = ""; 
        PreparedStatement pt = getConnection().prepareStatement("CALL ganador()"); 
        ResultSet rs = pt.executeQuery(); 
        if(rs.next()){
            ganador = rs.getString("resultado"); 
        }
        
        
        if(!(ganador.equals("Se Bugeo"))){
            System.exit(0); 
            System.out.println("El ganador es "+ganador);
            pt.close(); 
            rs.close(); 
        }
    }
    
    
    public static class ClaseComun{
       private int J_Turno; 
        
        public ClaseComun(){}
        
        public int getJ_Turno(){return this.J_Turno; }
        public void setJ_Turno(int t){ this.J_Turno = t;  }
        
    }
    
    public static class HiloJugador2 implements Runnable{
        
        private final ClaseComun c; 
        
        public HiloJugador2(ClaseComun c){
            this.c = c; 
        }

        @Override
        public void run() {
            
            
            try{

                                /*!!!!!!!!!!!!    REGISTRO DE USUARIO   !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
                     /*
                     System.out.print("Dime tu nombre: ");
                     String nombre1 = sc.nextLine(); 
                     System.out.print("Dime tus apellidos: ");
                     String apellido1 = sc.nextLine(); 
                     System.out.println("Registra un usuario");
                     String user1 = sc.nextLine(); 
                     System.out.println("Registra una contraseña");
                     String pass1 = sc.nextLine(); 


                     System.out.print("Dime tu nombre: ");
                     String nombre2 = sc.nextLine(); 
                     System.out.print("Dime tus apellidos: ");
                     String apellido2 = sc.nextLine();
                     System.out.println("Registra un usuario");
                     String user2 = sc.nextLine(); 
                     System.out.println("Registra una contraseña");
                     String pass3 = sc.nextLine(); 

                     */

                     /*Se llama a la BBDD para insertar los dos usuarios*/
                     /*
                     usuarios.insertar(new Usuario(nombre1, apellido1, user1, pass1));
                     usuarios.insertar(new Usuario(nombre2, apellido2, user2, pass3));
                     */
                     /*****************************************************************/



                     /*!!!!!!!!!!!!!!!!!!!    INICIO DE SESIÓN DE LOS DOS JUGADORES    !!!!!!!!!!!!!!!!!!!!!!!*/


                     System.out.println("Inicio de sesión, Dime un nombre: ");
                     String name2 = sc.nextLine(); 
                     System.out.println("Dime la contraseña: ");
                     String contra2 = sc.nextLine();

                     /*Se llama a la BBDD para iniciar sesión con los dos jugadores*/


                     //u.inicioSesion(name2, contra2, 2);


                     UsuarioI usuario2 = u.porUser(name2); 
                     /**************************************************************************/

                     //En base a "usuario1 y usuario2" vamos a obtener los id necesarios para poder 
                     //realizar las cuatro consultas básicas de nuestro servidor. 

                        //Esta un poco más abajo donde instanciamos el "Tablero" 


                     /**************************************************************************/



                     /*!!!!!!!!!!!!!!!!!!!!    ASIGNANDO LOS JUGADORES CON SUS PERFILES !!!!!!!!!!!!!!!!!!!!!!!!*/


                     UsuarioI u2 = u.porUser("rufian"); 

                     Long idJ2 = u2.getUI_Id(); 
                     //System.out.println("id "+ idJ1);


                     Jugador2 jug2 = j2.porId(idJ2); // por "idJ1 y idJ2" para poder tener los datos de los jugadores en físico

                     /****************************************************************************/

                     
                     Tablero t = new Tablero( null, jug2); 
                     
                     t.inicioPartida(usuario2.getUI_Id(),2); //Borra todos los datos de la partida anterior
                     



                    /*!!!!!!!!!!!!!!!!!!!!    PARTE DEL JUEGO CON EL TABLERO    !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/

                    String[][] tableroPlantilla = new String[11][11]; 
                    for (int i = 0; i < tableroPlantilla.length; i++) {
                        for (int j = 0; j < tableroPlantilla[0].length; j++) {
                            // Verificar si estamos en el anillo exterior
                            if (i == 0 || i == 11 - 1 || j == 0 || j == 11 - 1) {
                                tableroPlantilla[i][j] = " x "; 
                            } else {
                                tableroPlantilla[i][j] = "   "; 
                            }
                        }
                    }


                    tableroPlantilla[0][0] = " S "; 

                    tableroPlantilla[0][1] = " R "; //Rosa
                    tableroPlantilla[0][3] = " R "; //Rosa

                    tableroPlantilla[0][6] = " A "; //Azul Claro
                    tableroPlantilla[0][8] = " A "; 
                    tableroPlantilla[0][9] = " A "; 

                    tableroPlantilla[0][10] = " C "; //Carcel
                    tableroPlantilla[10][0] = " C "; //Carcel

                    tableroPlantilla[1][0] = " A "; //Azul Oscuro
                    tableroPlantilla[3][0] = " A "; //Azul Oscuro

                    tableroPlantilla[6][0] = " V "; //Verde
                    tableroPlantilla[8][0] = " V "; //Verde
                    tableroPlantilla[9][0] = " V "; //Verde

                    tableroPlantilla[1][10] = " M "; //Morado
                    tableroPlantilla[3][10] = " M "; //Morado
                    tableroPlantilla[4][10] = " M "; //Morado

                    tableroPlantilla[6][10] = " N "; //Naranja
                    tableroPlantilla[8][10] = " N "; //Naranja
                    tableroPlantilla[9][10] = " N "; //Naranja

                    tableroPlantilla[10][1] = " A "; //Amarillo
                    tableroPlantilla[10][3] = " A "; //Amarillo
                    tableroPlantilla[10][4] = " A "; //Amarillo

                    tableroPlantilla[10][6] = " R "; //Rojo
                    tableroPlantilla[10][7] = " R "; //Rojo
                    tableroPlantilla[10][9] = " R "; //Rojo




                    String[][] tablero = new String[11][11];

                    // Inicializar tablero con bordes y espacios
                    for (int i = 0; i < tablero.length; i++) {
                        for (int j = 0; j < tablero[0].length; j++) {
                            if (i == 0 || i == 10 || j == 0 || j == 10) {
                                tablero[i][j] = " x ";
                            } else {
                                tablero[i][j] = "   ";
                            }
                        }
                    }


                    tablero[0][0] = " S "; 

                    tablero[0][1] = " R "; //Rosa
                    tablero[0][3] = " R "; //Rosa

                    tablero[0][6] = " A "; //Azul Claro
                    tablero[0][8] = " A "; 
                    tablero[0][9] = " A "; 

                    tablero[0][10] = " C "; //Carcel
                    tablero[10][0] = " C "; //Carcel

                    tablero[1][0] = " A "; //Azul Oscuro
                    tablero[3][0] = " A "; //Azul Oscuro

                    tablero[6][0] = " V "; //Verde
                    tablero[8][0] = " V "; //Verde
                    tablero[9][0] = " V "; //Verde

                    tablero[1][10] = " M "; //Morado
                    tablero[3][10] = " M "; //Morado
                    tablero[4][10] = " M "; //Morado

                    tablero[6][10] = " N "; //Naranja
                    tablero[8][10] = " N "; //Naranja
                    tablero[9][10] = " N "; //Naranja

                    tablero[10][1] = " A "; //Amarillo
                    tablero[10][3] = " A "; //Amarillo
                    tablero[10][4] = " A "; //Amarillo

                    tablero[10][6] = " R "; //Rojo
                    tablero[10][7] = " R "; //Rojo
                    tablero[10][9] = " R "; //Rojo



                    // Posición inicial del jugador
                    int x1 = 0;
                    int y1 = 0;
                    int x2 = 0; 
                    int y2 = 0; 
                    tablero[x1][y1] = " 1 ";
                    tablero[x2][y2] = " 2 ";
                    String respuesta;

                    int vueltaJ2 = 0; // jugador 1 y jugador 2





                    /*Cargar las casillas disponibles*/
                    cas.cargarCasillasCasilla(t);




                    int carcelJ2 = 0; 
                

                    do 
                            /*A partir de aquí empieza el tablero del juego donde los jugadores van
                            a ir turnandose entre ellos para ir tirando el dado */

                    {

                        
                            
                            synchronized(c){
                                try{
                            
                                    System.out.println("Me pongo en modo espera...");
                                    ESTADO_DE_TURNO = 0; 
                                    c.wait(); 
                                }catch(InterruptedException e4){
                                                
                                }
                                            
                                            
                            }
                        ESTADO_DE_TURNO = 1; 

                        System.out.println("======================");
                        System.out.print("¿Seguir jugando? (no para salir): ");
                        respuesta = sc.nextLine();

                        if (respuesta.equalsIgnoreCase("no")) {
                            break;
                        }








                        /*=====================================================================*/
                        /*=====================================================================*/
                        /*A partir de aquí juega el jugador 2 ---------------------------------*/
                        /*=====================================================================*/
                        /*=====================================================================*/




                        System.out.println("==============Jugador 2==================");


                        System.out.println("Tira el dado...");
                        int pasos2 = dado();
                        System.out.println("El dado mostró: " + pasos2);

                        // Actualizar la posición del jugador en sentido de las agujas del reloj 

                        String eleccionJ2; 
                        
                        do{
                            
                            
                            System.out.println("==============Jugador 2==================");
                            eleccionJ2 = menuJugador(); 
                            System.out.println("carcel "+carcelJ2);
                            if(carcelJ2 == 0){
                                switch(eleccionJ2){

                                    case "1": {
                                        for (int i = 0; i < pasos2; i++) {
                                            if (x2 == 0 && y2 < 10) { // Va hacia la derecha
                                                y2++;
                                                if(tablero[x2][y2].equals(" S ")){
                                                    System.out.println("vueltaaaaaaaa");
                                                    t.vueltaCompletada(idJ2, jug2,2);

                                                    ++vueltaJ2; 
                                                }
                                            } else if (y2 == 10 && x2 < 10) { // Baja
                                                x2++;
                                            } else if (x2 == 10 && y2 > 0) { // Va hacia la izquierda
                                                y2--;
                                            } else if (y2 == 0 && x2 > 0) { // Sube
                                                x2--;
                                                if(tablero[x2][y2].equals(" S ")){
                                                    System.out.println("vueltaaaaaaaa");
                                                    t.vueltaCompletada(idJ2, jug2,2);
                                                    ++vueltaJ2; 
                                                }
                                            }
                                        }

                                        resetearJugador(tablero);


                                        tablero[x2][y2] = " 2 "; // x2 = posI del jugador2 , y2 = posJ del jugador2 



                                        for(int i = 0; i < tablero.length ; i++){
                                            for(int j = 0 ; j< tablero[0].length ; j++){

                                                if(tablero[i][j].equals(" 1 ") ||tablero[i][j].equals(" 2 ")){
                                                    //continue; 
                                                }else {
                                                    tablero[i][j] = tableroPlantilla[i][j]; 
                                                }

                                            }
                                        }
                                        /*!!!!!!!!!Enviamos las posiciones de el Jugador1!!!!!!!!!*/
                                        posJ2.obtenerPosActual(idJ2,x2,y2); 


                                        tablero[posJ1.obtenerX()][posJ1.obtenerY()] = " 1 "; 

                                        listarTablero(tablero);


                                        System.out.println("Posicion J2 "+posJ2.obtenerX() + " "+posJ2.obtenerY());
                                        System.out.println("Posicion J1 "+posJ1.obtenerX() + " "+posJ1.obtenerY());


                                        /*!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/


                                        /*En caso de que caiga en la carcel*/
                                        if(tablero[0][10].equals(" 2 ")||tablero[10][0].equals(" 2 ")){
                                            System.out.println("Entra a la carcel...");
                                            carcelJ2 = 2; 
                                            PreparedStatement pt = getConnection().prepareStatement("UPDATE turno SET J_Turno = 0 WHERE J_Turno = 1; "); 
                                            pt.executeUpdate(); 
                                            
                                            break; 
                                        }

                                        /*!!!!!!!!En caso de que caiga en una casilla comprable!!!!!!!!!!!!!!*/

                                        Long id2 = cas.obtenerIdCasilla(x2, y2); 
                                        System.out.println("id "+id2);
                                        //System.out.println("jug1 "+ jug1);
                                        if(id2 != null){
                                            Casilla casilla = cas.porId(id2); 
                                            t.actualizarSaldoJ2(id2, jug2,4);
                                            System.out.println("Dinero disponible -> "+ jug2.getJ2_Dinero());


                                            if(casilla.getCAS_Propietario() == null){
                                                if(casilla.getCAS_Tipo().equals("Propiedad")){
                                                    System.out.println("Quiere comprar la propiedad "+ cas.porId(id2)+ " [Y/N]");
                                                    String respuesta2 = sc.nextLine(); 
                                                    if(respuesta2.equalsIgnoreCase("y")){
                                                        casilla.setCAS_Disponibilidad(1);
                                                        t.CargarCasillaJ2(id2, jug2); //11
                                                        //System.out.println(t.casillasJugador2());
                                                        //casilla.setCAS_Disponibilidad(1);
                                                    }
                                                }else if(casilla.getCAS_Tipo().equals("Suerte")){
                                                    t.suerte2(jug2); 
                                                }
                                            }else if(casilla.getCAS_Propietario().equals("jugador1")|| casilla.getCAS_Propietario().equals("jugador2")){
                                                //System.out.println("dentro de comprobacion");
                                                t.actualizarSaldoJ2(id2, jug2,1);

                                            }
                                            //System.out.println("Cuanto dinero le queda"+ jug1.getJ1_Dinero());
                                        }

                                        break; //Muy necesario ya que si no salta al siguiente "case" 
                                    }

                                    case "2": 
                                            /*Se encarga de mostrar las casillas que quedan disponibles para comprar
                                        para los dos jugadores, es decir, aquellas que no tienen dueño aún.*/
                                    {
                                        t.ActualizarCasillasDisponibles();
                                        cas.cargarCasillasCasilla(t);
                                        System.out.println(t.casillasDisponibles());

                                        break; 
                                    }

                                    case "3": {
                                        t.limiparCasillasJugador2();
                                        System.out.println(t.addCasillasJugador2());
                                        break; 
                                    }

                                    case "4": {
                                        t.actualizarSaldoJ2(100L, jug2,4);
                                        System.out.println("Dinero Actual -> "+ jug2.getJ2_Dinero());
                                        break; 
                                    }
                                    
                                    case "5": {
                                        synchronized(c){
                                            while(true){
                                                try{
                                                    
                                                    c.wait();
                                                    
                                                }catch(InterruptedException e3){
                                                    
                                                }
                                            }
                                        }
                                    }
                                    
                                    case "6" : {
                                        System.exit(0); 
                                    }

                                }
                            }else{
                                System.out.println("No puede hacer nada, está en la carcel!!!!!!!!!");
                                carcelJ2--; 
                                /*
                                PreparedStatement pt = getConnection().prepareStatement("UPDATE turno SET J_Turno = 0 WHERE J_Turno = 1; "); 
                                pt.executeUpdate(); 
                                */
                            }
                            
                           
                            
                            ganador(jug2); 
                        
                            if(eleccionJ2.equals("1")){
                                PreparedStatement pt = getConnection().prepareStatement("UPDATE turno SET J_Turno = 0 WHERE J_Turno = 1; "); 
                                pt.executeUpdate(); 
                            }
                            
                        }while(!(eleccionJ2.equals("1"))); 
                          
                        
                        
                    } while (!respuesta.equalsIgnoreCase("no"));


                
                
            }catch(SQLException e){
                
            }catch(Exception e2){
                
            }
            
            
            
        }
        
        
        
    }
    
    
    
    public static class Comprobar implements Runnable{

        private final ClaseComun c; 
        
        
        
        public Comprobar(ClaseComun c){
            this.c = c; 
        }
        
        @Override
        public void run() {
            try{
                while(true){
                    Thread.sleep(10000); 
                    if(ESTADO_DE_TURNO == 0){
                        //ClaseComun c = new ClaseComun(); 
                        System.out.println("\nCompruebo...");
                        PreparedStatement pt = getConnection().prepareStatement("SELECT * FROM turno"); 
                        ResultSet rs = pt.executeQuery(); 
                        if(rs.next()){
                            c.setJ_Turno(rs.getInt("J_Turno"));
                        }

                        if(c.getJ_Turno() == 1){
                            synchronized(c){
                                c.notifyAll();
                            }
                        }
                    }
                }
                
            }catch(InterruptedException e){
                
            }catch(SQLException e2){
                
            }catch(Exception e3){
                
            }
        }
    
    }
    
    /*----------------INSTANCIACIÓN DE TODOS LOS OBJETOS NECESARIOS PARA EL FUNCIONAMIENTO DEL JUEGO --------------------------------*/
        static Scanner sc = new Scanner(System.in); 
        //static IUsuarioRepositorio usuarios =  new UsuarioRepositorio(); //Nos encargamos de poder registrar los usuarios
        //y que con ayuda del trigger dentro de la BBDD se añadan a la tabla "usuarioI". 
        static IUsuarioRepositorio<UsuarioI> u  = new UsuarioIRepositorio(); //Necesario para confirmar el registro de los usuarios 
        
        static IJugadoresRepositorio<Jugador2>  j2 = new Jugador2Repositorio(); 
        
        static ICasillasRepositorio cas = new CasillasRepositorio(); //Con esto vamos a añadir y actualizar las casillas disponibles
        
        
        static IPosicionRepositorio<PosicionJ1> posJ1 = new PosicionJ1Repositorio(); 
        static IPosicionRepositorio<PosicionJ2> posJ2 = new PosicionJ2Repositorio(); 
   
    
    
    public static void main(String[] args)throws SQLException, Exception {
        
        ClaseComun cls = new ClaseComun(); 
        Thread t = new Thread(new HiloJugador2(cls)); 
        Thread t2 = new Thread(new Comprobar(cls)); 
        
        t.start(); 
        t2.start(); 
        
    }
    
}
