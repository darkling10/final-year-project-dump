package com.example.pdfprocessing.requestSchemas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PdfArrayJson {
    private String name;
    private String pdfLink;
    private String description;
}
