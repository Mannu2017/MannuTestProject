package tst1;

import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class Barcode2 {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new Barcode2();
			}
		});
	}

	
	public Barcode2() {
		try {
			PDDocument readdoc=PDDocument.load(new File("C:\\Users\\Mannu\\Desktop\\New folder (2)\\Gray Split\\400759351_400759400 GRAYSCALE.pdf"));
			PDFRenderer pdfRenderer = new PDFRenderer(readdoc);
			String newcode="";
			PDDocument writepdf=null;
			PDPage pdpage =null;
			int s=-1;
			for (int page = 0; page < readdoc.getNumberOfPages(); ++page)
			{ 
				BufferedImage bim = pdfRenderer.renderImageWithDPI(page, 100, ImageType.RGB);
				String decodeQRCode=decodeQRCode(newcode,pdfRenderer,page);
				if(!decodeQRCode.equals("")) {
					newcode=decodeQRCode;
				}
				//ImageIO.write(bim, "png", new File("C:\\temp2\\"+page+".png"));
			}		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private String decodeQRCode(String newcode, PDFRenderer pdfRenderer, int page) throws IOException {
		BufferedImage codebuf = pdfRenderer.renderImageWithDPI(page, 150, ImageType.RGB);
		BufferedImage bi=codebuf.getSubimage(codebuf.getWidth()/2-150, 20, 600 , 300);
		ImageIO.write(bi, "png", new File("C:\\temp2\\"+page+".png"));
		LuminanceSource source = new BufferedImageLuminanceSource(bi);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            System.out.println("Code: "+result.getText());
            return result.getText();
        } catch (NotFoundException e) {
            return "";
        }
    }
	
}
