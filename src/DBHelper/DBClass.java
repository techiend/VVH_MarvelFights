/*
 * Contenido creado por los integrantes
 * del Grupo #2, prohibida su reutilizaci�n.
 * 
 *  - Alessandra Varisco
 *  - Carlos Hurtado
 *  - Carlos Verde
 */
package DBHelper;

import Clases.Agregado;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Grupo2
 */
public class DBClass {
    
    private static HikariDataSource db;
    static {
        
        Properties props = new Properties();
        props.setProperty("dataSourceClassName", "org.postgresql.ds.PGSimpleDataSource");
        props.setProperty("dataSource.user", "grupo2"); // Usuario de la DB
        props.setProperty("dataSource.password", "123456"); // Contrasena de la DB
        props.setProperty("dataSource.databaseName", "grupo2"); // Nombre de la DB creada
        props.setProperty("maximumPoolSize", "3");
        props.put("dataSource.logWriter", new PrintWriter(System.out));
        
        HikariConfig config = new HikariConfig(props);

        db = new HikariDataSource(config);
    }
    
    public static Connection getConn() throws SQLException {
        return db.getConnection();
    }
    
    public static int getLastValue(String tabla, String id){
    
        try(
            Connection conn = getConn();
        ){
              
            PreparedStatement pst = conn.prepareStatement("SELECT "+id+" FROM "+tabla+" order by "+id+" desc limit 1;");
            ResultSet rsGetID = pst.executeQuery();
             
            if (rsGetID.next()){
                
                return rsGetID.getInt(1) + 1;
                
            }else {
                
                return 1;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Agregado.class.getName()).log(Level.SEVERE, null, ex);
        }
         
          return -1;
    
    }
}
