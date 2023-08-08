package com.example.textextractorscript;

import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Utilities {
    public static void writeDataInCsv(String filePath,String DbName,String contentOFPdf) throws IOException {

        File file = new File(filePath);
        file.createNewFile();
        try {
            FileWriter outputfile = new FileWriter(file);

            CSVWriter writer = new CSVWriter(outputfile);

            List<String[]> data = new ArrayList<String[]>();
            data.add(new String[] { "Name of File", "Text" });
            data.add(new String[] { DbName, contentOFPdf });
            writer.writeAll(data);

            writer.close();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
