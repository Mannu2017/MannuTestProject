package tst1;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PRIndirectReference;
import com.itextpdf.text.pdf.PdfAConformanceLevel;
import com.itextpdf.text.pdf.PdfAWriter;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfString;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfToPDF1A {
	public static void main(String[] args) {
        // TODO Auto-generated method stub
        convert();
    }
 
     
    public static void convert() {
    	try {
    		PdfReader reader = new PdfReader("C:\\Users\\Mannu\\Desktop\\New folder (2)\\Split PDF\\400759351_400759400 BLACK AND WHITE\\0_060019700522051.pdf");
			Document document=new Document();
			PdfAWriter writer=PdfAWriter.getInstance(document,
					new FileOutputStream("C:\\temp2\\sample.pdf"), PdfAConformanceLevel.PDF_A_1A);
			document.addAuthor("Author");
			document.addSubject("Karvy-Pan");
			document.addLanguage("nl-nl");
	        document.addCreationDate();
	        document.addCreator("Karvy-Mannu");
	        writer.setTagged();
	        writer.createXmpMetadata();
	       document.open();
	       PdfContentByte cb=writer.getDirectContent();
			int pageCount = reader.getNumberOfPages();
			for (int i = 0; i < pageCount; i++) {
				PdfImportedPage p = cb.getPdfWriter().getImportedPage(reader, (i+1));
				Image img=Image.getInstance(p);
				img.setAccessibleAttribute(PdfName.ALT, new PdfString("Logo"));
				document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
				document.setMargins(0, 0, 0, 0);
				document.newPage();
				document.add(img);
            }
			
			
			document.close();
			writer.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
      
    }
  }