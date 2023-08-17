package com.example.pdfprocessing.requestSchemas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PdfArrayJson {
    private String pdfName;
    private String downloadURL;
    private String description;
}
