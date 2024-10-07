/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadCravero.accesoADatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author crist
 */
public class Conexion {
    private static final String URL="jdbc:mariadb://localhost/";
    private static final String BBDD="gp16_cravero_base";
    private static final String USUARIO="root";
    private static final String PASS="";
    
    private static Connection conexion;
    
    private Conexion(){}
    
    
    public static Connection getConexion(){
    
        if(conexion == null){
            
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                conexion = DriverManager.getConnection(URL+BBDD, USUARIO, PASS);
                
                
            } catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Error al cargar drivers de la base de datos");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al conectarse con la base de datos");
            }
        }
        
        return conexion;
    }
    
}
