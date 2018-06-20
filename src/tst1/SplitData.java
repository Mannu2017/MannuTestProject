package tst1;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class SplitData extends Thread{
	private ArrayList<String> totfile=new ArrayList<String>();
	private JLabel stalbl,runlbl;
	private String orgpat;
	private Connection con;

	public SplitData(ArrayList<String> totfile, JLabel stalbl, JLabel runlbl,String orgpat) {
		//this.con=DbCon.DbCon();
		this.totfile=totfile;
		this.runlbl=runlbl;
		this.stalbl=stalbl;
		this.orgpat=orgpat;
	}
		
	
	public void run() {
		for(String ss:totfile) {
			System.out.println("File: "+ss);
			File fi=new File(ss);
			String[] fn=fi.getName().split("\\.");
			File newfpat=new File(orgpat+"/"+fn[0]);
			if(!newfpat.exists()) {
				newfpat.mkdir();
			}
			try {
				PDDocument doc=PDDocument.load(new File(ss));
				PdfWriter writer;
				PDFRenderer read=new PDFRenderer(doc);
				String newcode="";
				int s=0;
				Document document = null;
				for(int i=0; i<doc.getNumberOfPages(); i++) {
					BufferedImage img=read.renderImageWithDPI(i, 150,ImageType.BINARY);
					String decodeQRCode=decodeQRCode(newcode,read,i);
					if(!decodeQRCode.equals("")) {
						newcode=decodeQRCode;
					}
					
					System.out.println("Page Size: "+img.getWidth()+"^"+img.getHeight());
					
					if(newcode!="") {
						stalbl.setText(fn[0]+"^"+newcode);
						runlbl.setText("Current: "+(s+1));
						if(!decodeQRCode.equals(newcode)) {
							s=1+s;
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
					        ImageIO.write(img, "png", baos);
							document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
							document.setMargins(0, 0, 0, 0);
							document.newPage();
							document.add(Image.getInstance(baos.toByteArray()));
						} else {
							if(s!=0) {
								document.close();
							}
							s=0;
							String inw="0";
//							try {
//								if(con.isClosed()) {
//									con=DbCon.DbCon();
//								}
//								PreparedStatement ps=con.prepareStatement("select distinct InwardNo from Inward where Ackno='"+decodeQRCode+"'");
//								ResultSet rs=ps.executeQuery();
//								if(rs.next()) {
//									inw=rs.getString(1);
//								}
//								ps.close();
//								rs.close();
//							} catch (Exception e) {
//								e.printStackTrace();
//							}
							
							document=new Document();
							writer = PdfWriter.getInstance(document, new FileOutputStream(newfpat+"\\"+inw+"_"+decodeQRCode+".pdf"));
							document.open();
							ByteArrayOutputStream baos = new ByteArrayOutputStream();
					        ImageIO.write(img, "png", baos);
							document.setPageSize(new Rectangle(img.getWidth(), img.getHeight()));
							document.setMargins(0, 0, 0, 0);
							document.newPage();
							document.add(Image.getInstance(baos.toByteArray()));
							
						}	
					}
					
				}
				doc.close();
				document.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		JOptionPane.showMessageDialog(null,"Done");
		
	}
	
	private String decodeQRCode(String newcode, PDFRenderer pdfRenderer, int page) throws IOException {
		BufferedImage codebuf = pdfRenderer.renderImageWithDPI(page, 350, ImageType.RGB);
		BufferedImage bi=codebuf.getSubimage(codebuf.getWidth()/2-300, 20, 1100 , 500);
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
