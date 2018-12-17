/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package Pruebas;

import DBHelper.DBClass;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class PruebaSQL {
    
        /*
        *
        *   Prueba de como hacer la conexion con la DB y como realizar una consulta simple 
        *
        *   Branch - Developer
        *
        */
    
    public static void main(String[] args) {   
//        
//        try(
//            Connection conn = DBClass.getConn();
//            // En caso de necesitar llenar algun campo con informacion especifica colocamos '?'
//            PreparedStatement pst = conn.prepareStatement("SELECT * from lugar");
//        ){
//            
//          
//            
//            // Pedimos ejecutar el query..
//            ResultSet rst = pst.executeQuery();
//            
//            // Ciclo mientras exista algo en la respuesta de la consulta
//            while (rst.next()){
//            }
//            
//            // Cerramos la conexion para no desperdiciar recursos
//            conn.close();
//        }
//        catch (SQLException ex) {
//             ex.printStackTrace();
//        }

        Calendar cal = Calendar.getInstance(); // creates calendar
        cal.setTime(new Date()); // sets calendar time/date
//        cal.add(Calendar.HOUR_OF_DAY, 7); // adds one hour
        cal.add(Calendar.DAY_OF_MONTH, 0);
        
        System.out.println(cal.getTime());
    }
        
}
