package com.example.travelwing.travelwing.Controller;


import com.example.travelwing.travelwing.Domain.Booking;
import com.example.travelwing.travelwing.Domain.User;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PdfService {


    public byte[] generateBookingPdf(Booking booking) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Booking Confirmation");
                contentStream.endText();

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Name: " + booking.getUser().getFirstname() + " " + booking.getUser().getLastname());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Email: " + booking.getUser().getEmail());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Departure: " + booking.getRoute().getDepartureLocation());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Destination: " + booking.getRoute().getDestinationLocation());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Booking Date: " + booking.getBookingDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                if (booking.getBookingEndDate() != null) {
                    contentStream.newLineAtOffset(0, -15);
                    contentStream.showText("End Date: " + booking.getBookingEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Status: " + booking.getBookingStatus());
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Total Cost: $" + String.format("%.2f", booking.getTotalCost()));
                contentStream.endText();
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream.toByteArray();
        }
    }

}
