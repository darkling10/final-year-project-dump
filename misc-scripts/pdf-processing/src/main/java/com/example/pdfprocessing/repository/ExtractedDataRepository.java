package com.example.pdfprocessing.repository;

import com.example.pdfprocessing.models.PdfDataModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExtractedDataRepository extends JpaRepository<PdfDataModel,String> {
}
