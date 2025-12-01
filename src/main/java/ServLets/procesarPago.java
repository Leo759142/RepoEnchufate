package ServLets;

import Email.EmailSender;
import Email.PDFGenerator;
import com.itextpdf.text.DocumentException;
import modelo.dao.VentaDAO;
import modelo.dto.Customer;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.file.Paths;

public class procesarPago extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // ==========================
        //  1. DATOS DEL CLIENTE
        // ==========================
        String email = customer.getCorreo();
        String customerName = customer.getNombre();
    int codCliente = customer.getCodcliente();

        int codEmpleado = 0; // O el que corresponda

        // ==========================
        //  2. DATOS DE TARJETA
        // ==========================
        String cardNumber = request.getParameter("card-number");
        String cardName = request.getParameter("card-name");
        String cardExpiry = request.getParameter("card-expiry");
        String cardCVV = request.getParameter("card-cvv");

        // ==========================
        //  3. DATOS DE COMPRA
        // ==========================
        int codProducto = Integer.parseInt(request.getParameter("codProducto"));
        int cantidad = Integer.parseInt(request.getParameter("cantidad"));
        String tipoPago = "Tarjeta"; // si quieres otro tipo, cámbialo

        VentaDAO ventaDAO = new VentaDAO();

        // ==========================
        //  4. REGISTRAR COMPRA
        // ==========================
        int codCompra = ventaDAO.registrarCompra(codCliente, codEmpleado);

        if (codCompra == -1) {
            System.out.println("❌ Error registrando compra");
            response.sendRedirect("errorPago.jsp");
            return;
        }

        System.out.println("✔ Compra registrada con ID: " + codCompra);

        // ==========================
        //  5. REGISTRAR DETALLE
        // ==========================
        ventaDAO.registrarDetalle(codCompra, codProducto, tipoPago, cantidad);

        System.out.println("✔ Detalle registrado para compra: " + codCompra);

        // ==========================
        //  6. GENERAR PDF
        // ==========================
        String pdfFilePath = Paths.get(System.getProperty("java.io.tmpdir"), "factura.pdf").toString();

        try {
            PDFGenerator.generateInvoice(
                    pdfFilePath,
                    customerName,
                    "Compra del producto #" + codProducto + " - Cantidad: " + cantidad
            );
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        // ==========================
        //  7. ENVIAR EMAIL
        // ==========================
        String subject = "Factura de tu compra - CodCompra #" + codCompra;
        String content = "Estimado/a " + customerName + ",\n\nGracias por su compra.\n" +
                "Adjuntamos su comprobante.\n\nSaludos.";

        try {
            EmailSender.sendEmail(email, subject, content);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ==========================
        //  8. REDIRECCIÓN FINAL
        // ==========================
        response.sendRedirect("procesarPago.jsp?status=ok&codCompra=" + codCompra);
    }

    @Override
    public String getServletInfo() {
        return "Servlet para procesar pagos y registrar compras";
    }
}
