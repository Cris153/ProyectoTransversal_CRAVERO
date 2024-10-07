/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadCravero;

import java.sql.Connection;
import java.time.LocalDate;
import java.time.Month;
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
        
        Alumno uno = new Alumno(43765287, "Cravero", "Cristian", LocalDate.of(2001, 10, 15), true);
        Alumno dos = new Alumno(40884234, "Perez", "Pablo", LocalDate.of(1997, 12, 24), true);
        Alumno tre = new Alumno(45264265, "Raposo", "Martin", LocalDate.of(2004, 2, 1), true);
        Alumno cuatro = new Alumno(42005001, "Martinez", "Lautaro", LocalDate.of(1999, 7, 31), true);
        
        AlumnoData al = new AlumnoData();
        
        
        System.out.println("Cargando alumnos");
        
        al.cargarAlumno(uno);
        al.cargarAlumno(dos);
        al.cargarAlumno(tre);
        al.cargarAlumno(cuatro);
        
        //SE CARGAN ALUMNOS Y SE LISTAN
        
        
        
        System.out.println("Listando Alumnos");
        
        for(Alumno alumno: al.listar()){
            System.out.println("---------");
            System.out.println("DNI: " + alumno.getDni());
            System.out.println("Apellido: " + alumno.getApellido());
            System.out.println("Nombre: " + alumno.getNombre());
            System.out.println("Fecha Nacimiento: " + alumno.getFecha_nac());
        }
        System.out.println("Eliminando alumno...");
        al.eliminarAlumno(45264265);
        
        //SE ELIMINA UNO Y SE LISTAN ALUMNOS
        
        System.out.println("Listando Alumnos");
        
        
        for(Alumno alumno: al.listar()){
            System.out.println("DNI: " + alumno.getDni());
            System.out.println("Apellido: " + alumno.getApellido());
            System.out.println("Nombre: " + alumno.getNombre());
            System.out.println("Fecha Nacimiento: " + alumno.getFecha_nac());
        }
        
        
        //MOSTRAR ALUMNO POR DNI
        
//        Alumno alDni = al.buscarAlumnoPorDni(0);
//        
//        System.out.println("DNI: " + alDni.getDni());
//        System.out.println("Apellido: " + alDni.getApellido());
//        System.out.println("Nombre: " + alDni.getNombre());
//        System.out.println("Fecha Nacimiento: " + alDni.getFecha_nac());
        
        
        //MOSTRAR ALUMNO POR ID
        
//        Alumno alId = al.buscarAlumnoPorId(4);
//        
//        System.out.println("DNI: " + alId.getDni());
//        System.out.println("Apellido: " + alId.getApellido());
//        System.out.println("Nombre: " + alId.getNombre());
//        System.out.println("Fecha Nacimiento: " + alId.getFecha_nac());
    }
    
}
