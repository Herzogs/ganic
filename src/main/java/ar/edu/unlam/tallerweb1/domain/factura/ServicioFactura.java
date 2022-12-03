package ar.edu.unlam.tallerweb1.domain.factura;

import ar.edu.unlam.tallerweb1.domain.Excepciones.UsuarioInvalidoException;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.MpEntidad;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.Pago;
import ar.edu.unlam.tallerweb1.domain.usuarios.ServicioLogin;
import ar.edu.unlam.tallerweb1.domain.usuarios.Usuario;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service("servicioFactura")
public class ServicioFactura {

    @Autowired
    private ServicioLogin login;

    public String generarFactura(Pago pagoRealizado, HttpServletRequest request) {
        Document document = new Document();
        LineSeparator ls = new LineSeparator();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 10, BaseColor.BLACK);
        String nombreDocumento = ".//" + pagoRealizado.hashCode() + "_factura.pdf";
        Usuario user = null;
        try {
            user = this.login.consultarPorID((Long) request.getSession().getAttribute("id"));
            PdfWriter.getInstance(document, new FileOutputStream(nombreDocumento));
            document.open();
            Paragraph parrafo1 = new Paragraph("SANDWICHERIA GANIC S.R.L\n" +
                    "CUIT: 11456724\n" +
                    "Teléfono: (011) 4480-8900\n" +
                    "Dirección: Florencio Varela 1903\n" +
                    "Fecha: " + LocalDateTime.now(ZoneId.of("America/Buenos_Aires")).format(DateTimeFormatter.ofPattern("dd/MM/yyyy - hh:mm")) + "\n" +
                    "Factura #" + pagoRealizado.hashCode() + "\n\n", font);
            parrafo1.setAlignment(Element.ALIGN_RIGHT);
            Paragraph parrafoCliente = new Paragraph("Nombre y Apellido: " + user.getNombre().concat(user.getPassword()) + "\n"
                    + "Direccion: "+ (String) request.getSession().getAttribute("DESTINO") + "\n", font);
            parrafoCliente.setAlignment(Element.ALIGN_LEFT);

            Chunk tituloTabla = new Chunk("\n\n\nDETALLE DE SU COMPRA", font);
            PdfPTable table = new PdfPTable(3);
            this.addTableHeader(table);
            this.addRows(table, pagoRealizado.getListaCobrar());
            Float rec = (Float)request.getSession().getAttribute("RECARGO");
            double impTot = this.calcularTotal(pagoRealizado.getListaCobrar()) + rec ;
            Paragraph parrafo2 = new Paragraph("\n\n\nTotal: $" +this.calcularTotal(pagoRealizado.getListaCobrar())
                    + "\nRecargo: $" + rec
                    + "\nTotal (+ recargo) : $" + impTot
                    + "\nGracias por su compra!", font);

            document.add(parrafo1);
            document.add(ls);
            document.add(parrafoCliente);
            document.add(tituloTabla);
            document.add(table);
            document.add(parrafo2);
            document.close();
        } catch (DocumentException | FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (UsuarioInvalidoException e) {
            System.err.println("No Existe el usuario");
        }
        return nombreDocumento;
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Cantidad", "Artículo", "SubTotal").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(1);
            header.setPhrase(new Phrase(columnTitle));
            table.addCell(header);
        });
    }

    private void addRows(PdfPTable table, java.util.List<MpEntidad> listaCobrar) {
        listaCobrar.forEach(mpEntidad -> {
            table.addCell(String.valueOf(mpEntidad.getCantidad()));
            table.addCell(mpEntidad.getSandwich().getNombre());
            table.addCell(String.valueOf(mpEntidad.getSandwich().obtenerMonto()));
        });
    }

    private double calcularTotal(java.util.List<MpEntidad> listaCobrar) {
        AtomicReference<Float> total = new AtomicReference<>(0F);
        listaCobrar.forEach(mpEntidad -> total.updateAndGet(v -> v + mpEntidad.calcularMonto()));
        return total.get();
    }
}
