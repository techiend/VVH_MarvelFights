/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package Controlador;

import Clases.EventoC;
import DBHelper.DBClass;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class DBController {
    
    public static JSONArray getPersonajes(){
        
        JSONArray listaPersonajes = new JSONArray();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetPersonajes = conn.prepareStatement(
                    "SELECT p.id_personaje, p.nombreoriginal_personaje, g.nombre_grupoafiliacion "
                    + "FROM acc_grupo_afiliacion g, acc_hist_per_ga h, acc_personaje p "
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
    
    public static boolean estaRelacionado(JSONArray inscritos, int personajeID, int numGroup){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetPersonajes = conn.prepareStatement("SELECT * FROM acc_relacion_personaje WHERE personaje_fk = ? AND personaje_relacion_fk = ?")
        ){
            
            for (int o = 0; o<inscritos.length();o++){
                
                if (inscritos.getJSONObject(o).getInt("gc") == numGroup){
                    pstGetPersonajes.setInt(1, personajeID);
                    pstGetPersonajes.setInt(2, inscritos.getJSONObject(o).getInt("id"));

                    ResultSet rsGetPersonajes = pstGetPersonajes.executeQuery();

                    if (rsGetPersonajes.next()){
                        return true;
                    }
                }
            }
            
            return false;
            
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }
    
    public static boolean difPowerIndicator(JSONArray inscritos, int personajeID, int numGroup){
        
        for (int o = 0; o<inscritos.length();o++){
            
            if (inscritos.getJSONObject(o).getInt("gc") == numGroup){
                double diferencia = getPowerIndicator(inscritos.getJSONObject(o).getInt("id")) - getPowerIndicator(personajeID);

                if (diferencia < 0)
                    diferencia = diferencia * (-1);

                if (diferencia > 1.50){
                    return true;
                }   
            }

        }
        
        return false;
        
    }
    
    public static double getPowerIndicator(int personajeID){
        
        double indicador = 0.00;
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetHabilidades = conn.prepareStatement("SELECT nombre_habilidad, valor_habilidad FROM acc_habilidad WHERE personaje_fk = ?")
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
    
    public static int createEvento(EventoC evento){
        int idEvento = 0;
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstCreateEvento = conn.prepareStatement("INSERT INTO acc_evento VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstGetHPGGA = conn.prepareStatement("SELECT id_hpg hpg, grupoAfiliacion_fk ga FROM acc_hist_per_ga WHERE personaje_fk = ? AND estatus_hpg = 'Activo'");
            PreparedStatement pstCreateInscrito = conn.prepareStatement("INSERT INTO acc_inscrito VALUES (?, ?, 0, false, ?, ?, ?)")
        ){
            
            pstCreateEvento.setInt(1, DBClass.getLastValue("acc_evento", "id_evento"));
            pstCreateEvento.setString(2, evento.getNombre());
            
            java.sql.Date fechaI = new java.sql.Date(evento.getFechaInicio().getTime()); 
            java.sql.Date fechaF = new java.sql.Date(evento.getFechaFin().getTime()); 

            pstCreateEvento.setDate(3, fechaI);
            pstCreateEvento.setDate(4, fechaF);
            
            if (evento.getDescripccion().trim().equals(""))
                pstCreateEvento.setNull(5, 0);
            else
                pstCreateEvento.setString(5, evento.getDescripccion());
            
            pstCreateEvento.executeUpdate();
            
            ResultSet rstEvento = pstCreateEvento.getGeneratedKeys();
            
            if (rstEvento.next()){
                idEvento = rstEvento.getInt(1);
                
                JSONArray inscritos = evento.getGrupos();
                
                for (int i = 0; i < inscritos.length(); i++){
                    
                    int idPersonaje = inscritos.getJSONObject(i).getInt("id");
                    int numGroup = inscritos.getJSONObject(i).getInt("gc");
                    
                    pstGetHPGGA.setInt(1, idPersonaje);
                    
                    ResultSet rstHPGGA = pstGetHPGGA.executeQuery();
                    
                    if (rstHPGGA.next()){
                        int idHPG = rstHPGGA.getInt(1);
                        int idGrupo = rstHPGGA.getInt(2);
                        
                        pstCreateInscrito.setInt(1, DBClass.getLastValue("acc_inscrito", "id_inscrito"));
                        pstCreateInscrito.setInt(2, numGroup);
                        pstCreateInscrito.setInt(3, idEvento);
                        pstCreateInscrito.setInt(4, idHPG);
                        pstCreateInscrito.setInt(5, idGrupo);
                        
                        pstCreateInscrito.executeUpdate();
                        
                    }
                    else{
                        return -3; // No posee grupo de afiliacion
                    }
                }
                
                return 0;
                
                
            }
            else {
                return -2; // Error al crear evento
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
}
