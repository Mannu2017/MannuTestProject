package tst1;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class Barcode {

	public static void main(String[] args) throws IOException {
		PDDocument doc = null;
		doc = new PDDocument();
		PDPage page =null;
		try{
		    BufferedImage awtImage = ImageIO.read( new File( "D:\\desine\\185882329.jpg" ) );
		    page = new PDPage(new PDRectangle( awtImage.getWidth(), awtImage.getHeight() ));
		    doc.addPage(page);
		    PDImageXObject  pdImageXObject = LosslessFactory.createFromImage(doc, awtImage);
		    PDPageContentStream contentStream = new PDPageContentStream(doc, page, true, false);
		    
		    contentStream.drawImage(pdImageXObject, 0, 0, awtImage.getWidth(), awtImage.getHeight());
		    contentStream.close();
		    doc.save( "c://temp2//PDF_image1.pdf" );
		    doc.close();
		} catch (Exception io){
		    System.out.println(" -- fail --" + io);
		}

	}

}
