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
import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;
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
 * @author aless
 */
public class Agregado {
       
        
    public static int AddPersonaje(JSONObject personaje){
        
        int lugarNac = -1;
        int personajeID = -1;
        int profesionID = -1;
         
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("INSERT INTO acc_personaje(id_personaje, nombreoriginal_personaje, nombrereal_personaje, apellidoreal_personaje, identidad_personaje, biografia_personaje, estadocivil_personaje, genero_personaje, altura_personaje, peso_personaje, colorojos_personaje, colorpelo_personaje, lugarNacimiento_fk, tipo_personaje) "
                    + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstInsertarLugar = conn.prepareStatement("INSERT INTO acc_lugar(id_lugar, nombre_lugar, tipo, tipo_greografia, id_lugar_fk) VALUES (?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstInsertarProfesion = conn.prepareStatement("INSERT INTO acc_profesion (id_profesion, nombre_profesion, descripcion_profesion) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            PreparedStatement psInsertarPJPR = conn.prepareStatement("INSERT INTO acc_pj_pr VALUES (?,?)")
                ){
            // INSERTAR PAIS
            pstInsertarLugar.setInt(1, DBClass.getLastValue("acc_lugar","id_lugar"));
            pstInsertarLugar.setString(2, personaje.getString("pais"));
            pstInsertarLugar.setString(3, "PAIS");
            pstInsertarLugar.setString(4, "TIERRA");
            pstInsertarLugar.setNull(5, 0);
            
            if (pstInsertarLugar.executeUpdate() > 0){
                
                ResultSet paisRST = pstInsertarLugar.getGeneratedKeys();
                if (paisRST.next()){
                    //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");
                
                    // INSERTAR ESTADO
                    pstInsertarLugar.setInt(1, DBClass.getLastValue("acc_lugar","id_lugar"));
                    pstInsertarLugar.setString(2, personaje.getString("estado"));
                    pstInsertarLugar.setString(3, "ESTADO");
                    pstInsertarLugar.setString(4, "TIERRA");
                    pstInsertarLugar.setInt(5, paisRST.getInt(1));
                    
                    if (pstInsertarLugar.executeUpdate() > 0){

                        ResultSet estadoRST = pstInsertarLugar.getGeneratedKeys();
                        if (estadoRST.next()){
                            //System.out.println("PERSONAJE: "+personaje.getString("nombre")+" ha sido agregado a la DB\n");

                            // INSERTAR ESTADO
                            pstInsertarLugar.setInt(1, DBClass.getLastValue("acc_lugar","id_lugar"));
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
                
                pstInsertar.setInt(1, DBClass.getLastValue("acc_personaje","id_personaje"));
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
           
            PreparedStatement pstGetParafernalia = conn.prepareStatement("SELECT p.id_parafernalia, p.nombre_parafernalia FROM acc_parafernalia p, acc_p_p pp" +
                                    " WHERE p.id_parafernalia = pp.parafernalia_fk AND pp.personaje_fk = ?" +
                                    " UNION" +
                                    " SELECT p.id_parafernalia, p.nombre_parafernalia FROM acc_parafernalia p" +
                                    " WHERE p.id_parafernalia IN (SELECT distinct(parafernalia_fk) FROM acc_pa_pa WHERE parafernalia_compuesta_fk in (SELECT p.id_parafernalia FROM acc_parafernalia p, acc_p_p pp WHERE p.id_parafernalia = pp.parafernalia_fk AND pp.personaje_fk = ?))" +
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
            PreparedStatement pstGetParafernalia = conn.prepareStatement("SELECT p.* FROM acc_parafernalia p, acc_p_p pp" +
                                    " WHERE p.id_parafernalia = pp.parafernalia_fk AND pp.personaje_fk = ?" +
                                    " UNION" +
                                    " SELECT p.* FROM acc_parafernalia p" +
                                    " WHERE p.id_parafernalia IN (SELECT distinct(parafernalia_fk) FROM acc_pa_pa WHERE parafernalia_compuesta_fk in (SELECT p.id_parafernalia FROM acc_parafernalia p, acc_p_p pp WHERE p.id_parafernalia = pp.parafernalia_fk AND pp.personaje_fk = ?))" +
                                    " ORDER BY id_parafernalia");
            PreparedStatement pstGetpp = conn.prepareStatement("SELECT alturametros_pp, pesokg_pp FROM acc_p_p WHERE parafernalia_fk = ?")
        ){
            pstGetParafernalia.setInt(1, personajeID);
            pstGetParafernalia.setInt(2, personajeID);
            
            ResultSet rsGetParafernalia = pstGetParafernalia.executeQuery();
            
            while (rsGetParafernalia.next()){
                JSONObject parafernalia = new JSONObject();
                
                int parafernaliaID = rsGetParafernalia.getInt(1);
                
                parafernalia.put("id", parafernaliaID);
                parafernalia.put("name", rsGetParafernalia.getString(2));
                parafernalia.put("tipo", rsGetParafernalia.getString(3));
                
                pstGetpp.setInt(1, parafernaliaID);
                
                ResultSet rstGetpp = pstGetpp.executeQuery();
                
                if (rstGetpp.next()){
//                    System.out.println("Entre?");
                    parafernalia.put("esPP", true);
                    parafernalia.put("altura", rstGetpp.getDouble(1));
                    parafernalia.put("peso", rstGetpp.getDouble(2));
                }
                else{
//                    System.out.println("No, no entraste");
                    parafernalia.put("esPP", false);
                }

//                System.out.println("PARA: "+parafernalia.toString(1));
                
                listaParafernalia.put(parafernalia);
            }
            
            return listaParafernalia;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaParafernalia;
        
    }
    
    public static JSONObject getPersonaje(String id){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetPersonaje = conn.prepareStatement("SELECT id_personaje \"ID\", tipo_personaje \"Tipo Personaje\", "
                    + "nombreoriginal_personaje \"Nombre Original\", nombrereal_personaje \"Nombre Real]\", "
                    + "apellidoreal_personaje \"Apellido Real\", identidad_personaje \"Identidad\", "
                    + "biografia_personaje \"Biografia\", estadocivil_personaje \"Estado Civil\" , genero_personaje \"Genero\","
                    + "altura_personaje \"Altura\", peso_personaje \"Peso\", "
                    + "colorojos_personaje \"Color ojos\",colorpelo_personaje \"Color cabello\" "
                    + "FROM acc_personaje WHERE id_personaje = ?;")
                ){
            
            JSONObject personaje = new JSONObject();
            
            pstGetPersonaje.setInt(1, Integer.valueOf(id));
            
            ResultSet rsGetPersonaje = pstGetPersonaje.executeQuery();
            
            if (rsGetPersonaje.next()){
                
                personaje.put("id", rsGetPersonaje.getInt(1));
                personaje.put("tipo", rsGetPersonaje.getString(2));
                personaje.put("nameo", rsGetPersonaje.getString(3));
                personaje.put("namer", rsGetPersonaje.getString(4));
                personaje.put("lname", rsGetPersonaje.getString(5));
                personaje.put("identidad", rsGetPersonaje.getString(6));
                personaje.put("biografia", rsGetPersonaje.getString(7));
                personaje.put("estadocivil", rsGetPersonaje.getString(8));
                personaje.put("genero", rsGetPersonaje.getString(9));
                personaje.put("altura", rsGetPersonaje.getDouble(10));
                personaje.put("peso", rsGetPersonaje.getDouble(11));
                personaje.put("colorOjos", rsGetPersonaje.getString(12));
                personaje.put("colorCabello", rsGetPersonaje.getString(13));
                
//                System.out.println("ALUMNO: "+alumno.toString());

            }
            
            return personaje;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
     
    public static JSONArray getPersonaje(){
        JSONArray listaPersonaje = new JSONArray();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetAlumnos = conn.prepareStatement("SELECT id_personaje \"ID\", tipo_personaje \"Tipo Personaje\", "
                    + "nombreoriginal_personaje \"Nombre Original\", nombrereal_personaje \"Nombre Real]\", "
                    + "apellidoreal_personaje \"Apellido Real\", identidad_personaje \"Identidad\", "
                    + "biografia_personaje \"Biografia\", estadocivil_personaje \"Estado Civil\" , genero_personaje \"Genero\","
                    + "altura_personaje \"Altura\", peso_personaje \"Peso\", "
                    + "colorojos_personaje \"Color ojos\",colorpelo_personaje \"Color cabello\""
                    + " FROM acc_personaje;")
        ){
            
            ResultSet rsGetPersonaje = pstGetAlumnos.executeQuery();
            
            while (rsGetPersonaje.next()){
                JSONObject personaje = new JSONObject();
                
                personaje.put("id", rsGetPersonaje.getInt(1));
                personaje.put("tipo", rsGetPersonaje.getString(2));
                personaje.put("nameo", rsGetPersonaje.getString(3));
                personaje.put("namer", rsGetPersonaje.getString(4));
                personaje.put("lname", rsGetPersonaje.getString(5));
                personaje.put("identidad", rsGetPersonaje.getString(6));
                personaje.put("biografia", rsGetPersonaje.getString(7));
                personaje.put("estadocivil", rsGetPersonaje.getString(8));
                personaje.put("genero", rsGetPersonaje.getString(9));
                personaje.put("altura", rsGetPersonaje.getInt(10));
                personaje.put("peso", rsGetPersonaje.getInt(11));
                personaje.put("colorojos", rsGetPersonaje.getString(12));
                personaje.put("colorpelo", rsGetPersonaje.getString(13));
                
                
//                System.out.println("ALUMNO: "+alumno.toString());

                listaPersonaje.put(personaje);
            }
            
            return listaPersonaje;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaPersonaje;
    }
     
    public static void DelPersonaje(String id){
    
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("DELETE FROM acc_personaje WHERE id_personaje = ?");
            PreparedStatement  pstSelect_PJPR  = conn.prepareStatement("select profesionpjpr_fk from acc_pj_pr where personajepjpr_fk = ? ;");
            PreparedStatement pstDelete_PJPR = conn.prepareStatement("delete from acc_pj_pr where personajepjpr_fk = ? ");
            PreparedStatement pstDelete_Profesion = conn.prepareStatement("delete from acc_profesion where id_profesion = ?")
          
        ){
            
                
                JSONArray profesiones = new JSONArray();
                
                pstSelect_PJPR.setInt(1, Integer.parseInt(id));
                ResultSet profesionesNAN = pstSelect_PJPR.executeQuery();
                
                while ( profesionesNAN.next()){
                    profesiones.put(profesionesNAN.getInt(1));
                }
                
                pstDelete_PJPR.setInt(1, Integer.parseInt(id));
                
                pstDelete_PJPR.executeUpdate();
                
                for (int i = 0; i < profesiones.length(); i++){
                    pstDelete_Profesion.setInt(1, profesiones.getInt(i));
                    pstDelete_Profesion.executeUpdate();
                }
                pstInsertar.setInt(1,Integer.parseInt(id));
                pstInsertar.executeUpdate();
                
           
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void ModPersonaje(JSONObject personaje){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("UPDATE acc_personaje SET"
                    + " tipo_personaje = ?, nombreoriginal_personaje = ?, nombrereal_personaje = ?,"
                    + " apellidoreal_personaje = ?, identidad_personaje = ?, biografia_personaje = ?,"
                    + " estadocivil_personaje = ?, genero_personaje = ?, altura_personaje = ?, peso_personaje = ?,"
                    + " colorojos_personaje = ?, colorpelo_personaje = ? WHERE id_personaje = ?")
        ){
            
            pstInsertar.setString(1, personaje.getString("tipo"));
            pstInsertar.setString(2, personaje.getString("nameo"));
            pstInsertar.setString(3, personaje.getString("namer"));
            pstInsertar.setString(4, personaje.getString("lname"));
            pstInsertar.setString(5, personaje.getString("identidad"));
            pstInsertar.setString(6, personaje.getString("biografia"));
            pstInsertar.setString(7, personaje.getString("estadocivil"));
            pstInsertar.setString(8, personaje.getString("genero"));
            pstInsertar.setDouble(9, Double.parseDouble(personaje.getString("altura")));
            pstInsertar.setDouble(10, Double.parseDouble(personaje.getString("peso")));
            pstInsertar.setString(11, personaje.getString("colorojos"));
            pstInsertar.setString(12, personaje.getString("colorpelo"));
            pstInsertar.setInt(13, Integer.parseInt(personaje.getString("id")));
            
            System.out.println(pstInsertar.toString());
            
            if (pstInsertar.executeUpdate() > 0){
                
                System.out.println("\nALUMNO: "+personaje.getString("id")+" ha sido actualizado en la DB\n");
                
            }else{
                
                System.out.println("\nALUMNO: "+personaje.getString("id")+" no ha sido actualizado en la DB\n");
                
            }
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void AddParafernalia(JSONObject parafernalia, int idPersonaje, boolean isPart){
         
        try(
            Connection conn = DBClass.getConn();
            
            PreparedStatement pstInsertarParafernalia = conn.prepareStatement("INSERT INTO acc_parafernalia (id_parafernalia, nombre_parafernalia, tipo_parafernalia)"
                    + "VALUES (?,?,?);", Statement.RETURN_GENERATED_KEYS);
            PreparedStatement pstInsertarPA_PA = conn.prepareStatement("INSERT INTO acc_pa_pa VALUES (?,?)");
            PreparedStatement pstInsertarP_P = conn.prepareStatement("INSERT INTO acc_p_p VALUES (?,?,?,?)")
        ){
            
            if (isPart){
                
                pstInsertarParafernalia.setInt(1, DBClass.getLastValue("acc_parafernalia","id_parafernalia"));
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
            
                pstInsertarParafernalia.setInt(1, DBClass.getLastValue("acc_parafernalia","id_parafernalia"));
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

    public static JSONArray getEvento() {
        JSONArray listaEvento = new JSONArray();
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetEventos = conn.prepareStatement("SELECT id_evento \"ID\", nombre_evento \"Nombre evento\","
                    + " fechainicio_evento \"Fecha inicio\", fechafin_evento \"Fecha fin\", descripcion_evento \"Descripcion\" "
                    + "FROM acc_evento")
        ){
            
            ResultSet rsGetEventos = pstGetEventos.executeQuery();
            
            while (rsGetEventos.next()){    
                JSONObject evento = new JSONObject();
                
                evento.put("id", rsGetEventos.getInt(1));
                evento.put("nombreevento", rsGetEventos.getString(2));
                evento.put("fechainicio", rsGetEventos.getString(3));
                evento.put("fechafin", rsGetEventos.getString(4));
                evento.put("descripcion", rsGetEventos.getString(5));
                
                try {
            
                    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    String diaActual = dateFormat.format(new Date());

                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                    Date actual = sdf.parse(diaActual);
                    Date tfinal = sdf.parse(evento.getString("fechafin"));

                    if (actual.compareTo(tfinal) > 0){
                        System.out.println("Evento inactivo");
                        evento.put("status", "INACTIVO");
                    }else{
                        System.out.println("Evento activo");
                        evento.put("status", "ACTIVO");
                    }

                    System.out.println(actual.compareTo(tfinal));
                } catch (ParseException ex) {
                    Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println(evento.toString(1));
//                System.out.println("ALUMNO: "+alumno.toString());

                listaEvento.put(evento);
            }
            
            return listaEvento;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return listaEvento;
    }
    
    public static JSONArray getParafernalia(){
     JSONArray listaParafernalia = new JSONArray();

     try(
         Connection conn = DBClass.getConn();
         PreparedStatement pstGetParafernalia = conn.prepareStatement("SELECT nombre_parafernalia from acc_parafernalia;")
     ){

         ResultSet rsGetParafernalia = pstGetParafernalia.executeQuery();

         while (rsGetParafernalia.next()){
             JSONObject parafernalia = new JSONObject();

             parafernalia.put("nombre", rsGetParafernalia.getInt(1));


//                System.out.println("ALUMNO: "+alumno.toString());

             listaParafernalia.put(parafernalia);
         }

         return listaParafernalia;

     } catch (SQLException ex) {
         Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
     }

     return listaParafernalia;
 }

    public static JSONObject getParafernaliaMod(String id){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetParafernalia = conn.prepareStatement("select nombre_parafernalia, tipo_parafernalia from acc_parafernalia where id_parafernalia = ?;");
                PreparedStatement pstGetPP = conn.prepareStatement("select * from acc_p_p where parafernalia_fk = ?;")
                ){
            
            JSONObject parafernalia = new JSONObject();
            
            pstGetParafernalia.setInt(1, Integer.valueOf(id));
            
            ResultSet rsGetParafernalia = pstGetParafernalia.executeQuery();
            
            if (rsGetParafernalia.next()){
                
                parafernalia.put("nombre", rsGetParafernalia.getString(1));
                parafernalia.put("tipo", rsGetParafernalia.getString(2));
                
                pstGetPP.setInt(1, Integer.valueOf(id));
                
                ResultSet rsPP = pstGetPP.executeQuery();
                
                if (rsPP.next()){
                    parafernalia.put("esPP", true);
                    parafernalia.put("altura", rsPP.getDouble(3));
                    parafernalia.put("peso", rsPP.getDouble(4));
                    
                }
                else{
                    parafernalia.put("esPP", false);
                }
            }
            
            return parafernalia;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
       
    public static void ModParafernalia(JSONObject parafernalia){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstUpdate = conn.prepareStatement("UPDATE acc_parafernalia SET nombre_parafernalia = ?, tipo_parafernalia = ? WHERE id_parafernalia = ?");
            PreparedStatement pstUpdatePP = conn.prepareStatement("UPDATE acc_p_p SET alturametros_pp = ?, pesokg_pp = ? WHERE parafernalia_fk = ?")
        ){
            
            pstUpdate.setString(1, parafernalia.getString("nombre"));
            pstUpdate.setString(2, parafernalia.getString("tipo"));
            pstUpdate.setInt(3, parafernalia.getInt("id"));
            
            System.out.println(pstUpdate.toString());
            
            if (pstUpdate.executeUpdate() > 0){
                
                if (parafernalia.getBoolean("esPP")){
                    pstUpdatePP.setDouble(1, Double.parseDouble(parafernalia.getString("altura")));
                    pstUpdatePP.setDouble(2, Double.parseDouble(parafernalia.getString("peso")));
                    pstUpdatePP.setInt(3, parafernalia.getInt("id"));
                    
                    pstUpdatePP.executeUpdate();
                }
                
                System.out.println("\nParafernalia: "+parafernalia.getString("nombre")+" ha sido actualizado en la DB\n");
                
            }else{
                
                System.out.println("\nParafernalia: "+parafernalia.getString("nombre")+" no ha sido actualizado en la DB\n");
                
            }
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void DelParafernalia(String id){
    
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstDelPAPA = conn.prepareStatement("DELETE FROM acc_pa_pa WHERE parafernalia_compuesta_fk = ?");
            PreparedStatement pstDelPP = conn.prepareStatement("DELETE FROM acc_p_p WHERE parafernalia_fk = ?");
            PreparedStatement pstDelP = conn.prepareStatement("DELETE FROM acc_parafernalia WHERE id_parafernalia = ?")
            
        ){          
            // Eliminar la relacion entre Parafernalia y Parafernalia
            pstDelPAPA.setInt(1, Integer.parseInt(id));
            pstDelPAPA.executeUpdate();
            
            // Eliminar la relacion entre Parafernalia y Personaje
            pstDelPP.setInt(1, Integer.parseInt(id));
            pstDelPP.executeUpdate();
            
            // Eliminar Parafernalia
            pstDelP.setInt(1, Integer.parseInt(id));
            pstDelP.executeUpdate();
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    public static void AddAlias(JSONObject alias, int personajeID){
    
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("INSERT INTO acc_alias "
                    + "VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS)
                ){
            
           
               
                pstInsertar.setInt(1, DBClass.getLastValue("acc_alias","id_alias"));
                pstInsertar.setString(2, alias.getString("nombrealias"));
                pstInsertar.setString(3, alias.getString("descripcion")); // Donde mandas este campo? nunca
                
                
                if (alias.getString("descripcion").equals(""))
                    pstInsertar.setNull(3, 0);
                else
                    pstInsertar.setString(3, alias.getString("descripcion"));
                
                pstInsertar.setInt(4, personajeID);
           
                if (pstInsertar.executeUpdate() > 0){
                    System.out.println("Inserto el Alias");
                }else{
                    System.out.println("No inserto el Alias");
                }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

    public static JSONArray getAliasList(int personajeID){
     JSONArray listaAlias = new JSONArray();

     try(
         Connection conn = DBClass.getConn();
         PreparedStatement pstGetAlias = conn.prepareStatement("SELECT id_alias, nombre_alias, descripcion_alias from acc_alias WHERE personaje_fk = ?;")
     ){

         pstGetAlias.setInt(1, personajeID);
         
         ResultSet rsGetAlias = pstGetAlias.executeQuery();

         while (rsGetAlias.next()){
             JSONObject alias = new JSONObject();
             
             alias.put("id",rsGetAlias.getInt(1) );
             alias.put("nombrealias", rsGetAlias.getString(2));
             alias.put("descripcion", (rsGetAlias.getString(3) == null) ? "" : rsGetAlias.getString(3));

             listaAlias.put(alias);
         }

         return listaAlias;

     } catch (SQLException ex) {
         Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
     }

     return listaAlias;
 }
 
    public static JSONObject getAlias(String id){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetAlias = conn.prepareStatement("SELECT id_alias, nombre_alias, descripcion_alias FROM acc_alias WHERE id_Alias= ?;")
                ){
            
            JSONObject alias = new JSONObject();
            
            pstGetAlias.setInt(1, Integer.valueOf(id));
            
            ResultSet rsGetAlias = pstGetAlias.executeQuery();
            
            if (rsGetAlias.next()){
                
                alias.put("id", rsGetAlias.getInt(1));
                alias.put("nombrealias", rsGetAlias.getString(2));
                alias.put("descripcion", (rsGetAlias.getString(3) == null) ? "" : rsGetAlias.getString(3));
            }
            
            return alias;
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    public static void DelAlias(String id){
    
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstDelete = conn.prepareStatement("DELETE FROM acc_alias WHERE id_alias = ?")
        ){
            
            pstDelete.setInt(1, Integer.parseInt(id));
            
            
            if (pstDelete.executeUpdate() > 0){
                
                System.out.println("\nAlias: "+id+" ha sido borrado de la DB\n");
                
            }else{
                
                System.out.println("\nAlias: "+id+" no ha sido borrado de la DB\n");
                
            }
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void ModAlias(JSONObject alias, int aliasID){
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("UPDATE acc_alias SET nombre_alias = ?, descripcion_alias = ? WHERE id_alias = ?")
        ){
            
            pstInsertar.setString(1, alias.getString("nombrealias"));
            pstInsertar.setString(2, alias.getString("descripcion"));
            pstInsertar.setInt(3, aliasID); 
            
            
            if (pstInsertar.executeUpdate() > 0){
                
                System.out.println("\nALIAS: "+alias.getString("nombrealias")+" ha sido actualizado en la DB\n"); // aquii te va a fallar por copiarlo sin pensar 
                
            }else{
                
                System.out.println("\nALIAS: "+alias.getString("nombrealias")+" no ha sido actualizado en la DB\n");
                
            }
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
    public static void AddHabilidad(JSONObject habilidades, int personajeID){
    
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstInsertar = conn.prepareStatement("INSERT INTO acc_habilidad "
                    + "VALUES (?,?,?,?);", Statement.RETURN_GENERATED_KEYS)
                ){
            
            
                pstInsertar.setInt(1, DBClass.getLastValue("acc_habilidad","id_habilidad"));
                pstInsertar.setString(2, "Inteligencia");
                pstInsertar.setInt(3, habilidades.getInt("Inteligencia"));
                pstInsertar.setInt(4, personajeID);       
                
                pstInsertar.executeUpdate();
                
                
                pstInsertar.setInt(1, DBClass.getLastValue("acc_habilidad","id_habilidad"));
                pstInsertar.setString(2, "Fuerza");
                pstInsertar.setInt(3, habilidades.getInt("Fuerza"));
                pstInsertar.setInt(4, personajeID);       
                
                pstInsertar.executeUpdate();
            
            
                pstInsertar.setInt(1, DBClass.getLastValue("acc_habilidad","id_habilidad"));
                pstInsertar.setString(2, "Velocidad");
                pstInsertar.setInt(3, habilidades.getInt("Velocidad"));
                pstInsertar.setInt(4, personajeID);       
                
                pstInsertar.executeUpdate();
            
            
                pstInsertar.setInt(1, DBClass.getLastValue("acc_habilidad","id_habilidad"));
                pstInsertar.setString(2, "Resistencia");
                pstInsertar.setInt(3, habilidades.getInt("Resistencia"));
                pstInsertar.setInt(4, personajeID);       
                
                pstInsertar.executeUpdate();
            
            
                pstInsertar.setInt(1, DBClass.getLastValue("acc_habilidad","id_habilidad"));
                pstInsertar.setString(2, "Proyeccion de energia");
                pstInsertar.setInt(3, habilidades.getInt("Proyeccion de energia"));
                pstInsertar.setInt(4, personajeID);       
                
                pstInsertar.executeUpdate();
            
            
                pstInsertar.setInt(1, DBClass.getLastValue("acc_habilidad","id_habilidad"));
                pstInsertar.setString(2, "Habilidades de combate");
                pstInsertar.setInt(3, habilidades.getInt("Habilidades de combate"));
                pstInsertar.setInt(4, personajeID);       
                
                pstInsertar.executeUpdate();
                
                
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
  
    public static JSONObject getHabilidad(int personajeID){
    
        JSONObject habilidad = new JSONObject();
        
        try(
        
            Connection conn = DBClass.getConn();
            PreparedStatement pstGet = conn.prepareStatement("SELECT nombre_habilidad, valor_habilidad FROM acc_habilidad WHERE personaje_fk = ?")
                
        ){
            
            pstGet.setInt(1, personajeID);
                
            ResultSet rsGet = pstGet.executeQuery();
            
            while (rsGet.next()){
                habilidad.put(rsGet.getString(1), rsGet.getInt(2));
            }
                
            return habilidad;
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return habilidad;
    
    }
      

} 