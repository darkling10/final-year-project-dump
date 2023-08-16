package com.example.pdfprocessing.service;

import com.example.pdfprocessing.GlobalVariables;
import com.example.pdfprocessing.requestSchemas.PdfArrayJson;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Service
public class PDFservices {

    private static HttpRequest getHttpRequest(String uri) {
        return HttpRequest.newBuilder()
                .uri(URI.create(uri))
                .GET()
                .build();
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

    public void downloadPDFService(List<PdfArrayJson> pdfArrayJson) throws IOException, InterruptedException {
        for (PdfArrayJson pdfArrayJson1: pdfArrayJson){
            String pdfName = pdfArrayJson1.getPdfName();
            String downloadURL = pdfArrayJson1.getDownloadURL();
            String description = pdfArrayJson1.getDescription();
            String filePath = GlobalVariables.filePathPDF+pdfName+".pdf";

            downloadPDF(downloadURL,filePath);
        }
    }
}
