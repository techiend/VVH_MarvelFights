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
import javax.swing.JOptionPane;

/**
 *
 * @author aless
 */
public class GA_MasGanadores {
    
        public static ArrayList<String> getGrupoA(){
        
        ArrayList<String> listaGA = new ArrayList();
        
        try{
            Connection conn = DBClass.getConn();
            PreparedStatement pstMostrar;
            pstMostrar = conn.prepareStatement("Select id_grupoafiliacion, nombre_grupoafiliacion from acc_grupo_afiliacion");
            
            ResultSet rs = pstMostrar.executeQuery();
            
            while (rs.next()){
                listaGA.add(Integer.toString(rs.getInt(1))+"."+rs.getString(2));
            }
            
            return listaGA;
            
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e);
        }
        
        return listaGA;
    }
}
