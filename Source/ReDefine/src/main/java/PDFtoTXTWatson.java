import com.ibm.watson.developer_cloud.document_conversion.v1.DocumentConversion;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public class PDFtoTXTWatson {
   public static void main(String[] args) throws IOException {
        DocumentConversion service = new DocumentConversion(DocumentConversion.VERSION_DATE_2015_12_01);
        service.setUsernameAndPassword("26008258-bc7c-4639-bfbe-e04cfe9eeb40", "jYpdOjxBEjmu");
        File pdf = new File("sparkpaper.pdf");
        System.out.println("Converting pdf document to Text");
        String normalizedtext = service.convertDocumentToText(pdf).execute();
        System.out.println(normalizedtext);
        BufferedWriter output = null;
        try {
            File file = new File("paper1.txt");
            output = new BufferedWriter(new FileWriter(file));
            output.write(normalizedtext);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }
}