package modelo.dao;

import conexion.ConectaBD;
import modelo.dto.Cubiculo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CubiculoDAO {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ConectaBD conecta = new ConectaBD();

    public List<Cubiculo> listarCubiculosPorLocal(int codLocal) throws SQLException {
        List<Cubiculo> lista = new ArrayList<>();
        String sql = "SELECT CodCubiculo, CodLocal, CodNivel, TipoCubiculo, Capacidad, " +
                     "PrecioHora, EstadoCubiculo, Descripcion FROM Cubiculo " +
                     "WHERE CodLocal = ? ORDER BY CodNivel, CodCubiculo";
        
        try {
            conn = conecta.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, codLocal);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Cubiculo cubiculo = new Cubiculo();
                cubiculo.setCodCubiculo(rs.getInt("CodCubiculo"));
                cubiculo.setCodLocal(rs.getInt("CodLocal"));
                cubiculo.setCodNivel(rs.getInt("CodNivel"));
                cubiculo.setTipoCubiculo(rs.getString("TipoCubiculo"));
                cubiculo.setCapacidad(rs.getInt("Capacidad"));
                cubiculo.setPrecioHora(rs.getDouble("PrecioHora"));
                cubiculo.setEstadoCubiculo(rs.getString("EstadoCubiculo"));
                cubiculo.setDescripcion(rs.getString("Descripcion"));
                lista.add(cubiculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            cerrarRecursos();
        }
        
        return lista;
    }

    public Cubiculo obtenerCubiculo(int codCubiculo) throws SQLException {
        Cubiculo cubiculo = null;
        String sql = "SELECT CodCubiculo, CodLocal, CodNivel, TipoCubiculo, Capacidad, " +
                     "PrecioHora, EstadoCubiculo, Descripcion FROM Cubiculo WHERE CodCubiculo = ?";
        
        try {
            conn = conecta.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, codCubiculo);
            rs = ps.executeQuery();
            
            if (rs.next()) {
                cubiculo = new Cubiculo();
                cubiculo.setCodCubiculo(rs.getInt("CodCubiculo"));
                cubiculo.setCodLocal(rs.getInt("CodLocal"));
                cubiculo.setCodNivel(rs.getInt("CodNivel"));
                cubiculo.setTipoCubiculo(rs.getString("TipoCubiculo"));
                cubiculo.setCapacidad(rs.getInt("Capacidad"));
                cubiculo.setPrecioHora(rs.getDouble("PrecioHora"));
                cubiculo.setEstadoCubiculo(rs.getString("EstadoCubiculo"));
                cubiculo.setDescripcion(rs.getString("Descripcion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            cerrarRecursos();
        }
        
        return cubiculo;
    }

    public boolean registrarCubiculo(Cubiculo cubiculo) throws SQLException {
        // IMPORTANTE: Ajusta los campos según tu tabla
        // Si NO tienes el campo "Estado", quita ", Estado, 'Activo'"
        String sql = "INSERT INTO Cubiculo (CodLocal, CodNivel, TipoCubiculo, Capacidad, " +
                     "PrecioHora, EstadoCubiculo, Estado, Descripcion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, 'Activo', ?)";
        
        try {
            conn = conecta.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cubiculo.getCodLocal());
            ps.setInt(2, cubiculo.getCodNivel());
            ps.setString(3, cubiculo.getTipoCubiculo());
            ps.setInt(4, cubiculo.getCapacidad());
            ps.setDouble(5, cubiculo.getPrecioHora());
            ps.setString(6, cubiculo.getEstadoCubiculo());
            ps.setString(7, cubiculo.getDescripcion());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("ERROR AL REGISTRAR CUBICULO:");
            e.printStackTrace();
            throw e;
        } finally {
            cerrarRecursos();
        }
    }

    public boolean actualizarCubiculo(Cubiculo cubiculo) throws SQLException {
        String sql = "UPDATE Cubiculo SET CodLocal = ?, CodNivel = ?, TipoCubiculo = ?, " +
                     "Capacidad = ?, PrecioHora = ?, EstadoCubiculo = ?, Descripcion = ? " +
                     "WHERE CodCubiculo = ?";
        
        try {
            conn = conecta.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, cubiculo.getCodLocal());
            ps.setInt(2, cubiculo.getCodNivel());
            ps.setString(3, cubiculo.getTipoCubiculo());
            ps.setInt(4, cubiculo.getCapacidad());
            ps.setDouble(5, cubiculo.getPrecioHora());
            ps.setString(6, cubiculo.getEstadoCubiculo());
            ps.setString(7, cubiculo.getDescripcion());
            ps.setInt(8, cubiculo.getCodCubiculo());
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            cerrarRecursos();
        }
    }

    public boolean eliminarCubiculo(int codCubiculo) throws SQLException {
        String sql = "DELETE FROM Cubiculo WHERE CodCubiculo = ?";
        
        try {
            conn = conecta.getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, codCubiculo);
            
            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        } finally {
            cerrarRecursos();
        }
    }

    private void cerrarRecursos() {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}