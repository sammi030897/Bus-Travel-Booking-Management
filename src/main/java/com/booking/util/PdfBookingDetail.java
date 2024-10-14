package com.booking.util;

import com.booking.payload.BookingDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class PdfBookingDetail {

    public static ByteArrayInputStream exportBookingsToPdf(List<BookingDTO> bookings) throws DocumentException {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, out);
        document.open();

        Font font = FontFactory.getFont(FontFactory.COURIER_BOLD, 20);
        Paragraph title = new Paragraph("Booking Details", font);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        PdfPTable table = new PdfPTable(8);
        table.setWidthPercentage(112);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        addTableHeader(table, font);

        for (BookingDTO booking : bookings) {
            addBookingToTable(table, booking);
        }

        document.add(table);
        document.close();

        return new ByteArrayInputStream(out.toByteArray());
    }

    private static void addTableHeader(PdfPTable table, Font font) throws DocumentException {
        String[] headerNames = {"ID", "FirstName", "LastName", "Age", "Gender", "Price    Paid", "Booking  Status", "Type"};
        float[] columnWidths = {0.5f, 1f, 1f, 0.5f, 0.8f, 1f, 1f, 1.1f};
        font.setSize(13);
        table.setWidths(columnWidths);
        for (String header : headerNames) {
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.setPadding(5);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPhrase(new Phrase(header, font));
            table.addCell(cell);
        }
    }

    private static void addBookingToTable(PdfPTable table, BookingDTO booking) {
        table.addCell(String.valueOf(booking.getId()));
        table.addCell(booking.getFirstName());
        table.addCell(booking.getLastName());
        table.addCell(String.valueOf(booking.getAge()));
        table.addCell(booking.getGender());
        table.addCell(String.valueOf(booking.getAmount()));
        table.addCell(booking.getStatus());
        table.addCell(String.valueOf(booking.getType()));
    }
}

