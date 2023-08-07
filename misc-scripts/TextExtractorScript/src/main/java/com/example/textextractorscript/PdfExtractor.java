package com.example.textextractorscript;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class PdfExtractor {
    private static HttpRequest getHttpRequest(String uri) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
        return request;
    }


    private static HttpResponse<InputStream> getResponse(HttpRequest request) throws IOException, InterruptedException {
        return HttpClient
                .newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofInputStream());
    }

    private static void downloadPDF(String pdfFile) throws IOException, InterruptedException {
        HttpRequest request = getHttpRequest(pdfFile);
        HttpResponse<InputStream> response = getResponse(request);

        System.out.println("status code: " + response.statusCode());
        InputStream body = response.body();

        WriteLocal(body, "sample.pdf");
    }

    private static void WriteLocal(InputStream body, String destinationFIle) throws IOException {
        FileOutputStream fos = new FileOutputStream(destinationFIle);
        fos.write(body.readAllBytes());
        fos.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // Replace "path/to/your/pdf/file.pdf" with the actual path to your PDF file
        File file = new File("C:\\Final Year Project\\final-year-project-dump\\misc-scripts\\TextExtractorScript\\sample.pdf");

        downloadPDF("https://agritech.tnau.ac.in/horticulture/extraction_techniques%20_medicinal_plants.pdf");
        try (InputStream inputStream = new FileInputStream(file)) {
            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();

            Metadata metadata = new Metadata();
            ParseContext pcontext = new ParseContext();

            parser.parse(inputStream, handler, metadata, pcontext);

            String textContent = handler.toString();
            System.out.println("Extracted text from PDF:");
            System.out.println(textContent);

            StoreInDatabase.storeTextInDatabase("Plant Data",textContent);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
