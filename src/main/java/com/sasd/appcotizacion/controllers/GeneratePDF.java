package com.sasd.appcotizacion.controllers;

import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class GeneratePDF {

    public static void createPDF(String id, String products, String clientNameTex, String date, String clientIDText, String clientPhone, String total){
        Document doc = new Document();
        try{
            String noSpacesInName = clientNameTex.replace(" ", "_");
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream("AppCotizaciones/" + noSpacesInName + date +".pdf"));

            doc.open();

            Font font = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new Color(255, 155, 80));
            Font blackFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, Color.BLACK);

            Phrase brand = new Phrase("Cotizaciones\n", FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, new Color(255, 155, 80)));
            Phrase phoneNumber = new Phrase("Telefono:    ", font);
            phoneNumber.add(new Chunk("3117017053\n", blackFont));
            Phrase quotationID = new Phrase("Cotizacion ID:    ", font);
            quotationID.add(new Chunk(id + "\n", blackFont));
            Phrase datePhrase = new Phrase("Fecha:    ", font);
            datePhrase.add(new Chunk(date + "\n", blackFont));

            Chunk chunk = new Chunk("Cliente\n", FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, new Color(255, 255, 255)));
            chunk.setBackground(new Color(255, 155, 80), 3, 5,200,5);

            Phrase clientID = new Phrase("ID:   ", font);
            clientID.add(new Chunk(clientIDText + "\n", blackFont));
            Phrase clientName = new Phrase("Nombre:    ", font);
            clientName.add(new Chunk(clientNameTex + "\n", blackFont));
            Phrase clientPhoneNumber = new Phrase("Telefono:    ", font);
            clientPhoneNumber.add(new Chunk(clientPhone, blackFont));

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.getDefaultCell().setHorizontalAlignment(PdfPTable.ALIGN_CENTER);
            table.getDefaultCell().setPadding(5);
            table.getDefaultCell().setBorderWidth(2);

            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, Font.BOLD, Color.BLACK);

            table.addCell("PRODUCTO");
            table.addCell("VARIANTE");
            table.addCell("PRECIO");
            table.addCell("CANTIDAD");
            table.addCell("TOTAL");

            table.setHeaderRows(1);

            for(String p : products.split(";")){
                for (String j : p.split(" ")){
                    table.addCell(j);
                }
            }

            doc.add(brand);
            doc.add(phoneNumber);
            doc.add(quotationID);
            doc.add(chunk);
            doc.add(clientID);
            doc.add(clientName);
            doc.add(clientPhoneNumber);
            doc.add(table);

        }catch (DocumentException | IOException e){
            throw new RuntimeException(e);
        }
        doc.close();
    }

}
