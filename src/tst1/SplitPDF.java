package tst1;

import java.io.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
public class SplitPDF {  
     public static void main(String[] args){
        try {
          PdfReader Split_PDF_Document = new PdfReader("C:\\\\Users\\\\Mannu\\\\Desktop\\\\New folder (2)\\\\Gray Split\\\\400759351_400759400 GRAYSCALE.pdf");
          Document document; 
          PdfCopy copy;           
          int number_of_pages = Split_PDF_Document.getNumberOfPages();
          for (int i = 0; i < number_of_pages; ) {
                  document = new Document();
                  String FileName="File"+ ++i+".pdf";                     
                  copy = new PdfCopy(document,new FileOutputStream("C:\\temp2\\"+FileName));
                  document.open();                
                  copy.addPage(copy.getImportedPage(Split_PDF_Document, i));              
                  document.close();
                    }
        }
        catch (Exception i)
        {
            System.out.println(i);
        }
    }
}
