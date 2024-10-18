/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package universidadCravero.accesoADatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import universidadCravero.entidades.Alumno;
import universidadCravero.entidades.Inscripcion;
import universidadCravero.entidades.Materia;

/**
 *
 * @author crist
 */
public class InscripcionData {
    private Connection conexion = null;
    private AlumnoData aluData = new AlumnoData();
    private MateriaData matData = new MateriaData();
    
    
    
    public InscripcionData(){
        
        conexion = Conexion.getConexion();
        
    }
    
    
    public List<Alumno> listarAlumnosPorMateria(int id_materia){
        
        String select = "SELECT a.idAlumno, dni, apellido, nombre, fecha_nacimiento, estado FROM inscripcion i, alumno a "
                + "WHERE i.id_alumno = a.idAlumno AND id_materia = ?";
        
        ArrayList<Alumno> aluMateria = new ArrayList<>();
        
        try {
            PreparedStatement ps = conexion.prepareStatement(select);
            ps.setInt(1, id_materia);
            
            ResultSet res = ps.executeQuery();
            while(res.next()){
                Alumno alu = new Alumno();
                alu.setIdAlumno(res.getInt("idAlumno"));
                alu.setDni(res.getInt("dni"));
                alu.setApellido(res.getString("apellido"));
                alu.setNombre(res.getString("nombre"));
                alu.setFecha_nac(res.getDate("fecha_nacimiento").toLocalDate());
                alu.setEstado(res.getBoolean("estado"));
                
                aluMateria.add(alu);
            
            }
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla inscripciones(listarAlumnosPorMateria)");
        }
        return aluMateria;
    }
    
    
    public List<Inscripcion> listarInscripciones(){
            
        ArrayList<Inscripcion> inscripciones = new ArrayList<>();
        
        String select = "SELECT * FROM inscripcion";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(select);
            ResultSet res = ps.executeQuery();
            
            while(res.next()){
                Inscripcion insc = new Inscripcion();
                
                insc.setId_inscripcion(res.getInt("id_inscripto"));
                insc.setNota(res.getDouble("nota"));
                Alumno al = aluData.buscarAlumnoPorId(res.getInt("id_alumno"));
                Materia mat = matData.buscarMateriaPorID(res.getInt("id_materia"));
                insc.setAlumno(al);
                insc.setMateria(mat);
                
                inscripciones.add(insc);
            
            }
            ps.close();
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla inscripciones(listarInscripciones)");
        }
        return inscripciones;
    }
    
    public List<Inscripcion> listarInscripcionesPorAlumno(int id_alumno){
            
        ArrayList<Inscripcion> inscripciones = new ArrayList<>();
        
        String select = "SELECT * FROM inscripcion WHERE `id_alumno` = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(select);
            ps.setInt(1, id_alumno);
            ResultSet res = ps.executeQuery();
            
            while(res.next()){
                Inscripcion insc = new Inscripcion();
                
                insc.setId_inscripcion(res.getInt("id_inscripto"));
                insc.setNota(res.getDouble("nota"));
                Alumno al = aluData.buscarAlumnoPorId(res.getInt("id_alumno"));
                Materia mat = matData.buscarMateriaPorID(res.getInt("id_materia"));
                insc.setAlumno(al);
                insc.setMateria(mat);
                
                inscripciones.add(insc);
            
            }
            ps.close();
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla inscripciones(listarInscripcionesPorAlumno)");
        }
        return inscripciones;
    }
    
    public List<Materia> listaMateriasCursadas(int id_alumno){
        
        String select = "SELECT insc.id_materia, nombre, anio FROM inscripcion insc, materia"
                + " WHERE insc.id_materia = materia.id_materia AND insc.id_alumno = ?";
        
        ArrayList<Materia> materias = new ArrayList<>();
        
        try {
            PreparedStatement ps = conexion.prepareStatement(select);
            ps.setInt(1, id_alumno);
            ResultSet res = ps.executeQuery();
            
            while(res.next()){
                
                Materia materia = new Materia();
                materia.setId_materia(res.getInt("id_materia"));
                materia.setNombre(res.getString("nombre"));
                materia.setAnio(res.getInt("anio"));
                
                materias.add(materia);
            
            }
            ps.close();
   
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla inscripciones(listaMateriasCursadas)");
        }
        return materias;
    }
    
    
    public List<Materia> listaMateriasNoCursadas(int id_alumno){
        
        String select = "SELECT * FROM materia WHERE estado = 1 AND id_materia NOT IN "
                + "(SELECT id_materia FROM inscripcion WHERE id_alumno = ?)";
        
        ArrayList<Materia> materias = new ArrayList<>();
        
        try {
            PreparedStatement ps = conexion.prepareStatement(select);
            ps.setInt(1, id_alumno);
            ResultSet res = ps.executeQuery();
            
            while(res.next()){
                
                Materia materia = new Materia();
                materia.setId_materia(res.getInt("id_materia"));
                materia.setNombre(res.getString("nombre"));
                materia.setAnio(res.getInt("anio"));
                
                materias.add(materia);
            
            }
            ps.close();
    
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla inscripciones(listaMateriasNoCursadas)");
        }    
        return materias;
    }
    
    
    public void altaInscripcion(Inscripcion insc){
       
        String insert = "INSERT INTO `inscripcion`( `nota`, `id_alumno`, `id_materia`) VALUES (?,?,?)";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, insc.getNota());
            ps.setInt(2, insc.getAlumno().getIdAlumno());
            ps.setInt(3, insc.getMateria().getId_materia());
            ps.executeUpdate();
            
            ResultSet res = ps.getGeneratedKeys();
            if(res.next()){
                insc.setId_inscripcion(res.getInt(1));
                JOptionPane.showMessageDialog(null, "Inscripción cargada exitosamente");
            }
            
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla inscripciones(altaInscripcion)");
        }
    
    }
    
    
    public void modificarNota(int id_alumno, int id_materia, double nota){
    
        String update="UPDATE `inscripcion` SET `nota` = ? WHERE `id_alumno` = ? AND `id_materia` = ?";
    
        try {
            PreparedStatement ps = conexion.prepareStatement(update);
            
            ps.setDouble(1, nota);
            ps.setInt(2, id_alumno);
            ps.setInt(3, id_materia);
            
            int res = ps.executeUpdate();
            
            if(res > 0){
                JOptionPane.showMessageDialog(null, "Nota modificada con éxito");
            }
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla inscripciones(modificarNota)");
        }
    }
    
    
    public void bajaInscripcion(int id_alumno, int id_materia){
    
        String delete = "DELETE FROM `inscripcion` WHERE `id_alumno` = ? AND `id_materia` = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(delete);
            
            ps.setInt(1, id_alumno);
            ps.setInt(2, id_materia);
            int res = ps.executeUpdate();
            
            if(res > 0){
                JOptionPane.showMessageDialog(null, "Inscripcion dada de baja con éxito");
            
            }
            
            ps.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla inscripciones(bajaInscripcion)");
        }
    
    }
    
    
}
