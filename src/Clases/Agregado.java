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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author aless
 */
public class Agregado {
       
        
     public static int AddPersonaje(JSONObject personaje){
        
        int lugarNac = -1;
        int personajeID = -1;
         
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("INSERT INTO personaje(id_personaje, nombreoriginal_personaje, nombrereal_personaje, apellidoreal_personaje, identidad_personaje, biografia_personaje, estadocivil_personaje, genero_personaje, altura_personaje, peso_personaje, colorojos_personaje, colorpelo_personaje, lugarNacimiento_fk, tipo_personaje) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstInsertarLugar = conn.prepareStatement("INSERT INTO lugar(id_lugar, nombre_lugar, tipo, tipo_greografia, id_lugar_fk) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS)
        ){
            // INSERTAR PAIS
            pstInsertarLugar.setInt(1, DBClass.getLastValue("lugar"));
            pstInsertarLugar.setString(2, personaje.getString("pais"));
            pstInsertarLugar.setString(3, "PAIS");
            pstInsertarLugar.setString(4, "TIERRA");
            pstInsertarLugar.setNull(5, 0);
            
            if (pstInsertarLugar.executeUpdate() > 0){
                
                ResultSet paisRST = pstInsertarLugar.getGeneratedKeys();
                if (paisRST.next()){
                    //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");
                
                    // INSERTAR ESTADO
                    pstInsertarLugar.setInt(1, DBClass.getLastValue("lugar"));
                    pstInsertarLugar.setString(2, personaje.getString("estado"));
                    pstInsertarLugar.setString(3, "ESTADO");
                    pstInsertarLugar.setString(4, "TIERRA");
                    pstInsertarLugar.setInt(5, paisRST.getInt(1));
                    
                    if (pstInsertarLugar.executeUpdate() > 0){

                        ResultSet estadoRST = pstInsertarLugar.getGeneratedKeys();
                        if (estadoRST.next()){
                            //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                            // INSERTAR ESTADO
                            pstInsertarLugar.setInt(1, DBClass.getLastValue("lugar"));
                            pstInsertarLugar.setString(2, personaje.getString("ciudad"));
                            pstInsertarLugar.setString(3, "CIUDAD");
                            pstInsertarLugar.setString(4, "TIERRA");
                            pstInsertarLugar.setInt(5, estadoRST.getInt(1));

                            if (pstInsertarLugar.executeUpdate() > 0){

                                ResultSet ciudadRST = pstInsertarLugar.getGeneratedKeys();
                                if (ciudadRST.next()){
                                    //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                                    // INSERTAR CIUDAD
                                    lugarNac = ciudadRST.getInt(1);

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
            
            if (lugarNac > 0){
                
                pstInsertar.setInt(1, DBClass.getLastValue("personaje"));
                pstInsertar.setString(2, personaje.getString("nombre"));
                pstInsertar.setString(3, personaje.getString("nombreR"));
                pstInsertar.setString(4, personaje.getString("apellidoR"));
                pstInsertar.setString(5, personaje.getString("identidad"));
                pstInsertar.setString(6, personaje.getString("biografia"));
                pstInsertar.setString(7, personaje.getString("estadoCivil"));
                pstInsertar.setString(8, personaje.getString("sexo"));
                pstInsertar.setDouble(9, Double.parseDouble(personaje.getString("altura")));
                pstInsertar.setDouble(10, Double.parseDouble(personaje.getString("peso")));
                pstInsertar.setString(11, personaje.getString("colorOjos"));
                pstInsertar.setString(12, personaje.getString("colorCabello"));
                pstInsertar.setInt (13, lugarNac);
                pstInsertar.setString(14, personaje.getString("tipo"));


                if (pstInsertar.executeUpdate() > 0){

                    ResultSet personajeRST = pstInsertar.getGeneratedKeys();
                    if (personajeRST.next()){
                        //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                        // INSERTAR PERSONAJE
                        personajeID = personajeRST.getInt(1);
                        
                        System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB con el numero: "+personajeID+"\n");
                        return personajeID;
                    }

                }else{

                    System.out.println("PERSONAJE: "+personaje.getString("nombre")+" no ha sido agregado a la DB\n");
                    return -1;
                }
            
            }else{
                System.out.println("ERROR, lugar de nacimiento no insertado\n");
                    return -1;
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    
        return -1;
}
     
     public static ArrayList listParafernalia(int personajeID){
     
         System.out.println(personajeID);
         ArrayList<String> listaP = new ArrayList();
         
         try(
            Connection conn = DBClass.getConn();
           
            PreparedStatement pstGetParafernalia = conn.prepareStatement("SELECT p.id_parafernalia, p.nombre_parafernalia FROM parafernalia p, p_p pp" +
                                    " WHERE p.id_parafernalia = pp.parafernalia_fk AND pp.personaje_fk = ?" +
                                    " UNION" +
                                    " SELECT p.id_parafernalia, p.nombre_parafernalia FROM parafernalia p" +
                                    " WHERE p.id_parafernalia IN (SELECT distinct(parafernalia_fk) FROM pa_pa WHERE parafernalia_compuesta_fk in (SELECT p.id_parafernalia FROM parafernalia p, p_p pp WHERE p.id_parafernalia = pp.parafernalia_fk AND pp.personaje_fk = ?))" +
                                    " ORDER BY id_parafernalia")
        ){
            
            pstGetParafernalia.setInt(1, personajeID);
            pstGetParafernalia.setInt(2, personajeID);
            
            ResultSet rsParafernalia = pstGetParafernalia.executeQuery();
             
            while (rsParafernalia.next()){
                listaP.add(Integer.toString(rsParafernalia.getInt(1))+"."+rsParafernalia.getString(2));
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         System.out.println(listaP.toString());
         return listaP;
         
     }
     
    public static JSONArray getParafernalia(int personajeID){
        JSONArray listaParafernalia = new JSONArray();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetParafernalia = conn.prepareStatement("SELECT p.* FROM parafernalia p, p_p pp" +
                                    " WHERE p.id_parafernalia = pp.parafernalia_fk AND pp.personaje_fk = ?" +
                                    " UNION" +
                                    " SELECT p.* FROM parafernalia p" +
                                    " WHERE p.id_parafernalia IN (SELECT distinct(parafernalia_fk) FROM pa_pa WHERE parafernalia_compuesta_fk in (SELECT p.id_parafernalia FROM parafernalia p, p_p pp WHERE p.id_parafernalia = pp.parafernalia_fk AND pp.personaje_fk = ?))" +
                                    " ORDER BY id_parafernalia")
        ){
            pstGetParafernalia.setInt(1, personajeID);
            pstGetParafernalia.setInt(2, personajeID);
            
            ResultSet rsGetParafernalia = pstGetParafernalia.executeQuery();
            
            while (rsGetParafernalia.next()){
                JSONObject parafernalia = new JSONObject();
                
                parafernalia.put("id", rsGetParafernalia.getInt(1));
                parafernalia.put("name", rsGetParafernalia.getString(2));
                parafernalia.put("tipo", rsGetParafernalia.getString(4));

                listaParafernalia.put(parafernalia);
            }
            
            return listaParafernalia;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaParafernalia;
    }
     
     public static void AddParafernalia(JSONObject parafernalia, int idPersonaje, boolean isPart){
         
        try(
            Connection conn = DBClass.getConn();
            
            PreparedStatement pstInsertarParafernalia = conn.prepareStatement("INSERT INTO parafernalia (id_parafernalia, nombre_parafernalia, tipo_parafernalia)"
                    + "VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstInsertarPA_PA = conn.prepareStatement("INSERT INTO pa_pa VALUES (?,?)");
            PreparedStatement pstInsertarP_P = conn.prepareStatement("INSERT INTO p_p VALUES (?,?,?,?)")
        ){
            
            if (isPart){
                
                pstInsertarParafernalia.setInt(1, DBClass.getLastValue("parafernalia"));
                pstInsertarParafernalia.setString(2, parafernalia.getString("name"));
                pstInsertarParafernalia.setString(3, parafernalia.getString("tipo"));
                
                if  (pstInsertarParafernalia.executeUpdate() > 0 ){
                    ResultSet rsParafernalia = pstInsertarParafernalia.getGeneratedKeys();
                    if (rsParafernalia.next()){
                        pstInsertarPA_PA.setInt(1, Integer.parseInt(parafernalia.getString("id_pa")));
                        pstInsertarPA_PA.setInt(2, rsParafernalia.getInt(1));
                        
                        pstInsertarPA_PA.executeUpdate();
                    }
                }
                
            }else{
            
                pstInsertarParafernalia.setInt(1, DBClass.getLastValue("parafernalia"));
                pstInsertarParafernalia.setString(2, parafernalia.getString("name"));
                pstInsertarParafernalia.setString(3, parafernalia.getString("tipo"));
                
                if  (pstInsertarParafernalia.executeUpdate() > 0 ){
                    ResultSet rsParafernalia = pstInsertarParafernalia.getGeneratedKeys();
                    if (rsParafernalia.next()){
                        pstInsertarP_P.setInt(1, rsParafernalia.getInt(1));
                        pstInsertarP_P.setInt(2, idPersonaje);
                        pstInsertarP_P.setDouble(3, Double.parseDouble(parafernalia.getString("altura")));
                        pstInsertarP_P.setDouble(4, Double.parseDouble(parafernalia.getString("peso")));
                        
                        pstInsertarP_P.executeUpdate();
                    }
                }
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
} 