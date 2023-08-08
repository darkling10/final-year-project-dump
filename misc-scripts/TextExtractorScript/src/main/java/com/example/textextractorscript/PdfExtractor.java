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

    private static void downloadPDF(String pdfFile,String FileName) throws IOException, InterruptedException {
        HttpRequest request = getHttpRequest(pdfFile);
        HttpResponse<InputStream> response = getResponse(request);

        System.out.println("status code: " + response.statusCode());
        InputStream body = response.body();

        WriteLocal(body, FileName);
    }

    private static void WriteLocal(InputStream body, String destinationFIle) throws IOException {
        FileOutputStream fos = new FileOutputStream(destinationFIle);

//        body.close();
        fos.write(body.readAllBytes());
        fos.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        // Replace "path/to/your/pdf/file.pdf" with the actual path to your PDF file

        String DbName = "MIT_physics_rel";
        String filePathPDF = "./PDF/";
        String filePathCSV = "./CSV/";

        String FilePathPdfRel = filePathPDF+DbName+".pdf";
        String FilePathCSvRel = filePathCSV+DbName+".csv";

        System.out.println(FilePathPdfRel);

        String FilePathCsv = String.format("C:\\Final Year Project\\final-year-project-dump\\misc-scripts\\TextExtractorScript\\CSV\\%s",DbName+".csv");

        downloadPDF("http://catalog.mit.edu/schools/science/physics/physics.pdf",FilePathPdfRel);
        File file = new File(FilePathPdfRel);
        try (InputStream inputStream = new FileInputStream(file)) {
            //Increasing TIKA limit to 10000+
            StringWriter any = new StringWriter();

            Parser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler(any);

            Metadata metadata = new Metadata();
            ParseContext pcontext = new ParseContext();

            parser.parse(inputStream, handler, metadata, pcontext);

            String textContent = handler.toString();
            System.out.println("Extracted text from PDF:");
            String oneLine = textContent.replace("\n", "");
            String noComma = oneLine.replace(","," ");
//            System.out.println(oneLine);

            System.out.println("Length of the data is " + textContent.length());
            System.out.println("One Line of the data is " + oneLine.length());
            System.out.println("No comma Line of the data is " + noComma.length());

            StoreInDatabase.storeTextInDatabase(DbName,noComma);
            Utilities.writeDataInCsv(FilePathCSvRel,DbName,noComma);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
