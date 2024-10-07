/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadCravero.accesoADatos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import universidadCravero.entidades.Alumno;

/**
 *
 * @author crist
 */
public class AlumnoData {
    private Connection conexion = null;

    public AlumnoData() {
        
        conexion = Conexion.getConexion();
        
    }
    
    public Alumno buscarAlumnoPorId(int id){
        String buscarPorId="SELECT dni, apellido, nombre, fecha_nacimiento FROM alumno WHERE idAlumno = ? AND estado = 1";
        Alumno alumno = null;
        try {
            PreparedStatement ps = conexion.prepareStatement(buscarPorId);
            
            ps.setInt(1, id);
            
            ResultSet res = ps.executeQuery();
            
            if(res.next()){
                
                alumno = new Alumno();
                alumno.setIdAlumno(id);
                alumno.setDni(res.getInt("dni"));
                alumno.setApellido(res.getString("apellido"));
                alumno.setNombre(res.getString("nombre"));
                alumno.setFecha_nac(res.getDate("fecha_nacimiento").toLocalDate());
                alumno.setEstado(true);
                
                
            }else{
                JOptionPane.showMessageDialog(null, "El alumno no fue encontrado");
            }
                    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno(buscarAlumnoId)");
        }
        return alumno;
    }
    
    public Alumno buscarAlumnoPorDni(int dni){
        String buscarPorDni="SELECT idAlumno, dni, apellido, nombre, fecha_nacimiento FROM alumno WHERE dni = ? AND estado = 1";
        Alumno alumno = null;
        try {
            PreparedStatement ps = conexion.prepareStatement(buscarPorDni);
            
            ps.setInt(1, dni);
            
            ResultSet res = ps.executeQuery();
            
            if(res.next()){
                
                alumno = new Alumno();
                alumno.setIdAlumno(res.getInt("idAlumno"));
                alumno.setDni(res.getInt("dni"));
                alumno.setApellido(res.getString("apellido"));
                alumno.setNombre(res.getString("nombre"));
                alumno.setFecha_nac(res.getDate("fecha_nacimiento").toLocalDate());
                alumno.setEstado(true);
                
                
            }else{
                JOptionPane.showMessageDialog(null, "El dni buscado no existe");
            }
                    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno(buscarAlumnoDni)");
        }
        return alumno;
    }
    
    
    public List<Alumno> listar(){
        String lista="SELECT idAlumno, dni, apellido, nombre, fecha_nacimiento "
                + "FROM alumno WHERE estado = 1";
        
        ArrayList <Alumno> alumnos = new ArrayList<>();
        try {
            PreparedStatement ps = conexion.prepareStatement(lista);
            
            ResultSet res = ps.executeQuery();
            
            while(res.next()){
                
                Alumno alumno = new Alumno();
                alumno.setIdAlumno(res.getInt("idAlumno"));
                alumno.setDni(res.getInt("dni"));
                alumno.setApellido(res.getString("apellido"));
                alumno.setNombre(res.getString("nombre"));
                alumno.setFecha_nac(res.getDate("fecha_nacimiento").toLocalDate());
                alumno.setEstado(true);
                
                alumnos.add(alumno);
            }
            ps.close();
                    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno(listar)");
        }
        return alumnos;
    }
   
    
    
    public void cargarAlumno(Alumno alumno){
        
        String insertar = "INSERT INTO `alumno`( `dni`, `apellido`, `nombre`, `fecha_nacimiento`, `estado`) "
                + "VALUES (?,?,?,?,?)";
        
        try {
            PreparedStatement ps= conexion.prepareStatement(insertar,Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFecha_nac()));
            ps.setBoolean(5, alumno.isEstado());
            ps.executeUpdate(); 
            
            ResultSet rs=ps.getGeneratedKeys();
            
            if(rs.next()){
                alumno.setIdAlumno(rs.getInt(1));
                JOptionPane.showMessageDialog(null, "Alumno guardado exitosamente");
            
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno(cargarAlumno)");
        }
    }
    
    
    public void modificarAlumno(Alumno alumno){
        
        String actualizar = "UPDATE alumno SET dni=?, apellido=?, nombre=?, "
                + "fecha_nacimiento=? WHERE idAlumno = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(actualizar);
            
            ps.setInt(1, alumno.getDni());
            ps.setString(2, alumno.getApellido());
            ps.setString(3, alumno.getNombre());
            ps.setDate(4, Date.valueOf(alumno.getFecha_nac()));
            ps.setInt(5, alumno.getIdAlumno());
            int filas_act = ps.executeUpdate();
            
            if(filas_act == 1){
                JOptionPane.showMessageDialog(null, "Alumno modificado exitosamente");
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno(modificarAlumno)");
        }
    
    
    }
    
    
    public void eliminarAlumno(int dni){
        
        String eliminar = "UPDATE alumno SET estado = 0 WHERE dni = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(eliminar);
            
            ps.setInt(1, dni);
            int resultado = ps.executeUpdate();
            
            if(resultado == 1){
                JOptionPane.showMessageDialog(null, "Alumno eliminado exitosamente");
            
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a la tabla Alumno(eliminarAlumno)");
        }
        
    }
    
}
