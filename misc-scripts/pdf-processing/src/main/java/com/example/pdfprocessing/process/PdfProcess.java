package com.example.pdfprocessing.process;

import com.example.pdfprocessing.requestSchemas.PdfArrayJson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/process/pdf")
public class PdfProcess {
    @GetMapping("/get")
    public ResponseEntity<Object> getCheck(){
        return ResponseEntity.status(200).body("Hello from proccess API");
    }

    @GetMapping("/download/json")
    public ResponseEntity<Object> downloadPDF(@RequestBody List<PdfArrayJson> pdfArrayJson){
        return ResponseEntity.status(200).body(pdfArrayJson);
    }
}
