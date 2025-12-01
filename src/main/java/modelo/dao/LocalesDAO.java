package modelo.dao;

import conexion.ConectaBD;
import modelo.dto.Locales;
import java.sql.*;
import java.util.ArrayList;

public class LocalesDAO {
    
    private Connection conn = null;
    private PreparedStatement ps = null;
    private ResultSet rs = null;
    private ConectaBD conecta = new ConectaBD();

    public ArrayList<Locales> getList() {
        ArrayList<Locales> lista = new ArrayList<>();
        String sql = "SELECT CodLocal, Nombre, Direccion, Telefono FROM Locales ORDER BY Nombre";
        
        try {
            conn = conecta.getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while (rs.next()) {
                Locales local = new Locales();
                local.setCodLocal(rs.getInt("CodLocal"));
                local.setNombre(rs.getString("Nombre"));
                local.setDireccion(rs.getString("Direccion"));
                local.setTelefono(rs.getInt("Telefono"));
                lista.add(local);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarRecursos();
        }
        
        return lista;
    }

    public ArrayList<Locales> mostrarLocales() {
        return getList();
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