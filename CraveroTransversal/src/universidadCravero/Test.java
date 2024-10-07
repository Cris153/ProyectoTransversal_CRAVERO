/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadCravero;

import java.sql.Connection;
import java.time.LocalDate;
import universidadCravero.accesoADatos.AlumnoData;
import universidadCravero.accesoADatos.Conexion;
import universidadCravero.entidades.Alumno;

/**
 *
 * @author Cristián Cravero
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Alumno juan= new Alumno(456456457, "Martinez", "Oscar", LocalDate.of(1994, 4,12), true);
        
        AlumnoData al = new AlumnoData();
        
        for(Alumno alumno: al.listar()){
            System.out.println(alumno.getDni());
            System.out.println(alumno.getApellido());
            System.out.println(alumno.getNombre());
        }
    }
    
}
