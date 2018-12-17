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
import java.util.Calendar;
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
            PreparedStatement pstCreateInscrito = conn.prepareStatement("INSERT INTO acc_inscrito VALUES (?, ?, 0, false, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)
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
                        
                        ResultSet rstInscrito = pstCreateInscrito.getGeneratedKeys();
                        
                        if (rstInscrito.next()){
                           inscritos.getJSONObject(i).put("id_inscrito", rstInscrito.getInt(1));
                        }
                    }
                    else{
                        return -3; // No posee grupo de afiliacion
                    }
                }
                
                evento.setGrupos(inscritos);
                
                return idEvento;
                
                
            }
            else {
                return -2; // Error al crear evento
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    public static void createPeleasEtapa1(EventoC evento){
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetInscritos = conn.prepareStatement("select id_inscrito, numerogrupo_inscrito from acc_inscrito WHERE evento_fk = ?");
            PreparedStatement pstGetInscritosGrupo = conn.prepareStatement("select id_inscrito, numerogrupo_inscrito from acc_inscrito "
                    + "WHERE evento_fk = ? AND id_inscrito != ? AND numerogrupo_inscrito = ?;");
            PreparedStatement pstSaberSipelean = conn.prepareStatement("select * from acc_contienda WHERE etapa_contienda = 1 AND"
                    + "((inscrito1 = ? AND inscrito2 = ?) or (inscrito2 = ? AND inscrito1 = ?));");
            PreparedStatement pstCreateContienda = conn.prepareStatement("INSERT INTO acc_contienda"
                    + "(id_contienda, etapa_contienda, fecha_contienda, inscrito1, inscrito2) "
                    + "VALUES "
                    + "(?,1, ?,?,?)")
        ){
            int idEvento = evento.getIdEvento();
            
            System.out.println("EVENTO #"+idEvento);
            pstGetInscritos.setInt(1, evento.getIdEvento());
                        
            ResultSet rsGetInscritos = pstGetInscritos.executeQuery();
            
            int count = 0;
            while (rsGetInscritos.next()){
                
                int idPeleador1 = rsGetInscritos.getInt(1);
                int grupo = rsGetInscritos.getInt(2);
                
                pstGetInscritosGrupo.setInt(1, evento.getIdEvento());
                pstGetInscritosGrupo.setInt(2, idPeleador1);
                pstGetInscritosGrupo.setInt(3, grupo);
                
                ResultSet rsGetPeleasPosibles = pstGetInscritosGrupo.executeQuery();
                
                while (rsGetPeleasPosibles.next()){
                    int idPeleador2 = rsGetPeleasPosibles.getInt(1);
                    
                    pstSaberSipelean.setInt(1, idPeleador1);
                    pstSaberSipelean.setInt(2, idPeleador2);
                    pstSaberSipelean.setInt(3, idPeleador1);
                    pstSaberSipelean.setInt(4, idPeleador2);
                    
                    ResultSet saberSiPeleanYa = pstSaberSipelean.executeQuery();
                    
                    if (saberSiPeleanYa.next()){
                    }
                    else{
                        Calendar cal = Calendar.getInstance(); // creates calendar
                        cal.setTime(evento.getFechaInicio()); // sets calendar time/date
                        cal.add(Calendar.DAY_OF_MONTH, count);
//                        cal.getTime(); // returns new date object, one hour in the future


                        java.sql.Date fechaPelea = new java.sql.Date(cal.getTime().getTime()); 
                        count += 1;
                        
                        if (count == 3){
                            count = 0;
                        }
                        
                        pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                        pstCreateContienda.setDate(2, fechaPelea );
                        pstCreateContienda.setInt(3, idPeleador1);
                        pstCreateContienda.setInt(4, idPeleador2);
                        
                        pstCreateContienda.executeUpdate();
                    }
                    
                }
                
            }
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static void createPeleasEtapa2(EventoC evento){
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetGanadoresGrupo = conn.prepareStatement("SELECT * FROM acc_inscrito "
                    + "WHERE evento_fk = ? AND numerogrupo_inscrito = ? ORDER BY puntajeetapa1_inscrito DESC LIMIT 2");
            PreparedStatement pstCreateContienda = conn.prepareStatement("INSERT INTO acc_contienda"
                    + "(id_contienda, etapa_contienda, fecha_contienda, inscrito1, inscrito2) "
                    + "VALUES "
                    + "(?,2, ?,?,?)")
        ){
            int idEvento = evento.getIdEvento();
            
            System.out.println("EVENTO #"+idEvento);
            
            JSONArray lista = new JSONArray();
            
            for (int i = 1; i <= evento.getNumGroups(); i++ ){
                
                pstGetGanadoresGrupo.setInt(1, evento.getIdEvento());
                pstGetGanadoresGrupo.setInt(2, i);

                ResultSet rsGetGanadoresGrupo = pstGetGanadoresGrupo.executeQuery();

                JSONObject grupo = new JSONObject();
                
                grupo.put("grupo", i);
                
                int count = 1;
                while (rsGetGanadoresGrupo.next()){
                    
                    grupo.put("p"+count, rsGetGanadoresGrupo.getInt(1));
                    count++;
                }
                
                lista.put(grupo);
            }
            
            Calendar cal = Calendar.getInstance(); // creates calendar
            cal.setTime(evento.getFechaInicio()); // sets calendar time/date
            cal.add(Calendar.DAY_OF_MONTH, 3);
//                        cal.getTime(); // returns new date object, one hour in the future


            java.sql.Date fechaPelea = new java.sql.Date(cal.getTime().getTime()); 
            
            int idPeleador1 = 0;
            int idPeleador2 = 0;
            
            switch (evento.getNumGroups()){
                case 2:
                    
                    // Primera pelea
                    idPeleador1 = lista.getJSONObject(0).getInt("p1");
                    idPeleador2 = lista.getJSONObject(1).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    System.out.println("Inserto primera pelea");
                    
                    // Segunda pelea
                    idPeleador1 = lista.getJSONObject(0).getInt("p2");
                    idPeleador2 = lista.getJSONObject(1).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    System.out.println("Inserto segunda pelea");
                    
                    break;
                case 4:
                    
                    // Primera pelea
                    idPeleador1 = lista.getJSONObject(0).getInt("p1");
                    idPeleador2 = lista.getJSONObject(2).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Segunda pelea
                    idPeleador1 = lista.getJSONObject(0).getInt("p2");
                    idPeleador2 = lista.getJSONObject(2).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Tercera pelea
                    idPeleador1 = lista.getJSONObject(1).getInt("p1");
                    idPeleador2 = lista.getJSONObject(3).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Cuarta pelea
                    idPeleador1 = lista.getJSONObject(1).getInt("p2");
                    idPeleador2 = lista.getJSONObject(3).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    break;
                case 6:
                    
                    // Primera pelea
                    idPeleador1 = lista.getJSONObject(0).getInt("p1");
                    idPeleador2 = lista.getJSONObject(3).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Segunda pelea
                    idPeleador1 = lista.getJSONObject(0).getInt("p2");
                    idPeleador2 = lista.getJSONObject(3).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Tercera pelea
                    idPeleador1 = lista.getJSONObject(1).getInt("p1");
                    idPeleador2 = lista.getJSONObject(4).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Cuarta pelea
                    idPeleador1 = lista.getJSONObject(1).getInt("p2");
                    idPeleador2 = lista.getJSONObject(4).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Quita pelea
                    idPeleador1 = lista.getJSONObject(2).getInt("p1");
                    idPeleador2 = lista.getJSONObject(5).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Sexta pelea
                    idPeleador1 = lista.getJSONObject(2).getInt("p2");
                    idPeleador2 = lista.getJSONObject(5).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    break;
                case 8:
                    
                    // Primera pelea
                    idPeleador1 = lista.getJSONObject(0).getInt("p1");
                    idPeleador2 = lista.getJSONObject(4).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Segunda pelea
                    idPeleador1 = lista.getJSONObject(0).getInt("p2");
                    idPeleador2 = lista.getJSONObject(4).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Tercera pelea
                    idPeleador1 = lista.getJSONObject(1).getInt("p1");
                    idPeleador2 = lista.getJSONObject(5).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Cuarta pelea
                    idPeleador1 = lista.getJSONObject(1).getInt("p2");
                    idPeleador2 = lista.getJSONObject(5).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Quita pelea
                    idPeleador1 = lista.getJSONObject(2).getInt("p1");
                    idPeleador2 = lista.getJSONObject(6).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Sexta pelea
                    idPeleador1 = lista.getJSONObject(2).getInt("p2");
                    idPeleador2 = lista.getJSONObject(6).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Septima pelea
                    idPeleador1 = lista.getJSONObject(3).getInt("p1");
                    idPeleador2 = lista.getJSONObject(7).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Octava pelea
                    idPeleador1 = lista.getJSONObject(3).getInt("p2");
                    idPeleador2 = lista.getJSONObject(7).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    break;
                case 10:
                    
                    // Primera pelea
                    idPeleador1 = lista.getJSONObject(0).getInt("p1");
                    idPeleador2 = lista.getJSONObject(5).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Segunda pelea
                    idPeleador1 = lista.getJSONObject(0).getInt("p2");
                    idPeleador2 = lista.getJSONObject(5).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Tercera pelea
                    idPeleador1 = lista.getJSONObject(1).getInt("p1");
                    idPeleador2 = lista.getJSONObject(6).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Cuarta pelea
                    idPeleador1 = lista.getJSONObject(1).getInt("p2");
                    idPeleador2 = lista.getJSONObject(6).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Quita pelea
                    idPeleador1 = lista.getJSONObject(2).getInt("p1");
                    idPeleador2 = lista.getJSONObject(7).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Sexta pelea
                    idPeleador1 = lista.getJSONObject(2).getInt("p2");
                    idPeleador2 = lista.getJSONObject(7).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Septima pelea
                    idPeleador1 = lista.getJSONObject(3).getInt("p1");
                    idPeleador2 = lista.getJSONObject(8).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Octava pelea
                    idPeleador1 = lista.getJSONObject(3).getInt("p2");
                    idPeleador2 = lista.getJSONObject(8).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Novena pelea
                    idPeleador1 = lista.getJSONObject(4).getInt("p1");
                    idPeleador2 = lista.getJSONObject(9).getInt("p2");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    
                    // Decima pelea
                    idPeleador1 = lista.getJSONObject(4).getInt("p2");
                    idPeleador2 = lista.getJSONObject(9).getInt("p1");
                    
                    pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                    pstCreateContienda.setDate(2, fechaPelea );
                    pstCreateContienda.setInt(3, idPeleador1);
                    pstCreateContienda.setInt(4, idPeleador2);
                    
                    pstCreateContienda.executeUpdate();
                    break;
            }
        
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
    public static void createPeleasEtapa3(EventoC evento){
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstGetGanadoresEtapa2 = conn.prepareStatement("select ganador_contienda, inscrito1, inscrito2 from acc_contienda where etapa_contienda = 2 "
                    + "and (inscrito1 in (select id_inscrito from acc_inscrito where evento_fk = ?) OR inscrito2 in (select id_inscrito from acc_inscrito where evento_fk = ?));");
            PreparedStatement pstGetPosiblesPeleas = conn.prepareStatement("select ganador_contienda, inscrito1, inscrito2 from acc_contienda "
                    + "where etapa_contienda = 2 and (inscrito1 != ? AND inscrito2 != ?) "
                    + "and (inscrito1 in (select id_inscrito from acc_inscrito where evento_fk = ?) OR inscrito2 in (select id_inscrito from acc_inscrito where evento_fk = ?));");
                
                
            PreparedStatement pstSaberSipelean = conn.prepareStatement("select * from acc_contienda WHERE etapa_contienda = 3 AND"
                    + "((inscrito1 = ? AND inscrito2 = ?) or (inscrito2 = ? AND inscrito1 = ?));");
            PreparedStatement pstCreateContienda = conn.prepareStatement("INSERT INTO acc_contienda"
                    + "(id_contienda, etapa_contienda, fecha_contienda, inscrito1, inscrito2) "
                    + "VALUES "
                    + "(?,3, ?,?,?)")
        ){
            int idEvento = evento.getIdEvento();
            
            System.out.println("EVENTO #"+idEvento);
            pstGetGanadoresEtapa2.setInt(1, evento.getIdEvento());
            pstGetGanadoresEtapa2.setInt(2, evento.getIdEvento());
                        
            ResultSet rsGetGanadoresEtapa2 = pstGetGanadoresEtapa2.executeQuery();
            
            while (rsGetGanadoresEtapa2.next()){
                int peleador1 = (rsGetGanadoresEtapa2.getInt(1) == 1)? rsGetGanadoresEtapa2.getInt(2) : rsGetGanadoresEtapa2.getInt(3);
                
                pstGetPosiblesPeleas.setInt(1, peleador1);
                pstGetPosiblesPeleas.setInt(2, peleador1);
                pstGetPosiblesPeleas.setInt(3, evento.getIdEvento());
                pstGetPosiblesPeleas.setInt(4, evento.getIdEvento());
                
                ResultSet rstPosiblesPeleas = pstGetPosiblesPeleas.executeQuery();
                
                while (rstPosiblesPeleas.next()){
                    int peleador2 = (rstPosiblesPeleas.getInt(1) == 1)? rstPosiblesPeleas.getInt(2) : rstPosiblesPeleas.getInt(3);
                    
                    pstSaberSipelean.setInt(1, peleador1);
                    pstSaberSipelean.setInt(2, peleador2);
                    pstSaberSipelean.setInt(3, peleador1);
                    pstSaberSipelean.setInt(4, peleador2);
                    
                    ResultSet saberSiPeleanYa = pstSaberSipelean.executeQuery();
                    
                    if (saberSiPeleanYa.next()){
                    }
                    else{
                        Calendar cal = Calendar.getInstance(); // creates calendar
                        cal.setTime(evento.getFechaInicio()); // sets calendar time/date
                        cal.add(Calendar.DAY_OF_MONTH, 6);
//                        cal.getTime(); // returns new date object, one hour in the future


                        java.sql.Date fechaPelea = new java.sql.Date(cal.getTime().getTime()); 
                        
                        
                        pstCreateContienda.setInt(1, DBClass.getLastValue("acc_contienda", "id_contienda"));
                        pstCreateContienda.setDate(2, fechaPelea );
                        pstCreateContienda.setInt(3, peleador1);
                        pstCreateContienda.setInt(4, peleador2);
                        
                        pstCreateContienda.executeUpdate();
                    }
                }
                
            }
            
            
        
        } catch (SQLException ex) {
            Logger.getLogger(DBController.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
}
