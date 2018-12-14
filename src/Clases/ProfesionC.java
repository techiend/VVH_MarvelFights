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
public class ProfesionC {
    
    public static JSONArray getProfesiones(int personajeID){
        JSONArray listaProfesion = new JSONArray();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetProfesion = conn.prepareStatement("select p.* from acc_profesion p, acc_pj_pr pp WHERE pp.personajepjpr_fk = ? AND p.id_profesion = pp.profesionpjpr_fk ")
        ){
            pstGetProfesion.setInt(1, personajeID);
            
            ResultSet rsGetProfesion = pstGetProfesion.executeQuery();
            
            while (rsGetProfesion.next()){
                JSONObject profesion = new JSONObject();
                
                profesion.put("id", rsGetProfesion.getInt(1));
                profesion.put("name", rsGetProfesion.getString(2));
                profesion.put("desc", (rsGetProfesion.getString(3) == null) ? "":rsGetProfesion.getString(3));

                listaProfesion.put(profesion);
            }
            
            return listaProfesion;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaProfesion;
        
    }
    
    public static JSONObject getProfesion(int profesionID){
        
        JSONObject profesion = new JSONObject();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetProfesion = conn.prepareStatement("select * from acc_profesion WHERE id_profesion = ? ")
        ){
            pstGetProfesion.setInt(1, profesionID);
            
            ResultSet rsGetProfesion = pstGetProfesion.executeQuery();
            
            if (rsGetProfesion.next()){
                
                profesion.put("id", rsGetProfesion.getInt(1));
                profesion.put("name", rsGetProfesion.getString(2));
                profesion.put("desc", (rsGetProfesion.getString(3) == null) ? "":rsGetProfesion.getString(3));

                return profesion;
            }
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return profesion;
        
    }
    
    public static void addProfesion(JSONObject profesion){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstAddProfesion = conn.prepareStatement("INSERT INTO acc_profesion VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstAddPJPR = conn.prepareStatement("INSERT INTO acc_pj_pr VALUES (?,?)")
        ){
            pstAddProfesion.setInt(1, DBClass.getLastValue("acc_profesion","id_profesion"));
            pstAddProfesion.setString(2, profesion.getString("name"));
            
            if (profesion.getString("desc").equals(""))
                pstAddProfesion.setNull(3, 0);
            else
                pstAddProfesion.setString(3, profesion.getString("desc"));
            
            if (pstAddProfesion.executeUpdate() > 0){
               ResultSet rsAddProfesion = pstAddProfesion.getGeneratedKeys();
               
               if (rsAddProfesion.next()){
                   pstAddPJPR.setInt(1, rsAddProfesion.getInt(1));
                   pstAddPJPR.setInt(2, profesion.getInt("id_p"));
                   
                   pstAddPJPR.executeUpdate();
               }
            }
            else{
                System.out.println("No inserto la profesion");
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void delProfesion(int profesionID){
        
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstDelProfesion = conn.prepareStatement("DELETE FROM acc_profesion WHERE id_profesion = ? ");
            PreparedStatement pstDelPJPR = conn.prepareStatement("DELETE FROM acc_pj_pr WHERE profesionpjpr_fk = ? ")
        ){
            pstDelPJPR.setInt(1, profesionID);
            
            if (pstDelPJPR.executeUpdate() > 0){
            
                pstDelProfesion.setInt(1, profesionID);
            
                if (pstDelProfesion.executeUpdate() > 0){
                    System.out.println("Elimino la profesion");
                }
                else{
                    System.out.println("No elimino la profesion");
                }
                
            }
            else{
                System.out.println("No elimino la NAN Profesion Personaje");
            }
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    public static void modProfesion(JSONObject profesion, int profesionID){
        
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstModProfesion = conn.prepareStatement("UPDATE acc_profesion SET nombre_profesion = ?, descripcion_profesion = ? WHERE id_profesion = ? ");
        ){
            
            pstModProfesion.setString(1, profesion.getString("name"));
            
            if (profesion.getString("desc").equals(""))
                pstModProfesion.setNull(2, 0);
            else
                pstModProfesion.setString(2, profesion.getString("desc"));
            
            pstModProfesion.setInt(3, profesionID);
            
            if (pstModProfesion.executeUpdate() > 0){
                System.out.println("Inserto la profesion");
                
            }
            else{
                System.out.println("No inserto la profesion");
            }
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
