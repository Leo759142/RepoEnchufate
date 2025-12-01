package modelo.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.dto.Venta;
import conexion.ConectaBD;

public class VentaDAO {

  
    //  OBTENER VENTAS (REP)
  
    public List<Venta> obtenerVentas(String fechaInicio, String fechaFin) {
        List<Venta> ventas = new ArrayList<>();

        String sql = "SELECT Compra.Fecha, SUM(DetalleCompra.Cantidad) AS Total "
                   + "FROM DetalleCompra "
                   + "INNER JOIN Compra ON DetalleCompra.CodCompra = Compra.CodCompra "
                   + "WHERE Compra.Fecha BETWEEN ? AND ? "
                   + "GROUP BY Compra.Fecha";

        try (Connection cnx = new ConectaBD().getConnection();
             PreparedStatement pst = cnx.prepareStatement(sql)) {

            pst.setString(1, fechaInicio);
            pst.setString(2, fechaFin);

            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    Venta venta = new Venta();
                    venta.setFecha(rs.getDate("Fecha"));
                    venta.setTotal(rs.getInt("Total"));
                    ventas.add(venta);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventas;
    }

 
    //   pasarela pago
    
  public int registrarCompra(int codCliente, int codEmpleado) {
    String sql = "INSERT INTO Compra (CodCliente, CodEmpleado, Fecha, Hora) VALUES (?, ?, CURDATE(), CURTIME())";
    try (Connection cnx = new ConectaBD().getConnection();
         PreparedStatement pst = cnx.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

        pst.setInt(1, codCliente);
        pst.setInt(2, codEmpleado);

        pst.executeUpdate();
        try (ResultSet rs = pst.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getInt(1); // devuelve CodCompra generado
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}
public void registrarDetalle(int codCompra, int codProducto, String tipoPago, int cantidad) {
    String sql = "INSERT INTO DetalleCompra (CodCompra, CodProducto, TipoPago, Cantidad) VALUES (?, ?, ?, ?)";
    try (Connection cnx = new ConectaBD().getConnection();
         PreparedStatement pst = cnx.prepareStatement(sql)) {

        pst.setInt(1, codCompra);
        pst.setInt(2, codProducto);
        pst.setString(3, tipoPago);
        pst.setInt(4, cantidad);

        pst.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

  
}
