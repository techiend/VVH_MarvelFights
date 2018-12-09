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
            PreparedStatement pstGetPersonajes = conn.prepareStatement(
                    "SELECT p.id_personaje, p.nombreoriginal_personaje, g.nombre_grupoafiliacion "
                    + "FROM grupo_afiliacion g, hist_per_ga h, personaje p "
                    + "WHERE g.id_grupoafiliacion = h.grupoafiliacion_fk AND h.estatus_hpg = 'Activo' AND p.id_personaje = h.personaje_fk;")
//            PreparedStatement pstGetPersonajes = conn.prepareStatement(
//                    "SELECT p.id_personaje, p.nombreoriginal_personaje, g.nombre_grupoafiliacion "
//                    + "FROM grupo_afiliacion g, hist_per_ga h, personaje p "
//                    + "WHERE g.id_grupoafiliacion = h.grupoafiliacion_fk AND p.id_personaje = h.personaje_fk;")
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
    
    public static boolean estaRelacionado(JSONArray inscritos, int personajeID){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetPersonajes = conn.prepareStatement("SELECT * FROM relacion_personaje WHERE personaje_fk = ? AND personaje_relacion_fk = ?")
        ){
            
            for (int o = 0; o<inscritos.length();o++){
            
                pstGetPersonajes.setInt(1, personajeID);
                pstGetPersonajes.setInt(2, inscritos.getJSONObject(o).getInt("id"));
                
                ResultSet rsGetPersonajes = pstGetPersonajes.executeQuery();

                if (rsGetPersonajes.next()){
                    return true;
                }
            
            }
            
            return false;
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public static boolean difPowerIndicator(JSONArray inscritos, int personajeID){
        
        for (int o = 0; o<inscritos.length();o++){
            
            double diferencia = getPowerIndicator(inscritos.getJSONObject(o).getInt("id")) - getPowerIndicator(personajeID);
            
            if (diferencia < 0)
                diferencia = diferencia * (-1);
            
            if (diferencia > 1.50){
                return true;
            }

        }
        
        return false;
        
    }
    
    public static double getPowerIndicator(int personajeID){
        
        double indicador = 0.00;
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetHabilidades = conn.prepareStatement("SELECT nombre_habilidad, valor_habilidad FROM habilidad WHERE personaje_fk = ?")
        ){
            
            pstGetHabilidades.setInt(1, personajeID);
            ResultSet rsGetHabilidades = pstGetHabilidades.executeQuery();
            
            while(rsGetHabilidades.next()){
                String habilidad = rsGetHabilidades.getString(1);
                int pto = rsGetHabilidades.getInt(2);
                
                switch(habilidad){
                    case "Resistencia": 
                        indicador += pto * 0.20;
                        break;
                    case "Proyeccion de energia": 
                        indicador += pto * 0.10;
                        break;
                    case "Habilidades de combate": 
                        indicador += pto * 0.20;
                        break;
                    case "Inteligencia": 
                        indicador += pto * 0.15;
                        break;
                    case "Velocidad": 
                        indicador += pto * 0.10;
                        break;
                    case "Fuerza": 
                        indicador += pto * 0.25;
                        break;
                }
            }
            
            System.out.println("Indicador de poder: "+ indicador);
            return indicador;
        
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return indicador;
    }
    
}
