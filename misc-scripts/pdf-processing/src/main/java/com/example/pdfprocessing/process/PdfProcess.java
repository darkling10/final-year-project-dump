package com.example.pdfprocessing.process;

import com.example.pdfprocessing.requestSchemas.PdfArrayJson;
import com.example.pdfprocessing.service.PDFservices;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/process/pdf")
@AllArgsConstructor
public class PdfProcess {

    private final PDFservices pdFservices;

    @GetMapping("/get")
    public ResponseEntity<Object> getCheck(){
        return ResponseEntity.status(200).body("Hello from proccess API");
    }

    @PostMapping("/download/json")
    public ResponseEntity<Object> downloadPDF(@RequestBody List<PdfArrayJson> pdfArrayJson) throws IOException, InterruptedException {
        pdFservices.downloadPDFService(pdfArrayJson);
        return ResponseEntity.status(201).body(pdfArrayJson);
    }
}
