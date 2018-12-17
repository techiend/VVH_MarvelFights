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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
/**
 *
 * @author Carse
 */
public class Categorias {
    
     public static JSONArray getPersonajenc(){
        JSONArray listaPersonaje = new JSONArray();
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetAlumnos = conn.prepareStatement("SELECT id_personajenc \"ID\", "
                    + "nombre_personajenc \"Nombre Original\", nombrereal_personajenc \"Nombre Real]\", "
                    + "apellidoreal_personajenc \"Apellido Real\", genero_personajenc \"Genero\", "
                    + "fallecido_personajenc \"Estado\""
                    + " FROM acc_personaje_no_combate;")
        ){
            
            ResultSet rsGetPersonajenc = pstGetAlumnos.executeQuery();
            
            while (rsGetPersonajenc.next()){
                JSONObject personajenc = new JSONObject();
                
                personajenc.put("id", rsGetPersonajenc.getInt(1));
                personajenc.put("nameo", rsGetPersonajenc.getString(2));
               // personajenc.put("namer", rsGetPersonajenc.getString(3));
                personajenc.put("namer", (rsGetPersonajenc.getString(3) == null) ? "" : rsGetPersonajenc.getString(3));
               // personajenc.put("lname", rsGetPersonajenc.getString(4));
                personajenc.put("lname", (rsGetPersonajenc.getString(4) == null) ? "" : rsGetPersonajenc.getString(4));
                personajenc.put("genero", rsGetPersonajenc.getString(5));
                personajenc.put("estado", rsGetPersonajenc.getString(6));
                
                
//                System.out.println("ALUMNO: "+alumno.toString());

                listaPersonaje.put(personajenc);
            }
            
            return listaPersonaje;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaPersonaje;
    }
    
     
         public static JSONArray getPersonajeNCList(int personajeID){
     JSONArray listaPersonajeNC = new JSONArray();

     try(
         Connection conn = DBClass.getConn();
         PreparedStatement pstGetPersonajeNC = conn.prepareStatement("SELECT b.id_personajenc, b.nombre_personajenc, b.nombrereal_personajenc, b.apellidoreal_personajenc, b.genero_personajenc, b.fallecido_personajenc, a.tiporelacion_repe, a.parentesco_repe, a.descripcion_repe from acc_relacion_personaje a, acc_personaje_no_combate b WHERE a.personaje_fk = ? and a.personaje_no_combate_fk = b.id_personajenc ;");
             ){

         pstGetPersonajeNC.setInt(1, personajeID);
         
         ResultSet rsGetPersonajeNC = pstGetPersonajeNC.executeQuery();
         
         while (rsGetPersonajeNC.next()){
             JSONObject personajeNC = new JSONObject();
             
                personajeNC.put("id", rsGetPersonajeNC.getInt(1));
                personajeNC.put("nameo", rsGetPersonajeNC.getString(2));
                personajeNC.put("namer", (rsGetPersonajeNC.getString(3) == null) ? "" : rsGetPersonajeNC.getString(3));
                personajeNC.put("lname", (rsGetPersonajeNC.getString(4) == null) ? "" : rsGetPersonajeNC.getString(4));
                personajeNC.put("genero", rsGetPersonajeNC.getString(5));
                personajeNC.put("estado", rsGetPersonajeNC.getString(6));
///////////////////////////////
                personajeNC.put("relacion", rsGetPersonajeNC.getString(7));
                personajeNC.put("parentesco", (rsGetPersonajeNC.getString(8) == null) ? "" : rsGetPersonajeNC.getString(8));
                personajeNC.put("descripcion", (rsGetPersonajeNC.getString(9) == null) ? "" : rsGetPersonajeNC.getString(9));
             listaPersonajeNC.put(personajeNC);
         }

         return listaPersonajeNC;

     } catch (SQLException ex) {
         Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
     }

     return listaPersonajeNC;
 }
         
             public static void AddPersonajeNC(JSONObject personajenc, int personajeID){
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("INSERT INTO acc_personaje_no_combate "
                    + "VALUES (?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstRelacionPJ = conn.prepareStatement("INSERT INTO acc_relacion_personaje(id_repe, tiporelacion_repe, parentesco_repe, descripcion_repe, personaje_no_combate_fk, personaje_fk) VALUES (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)
                ){
            
           
               
                pstInsertar.setInt(1, DBClass.getLastValue("acc_personaje_no_combate","id_personajenc"));
                pstInsertar.setString(2, personajenc.getString("nombrepesonajenc"));
                pstInsertar.setString(3, personajenc.getString("nombrerealpesonajenc"));
                pstInsertar.setString(4, personajenc.getString("apellidopesonajenc"));
                pstInsertar.setString(5, personajenc.getString("generopersonajenc"));
                pstInsertar.setString(6, personajenc.getString("fallecidopersonajenc")); // Donde mandas este campo? nunca
                
                if (personajenc.getString("nombrerealpesonajenc").equals(""))
                    pstInsertar.setNull(3, 0);
                else
                    pstInsertar.setString(3, personajenc.getString("nombrerealpesonajenc"));
                if (personajenc.getString("apellidopesonajenc").equals(""))
                    pstInsertar.setNull(4, 0);
                else
                    pstInsertar.setString(4, personajenc.getString("apellidopesonajenc"));
                
                    
                
                
                if (pstInsertar.executeUpdate() > 0){
                    System.out.println("Inserto el Personaje no combate");
                    ResultSet estadoRST = pstInsertar.getGeneratedKeys();
                    if (estadoRST.next()){
                        pstRelacionPJ.setInt(1, DBClass.getLastValue("acc_relacion_personaje","id_repe"));
                        pstRelacionPJ.setString(2, personajenc.getString("relacion"));
                        pstRelacionPJ.setString(3, personajenc.getString("parentesco"));
                        pstRelacionPJ.setString(4, personajenc.getString("descripcion"));                        
                        pstRelacionPJ.setInt(5,estadoRST.getInt(1) );
                        pstRelacionPJ.setInt(6, personajeID);
                        
                        
                        if (personajenc.getString("relacion").equals(""))
                            pstRelacionPJ.setNull(3, 0);
                        else
                            pstRelacionPJ.setString(3, personajenc.getString("parentesco"));
                        if (personajenc.getString("descripcion").equals(""))
                            pstInsertar.setNull(4, 0);
                        else
                            pstRelacionPJ.setString(4, personajenc.getString("descripcion"));
                        }
                    if(pstRelacionPJ.executeUpdate() > 0){
                        
                        System.out.println("inserto Relacion");
                    }
                    else {
                    System.out.println("no inserto Relacion");
                    }
                }
                else{
                    System.out.println("No inserto el Personaje no combate");
                }

            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
          
        public static JSONObject getPersonajeNCRelacion(String id){
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetAlias = conn.prepareStatement("SELECT b.id_personajenc, b.nombre_personajenc, b.nombrereal_personajenc, b.apellidoreal_personajenc, b.genero_personajenc, b.fallecido_personajenc, a.tiporelacion_repe, a.parentesco_repe, a.descripcion_repe from acc_relacion_personaje a, acc_personaje_no_combate b WHERE b.id_personajenc = ? and a.personaje_no_combate_fk = b.id_personajenc ;")
                ){
            
            JSONObject personajeNC = new JSONObject();
            
            pstGetAlias.setInt(1, Integer.valueOf(id));
            
            ResultSet rsGetPersonajeNC = pstGetAlias.executeQuery();
            
            if (rsGetPersonajeNC.next()){
                
                personajeNC.put("id", rsGetPersonajeNC.getInt(1));
                personajeNC.put("nameo", rsGetPersonajeNC.getString(2));
                personajeNC.put("namer", (rsGetPersonajeNC.getString(3) == null) ? "" : rsGetPersonajeNC.getString(3));
                personajeNC.put("lname", (rsGetPersonajeNC.getString(4) == null) ? "" : rsGetPersonajeNC.getString(4));
                personajeNC.put("genero", rsGetPersonajeNC.getString(5));
                personajeNC.put("estado", rsGetPersonajeNC.getString(6));
///////////////////////////////
                personajeNC.put("relacion", rsGetPersonajeNC.getString(7));
                personajeNC.put("parentesco", (rsGetPersonajeNC.getString(8) == null) ? "" : rsGetPersonajeNC.getString(8));
                personajeNC.put("descripcion", (rsGetPersonajeNC.getString(9) == null) ? "" : rsGetPersonajeNC.getString(9));
            }
            
            return personajeNC;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
        
        public static void DelPersonajeNC(String id){
    
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("DELETE FROM acc_personaje_no_combate WHERE id_personajenc = ?");
            PreparedStatement pstRel = conn.prepareStatement("DELETE FROM acc_relacion_personaje WHERE personaje_no_combate_fk = ?");
            PreparedStatement pstPerGa = conn.prepareStatement("DELETE FROM acc_hist_per_ga WHERE personaje_no_combate_fk = ?");
          
        ){
            
                
                pstRel.setInt(1, Integer.parseInt(id));
                pstRel.executeUpdate();
                pstInsertar.setInt(1,Integer.parseInt(id));
                pstInsertar.executeUpdate();
                pstPerGa.setInt(1, Integer.parseInt(id));
                pstPerGa.executeUpdate();
           
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
             
            public static void ModPersonajeNC(JSONObject personajeNC, int personajeID){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("UPDATE acc_personaje_no_combate SET nombre_personajenc = ?, nombrereal_personajenc = ?, apellidoreal_personajenc = ?,"
                    + " genero_personajenc = ?, fallecido_personajenc = ? WHERE id_personajenc = ?", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstInsertarRelacion = conn.prepareStatement("UPDATE acc_relacion_personaje SET tiporelacion_repe = ?, parentesco_repe = ?, descripcion_repe = ? WHERE personaje_no_combate_fk = ?", Statement.RETURN_GENERATED_KEYS)
        ){
            
            pstInsertar.setString(1, personajeNC.getString("nombrepesonajenc"));
            pstInsertar.setString(2, personajeNC.getString("nombrerealpesonajenc"));
            pstInsertar.setString(3, personajeNC.getString("apellidopesonajenc"));
            pstInsertar.setString(4, personajeNC.getString("generopersonajenc"));
            pstInsertar.setString(5, personajeNC.getString("fallecidopersonajenc"));
            pstInsertar.setInt(6, personajeID); 
            
                if (personajeNC.getString("nombrerealpesonajenc").equals(""))
                    pstInsertar.setNull(2, 0);
                else
                    pstInsertar.setString(2, personajeNC.getString("nombrerealpesonajenc"));
                if (personajeNC.getString("apellidopesonajenc").equals(""))
                    pstInsertar.setNull(3, 0);
                else
                    pstInsertar.setString(3, personajeNC.getString("apellidopesonajenc"));
            
            
            if (pstInsertar.executeUpdate() > 0){
                 
                System.out.println("\nPERSONAJE: "+personajeNC.getString("nombrepesonajenc")+" ha sido actualizado en la DB\n"); // aquii te va a fallar por copiarlo sin pensar 
                ResultSet RelacionM = pstInsertar.getGeneratedKeys();
                if(RelacionM.next()){
                        pstInsertarRelacion.setString(1, personajeNC.getString("relacion"));
                        pstInsertarRelacion.setString(2, personajeNC.getString("parentesco"));
                        pstInsertarRelacion.setString(3, personajeNC.getString("descripcion"));
                        pstInsertarRelacion.setInt(4, personajeID);
                        if (personajeNC.getString("parentesco").equals(""))
                            pstInsertarRelacion.setNull(2, 0);
                        else
                            pstInsertarRelacion.setString(2, personajeNC.getString("parentesco"));
                        if (personajeNC.getString("descripcion").equals(""))
                            pstInsertar.setNull(3, 0);
                        else
                            pstInsertarRelacion.setString(3, personajeNC.getString("descripcion"));
                        if(pstInsertarRelacion.executeUpdate() > 0){
                        
                        System.out.println("Se modifico Relacion con exito");
                    }
                    else {
                    System.out.println("no se modifico relacion con exito");
                    }
                }
                
            }
            else{
                
                System.out.println("\nPERSONAJE: "+personajeNC.getString("nombrepersonajenc")+" no ha sido actualizado en la DB\n");
                
            }
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
  ////////////////////////////////////////////////////////////////////////////////////// PODER
            
    public static JSONArray getPoderList(int personajeID){
     JSONArray listaPoder = new JSONArray();

     try(
         Connection conn = DBClass.getConn();
         PreparedStatement pstGetPoder = conn.prepareStatement("SELECT a.id_poder, a.nombre_poder, a.descripcion_poder from acc_poder a, acc_per_pod b WHERE b.personaje_fk = ? and a.id_poder = b.poder_fk;")
     ){

         pstGetPoder.setInt(1, personajeID);
         
         ResultSet rsGetPoder = pstGetPoder.executeQuery();

         while (rsGetPoder.next()){
             JSONObject Poder = new JSONObject();
             
             Poder.put("id",rsGetPoder.getInt(1) );
             Poder.put("nombrepoder", rsGetPoder.getString(2));
             Poder.put("descripcion", (rsGetPoder.getString(3) == null) ? "" : rsGetPoder.getString(3));

             listaPoder.put(Poder);
         }

         return listaPoder;

     } catch (SQLException ex) {
         Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
     }

     return listaPoder;
 }        
       
    public static JSONObject getPoder(String id){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetPoder = conn.prepareStatement("SELECT id_poder, nombre_poder, descripcion_poder FROM acc_poder WHERE id_poder = ?;")
                ){
            
            JSONObject poder = new JSONObject();
            
            pstGetPoder.setInt(1, Integer.valueOf(id));
            
            ResultSet rsGetPoder = pstGetPoder.executeQuery();
            
            if (rsGetPoder.next()){
                
                poder.put("id", rsGetPoder.getInt(1));
                poder.put("nombrePoder", rsGetPoder.getString(2));
                poder.put("descripcion", (rsGetPoder.getString(3) == null) ? "" : rsGetPoder.getString(3));
            }
            
            return poder;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }    
    
        public static void AddPoder(JSONObject poder, int personajeID){
    
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("INSERT INTO acc_poder "
                    + "VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstRelacion = conn.prepareStatement("INSERT INTO acc_per_pod VALUES (?,?);")
                ){
            
           
               
                pstInsertar.setInt(1, DBClass.getLastValue("acc_poder","id_poder"));
                pstInsertar.setString(2, poder.getString("nombrepoder"));
                pstInsertar.setString(3, poder.getString("descripcion")); // Donde mandas este campo? nunca
                
                
                if (poder.getString("descripcion").equals(""))
                    pstInsertar.setNull(3, 0);
                else
                    pstInsertar.setString(3, poder.getString("descripcion"));
                
                //pstInsertar.setInt(4, personajeID);
           
                if (pstInsertar.executeUpdate() > 0){
                    System.out.println("Inserto el Poder");
                    ResultSet estadoRST = pstInsertar.getGeneratedKeys();
                    if (estadoRST.next()){
                    pstRelacion.setInt(1, personajeID);
                    pstRelacion.setInt(2, estadoRST.getInt(1));
                    }
                    if (pstRelacion.executeUpdate() > 0){
                    System.out.println("Inserto Relacion de Poder");
                    }
                    else {
                    System.out.println("No Inserto Relacion de Poder");
                    }
                }else{
                    System.out.println("No inserto el Alias");
                }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
        
            public static void ModPoder(JSONObject poder, int poderID){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("UPDATE acc_poder SET nombre_poder = ?, descripcion_poder = ? WHERE id_poder = ?")
        ){
            
            pstInsertar.setString(1, poder.getString("nombrepoder"));
            pstInsertar.setString(2, poder.getString("descripcion"));
            pstInsertar.setInt(3, poderID); 
            
            
            if (pstInsertar.executeUpdate() > 0){
                
                System.out.println("\nPODER: "+poder.getString("nombrepoder")+" ha sido actualizado en la DB\n"); // aquii te va a fallar por copiarlo sin pensar 
                
            }else{
                
                System.out.println("\nPODER: "+poder.getString("nombrealias")+" no ha sido actualizado en la DB\n");
                
            }
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
            
    public static void DelPoder(String id){
    
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstDeleteR = conn.prepareStatement("DELETE FROM acc_per_pod WHERE poder_fk = ?", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstDelete = conn.prepareStatement("DELETE FROM acc_poder WHERE id_poder = ?", Statement.RETURN_GENERATED_KEYS)
        ){
            pstDeleteR.setInt(1, Integer.parseInt(id));
            if(pstDeleteR.executeUpdate() > 0){
            System.out.println("La relacion a sido borrada");
            ResultSet estadoRST = pstDeleteR.getGeneratedKeys();
            if (estadoRST.next()){
            pstDelete.setInt(1, Integer.parseInt(id));
            
            
            if (pstDelete.executeUpdate() > 0){
                
                System.out.println("\nAlias: "+id+" ha sido borrado de la DB\n");
                
            }else{
                
                System.out.println("\nAlias: "+id+" no ha sido borrado de la DB\n");
                
            }
            }
            }
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }            
            
}
