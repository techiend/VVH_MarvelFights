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
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

public class CombateC {
    
    public static JSONArray getPeleadores(EventoC evento, int numEtapa){
        JSONArray peleadores = new JSONArray();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetPeleadores = conn.prepareStatement("SELECT id_contienda, p1.nombreoriginal_personaje, p2.nombreoriginal_personaje "
                    + "FROM acc_contienda c, acc_personaje p1, acc_personaje p2 "
                    + "WHERE etapa_contienda = ? AND ganador_contienda is null "
                    + "AND p1.id_personaje IN "
                    + "(SELECT personaje_fk FROM acc_hist_per_ga WHERE id_hpg IN "
                    + "(SELECT hpg_fk FROM acc_inscrito WHERE evento_fk = ? AND id_inscrito = c.inscrito1)) "
                    + "AND p2.id_personaje IN "
                    + "(SELECT personaje_fk FROM acc_hist_per_ga WHERE id_hpg IN "
                    + "(SELECT hpg_fk FROM acc_inscrito WHERE evento_fk = ? AND id_inscrito = c.inscrito2));")
        ){
        
            pstGetPeleadores.setInt(1, numEtapa);
            pstGetPeleadores.setInt(2, evento.getIdEvento());
            pstGetPeleadores.setInt(3, evento.getIdEvento());
            
            ResultSet rstPeleadores = pstGetPeleadores.executeQuery();
            
            while (rstPeleadores.next()){
                JSONObject peleador = new JSONObject();
                
                peleador.put("id", rstPeleadores.getInt(1));
                peleador.put("c1", rstPeleadores.getString(2));
                peleador.put("c2", rstPeleadores.getString(3));
                
                peleadores.put(peleador);
            }
            
            return peleadores;
            
        } catch (SQLException ex) {
            Logger.getLogger(CombateC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return peleadores;
    
    }
    
    public static int diferenciaMinutos(Calendar fechaInicial){
        Calendar fechaFinal = Calendar.getInstance();
        fechaFinal.setTime(new Date()); 
        
        int diferenciaMinutos=0;
        diferenciaMinutos=(fechaFinal.get(Calendar.MINUTE)-fechaInicial.get(Calendar.MINUTE));
        return diferenciaMinutos;
    }
    
    public static void setStatusPeleaEtapa1(Calendar fechaInicial, int idPelea, int ganador){
        //update acc_contienda set duracionmins_contienda = ? where id_contienda = ?;
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstUpdatePelea = conn.prepareStatement("UPDATE acc_contienda SET ganador_contienda = ?, duracionmins_contienda = ? WHERE id_contienda = ?");
            PreparedStatement pstGetPeleadores = conn.prepareStatement("SELECT inscrito1, inscrito2 FROM acc_contienda WHERE id_contienda = ?");
            PreparedStatement pstUpdatePuntos = conn.prepareStatement("UPDATE acc_inscrito "
                    + "SET puntajeetapa1_inscrito = (SELECT puntajeetapa1_inscrito + ?  FROM acc_inscrito WHERE id_inscrito = ?) WHERE id_inscrito = ?")
        ){
        
            int diferenciaMin = diferenciaMinutos(fechaInicial);
            
            System.out.println("Diferencia: "+diferenciaMin);
            
            pstUpdatePelea.setInt(1, ganador);
            pstUpdatePelea.setInt(2, diferenciaMin);
            pstUpdatePelea.setInt(3, idPelea);
            
            pstUpdatePelea.executeUpdate();
            
            pstGetPeleadores.setInt(1, idPelea);
            
            ResultSet rsGetPeleadores = pstGetPeleadores.executeQuery();
            
            if (rsGetPeleadores.next()){
                int peleador1 = rsGetPeleadores.getInt(1);
                int peleador2 = rsGetPeleadores.getInt(2);
                
                switch (ganador){
                    case 1: // GANADOR INSCRITO #1
                        pstUpdatePuntos.setInt(1, 2);
                        pstUpdatePuntos.setInt(2, peleador1);
                        pstUpdatePuntos.setInt(3, peleador1);
                        
                        pstUpdatePuntos.executeUpdate();
                        break;
                    case 2: // GANADOR INSCRITO #2
                        pstUpdatePuntos.setInt(1, 2);
                        pstUpdatePuntos.setInt(2, peleador2);
                        pstUpdatePuntos.setInt(3, peleador2);
                        
                        pstUpdatePuntos.executeUpdate();
                        break;
                    case 3: // EMPATE
                        pstUpdatePuntos.setInt(1, 1);
                        pstUpdatePuntos.setInt(2, peleador1);
                        pstUpdatePuntos.setInt(3, peleador1);
                        
                        pstUpdatePuntos.executeUpdate();
                        
                        pstUpdatePuntos.setInt(1, 1);
                        pstUpdatePuntos.setInt(2, peleador2);
                        pstUpdatePuntos.setInt(3, peleador2);
                        
                        pstUpdatePuntos.executeUpdate();
                        break;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CombateC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void setStatusPeleaEtapa2y3(Calendar fechaInicial, int idPelea, int ganador){
        //update acc_contienda set duracionmins_contienda = ? where id_contienda = ?;
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstUpdatePelea = conn.prepareStatement("UPDATE acc_contienda SET ganador_contienda = ?, duracionmins_contienda = ? WHERE id_contienda = ?");
        ){
        
            int diferenciaMin = diferenciaMinutos(fechaInicial);
            
            System.out.println("Diferencia: "+diferenciaMin);
            
            pstUpdatePelea.setInt(1, ganador);
            pstUpdatePelea.setInt(2, diferenciaMin);
            pstUpdatePelea.setInt(3, idPelea);
            
            pstUpdatePelea.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(CombateC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public static JSONObject setWinner(EventoC evento){
    
        String nameGanador = "";
        JSONObject pjGanador = new JSONObject();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement getWinnersEtapa3 = conn.prepareStatement("select ganador_contienda, inscrito1, inscrito2 from acc_contienda where etapa_contienda = 3 "
                    + "and (inscrito1 in (select id_inscrito from acc_inscrito where evento_fk = ?) OR inscrito2 in (select id_inscrito from acc_inscrito where evento_fk = ?));");
        ){
        
            getWinnersEtapa3.setInt(1, evento.getIdEvento());
            getWinnersEtapa3.setInt(2, evento.getIdEvento());
            
            ResultSet rstWinner3 = getWinnersEtapa3.executeQuery();
            
            JSONArray ganadores = new  JSONArray();
            
            while (rstWinner3.next()){
                JSONObject personaje = new JSONObject();
                
                personaje.put("id", (rstWinner3.getInt(1) == 1) ? rstWinner3.getInt(2) : rstWinner3.getInt(3));
                
                ganadores.put(personaje);
            }
            
            int maxGanadas = 0;
            int ganadas = 0;
            
            
            
            for (int i = 0; i < ganadores.length(); i++){
                JSONObject ganador = ganadores.getJSONObject(i);
                
                for (int j = 0; j < ganadores.length(); j++){
                    JSONObject ganador2 = ganadores.getJSONObject(j);
                    
                    if (ganador.getInt("id") == ganador2.getInt("id")){
                        ganadas++;
                    }

                }
                
                if (ganadas > maxGanadas){
                    maxGanadas = ganadas;
                    pjGanador = ganador;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CombateC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pjGanador;
        
    }
    
    public static void setPoints(int idInscrito){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetInfo = conn.prepareStatement("SELECT personaje_fk, grupoafiliacion_fk FROM acc_hist_per_ga WHERE id_hpg = (SELECT hpg_fk FROM acc_inscrito WHERE id_inscrito = ?)");
            PreparedStatement pstGetHabs = conn.prepareStatement("SELECT nombre_habilidad, valor_habilidad FROM acc_habilidad WHERE personaje_fk = ?");
            PreparedStatement pstUpdHabs = conn.prepareStatement("UPDATE acc_habilidad SET valor_habilidad = (SELECT valor_habilidad FROM acc_habilidad WHERE personaje_fk = ? AND nombre_habilidad = ?) + 1 WHERE personaje_fk = ? AND nombre_habilidad = ?");
            PreparedStatement pstAllPers = conn.prepareStatement("SELECT personaje_fk, count(*) FROM acc_hist_per_ga WHERE estatus_hpg = 'Activo' AND grupoafiliacion_fk = ? GROUP BY personaje_fk");
            PreparedStatement pstUpdGrup = conn.prepareStatement("UPDATE acc_grupo_afiliacion SET indicadordepoderaumentado_grupoafiliacion = ? WHERE id_grupoafiliacion = ?");
            PreparedStatement pstSetWinn = conn.prepareStatement("UPDATE acc_inscrito SET campeon_inscrito = true WHERE id_inscrito = ?")
        ){
                    
            pstGetInfo.setInt(1, idInscrito);
            
            ResultSet rsGetInfo = pstGetInfo.executeQuery();
            
            int count = 0;
            if (rsGetInfo.next()){
                int idPersonaje = rsGetInfo.getInt(1);
                int idGrupo = rsGetInfo.getInt(2);
                
                pstGetHabs.setInt(1, idPersonaje);
                
                ResultSet rsGetHabs = pstGetHabs.executeQuery();
                
                while(rsGetHabs.next()){
                    String nameH = rsGetHabs.getString(1);
                    if(rsGetHabs.getInt(2) == 7){
                        count++;
                    }
                    else{
                        pstUpdHabs.setInt(1, idPersonaje);
                        pstUpdHabs.setString(2, nameH);
                        pstUpdHabs.setInt(3, idPersonaje);
                        pstUpdHabs.setString(4, nameH);
                        
                        pstUpdHabs.executeUpdate();
                    }
                }
                
                if(count == 6){
                    pstAllPers.setInt(1, idGrupo);
                    
                    ResultSet rsAllPers = pstAllPers.executeQuery();
                    
                    int cantPers = 1;
                    double IPA = 0.00;
                    double IPAgrupo = 0.00;
                    while (rsAllPers.next()){
                        cantPers = rsAllPers.getInt(2);
                        IPA += DBController.getPowerIndicator(rsAllPers.getInt(1));
                    }
                    
                    IPAgrupo = (IPA/cantPers) * 1.05;
                    
                    pstUpdGrup.setDouble(1, IPAgrupo);
                    pstUpdGrup.setInt(2, idGrupo);
                    
                    pstUpdGrup.executeUpdate();
                    
                }
                
                pstSetWinn.setInt(1, idInscrito);
                pstSetWinn.executeUpdate();
                
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(CombateC.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
}
