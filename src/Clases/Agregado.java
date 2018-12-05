/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package Clases;

import DBHelper.DBClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author aless
 */
public class Agregado {
    
    public static void AddLugar(JSONObject lugar){
       
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("INSERT INTO lugar VALUES (?,?,?,?,?);")
        ){
            pstInsertar.setString(1, lugar.getString("nombre"));
            pstInsertar.setString(2, lugar.getString("tipo"));
            pstInsertar.setString(3, lugar.getString("tipoGreografia"));
            pstInsertar.setString(4, lugar.getString("descripcion"));
            
            if (pstInsertar.executeUpdate() > 0){
                
                System.out.println("Lugar: "+lugar.getString("nombre")+" ha sido agregado a la DB\n");
                
            }else{
                
                System.out.println("Lugar: "+lugar.getString("nombre")+" no ha sido agregado a la DB\n");
                
            }
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        
     public static void AddPersonaje(JSONObject personaje){
        
        int lugarNac = -1;
        int personajeID = -1;
         
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("INSERT INTO personaje(nombreoriginal_personaje, nombrereal_personaje, apellidoreal_personaje, identidad_personaje, biografia_personaje, estadocivil_personaje, genero_personaje, altura_personaje, peso_personaje, colorojos_personaje, colorpelo_personaje, lugarNacimiento_fk, tipo_personaje) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstInsertarLugar = conn.prepareStatement("INSERT INTO lugar(nombre_lugar, tipo, tipo_greografia, id_lugar_fk) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)
        ){
            // INSERTAR PAIS
            pstInsertarLugar.setString(1, personaje.getString("pais"));
            pstInsertarLugar.setString(2, "PAIS");
            pstInsertarLugar.setString(3, "TIERRA");
            pstInsertarLugar.setNull(4, 0);
            
            if (pstInsertarLugar.executeUpdate() > 0){
                
                ResultSet paisRST = pstInsertarLugar.getGeneratedKeys();
                if (paisRST.next()){
                    //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");
                
                    // INSERTAR ESTADO
                    pstInsertarLugar.setString(1, personaje.getString("estado"));
                    pstInsertarLugar.setString(2, "ESTADO");
                    pstInsertarLugar.setString(3, "TIERRA");
                    pstInsertarLugar.setInt(4, paisRST.getInt(1));
                    
                    if (pstInsertarLugar.executeUpdate() > 0){

                        ResultSet estadoRST = pstInsertarLugar.getGeneratedKeys();
                        if (estadoRST.next()){
                            //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                            // INSERTAR ESTADO
                            pstInsertarLugar.setString(1, personaje.getString("ciudad"));
                            pstInsertarLugar.setString(2, "CIUDAD");
                            pstInsertarLugar.setString(3, "TIERRA");
                            pstInsertarLugar.setInt(4, estadoRST.getInt(1));

                            if (pstInsertarLugar.executeUpdate() > 0){

                                ResultSet ciudadRST = pstInsertarLugar.getGeneratedKeys();
                                if (ciudadRST.next()){
                                    //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                                    // INSERTAR CIUDAD
                                    lugarNac = ciudadRST.getInt(1);

                                }
        
                            }else{

                                System.out.println("ERROR INSDRT CIUDAD");

                            }
                            
                        }
                        
                    }else{

                        System.out.println("ERROR INSDRT ESTADO");
                        
                    }
                }
                
            }else{
                
                System.out.println("ERROR INSDRT PAIS");
                
            }
            
            if (lugarNac > 0){
                
                
                pstInsertar.setString(1, personaje.getString("nombre"));
                pstInsertar.setString(2, personaje.getString("nombreR"));
                pstInsertar.setString(3, personaje.getString("apellidoR"));
                pstInsertar.setString(4, personaje.getString("identidad"));
                pstInsertar.setString(5, personaje.getString("biografia"));
                pstInsertar.setString(6, personaje.getString("estadoCivil"));
                pstInsertar.setString(7, personaje.getString("sexo"));
                pstInsertar.setDouble(8, Double.parseDouble(personaje.getString("altura")));
                pstInsertar.setDouble(9, Double.parseDouble(personaje.getString("peso")));
                pstInsertar.setString(10, personaje.getString("colorOjos"));
                pstInsertar.setString(11, personaje.getString("colorCabello"));
                pstInsertar.setInt (12, lugarNac);
                pstInsertar.setString(13, personaje.getString("tipo"));


                if (pstInsertar.executeUpdate() > 0){

                    ResultSet personajeRST = pstInsertar.getGeneratedKeys();
                    if (personajeRST.next()){
                        //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                        // INSERTAR PERSONAJE
                        personajeID = personajeRST.getInt(1);
                        System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB con el numero: "+personajeID+"\n");
                    }

                }else{

                    System.out.println("PERSONAJE: "+personaje.getString("nombre")+" no ha sido agregado a la DB\n");

                }
            
            }else{
                System.out.println("ERROR, lugar de nacimiento no insertado\n");
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    
}

} 