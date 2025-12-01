package ServLets;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import modelo.dao.CubiculoDAO;
import modelo.dao.ClienteDAO;
import modelo.dao.LocalesDAO;
import modelo.dto.Cubiculo;
import modelo.dto.Cliente;
import modelo.dto.Locales;

@WebServlet(name = "cntCubiculos", urlPatterns = {"/cntCubiculos"})
public class cntCubiculos extends HttpServlet {

    private CubiculoDAO cubiculoDAO;
    private ClienteDAO clienteDAO;
    private LocalesDAO localesDAO;
    
    @Override
    public void init() throws ServletException {
        cubiculoDAO = new CubiculoDAO();
        clienteDAO = new ClienteDAO();
        localesDAO = new LocalesDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        request.setCharacterEncoding("UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        String accion = request.getParameter("accion");
        Gson gson = new Gson();
        PrintWriter out = response.getWriter();
        
        try {
            if (accion == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Acción no especificada\"}");
                return;
            }
            
            switch (accion) {
                case "listarLocales":
                    listarLocales(request, response, gson, out);
                    break;
                case "listarClientes":
                    listarClientes(request, response, gson, out);
                    break;
                case "listar":
                    listarCubiculos(request, response, gson, out);
                    break;
                case "obtener":
                    obtenerCubiculo(request, response, gson, out);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Acción no válida: " + accion + "\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error del servidor: " + e.getMessage() + "\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        String accion = request.getParameter("accion");
        PrintWriter out = response.getWriter();
        
        try {
            if (accion == null) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Acción no especificada\"}");
                return;
            }
            
            switch (accion) {
                case "registrar":
                    registrarCubiculo(request, response, out);
                    break;
                case "actualizar":
                    actualizarCubiculo(request, response, out);
                    break;
                case "eliminar":
                    eliminarCubiculo(request, response, out);
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\":\"Acción no válida: " + accion + "\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error del servidor: " + e.getMessage() + "\"}");
        }
    }

    // ==================== MÉTODOS GET ====================
    
    private void listarLocales(HttpServletRequest request, HttpServletResponse response, 
                               Gson gson, PrintWriter out) {
        try {
            ArrayList<Locales> locales = localesDAO.mostrarLocales();
            String json = gson.toJson(locales);
            out.print(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    private void listarClientes(HttpServletRequest request, HttpServletResponse response,
                                Gson gson, PrintWriter out) {
        try {
            ArrayList<Cliente> clientes = clienteDAO.mostrarClientes();
            String json = gson.toJson(clientes);
            out.print(json);
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

   private void listarCubiculos(HttpServletRequest request, HttpServletResponse response,
                             Gson gson, PrintWriter out) {
    try {
        String codLocalStr = request.getParameter("codLocal"); // << CORREGIDO

        if (codLocalStr == null || codLocalStr.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Código de local no especificado\"}");
            return;
        }

        int codLocal = Integer.parseInt(codLocalStr);
        List<Cubiculo> cubiculos = cubiculoDAO.listarCubiculosPorLocal(codLocal);

        String json = gson.toJson(cubiculos);
        out.print(json);

    } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        out.print("{\"error\":\"Código de local inválido\"}");
    } catch (SQLException e) {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        out.print("{\"error\":\"Error de base de datos: " + e.getMessage() + "\"}");
    } catch (Exception e) {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        out.print("{\"error\":\"" + e.getMessage() + "\"}");
    }
}


    private void obtenerCubiculo(HttpServletRequest request, HttpServletResponse response,
                                 Gson gson, PrintWriter out) {
        try {
            String codCubiculoStr = request.getParameter("codCubiculo");
            if (codCubiculoStr == null || codCubiculoStr.isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Código de cubículo no especificado\"}");
                return;
            }
            
            int codCubiculo = Integer.parseInt(codCubiculoStr);
            Cubiculo cubiculo = cubiculoDAO.obtenerCubiculo(codCubiculo);
            
            if (cubiculo == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print("{\"error\":\"Cubículo no encontrado\"}");
            } else {
                String json = gson.toJson(cubiculo);
                out.print(json);
            }
        } catch (NumberFormatException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"error\":\"Código de cubículo inválido\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"Error de base de datos: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // ==================== MÉTODOS POST ====================
    
    private void registrarCubiculo(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            // Obtener parámetros
            String codLocalStr = request.getParameter("codLocal");
            String codNivelStr = request.getParameter("codNivel");
            String tipoCubiculo = request.getParameter("tipoCubiculo");
            String capacidadStr = request.getParameter("capacidad");
            String precioHoraStr = request.getParameter("precioHora");
            String estadoCubiculo = request.getParameter("estadoCubiculo");
            String descripcion = request.getParameter("descripcion");
            
            // Log para debug
            System.out.println("=== REGISTRAR CUBICULO - DATOS RECIBIDOS ===");
            System.out.println("codLocal: " + codLocalStr);
            System.out.println("codNivel: " + codNivelStr);
            System.out.println("tipoCubiculo: " + tipoCubiculo);
            System.out.println("capacidad: " + capacidadStr);
            System.out.println("precioHora: " + precioHoraStr);
            System.out.println("estadoCubiculo: " + estadoCubiculo);
            System.out.println("descripcion: " + descripcion);
            
            // Validaciones
            if (codLocalStr == null || codLocalStr.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\":false,\"message\":\"Debe seleccionar un local\"}");
                return;
            }
            
            if (codNivelStr == null || codNivelStr.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\":false,\"message\":\"Debe seleccionar un nivel\"}");
                return;
            }
            
            if (tipoCubiculo == null || tipoCubiculo.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\":false,\"message\":\"Debe seleccionar un tipo de cubículo\"}");
                return;
            }
            
            if (capacidadStr == null || capacidadStr.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\":false,\"message\":\"Debe ingresar la capacidad\"}");
                return;
            }
            
            if (precioHoraStr == null || precioHoraStr.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\":false,\"message\":\"Debe ingresar el precio por hora\"}");
                return;
            }
            
            if (estadoCubiculo == null || estadoCubiculo.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\":false,\"message\":\"Debe seleccionar un estado\"}");
                return;
            }
            
            // Crear objeto
            Cubiculo cubiculo = new Cubiculo();
            cubiculo.setCodLocal(Integer.parseInt(codLocalStr));
            cubiculo.setCodNivel(Integer.parseInt(codNivelStr));
            cubiculo.setTipoCubiculo(tipoCubiculo);
            cubiculo.setCapacidad(Integer.parseInt(capacidadStr));
            cubiculo.setPrecioHora(Double.parseDouble(precioHoraStr));
            cubiculo.setEstadoCubiculo(estadoCubiculo);
            cubiculo.setDescripcion(descripcion != null ? descripcion : "");
            
            System.out.println("=== OBJETO CUBICULO CREADO ===");
            System.out.println(cubiculo.toString());
            
            // Guardar en BD
            boolean exito = cubiculoDAO.registrarCubiculo(cubiculo);
            
            if (exito) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"success\":true,\"message\":\"Cubículo registrado correctamente\"}");
                System.out.println("=== CUBICULO REGISTRADO EXITOSAMENTE ===");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"success\":false,\"message\":\"No se pudo registrar el cubículo en la base de datos\"}");
                System.err.println("=== ERROR: No se insertó el cubículo ===");
            }
            
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("{\"success\":false,\"message\":\"Error en formato de números: " + e.getMessage() + "\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\":false,\"message\":\"Error de base de datos: " + e.getMessage() + "\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\":false,\"message\":\"Error inesperado: " + e.getMessage() + "\"}");
        }
    }

    private void actualizarCubiculo(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            String codCubiculoStr = request.getParameter("codCubiculo");
            
            if (codCubiculoStr == null || codCubiculoStr.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\":false,\"message\":\"Código de cubículo no especificado\"}");
                return;
            }
            
            Cubiculo cubiculo = new Cubiculo();
            cubiculo.setCodCubiculo(Integer.parseInt(codCubiculoStr));
            cubiculo.setCodLocal(Integer.parseInt(request.getParameter("codLocal")));
            cubiculo.setCodNivel(Integer.parseInt(request.getParameter("codNivel")));
            cubiculo.setTipoCubiculo(request.getParameter("tipoCubiculo"));
            cubiculo.setCapacidad(Integer.parseInt(request.getParameter("capacidad")));
            cubiculo.setPrecioHora(Double.parseDouble(request.getParameter("precioHora")));
            cubiculo.setEstadoCubiculo(request.getParameter("estadoCubiculo"));
            cubiculo.setDescripcion(request.getParameter("descripcion"));
            
            boolean exito = cubiculoDAO.actualizarCubiculo(cubiculo);
            
            if (exito) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"success\":true,\"message\":\"Cubículo actualizado correctamente\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"success\":false,\"message\":\"No se pudo actualizar el cubículo\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\":false,\"message\":\"Error: " + e.getMessage() + "\"}");
        }
    }

    private void eliminarCubiculo(HttpServletRequest request, HttpServletResponse response, PrintWriter out) {
        try {
            String codCubiculoStr = request.getParameter("codCubiculo");
            
            if (codCubiculoStr == null || codCubiculoStr.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"success\":false,\"message\":\"Código de cubículo no especificado\"}");
                return;
            }
            
            int codCubiculo = Integer.parseInt(codCubiculoStr);
            boolean exito = cubiculoDAO.eliminarCubiculo(codCubiculo);
            
            if (exito) {
                response.setStatus(HttpServletResponse.SC_OK);
                out.print("{\"success\":true,\"message\":\"Cubículo eliminado correctamente\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                out.print("{\"success\":false,\"message\":\"No se pudo eliminar el cubículo\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.print("{\"success\":false,\"message\":\"Error: " + e.getMessage() + "\"}");
        }
    }

    @Override
    public String getServletInfo() {
        return "Servlet para gestión de cubículos";
    }
}