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
import javax.swing.JOptionPane;
import universidadCravero.entidades.Materia;

/**
 *
 * @author crist
 */
public class MateriaData {
    private Connection conexion = null;

    public MateriaData() {
        
        conexion = Conexion.getConexion();
        
    }
    
    public void altaMateria(Materia materia){
        
        String alta = "INSERT INTO `materia`(`nombre`, `anio`, `estado`)"
                    + " VALUES (?,?,?)";
        
        try {
            
            PreparedStatement ps = conexion.prepareStatement(alta,Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnio());
            ps.setBoolean(3, materia.isEstado());
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            
            if(rs.next()){
                materia.setId_materia(rs.getInt(1));
                
                JOptionPane.showMessageDialog(null, "Materia cargada exitosamente");
                
            }  
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla Materia(altaMateria)");
        }
    }
    
    public void modificarMateria(Materia materia){
        String modificar = "UPDATE `materia` SET `nombre`=?,`anio`=?,`estado`=? WHERE `id_materia` = ?";
        
        
        try {
            PreparedStatement ps = conexion.prepareStatement(modificar);
            
            ps.setString(1, materia.getNombre());
            ps.setInt(2, materia.getAnio());
            ps.setBoolean(3, materia.isEstado());
            ps.setInt(4, materia.getId_materia());
            int resultado = ps.executeUpdate();
            
            if(resultado == 1){
                JOptionPane.showMessageDialog(null, "Materia actualizada exitosamente");
            }
            
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla Materia(modificarMateria)");
        }
    
    }
    
    public void eliminarMateria(int id){
    
        String eliminar = "UPDATE `materia` SET `estado` = 0 WHERE `id_materia` = ?";
        
        try {
            PreparedStatement ps = conexion.prepareStatement(eliminar);
            
            ps.setInt(1, id);
            int resultado = ps.executeUpdate();
            
            if(resultado == 1){
                JOptionPane.showMessageDialog(null, "Materia dada de baja exitosamente");
            }
                        
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla Materia(eliminarMateria)");
        }
    }
    
    public Materia buscarMateriaPorID(int id){
        
        String buscarID = "SELECT * FROM `materia` WHERE `id_materia` = ? AND `estado` = 1";
        Materia materia = null;
        
        try {
            PreparedStatement ps = conexion.prepareStatement(buscarID);
            
            ps.setInt(1, id);
            
            ResultSet res = ps.executeQuery();
            
            if(res.next()){
                
                materia = new Materia();
                
                materia.setId_materia(id);
                materia.setNombre(res.getString("nombre"));
                materia.setAnio(res.getInt("anio"));
                materia.setEstado(true);
                
            }else{
                JOptionPane.showMessageDialog(null, "0(cero) COINCIDENCIAS");
            }
                
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla Materia(buscarMateriaPorID)");
        }
        
        return materia;
    }
    
    public List<Materia> listarMaterias(){
        
        String mostrarTodo = "SELECT * FROM `materia` WHERE `estado` = 1";
        ArrayList<Materia> materias = new ArrayList<>();
        
        try {
            PreparedStatement ps = conexion.prepareStatement(mostrarTodo);
            
            ResultSet res = ps.executeQuery();
            
            while(res.next()){
                
                Materia materia = new Materia();
                
                materia.setId_materia(res.getInt("id_materia"));
                materia.setNombre(res.getString("nombre"));
                materia.setAnio(res.getInt("anio"));
                materia.setEstado(true);
                
                materias.add(materia);
            }
            
            ps.close();   
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al acceder a tabla Materia(listarMaterias)");
        }
        return materias;
    }
    
    
    
    
}
