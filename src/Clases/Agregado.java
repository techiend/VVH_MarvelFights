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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author aless
 */
public class Agregado {
    
    public static void AddPersonaje(JSONObject personaje){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("INSERT INTO personaje VALUES (?,?,?,?,?,?,?,?,?,?,?,?);")
        ){
            pstInsertar.setString(1, personaje.getString("tipo"));
            pstInsertar.setString(2, personaje.getString("nombre"));
            pstInsertar.setString(3, personaje.getString("nombreR"));
            pstInsertar.setString(4, personaje.getString("apellidoR"));
            pstInsertar.setString(5, personaje.getString("identidad"));
            pstInsertar.setString(6, personaje.getString("biografia"));
            pstInsertar.setString(7, personaje.getString("estadoCivil"));
            pstInsertar.setString(8, personaje.getString("sexo"));
            pstInsertar.setString(9, personaje.getString("altura"));
            pstInsertar.setString(9, personaje.getString("peso"));
            pstInsertar.setString(9, personaje.getString("colorOjos"));
            pstInsertar.setString(9, personaje.getString("colorCabello"));
            
            
            if (pstInsertar.executeUpdate() > 0){
                
                System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");
                
            }else{
                
                System.out.println("PERSONAJE: "+personaje.getString("nombre")+" no ha sido agregado a la DB\n");
                
            }
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
