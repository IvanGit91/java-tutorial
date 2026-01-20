package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Demonstrates PDF generation using iText library.
 * Creates complex PDF documents with tables, paragraphs, and formatting.
 */
public class Generate extends UtilPdf {

    private static final Logger logger = Logger.getLogger(Generate.class.getName());
    final MyEvent event = new MyEvent();

    public static void main(String[] args) {
        Generate g = new Generate();
        g.generateDocumentWithUtilities();
    }

    /**
     * Generates a PDF document using utility methods from parent class.
     */
    public void generateDocumentWithUtilities() {
        Document pdfDocument = null;
        PdfPCell companyCell;
        Paragraph dataCell;
        PdfPCell pcell;
        try {
            float spaceAfterLarge = 20f;
            float spaceAfterSmall = 10f;
            pdfDocument = initialize(DATA_FOLDER + "Attachment1.pdf", event);

            logger.info("DIR: " + Options.class.getSimpleName());
            logger.info("DIR: " + Options.ALIGNMENT);
            logger.info("DIR: " + Options.ALIGNMENT.getDeclaringClass().getSimpleName());
            logger.info("DIR: " + Options.ALIGNMENT.name());
            logger.info("DIR: " + Options.ALIGNMENT.ordinal());

            Map<String, Object> opt1 = new HashMap<>();
            opt1.put(Options.ALIGNMENT.name(), Element.ALIGN_RIGHT);
            opt1.put(Options.SPACE_AFTER.name(), spaceAfterLarge);
            createParagraph(pdfDocument, "Attachment 1", TIMES_ROMAN_12_BOLD, opt1);

            Map<String, Object> opt2 = new HashMap<>();
            opt2.put(Options.ALIGNMENT.name(), Element.ALIGN_CENTER);
            opt2.put(Options.SPACE_AFTER.name(), spaceAfterSmall);
            createParagraph(pdfDocument, "PROFESSIONAL APPRENTICESHIP", TIMES_ROMAN_11_BOLD, opt2);
            createParagraph(pdfDocument, "Participation Application", TIMES_ROMAN_12_BOLD, opt2);

            // Table and Cell in separate functions
            Map<String, Object> optTable = new HashMap<>();
            optTable.put(Options.SPACE_AFTER.name(), spaceAfterSmall);
            PdfPTable table = createOnlyTable(pdfDocument, 1, 100, widthCols(1), optTable);
            Map<String, Object> optCell = new HashMap<>();
            optCell.put(Options.ALIGNMENT.name(), Element.ALIGN_LEFT);
            optCell.put(Options.BORDER_COLOR.name(), BaseColor.BLACK);
            createCell(pdfDocument, table, "COMPANY", TIMES_ROMAN_11_BOLD, optCell);
            pdfDocument.add(table);

            // Cells must be multiples of column count
            // Example for PdfTable(2): Element | Element
            // For PdfTable(3): Element | Element | Element

            Map<String, Object> optTableCell = new HashMap<>();
            optTableCell.put(Options.SPACE_AFTER.name(), spaceAfterSmall);
            optTableCell.put(Options.ALIGNMENT.name(), Element.ALIGN_LEFT);
            optTableCell.put(Options.BORDER_COLOR.name(), BaseColor.BLACK);
            String[] textCells = new String[]{"COMPANY2", "COMPANY3", "COMPANY4", "COMPANY5"};
            createTableAndCells(pdfDocument, 2, 100, widthCols(1, 1), textCells, TIMES_ROMAN_11_BOLD, optTableCell);

            // Custom cell - occurs rarely, no need for separate function
            table = createOnlyTable(pdfDocument, 1, 100, widthCols(1), optTable);
            companyCell = new PdfPCell();
            dataCell = new Paragraph();
            dataCell.add(new Phrase("The undersigned ", TIMES_ROMAN_11_BOLD));
            dataCell.setIndentationLeft(spaceAfterSmall);
            dataCell.add(new Phrase("John ", TIMES_ROMAN_11));
            companyCell.addElement(dataCell);
            companyCell.setBorderColor(BaseColor.BLACK);
            table.addCell(companyCell);
            pdfDocument.add(table);

            Map<String, Object> optTableCell2 = new HashMap<>();
            optTableCell2.put(Options.SPACE_AFTER.name(), spaceAfterSmall);
            optTableCell2.put(Options.BORDER_COLOR.name(), BaseColor.BLACK);
            optTableCell2.put(Options.CELL_BACK_COLOR.name(), PEACH_COLOR);
            textCells = new String[]{"The undersigned", "That's me"};
            createTableAndCells(pdfDocument, 3, 100, widthCols(1, 2, 1), textCells, TIMES_ROMAN_11_BOLD, colspans(1, 2), optTableCell2);

            Map<String, Object> optTableCell3 = new HashMap<>();
            optTableCell3.put(Options.SPACE_AFTER.name(), spaceAfterSmall);
            textCells = new String[]{"S/N", "Name", "Age", "SURNAME", "FIRST NAME", "MIDDLE NAME", "1", "James", "Fish", "Stone", "17"};
            createTableAndCells(pdfDocument, 5, 100, widthCols(1, 2, 2, 2, 1), textCells, TIMES_ROMAN_11_BOLD, colspans(1, 3, 1), rowspans(2, 1, 2), optTableCell3);

            textCells = new String[]{" 1,1 ", " 1,2 ", "multi 1,3 and 1,4", " 2,1 ", " 2,2 "};
            createTableAndCells(pdfDocument, 4, 100, widthCols(1, 1, 1, 1), textCells, TIMES_ROMAN_11, colspans(1, 1, 2), rowspans(1, 1, 2), optTableCell3);

            // Complex table with headers
            table = new PdfPTable(8);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 1, 1, 1, 1, 1, 1, 1});
            table.setSpacingAfter(10f);

            int leadingFixed = 10;
            int leadingMultiplier = 0;

            // HEADERS
            pcell = new PdfPCell();
            dataCell = new Paragraph("Company granted de minimis aid", TIMES_ROMAN_10);
            dataCell.setAlignment(Element.ALIGN_LEFT);
            dataCell.setLeading(leadingFixed, leadingMultiplier);
            pcell.addElement(dataCell);
            pcell.setBorderColorTop(BaseColor.BLACK);
            pcell.setBorderColorLeft(BaseColor.WHITE);
            pcell.setBorderColorRight(BaseColor.WHITE);
            pcell.setBackgroundColor(PEACH_COLOR);
            pcell.setRowspan(2);
            table.addCell(pcell);

            pcell = new PdfPCell();
            dataCell = new Paragraph("Granting authority", TIMES_ROMAN_10);
            dataCell.setAlignment(Element.ALIGN_LEFT);
            dataCell.setLeading(leadingFixed, leadingMultiplier);
            pcell.addElement(dataCell);
            pcell.setBorderColor(BaseColor.WHITE);
            pcell.setBackgroundColor(PEACH_COLOR);
            pcell.setRowspan(2);
            table.addCell(pcell);

            pcell = new PdfPCell();
            dataCell = new Paragraph("Regulatory/administrative reference providing the benefit", TIMES_ROMAN_10);
            dataCell.setAlignment(Element.ALIGN_LEFT);
            dataCell.setLeading(leadingFixed, leadingMultiplier);
            pcell.addElement(dataCell);
            pcell.setBorderColor(BaseColor.WHITE);
            pcell.setBackgroundColor(PEACH_COLOR);
            pcell.setRowspan(2);
            table.addCell(pcell);

            pcell = new PdfPCell();
            dataCell = new Paragraph("Granting measure and date", TIMES_ROMAN_10);
            dataCell.setAlignment(Element.ALIGN_LEFT);
            dataCell.setLeading(leadingFixed, leadingMultiplier);
            pcell.addElement(dataCell);
            pcell.setBorderColor(BaseColor.WHITE);
            pcell.setBackgroundColor(PEACH_COLOR);
            pcell.setRowspan(2);
            table.addCell(pcell);

            pcell = new PdfPCell();
            dataCell = new Paragraph("EU de minimis Reg.", TIMES_ROMAN_10);
            dataCell.setAlignment(Element.ALIGN_LEFT);
            dataCell.setLeading(leadingFixed, leadingMultiplier);
            pcell.addElement(dataCell);
            pcell.setBorderColor(BaseColor.WHITE);
            pcell.setBackgroundColor(PEACH_COLOR);
            pcell.setRowspan(2);
            table.addCell(pcell);

            pcell = new PdfPCell();
            dataCell = new Paragraph("De minimis aid amount", TIMES_ROMAN_10);
            dataCell.setAlignment(Element.ALIGN_LEFT);
            dataCell.setLeading(leadingFixed, leadingMultiplier);
            pcell.addElement(dataCell);
            pcell.setBorderColor(BaseColor.WHITE);
            pcell.setBackgroundColor(PEACH_COLOR);
            pcell.setColspan(2);
            table.addCell(pcell);

            pcell = new PdfPCell();
            dataCell = new Paragraph("Of which attributable to road freight transport for third parties", TIMES_ROMAN_10);
            dataCell.setAlignment(Element.ALIGN_LEFT);
            dataCell.setLeading(leadingFixed, leadingMultiplier);
            pcell.addElement(dataCell);
            pcell.setBorderColor(BaseColor.WHITE);
            pcell.setBackgroundColor(PEACH_COLOR);
            pcell.setRowspan(2);
            table.addCell(pcell);

            pcell = new PdfPCell();
            dataCell = new Paragraph("Granted", TIMES_ROMAN_10);
            dataCell.setAlignment(Element.ALIGN_LEFT);
            dataCell.setLeading(leadingFixed, leadingMultiplier);
            pcell.addElement(dataCell);
            pcell.setBorderColor(BaseColor.WHITE);
            pcell.setBackgroundColor(PEACH_COLOR);
            table.addCell(pcell);

            pcell = new PdfPCell();
            dataCell = new Paragraph("Actual", TIMES_ROMAN_10);
            dataCell.setAlignment(Element.ALIGN_LEFT);
            dataCell.setLeading(leadingFixed, leadingMultiplier);
            pcell.addElement(dataCell);
            pcell.setBorderColor(BaseColor.WHITE);
            pcell.setBackgroundColor(PEACH_COLOR);
            table.addCell(pcell);

            table.setHeaderRows(2);

            table.addCell("1");
            table.addCell("2");
            table.addCell("3");
            table.addCell("4");
            table.addCell("5");
            table.addCell("6");
            table.addCell("7");
            table.addCell("8");

            pdfDocument.add(table);

            Paragraph title = new Paragraph("PROFESSIONAL APPRENTICESHIP", TIMES_ROMAN_11_BOLD);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(200f);
            pdfDocument.add(title);

            title = new Paragraph("PROFESSIONAL APPRENTICESHIP", TIMES_ROMAN_11_BOLD);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(200f);
            pdfDocument.add(title);

            title = new Paragraph("PROFESSIONAL APPRENTICESHIP", TIMES_ROMAN_11_BOLD);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(200f);
            pdfDocument.add(title);

            event.setCanAdd(false);

            int leadFixed = 10;
            int leadMult = 0;

            table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{2, 8});
            table.setSpacingAfter(10f);

            pcell = new PdfPCell();
            dataCell = new Paragraph("X", TIMES_ROMAN_11_BOLD);
            dataCell.setAlignment(Element.ALIGN_CENTER);
            dataCell.setLeading(leadFixed, leadMult);
            pcell.addElement(dataCell);
            table.addCell(pcell);

            pcell = new PdfPCell();
            dataCell = new Paragraph("ELIGIBLE AND FUNDABLE", TIMES_ROMAN_11_BOLD);
            dataCell.setAlignment(Element.ALIGN_LEFT);
            dataCell.setLeading(leadFixed, leadMult);
            pcell.addElement(dataCell);
            table.addCell(pcell);

            pcell = new PdfPCell();
            dataCell = new Paragraph("", TIMES_ROMAN_11_BOLD);
            dataCell.setAlignment(Element.ALIGN_CENTER);
            dataCell.setLeading(leadFixed, leadMult);
            pcell.addElement(dataCell);
            table.addCell(pcell);

            pcell = new PdfPCell();
            dataCell = new Paragraph("ELIGIBLE BUT NOT FUNDABLE", TIMES_ROMAN_11_BOLD);
            dataCell.setAlignment(Element.ALIGN_LEFT);
            dataCell.setLeading(leadFixed, leadMult);
            pcell.addElement(dataCell);
            table.addCell(pcell);

            pdfDocument.add(table);

            finalizePdf(pdfDocument);

            logger.info("PDF created successfully");
        } catch (Exception e) {
            logger.error("Error generating PDF", e);
        }
    }
}
