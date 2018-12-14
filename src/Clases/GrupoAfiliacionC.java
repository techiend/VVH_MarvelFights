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
 * @author cverd
 */
public class GrupoAfiliacionC {
    
    public static JSONArray getGruposAfiliacion(){
        JSONArray listaGrupoAfiliacion = new JSONArray();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetGA = conn.prepareStatement(""
                    + "SELECT ga.id_grupoafiliacion, ga.nombre_grupoafiliacion, ga.descripcion_grupoafiliacion, coalesce(ga.indicadordepoderaumentado_grupoafiliacion, 0) ipa, "
                    + "base.nombre_BOperaciones, lugar_BOps "
                    + "FROM acc_grupo_afiliacion ga, acc_base_operaciones base, acc_ga_bo gabo "
                    + "WHERE ga.id_grupoafiliacion = gabo.grupoAfiliacion AND base.id_BOperaciones = gabo.baseOperaciones")
        ){
            
            ResultSet rsGetGA = pstGetGA.executeQuery();
            
            while (rsGetGA.next()){
                JSONObject GrupoAfiliacion = new JSONObject();
                
                GrupoAfiliacion.put("id", rsGetGA.getInt(1));
                GrupoAfiliacion.put("name", rsGetGA.getString(2));
                GrupoAfiliacion.put("desc", (rsGetGA.getString(3) == null) ? "":rsGetGA.getString(3));
                GrupoAfiliacion.put("ipa", rsGetGA.getInt(4));
                GrupoAfiliacion.put("base", rsGetGA.getString(5));
                GrupoAfiliacion.put("dir", getDireccion(rsGetGA.getInt(6)));

                listaGrupoAfiliacion.put(GrupoAfiliacion);
            }
            
            return listaGrupoAfiliacion;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaGrupoAfiliacion;
        
    }
    
    public static void joinGrupoAfiliacion(int idGrupo, int idPersonaje, boolean isNoCombate){
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstSelectHPG = conn.prepareStatement("SELECT id_hpg FROM acc_hist_per_ga WHERE personaje_fk = ? AND estatus_hpg = 'Activo'");
            PreparedStatement pstUpdateHPG = conn.prepareStatement("UPDATE acc_hist_per_ga SET estatus_hpg = 'No activo' WHERE personaje_fk = ?");
            PreparedStatement pstCreateHPG = conn.prepareStatement("INSERT INTO acc_hist_per_ga "
                    + "VALUES (?, ?, ?, ?, ?)")
        ){
            
            pstSelectHPG.setInt(1, idPersonaje);
            
            ResultSet selectHPG = pstSelectHPG.executeQuery();
            
            if (selectHPG.next()){
                pstUpdateHPG.setInt(1, idPersonaje);
                
                pstUpdateHPG.executeUpdate();
            }
            
            pstCreateHPG.setInt(1, DBClass.getLastValue("acc_hist_per_ga", "id_hpg"));
            pstCreateHPG.setString(2, "Activo");
            pstCreateHPG.setInt(3, idGrupo);
            
            if (isNoCombate){
                pstCreateHPG.setNull(4, 0);
                pstCreateHPG.setInt(5, idPersonaje);
            }
            else{
                pstCreateHPG.setInt(4, idPersonaje);
                pstCreateHPG.setNull(5, 0);
            }
            
            pstCreateHPG.executeUpdate();
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static String getDireccion(int fkLugar){
    
        String dir = "";
        int otraDir = -1;
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetL = conn.prepareStatement("SELECT nombre_lugar, coalesce(id_lugar_fk, -1) FROM acc_lugar WHERE id_lugar = ?") // este puede que no exista... :D 
        ){
            
            pstGetL.setInt(1, fkLugar);
           
            ResultSet getLugar = pstGetL.executeQuery();
           
            if(getLugar.next()){
                dir += getLugar.getString(1);
               
                otraDir = getLugar.getInt(2);
                if (otraDir > 0){
                    dir += ", ";
                   
                    pstGetL.setInt(1, otraDir);

                    ResultSet getLugar2 = pstGetL.executeQuery();

                    if(getLugar2.next()){
                        dir += getLugar2.getString(1);

                        otraDir = getLugar2.getInt(2);
                        if (otraDir > 0){
                            dir += ", ";
                            
                            pstGetL.setInt(1, otraDir);

                            ResultSet getLugar3 = pstGetL.executeQuery();

                            if(getLugar3.next()){
                                dir += getLugar3.getString(1);

                                otraDir = getLugar3.getInt(2);
                                if (otraDir > 0){
                                    dir += ", ";
                                    
                                    pstGetL.setInt(1, otraDir);

                                    ResultSet getLugar4 = pstGetL.executeQuery();

                                    if(getLugar4.next()){
                                        dir += getLugar4.getString(1) + ".";
                                    }
                                    
                                }
                                else {
                                    dir += ".";
                                }
                            }
                            
                        }
                        else {
                            dir += ".";
                        }
                    }
                }
                else {
                    dir += ".";
                }               
           }
            
            
            return dir;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return dir;
    }
}
