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

public class PruebaSQL {
    
        /*
        *
        *   Prueba de como hacer la conexion con la DB y como realizar una consulta simple 
        *
        *   Branch - Developer
        *
        */
    
    public static void main(String[] args) {   
        
        try(
            Connection conn = DBClass.getConn();
            // En caso de necesitar llenar algun campo con informacion especifica colocamos '?'
            PreparedStatement pst = conn.prepareStatement("SELECT * FROM usuario WHERE apellido = ? or nombre = ?;");
        ){
            
            // Al colocar '?' luego simplemente especificamos que va en cada uno de ellos
            pst.setString(1, "Varisco");
            pst.setString(2, "Carlos");
            // Pedimos ejecutar el query..
            ResultSet rst = pst.executeQuery();
            
            // Ciclo mientras exista algo en la respuesta de la consulta
            while (rst.next()){
                
                // Por cada una de las filas que retorna el SELECT utilizamos la informacion como nos plazca
                System.out.println("\nUsuario #:"+rst.getInt("id")+"");
                System.out.println("Nombre y Apellido: "+rst.getString("nombre")+" "+rst.getString("apellido"));
                System.out.println("Telefono: "+rst.getString("telefono"));
                System.out.println("Profesion: "+rst.getString("profesion"));
            }
            
            // Cerramos la conexion para no desperdiciar recursos
            conn.close();
        }
        catch (SQLException ex) {
             ex.printStackTrace();
        }
    }
        
}
