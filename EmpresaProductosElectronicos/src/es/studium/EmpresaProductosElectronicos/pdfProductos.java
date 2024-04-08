package es.studium.EmpresaProductosElectronicos;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.UnitValue;

public class pdfProductos
{
	public static final String DATA = "FicheroConsultasProductos.txt";
	public static final String DEST = "ConsultaProductos.pdf";


	public pdfProductos(String dest) throws IOException
	{
			//Initialize PDF writer
			PdfWriter writer = new PdfWriter(dest);
			//Initialize PDF document
			PdfDocument pdf = new PdfDocument(writer);
			// Initialize document
			Document document = new Document(pdf, PageSize.A4.rotate());
			document.setMargins(10, 10, 10, 10);
			PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
			PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
			
			Table table = new Table(UnitValue.createPercentArray(new float[] { 1, 2, 2, 5, 2, 2, 3, 3, 5 })).useAllAvailableWidth();
			
			BufferedReader br = new BufferedReader(new FileReader(DATA));
			String line = br.readLine();
			process(table, line, font, true);
			document.add(new Paragraph("Productos Empresa Productos Electrónicos").setFont(bold));
			
			// Reading rest of csv
			while ((line = br.readLine()) != null)
			{
				process(table, line, font, false);
			}
			
			br.close();
			document.add(table);
			document.close();
			// Open the new PDF document just created
			Desktop.getDesktop().open(new File(DEST));
		}
	
	// El método process asigna la cabecera y el resto de celdas a la tabla
	public void process(Table table, String line, PdfFont font, boolean isHeader)
	{
		//Lee la primera linea separada por -
		StringTokenizer tokenizer = new StringTokenizer(line, "-");
		while (tokenizer.hasMoreTokens())
		{
			if (isHeader)
			{
				// Crea la primera celda de la tabla y le añade el primer párrafo
				table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
			} else
			{
				table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
			}
		}
	}
}
 
