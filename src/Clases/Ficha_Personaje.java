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
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.json.JSONObject;


/**
 *
 * @author aless
 */
public class Ficha_Personaje {
    

    public static ArrayList<String> getPersonajeFicha(){
        
        ArrayList<String> listaPersonajes = new ArrayList();
        
        try{
            Connection conn = DBClass.getConn();
            PreparedStatement pstMostrar;
            pstMostrar = conn.prepareStatement("Select id_personaje, nombreoriginal_personaje from acc_personaje");
            
            ResultSet rs = pstMostrar.executeQuery();
            
            while (rs.next()){
                listaPersonajes.add(Integer.toString(rs.getInt(1))+"."+rs.getString(2));
            }
            
            return listaPersonajes;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        return listaPersonajes;
    }
    
    public static JSONObject getInfoPersonaje(int personajeID){
    
        JSONObject info = new JSONObject();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstMostrar  = conn.prepareStatement("SELECT * FROM acc_personaje WHERE id_personaje = ?");
           // PreparedStatement pstAlias  = conn.prepareStatement("select * from acc_alias where personaje_fk = ?");    
                
                
        ){
            pstMostrar.setInt(1, personajeID);
            
            ResultSet rs = pstMostrar.executeQuery();
           // ResultSet rsA = pstAlias.executeQuery();
            
            if (rs.next()){
                info.put("bio", rs.getString("biografia_personaje"));
                info.put("nombreo", rs.getString("nombreoriginal_personaje"));
                info.put("nombrer", rs.getString("nombrereal_personaje"));
                info.put("apellidor", rs.getString("apellidoreal_personaje"));
                info.put("identidad", rs.getString("identidad_personaje"));
                //info.put("alias",rsA.getString("nombre_alias"));
                info.put("estadocivil", rs.getString("estadocivil_personaje"));
                info.put("peso", rs.getString("peso_personaje"));
                info.put("altura", rs.getString("altura_personaje"));
                info.put("ojo", rs.getString("colorojos_personaje"));
                info.put("pelo", rs.getString("colorpelo_personaje"));
            }
            
            return info;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        return info;
        
    }
    
}
