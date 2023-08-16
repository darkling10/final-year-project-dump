package com.example.pdfprocessing.service;

import com.example.pdfprocessing.models.PdfDataModel;
import com.example.pdfprocessing.repository.ExtractedDataRepository;
import com.example.pdfprocessing.requestSchemas.PdfArrayJson;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;

@Data
@RequiredArgsConstructor
@Service
public class ExtractedDataService {

    private final ExtractedDataRepository extractedDataRepository;

    public void saveInDatabase(PdfArrayJson pdfArrayJson,String file){
        PdfDataModel pdfDataModel;

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
            pdfDataModel = PdfDataModel.builder()
                    .subName(pdfArrayJson.getPdfName())
                    .description(pdfArrayJson.getDescription())
                    .dataFile(handler.toString().getBytes())
                    .build();

            extractedDataRepository.save(pdfDataModel);
        } catch (Exception e) {
            e.printStackTrace();
        }




    }
}
