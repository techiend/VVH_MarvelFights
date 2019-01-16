/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package Clases;

import Controlador.DBController;
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
public class ReporteEvento {
    
    public static JSONObject getInfo(int EventoID){
    
        int grupoID = 0, hpg = 0;
        JSONObject info = new JSONObject();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstEvento = conn.prepareStatement("SELECT nombre_Evento, fechaInicio_Evento, fechaFin_Evento, descripcion_Evento FROM acc_evento WHERE id_evento = ?");
            PreparedStatement pstNamePe = conn.prepareStatement("SELECT id_personaje, nombreoriginal_personaje FROM acc_personaje WHERE id_personaje = (SELECT personaje_fk FROM acc_hist_per_ga WHERE id_hpg = ?)");
            PreparedStatement pstHabsPe = conn.prepareStatement("SELECT nombre_habilidad, valor_habilidad FROM acc_habilidad WHERE personaje_fk = ?");
            PreparedStatement pstInscri = conn.prepareStatement("SELECT id_inscrito, numerogrupo_inscrito, puntajeetapa1_inscrito, campeon_inscrito, hpg_fk, grupoafiliacion FROM acc_inscrito WHERE evento_fk = ?");
            PreparedStatement pstEtapas = conn.prepareStatement("SELECT ganador_contienda, fecha_contienda, duracionmins_contienda, inscrito1, inscrito2 FROM acc_contienda WHERE inscrito1 in (SELECT id_inscrito FROM acc_inscrito WHERE evento_fk = ?) AND etapa_contienda = ?");
            PreparedStatement pstTeamAf = conn.prepareStatement("SELECT nombre_grupoafiliacion, indicadorDePoderAumentado_grupoAfiliacion FROM acc_grupo_afiliacion WHERE id_grupoAfiliacion = ?")
        ){
            
            pstEvento.setInt(1, EventoID);
            
            ResultSet rsEvento = pstEvento.executeQuery();
            
            if (rsEvento.next()){
            
                info.put("name", rsEvento.getString(1));
                info.put("fechaI", rsEvento.getString(2));
                info.put("fechaF", rsEvento.getString(3));
                info.put("desc", rsEvento.getString(4));
                
                pstInscri.setInt(1, EventoID);
                
                ResultSet rsInscritos = pstInscri.executeQuery();
                
                JSONArray listaInscritos = new JSONArray();
                while(rsInscritos.next())
                {
                    JSONObject inscrito = new JSONObject();
                    
                    inscrito.put("id", rsInscritos.getInt(1));
                    inscrito.put("numGrupo", rsInscritos.getInt(2));
                    inscrito.put("puntosEtapa1", rsInscritos.getInt(3));
                    inscrito.put("win", rsInscritos.getBoolean(4));
                    inscrito.put("hpg", rsInscritos.getInt(5));
                    inscrito.put("grupo", rsInscritos.getInt(6));
                    
                    pstNamePe.setInt(1, inscrito.getInt("hpg"));
                    
                    ResultSet namePe = pstNamePe.executeQuery();
                    
                    if (namePe.next()){
                        
                        pstHabsPe.setInt(1, namePe.getInt(1));
                        
                        ResultSet rsHabs = pstHabsPe.executeQuery();
                        
                        JSONArray habilidades = new JSONArray();
                        while (rsHabs.next()){
                            JSONObject habilidad = new JSONObject();
                            
                            habilidad.put("name", rsHabs.getString(1));
                            habilidad.put("value", rsHabs.getInt(2));
                            
                            habilidades.put(habilidad);
                        }
                        info.put("habs", habilidades);
                        
                        inscrito.put("name", namePe.getString(2));
                    }

                        
                    pstTeamAf.setInt(1, inscrito.getInt("grupo"));

                    ResultSet teamAf = pstTeamAf.executeQuery();

                    if (teamAf.next()){

                        
                        inscrito.put("nameGrupo", teamAf.getString(1));
                        if (inscrito.getBoolean("win")){
                            info.put("winner", inscrito.getString("name"));
                            info.put("nameGrupo", teamAf.getString(1));
                            info.put("indicador", teamAf.getDouble(2)); 
                        }   
                    }
                       
                    listaInscritos.put(inscrito);
                }
                
                info.put("inscritos", listaInscritos);
                
                pstEtapas.setInt(1, EventoID);
                pstEtapas.setInt(2, 1);
                
                ResultSet rsEtapa1 = pstEtapas.executeQuery();
                
                JSONArray listaEtapa1 = new JSONArray();
                while(rsEtapa1.next()){
                    JSONObject contienda = new JSONObject();
                    contienda.put("ganador", rsEtapa1.getInt(1));
                    contienda.put("fecha", rsEtapa1.getString(2));
                    contienda.put("duracion", (rsEtapa1.getInt(3) == 0) ? "<1 min" : rsEtapa1.getInt(3)+" mins");
                    contienda.put("peleador1", getName(rsEtapa1.getInt(4), listaInscritos));
                    contienda.put("peleador2", getName(rsEtapa1.getInt(5), listaInscritos));
                    
                    listaEtapa1.put(contienda);
                }
                
                info.put("etapa1", listaEtapa1);
                
                pstEtapas.setInt(1, EventoID);
                pstEtapas.setInt(2, 2);
                
                ResultSet rsEtapa2 = pstEtapas.executeQuery();
                
                JSONArray listaEtapa2 = new JSONArray();
                while(rsEtapa2.next()){
                    JSONObject contienda = new JSONObject();
                    contienda.put("ganador", rsEtapa2.getInt(1));
                    contienda.put("fecha", rsEtapa2.getString(2));
                    contienda.put("duracion", (rsEtapa2.getInt(3) == 0) ? "<1 min" : rsEtapa2.getInt(3)+" mins");
                    contienda.put("peleador1", getName(rsEtapa2.getInt(4), listaInscritos));
                    contienda.put("peleador2", getName(rsEtapa2.getInt(5), listaInscritos));
                    
                    listaEtapa2.put(contienda);
                }
                
                info.put("etapa2", listaEtapa2);
                
                pstEtapas.setInt(1, EventoID);
                pstEtapas.setInt(2, 3);
                
                ResultSet rsEtapa3 = pstEtapas.executeQuery();
                
                JSONArray listaEtapa3 = new JSONArray();
                while(rsEtapa3.next()){
                    JSONObject contienda = new JSONObject();
                    contienda.put("ganador", rsEtapa3.getInt(1));
                    contienda.put("fecha", rsEtapa3.getString(2));
                    contienda.put("duracion", (rsEtapa3.getInt(3) == 0) ? "<1 min" : rsEtapa3.getInt(3)+" mins");
                    contienda.put("peleador1", getName(rsEtapa3.getInt(4), listaInscritos));
                    contienda.put("peleador2", getName(rsEtapa3.getInt(5), listaInscritos));
                    
                    listaEtapa3.put(contienda);
                }
                
                info.put("etapa3", listaEtapa3);
                
                
            }
            
            
            return info;
        
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return info;
        
    }
    
    public static String getName(int inscritoID, JSONArray lista){
    
        for (int i = 0; i<lista.length(); i++){
            JSONObject personaje = lista.getJSONObject(i);
            
            if (personaje.getInt("id") == inscritoID){
                return personaje.getString("name");
            }
        }
        
        return null;
        
    }
    
    public static void main(String[] args) {
        
        JSONObject info = getInfo(1);
        
        System.out.println("INFO: " + info.toString(1));
    }
}
