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
                GrupoAfiliacion.put("ipa", rsGetGA.getDouble(4));
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
    
    public static JSONObject getGrupoAfiliacion(int grupo){
        
        JSONObject grupoInfo = new JSONObject();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetGA = conn.prepareStatement("SELECT ga.id_grupoafiliacion, ga.nombre_grupoafiliacion, ga.descripcion_grupoafiliacion, coalesce(ga.indicadordepoderaumentado_grupoafiliacion, 0) ipa, " +
                    "base.id_boperaciones, base.nombre_BOperaciones, base.descripcion_boperaciones " +
                    "FROM acc_grupo_afiliacion ga, acc_base_operaciones base, acc_ga_bo gabo " +
                    "WHERE ga.id_grupoafiliacion = ? AND ga.id_grupoafiliacion = gabo.grupoAfiliacion AND base.id_BOperaciones = gabo.baseOperaciones")
        ){
            
            pstGetGA.setInt(1, grupo);
            ResultSet rsGetGA = pstGetGA.executeQuery();
            
            if (rsGetGA.next()){
                
                grupoInfo.put("id_grupo", rsGetGA.getInt(1));
                grupoInfo.put("name_grupo", rsGetGA.getString(2));
                grupoInfo.put("desc_grupo", (rsGetGA.getString(3) == null) ? "":rsGetGA.getString(3));
                grupoInfo.put("ipa_grupo", rsGetGA.getInt(4));
                
                grupoInfo.put("id_base", rsGetGA.getInt(5));
                grupoInfo.put("base_base", rsGetGA.getString(6));
                grupoInfo.put("desc_base", rsGetGA.getString(7));

            }
            
            return grupoInfo;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return grupoInfo;
    }
    
    public static int createGrupoAfiliacion(JSONObject grupo){
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstCreateGrupo = conn.prepareStatement("INSERT INTO acc_grupo_afiliacion VALUES "
                    + "(?,?,?,0)", Statement.RETURN_GENERATED_KEYS)
        ){
            
            pstCreateGrupo.setInt(1,DBClass.getLastValue("acc_grupo_afiliacion", "id_grupoafiliacion"));
            pstCreateGrupo.setString(2, grupo.getString("name"));
            
            if (grupo.getString("desc").equals("")){
                pstCreateGrupo.setNull(3, 0);
            }
            else {
                pstCreateGrupo.setString(3, grupo.getString("desc"));
            } 
            
            pstCreateGrupo.executeUpdate();
            
            ResultSet rstGrupo = pstCreateGrupo.getGeneratedKeys();
            
            if (rstGrupo.next()){
                return rstGrupo.getInt(1);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public static int createBaseOperaciones(JSONObject base, JSONObject dir){
        int idLugar = 0;
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstCreateBase = conn.prepareStatement("INSERT INTO acc_base_operaciones VALUES "
                    + "(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstInsertarLugar = conn.prepareStatement("INSERT INTO acc_lugar(id_lugar, nombre_lugar, tipo, tipo_greografia, id_lugar_fk) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)
        ){
            
            
            // INSERTAR PAIS
            pstInsertarLugar.setInt(1, DBClass.getLastValue("acc_lugar","id_lugar"));
            pstInsertarLugar.setString(2, dir.getString("pais"));
            pstInsertarLugar.setString(3, "PAIS");
            pstInsertarLugar.setString(4, "TIERRA");
            pstInsertarLugar.setNull(5, 0);
            
            if (pstInsertarLugar.executeUpdate() > 0){
                
                ResultSet paisRST = pstInsertarLugar.getGeneratedKeys();
                if (paisRST.next()){
                    //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");
                
                    // INSERTAR ESTADO
                    pstInsertarLugar.setInt(1, DBClass.getLastValue("acc_lugar","id_lugar"));
                    pstInsertarLugar.setString(2, dir.getString("estado"));
                    pstInsertarLugar.setString(3, "ESTADO");
                    pstInsertarLugar.setString(4, "TIERRA");
                    pstInsertarLugar.setInt(5, paisRST.getInt(1));
                    
                    if (pstInsertarLugar.executeUpdate() > 0){

                        ResultSet estadoRST = pstInsertarLugar.getGeneratedKeys();
                        if (estadoRST.next()){
                            //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                            // INSERTAR ESTADO
                            pstInsertarLugar.setInt(1, DBClass.getLastValue("acc_lugar","id_lugar"));
                            pstInsertarLugar.setString(2, dir.getString("ciudad"));
                            pstInsertarLugar.setString(3, "CIUDAD");
                            pstInsertarLugar.setString(4, "TIERRA");
                            pstInsertarLugar.setInt(5, estadoRST.getInt(1));

                            if (pstInsertarLugar.executeUpdate() > 0){

                                ResultSet ciudadRST = pstInsertarLugar.getGeneratedKeys();
                                if (ciudadRST.next()){
                                    //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                                    // INSERTAR CIUDAD
                                    idLugar = ciudadRST.getInt(1);

                                }
                                else{
                                    return -1;
                                }
        
                            }else{

                                System.out.println("ERROR INSDRT CIUDAD");
                                return -1;

                            }
                            
                        }
                        
                    }else{

                        System.out.println("ERROR INSDRT ESTADO");
                        return -1;
                        
                    }
                }
                
            }else{
                
                System.out.println("ERROR INSDRT PAIS");
                    return -1;
                
            }
            
            
            
            pstCreateBase.setInt(1,DBClass.getLastValue("acc_base_operaciones", "id_boperaciones"));
            pstCreateBase.setString(2, base.getString("name"));
            pstCreateBase.setString(3, base.getString("desc"));
            
            
            pstCreateBase.setInt(4, idLugar);
            
            pstCreateBase.executeUpdate();
            
            ResultSet rstBase = pstCreateBase.getGeneratedKeys();
            
            if (rstBase.next()){
                return rstBase.getInt(1);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public static int createGaBo(int grupo, int base){
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstCreateGaBo = conn.prepareStatement("INSERT INTO acc_ga_bo VALUES "
                    + "(?,?)", Statement.RETURN_GENERATED_KEYS)
        ){
            
            pstCreateGaBo.setInt(1, grupo);
            pstCreateGaBo.setInt(2, base);
            
            
            pstCreateGaBo.executeUpdate();
            
            return 0;
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public static void deleteGrupoBase(int grupo, int base, boolean haveGabo){
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstDelGrupo = conn.prepareStatement("DELETE FROM acc_grupo_afiliacion WHERE id_grupoafiliacion = ?");
            PreparedStatement pstDelBase = conn.prepareStatement("DELETE FROM acc_base_operaciones WHERE id_boperaciones = ?");
            PreparedStatement pstDelGaBo = conn.prepareStatement("DELETE FROM acc_ga_bo WHERE grupoAfiliacion = ? AND baseOperaciones = ?")
        ){
            
            pstDelGrupo.setInt(1, grupo);
            
            pstDelBase.setInt(1, base);
            
            if (haveGabo){
                pstDelGaBo.setInt(1, grupo);
                pstDelGaBo.setInt(2, base);
                
                pstDelGaBo.executeUpdate();
            }
            
            pstDelGrupo.executeUpdate();
            pstDelBase.executeUpdate();
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static int deleteGBAll(int grupo){
        int base = 0;
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstDelGrupo = conn.prepareStatement("DELETE FROM acc_grupo_afiliacion WHERE id_grupoafiliacion = ?");
            PreparedStatement pstDelBase = conn.prepareStatement("DELETE FROM acc_base_operaciones WHERE id_boperaciones = ?");
            PreparedStatement pstDelGaBo = conn.prepareStatement("DELETE FROM acc_ga_bo WHERE grupoAfiliacion = ? AND baseOperaciones = ?");
            PreparedStatement pstGetBase = conn.prepareStatement("SELECT baseOperaciones FROM acc_ga_bo WHERE grupoAfiliacion = ?")
        ){
            pstGetBase.setInt(1, grupo);
            
            ResultSet rsGetBase = pstGetBase.executeQuery();
            
            while (rsGetBase.next()){
                
                base = rsGetBase.getInt(1);
                
                pstDelGaBo.setInt(1, grupo);
                pstDelGaBo.setInt(2, base);
                
                if (pstDelGaBo.executeUpdate() > 0){
                    
                    pstDelGrupo.setInt(1, grupo);
                    pstDelBase.setInt(1, base);
                    
                    pstDelGrupo.executeUpdate();
                    pstDelBase.executeUpdate();
                    
                    return 0;
                }
                else{
                    return -1;
                }
                
            }
            
            return -1;
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public static int joinGrupoAfiliacion(int idGrupo, int idPersonaje, boolean isNoCombate){
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstSelectHPG = conn.prepareStatement("SELECT * FROM acc_hist_per_ga WHERE personaje_fk = ?");
            PreparedStatement pstUpdateHPG = conn.prepareStatement("UPDATE acc_hist_per_ga SET estatus_hpg = 'No activo' WHERE personaje_fk = ?");
            PreparedStatement pstCreateHPG = conn.prepareStatement("INSERT INTO acc_hist_per_ga "
                    + "VALUES (?, ?, ?, ?, ?)");
            PreparedStatement pstActivarHPG = conn.prepareStatement("UPDATE acc_hist_per_ga SET estatus_hpg = 'Activo' WHERE id_hpg = ?")
        ){
            
            pstSelectHPG.setInt(1, idPersonaje);
            
            ResultSet selectHPG = pstSelectHPG.executeQuery();
            
            while (selectHPG.next()){
                
                if (idGrupo == selectHPG.getInt(3)){
                    if (selectHPG.getString(2).equals("Activo")){
                        return 1; // Ya esta activo
                    }
                    else{
                
                        pstUpdateHPG.setInt(1, idPersonaje);

                        pstUpdateHPG.executeUpdate(); // Desactivar los demas
                        
                        
                        pstActivarHPG.setInt(1, selectHPG.getInt(1));
                        
                        pstActivarHPG.executeUpdate();
                        
                        return 0; // Se activo el inactivo
                    }
                }
                
            }
            
            pstUpdateHPG.setInt(1, idPersonaje);

            pstUpdateHPG.executeUpdate(); // Desactivar los demas
                        
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
        
            return 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
        
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
    
    public static int updateGBAll(JSONObject info){
    
        int idLugar = 0;
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstUpdGrupo = conn.prepareStatement("UPDATE acc_grupo_afiliacion SET nombre_grupoafiliacion = ?, descripcion_grupoafiliacion = ? WHERE id_grupoafiliacion = ?");
            PreparedStatement pstUpdBase = conn.prepareStatement("UPDATE acc_base_operaciones SET nombre_boperaciones = ?, descripcion_boperaciones = ? WHERE id_boperaciones = ?");
            PreparedStatement pstUpdBaseL = conn.prepareStatement("UPDATE acc_base_operaciones SET nombre_boperaciones = ?, descripcion_boperaciones = ?, lugar_bops = ? WHERE id_boperaciones = ?");
            PreparedStatement pstInsertarLugar = conn.prepareStatement("INSERT INTO acc_lugar(id_lugar, nombre_lugar, tipo, tipo_greografia, id_lugar_fk) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)
        ){
            
            if(info.getBoolean("isMoving")){
                
                // INSERTAR PAIS
                pstInsertarLugar.setInt(1, DBClass.getLastValue("acc_lugar","id_lugar"));
                pstInsertarLugar.setString(2, info.getString("pais"));
                pstInsertarLugar.setString(3, "PAIS");
                pstInsertarLugar.setString(4, "TIERRA");
                pstInsertarLugar.setNull(5, 0);

                if (pstInsertarLugar.executeUpdate() > 0){

                    ResultSet paisRST = pstInsertarLugar.getGeneratedKeys();
                    if (paisRST.next()){
                        //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                        // INSERTAR ESTADO
                        pstInsertarLugar.setInt(1, DBClass.getLastValue("acc_lugar","id_lugar"));
                        pstInsertarLugar.setString(2, info.getString("estado"));
                        pstInsertarLugar.setString(3, "ESTADO");
                        pstInsertarLugar.setString(4, "TIERRA");
                        pstInsertarLugar.setInt(5, paisRST.getInt(1));

                        if (pstInsertarLugar.executeUpdate() > 0){

                            ResultSet estadoRST = pstInsertarLugar.getGeneratedKeys();
                            if (estadoRST.next()){
                                //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                                // INSERTAR ESTADO
                                pstInsertarLugar.setInt(1, DBClass.getLastValue("acc_lugar","id_lugar"));
                                pstInsertarLugar.setString(2, info.getString("ciudad"));
                                pstInsertarLugar.setString(3, "CIUDAD");
                                pstInsertarLugar.setString(4, "TIERRA");
                                pstInsertarLugar.setInt(5, estadoRST.getInt(1));

                                if (pstInsertarLugar.executeUpdate() > 0){

                                    ResultSet ciudadRST = pstInsertarLugar.getGeneratedKeys();
                                    if (ciudadRST.next()){
                                        //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                                        // INSERTAR CIUDAD
                                        idLugar = ciudadRST.getInt(1);
                                        
                                        pstUpdBaseL.setString(1, info.getString("name_base"));
                                        pstUpdBaseL.setString(2, info.getString("desc_base"));
                                        pstUpdBaseL.setInt(3, idLugar);
                                        pstUpdBaseL.setInt(4, Integer.parseInt(info.getString("id_base")));

                                        if (pstUpdBaseL.executeUpdate() > 0){
                                            
                                        }
                                        else {
                                            return -3;
                                        }

                                    }
                                    else{
                                        return -1;
                                    }

                                }else{

                                    System.out.println("ERROR INSDRT CIUDAD");
                                    return -1;

                                }

                            }

                        }else{

                            System.out.println("ERROR INSDRT ESTADO");
                            return -1;

                        }
                    }

                }else{

                    System.out.println("ERROR INSDRT PAIS");
                    return -1;

                }
                
                
            }
            else{
                pstUpdBase.setString(1, info.getString("name_base"));
                pstUpdBase.setString(2, info.getString("desc_base"));
                pstUpdBase.setInt(3, Integer.parseInt(info.getString("id_base")));
                
                if (pstUpdBase.executeUpdate() > 0){
                    
                }
                else {
                    return -3;
                }
            }
            
            pstUpdGrupo.setString(1, info.getString("name_grupo"));
            pstUpdGrupo.setString(2, info.getString("desc_grupo"));
            pstUpdGrupo.setInt(3, Integer.parseInt(info.getString("id_grupo")));
            
            if (pstUpdGrupo.executeUpdate() > 0){
                return 0;
            }
            else {
                return -2;
            }
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return 1;
    }
}
