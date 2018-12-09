/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package Controlador;

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
 * @author cverd
 */
public class DBController {
    
    public static JSONArray getPersonajes(){
        
        JSONArray listaPersonajes = new JSONArray();
        
        try(
            Connection conn = DBClass.getConn();
//            PreparedStatement pstGetPersonajes = conn.prepareStatement(
//                    "SELECT p.id_personaje, p.nombreoriginal_personaje, g.nombre_grupoafiliacion "
//                    + "FROM grupo_afiliacion g, hist_per_ga h, personaje p "
//                    + "WHERE g.id_grupoafiliacion = h.grupoafiliacion_fk AND h.estatus_hpg = 'Activo' AND p.id_personaje = h.personaje_fk;")
            PreparedStatement pstGetPersonajes = conn.prepareStatement(
                    "SELECT p.id_personaje, p.nombreoriginal_personaje, g.nombre_grupoafiliacion "
                    + "FROM grupo_afiliacion g, hist_per_ga h, personaje p "
                    + "WHERE g.id_grupoafiliacion = h.grupoafiliacion_fk AND p.id_personaje = h.personaje_fk;")
        ){
            
            ResultSet rsGetPersonajes = pstGetPersonajes.executeQuery();
            
            while (rsGetPersonajes.next()){
                JSONObject personaje = new JSONObject();
                
                personaje.put("id", rsGetPersonajes.getInt(1));
                personaje.put("name", rsGetPersonajes.getString(2));
                personaje.put("ga", rsGetPersonajes.getString(3));
                

                listaPersonajes.put(personaje);
            }
            
            return listaPersonajes;
        
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaPersonajes;
    }
    
}
