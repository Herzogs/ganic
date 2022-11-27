package ar.edu.unlam.tallerweb1.domain.factura;

import ar.edu.unlam.tallerweb1.domain.MercadoPago.MpEntidad;
import ar.edu.unlam.tallerweb1.domain.MercadoPago.Pago;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Service("servicioFactura")
public class ServicioFactura {

    public String generarFactura(Pago pagoRealizado) throws DocumentException, IOException {
        Document document = new Document();
        /*Image imagen = Image.getInstance("src//main//resources//images//ganiclogo.png");*/
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK);
        String nombreDocumento = ".//" + pagoRealizado.hashCode() + "_factura.pdf";
        PdfWriter.getInstance(document, new FileOutputStream(nombreDocumento));
        document.open();
        /*imagen.scaleAbsolute(170f, 150f);
        imagen.setAbsolutePosition(0f, 670f);
        */
        Paragraph parrafo1 = new Paragraph("SANDWICHERIA GANIC S.R.L\n" +
                "CUIT: 11456724\n" +
                "Teléfono: 261-4978344\n" +
                "Dirección: Malvinas Argentinas 445\n" +
                "Fecha: " + LocalDateTime.now(ZoneId.of("America/Buenos_Aires")) + "\n" +
                "Factura #" + pagoRealizado.hashCode() + "\n", font);
        parrafo1.setAlignment(Element.ALIGN_RIGHT);
        Chunk tituloTabla = new Chunk("DETALLE DE SU COMPRA", font);
        PdfPTable table = new PdfPTable(3);
        this.addTableHeader(table);
        this.addRows(table, pagoRealizado.getListaCobrar());
        Paragraph parrafo2 = new Paragraph("Total: $" + this.calcularTotal(pagoRealizado.getListaCobrar()) + "\n" +
                "Gracias por su compra!", font);
        /*document.add(imagen);*/
        document.add(parrafo1);
        document.add(tituloTabla);
        document.add(table);
        document.add(parrafo2);
        document.close();
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
            table.addCell(mpEntidad.getSandwich().getDescripcion());
            table.addCell(String.valueOf(mpEntidad.calcularMonto()));
        });
    }

    private double calcularTotal(java.util.List<MpEntidad> listaCobrar) {
        AtomicReference<Float> total = new AtomicReference<>(0F);
        listaCobrar.forEach(mpEntidad -> total.updateAndGet(v -> v + mpEntidad.calcularMonto()));
        return total.get();
    }
}
