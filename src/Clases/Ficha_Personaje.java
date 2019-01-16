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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;


/**
 *
 * @author aless
 */
public class Ficha_Personaje {
    

    public static ArrayList<String> getPersonajeFicha(){
        
        ArrayList<String> listaPersonajes = new ArrayList();
        
        try{
            Connection conn = DBClass.getConn();
            PreparedStatement pstMostrar;
            pstMostrar = conn.prepareStatement("Select id_personaje, nombreoriginal_personaje from acc_personaje");
            
            ResultSet rs = pstMostrar.executeQuery();
            
            while (rs.next()){
                listaPersonajes.add(Integer.toString(rs.getInt(1))+"."+rs.getString(2));
            }
            
            return listaPersonajes;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        return listaPersonajes;
    }
    
    public static JSONObject getInfoPersonaje(int personajeID){
    
        JSONObject info = new JSONObject();
        int fk_Lugar = 0;
        
        try(
            Connection conn = DBClass.getConn();
            PreparedStatement pstMostrar = conn.prepareStatement("SELECT * FROM acc_personaje WHERE id_personaje = ?");
            PreparedStatement pstAlias = conn.prepareStatement("select nombre_alias from acc_alias where personaje_fk = ?");    
            PreparedStatement pstGrupo = conn.prepareStatement("SELECT nombre_grupoafiliacion FROM acc_grupo_afiliacion WHERE id_grupoafiliacion in (select grupoafiliacion_fk from acc_hist_per_ga where personaje_fk = ?)");
            PreparedStatement pstHabsPe = conn.prepareStatement("SELECT nombre_habilidad, valor_habilidad FROM acc_habilidad WHERE personaje_fk = ?");
            PreparedStatement pstPodsPe = conn.prepareStatement("SELECT nombre_poder FROM acc_poder WHERE id_poder in (SELECT poder_fk FROM acc_per_pod WHERE personaje_fk = ?)");
            PreparedStatement pstProfPe = conn.prepareStatement("SELECT nombre_profesion FROM acc_profesion WHERE id_profesion in (SELECT profesionpjpr_fk FROM acc_pj_pr WHERE personajepjpr_fk = ?)");
            PreparedStatement pstRelacion = conn.prepareStatement("SELECT pn.nombre_personajenc, rp.tiporelacion_repe, coalesce(rp.parentesco_repe, 'Ninguno') "
                    + "FROM acc_relacion_personaje rp, acc_personaje_no_combate pn "
                    + "WHERE rp.personaje_fk = ? AND pn.id_personajenc = rp.personaje_no_combate_fk "
                    + "UNION "
                    + "SELECT pn.nombreoriginal_personaje, rp.tiporelacion_repe, coalesce(rp.parentesco_repe, 'Ninguno') "
                    + "FROM acc_relacion_personaje rp, acc_personaje pn "
                    + "WHERE rp.personaje_fk = ? AND pn.id_personaje = rp.personaje_relacion_fk");
                
                
        ){
            pstMostrar.setInt(1, personajeID);
            
            ResultSet rs = pstMostrar.executeQuery();
            
            if (rs.next()){
                info.put("bio", rs.getString("biografia_personaje"));
                info.put("nombreo", rs.getString("nombreoriginal_personaje"));
                info.put("nombrer", rs.getString("nombrereal_personaje"));
                info.put("apellidor", rs.getString("apellidoreal_personaje"));
                info.put("identidad", rs.getString("identidad_personaje"));
                info.put("estadocivil", rs.getString("estadocivil_personaje"));
                info.put("peso", rs.getString("peso_personaje"));
                info.put("altura", rs.getString("altura_personaje"));
                info.put("ojo", rs.getString("colorojos_personaje"));
                info.put("pelo", rs.getString("colorpelo_personaje"));
                
                fk_Lugar = rs.getInt("lugarnacimiento_fk");
//                System.out.println("LUGAR: " + fk_Lugar);
                info.put("dir", GrupoAfiliacionC.getDireccion(fk_Lugar));
                
                pstAlias.setInt(1, personajeID);
                
                ResultSet rsAlias = pstAlias.executeQuery();
                
                JSONArray alias = new JSONArray();
                while (rsAlias.next()){
                    alias.put(rsAlias.getString(1));
                }
                
                info.put("alias", alias);
                
                pstGrupo.setInt(1, personajeID);
                
                ResultSet rsGrupo = pstGrupo.executeQuery();
                
                JSONArray grupos = new JSONArray();
                while (rsGrupo.next()){
                    grupos.put(rsGrupo.getString(1));
                }
                
                info.put("grupos", grupos);
                
                pstHabsPe.setInt(1, personajeID);
                        
                ResultSet rsHabs = pstHabsPe.executeQuery();

                JSONArray habilidades = new JSONArray();
                while (rsHabs.next()){
                    JSONObject habilidad = new JSONObject();

                    habilidad.put("name", rsHabs.getString(1));
                    habilidad.put("value", rsHabs.getInt(2));

                    habilidades.put(habilidad);
                }
                info.put("habs", habilidades);
                
                pstPodsPe.setInt(1, personajeID);
                
                ResultSet rsPoderes = pstPodsPe.executeQuery();
                
                JSONArray poderes = new JSONArray();
                while (rsPoderes.next()){
                    poderes.put(rsPoderes.getString(1));
                }
                
                info.put("poderes", poderes);
                
                pstProfPe.setInt(1, personajeID);
                
                ResultSet rsProfesion = pstProfPe.executeQuery();
                
                JSONArray profesiones = new JSONArray();
                while (rsProfesion.next()){
                    profesiones.put(rsProfesion.getString(1));
                }
                
                info.put("profesiones", profesiones);
                
                
                pstRelacion.setInt(1, personajeID);
                pstRelacion.setInt(2, personajeID);
                
                ResultSet rsRelacion = pstRelacion.executeQuery();
                
                JSONArray aliados = new JSONArray();
                JSONArray enemigos = new JSONArray();
                JSONArray parientes = new JSONArray();
                while(rsRelacion.next()){
                    JSONObject relacion = new JSONObject();
                    
                    relacion.put("name", rsRelacion.getString(1));
                    relacion.put("parentesco", rsRelacion.getString(3));
                    
                    switch(rsRelacion.getString(2)){
                        case "Aliado":
                            aliados.put(relacion);
                            break;
                        case "Enemigo":
                            enemigos.put(relacion);
                            break;
                        case "Pariente":
                            parientes.put(relacion);
                            break;
                    }
                    
                
                }
                
                info.put("aliados", aliados);
                info.put("enemigos", enemigos);
                info.put("parientes", parientes);
            }
            
            return info;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        return info;
        
    }
    
    
}
