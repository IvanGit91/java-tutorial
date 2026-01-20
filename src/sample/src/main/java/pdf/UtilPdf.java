package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Map;

/**
 * Abstract utility class for PDF document generation using iText library.
 * Provides common functionality for creating paragraphs, tables, and cells.
 */
public abstract class UtilPdf {

    // Default output directory - uses user's home directory with a pdf subfolder
    protected static final String DATA_FOLDER = Paths.get(System.getProperty("user.home"), "pdf-output")
            + FileSystems.getDefault().getSeparator();

    // Color constants for PDF styling
    protected static final BaseColor GRAY_DATA = new BaseColor(201, 201, 201);
    protected static final BaseColor BLUE_FIRST_YEAR = new BaseColor(179, 197, 233);
    protected static final BaseColor YELLOW_SECOND_YEAR = new BaseColor(254, 217, 102);
    protected static final BaseColor GREEN_THIRD_YEAR = new BaseColor(168, 208, 138);
    protected static final BaseColor PEACH_COLOR = new BaseColor(251, 228, 214);

    // Font constants
    protected static final Font TIMES_ROMAN_12 = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
    protected static final Font TIMES_ROMAN_12_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    protected static final Font TIMES_ROMAN_11 = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.NORMAL);
    protected static final Font TIMES_ROMAN_11_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.BOLD);
    protected static final Font TIMES_ROMAN_11_UNDERLINE = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.UNDERLINE);
    protected static final Font TIMES_ROMAN_11_UNDERLINE_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 11, Font.UNDERLINE | Font.BOLD);
    protected static final Font TIMES_ROMAN_10 = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.NORMAL);
    protected static final Font TIMES_ROMAN_10_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 10, Font.BOLD);

    private static final Logger logger = Logger.getLogger(UtilPdf.class.getName());

    protected UtilPdf() {
    }

    /**
     * Returns the output directory path. Can be overridden for custom paths.
     */
    public static String getOutputFolder() {
        return DATA_FOLDER;
    }

    /**
     * Checks if a string is not null and not empty after trimming.
     */
    protected static boolean notNullAndNotEmpty(String s) {
        return s != null && !s.trim().isEmpty();
    }

    /**
     * Initializes a PDF document with the specified output path and optional page event handler.
     */
    protected static <T extends PdfPageEventHelper> Document initialize(String outputPath, T event) {
        Document pdfDocument = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(pdfDocument, new FileOutputStream(outputPath));
            pdfDocument.setPageSize(PageSize.A4);
            if (event != null) {
                writer.setPageEvent(event);
            }
            pdfDocument.open();
        } catch (FileNotFoundException e) {
            logger.error("File not found: " + outputPath, e);
        } catch (DocumentException e) {
            logger.error("Document exception during initialization", e);
        }
        return pdfDocument;
    }

    /**
     * Closes the PDF document.
     */
    protected static void finalizePdf(Document pdfDocument) {
        pdfDocument.close();
    }

    /**
     * Creates and adds a simple paragraph to the document.
     */
    protected static void createParagraph(Document d, String text, Font font) {
        try {
            Paragraph paragraph = new Paragraph();
            paragraph.add(new Phrase(text, font));
            d.add(paragraph);
        } catch (DocumentException e) {
            logger.error("Error creating paragraph", e);
        }
    }

    /**
     * Creates a paragraph with additional formatting options.
     */
    protected static Paragraph createParagraph(Document d, String text, Font font, Map<String, Object> options) {
        Paragraph paragraph = null;
        try {
            paragraph = new Paragraph();
            paragraph.add(new Phrase(text, font));
            if (options.containsKey(Options.ALIGNMENT.name())) {
                paragraph.setAlignment((int) options.get(Options.ALIGNMENT.name()));
            }
            if (options.containsKey(Options.SPACE_AFTER.name())) {
                paragraph.setSpacingAfter((float) options.get(Options.SPACE_AFTER.name()));
            }
            if (options.containsKey(Options.SPACE_LEFT.name())) {
                paragraph.setIndentationLeft((float) options.get(Options.SPACE_LEFT.name()));
            }
            if (!options.containsKey(Options.NOT_ADD_PARAGRAPH.name())) {
                d.add(paragraph);
            } else {
                options.remove(Options.NOT_ADD_PARAGRAPH.name());
            }
        } catch (DocumentException e) {
            logger.error("Error creating paragraph with options", e);
        }
        return paragraph;
    }

    /**
     * Creates a table without cells.
     */
    protected static PdfPTable createOnlyTable(Document d, int columns, int widthPercentage,
                                               float[] columnWidths, Map<String, Object> options) {
        return createTableAndCells(d, columns, widthPercentage, columnWidths, null, null,
                new int[]{1}, new int[1], options);
    }

    /**
     * Creates multiple cells with different texts and fonts.
     */
    protected static void createCellWithMultipleText(Document d, PdfPTable table,
                                                     String[] texts, Font[] fonts, Map<String, Object> options) {
        if (texts.length == fonts.length) {
            for (int i = 0; i < texts.length; i++) {
                createCell(d, table, texts[i], fonts[i], 1, 1, options);
            }
        } else {
            logger.error("Array length mismatch: texts=" + texts.length + ", fonts=" + fonts.length);
        }
    }

    /**
     * Creates cells with specified colspan and rowspan arrays.
     */
    protected static void createCellWithColspanAndRowspan(Document d, PdfPTable table,
                                                          String text, Font font, int[] colspans, int[] rowspans, Map<String, Object> options) {
        if (colspans.length == rowspans.length) {
            for (int i = 0; i < colspans.length; i++) {
                createCell(d, table, text, font, 1, 1, options);
            }
        } else {
            logger.error("Array length mismatch: colspans=" + colspans.length + ", rowspans=" + rowspans.length);
        }
    }

    /**
     * Creates a table with cells using default colspan/rowspan.
     */
    protected static PdfPTable createTableAndCells(Document d, int columns, int widthPercentage,
                                                   float[] columnWidths, String[] cellTexts, Font cellFont, Map<String, Object> options) {
        return createTableAndCells(d, columns, widthPercentage, columnWidths, cellTexts, cellFont,
                new int[]{1}, new int[]{1}, options);
    }

    /**
     * Creates a table with cells using specified colspan.
     */
    protected static PdfPTable createTableAndCells(Document d, int columns, int widthPercentage,
                                                   float[] columnWidths, String[] cellTexts, Font cellFont, int[] colspans, Map<String, Object> options) {
        return createTableAndCells(d, columns, widthPercentage, columnWidths, cellTexts, cellFont,
                colspans, new int[]{1}, options);
    }

    /**
     * Creates a table with cells using specified colspan and rowspan.
     */
    protected static PdfPTable createTableAndCells(Document d, int columns, int widthPercentage,
                                                   float[] columnWidths, String[] cellTexts, Font cellFont, int[] colspans, int[] rowspans,
                                                   Map<String, Object> options) {
        PdfPTable table = null;
        try {
            table = new PdfPTable(columns);
            table.setWidthPercentage(widthPercentage);
            table.setWidths(columnWidths);
            if (options.containsKey(Options.SPACE_AFTER.name())) {
                table.setSpacingAfter((float) options.get(Options.SPACE_AFTER.name()));
            }
            if (cellTexts != null && cellFont != null) {
                createCells(d, table, cellTexts, cellFont, colspans, rowspans, options);
            }
        } catch (DocumentException e) {
            logger.error("Error creating table", e);
        }
        return table;
    }

    /**
     * Creates and adds cells to a table.
     */
    protected static void createCells(Document d, PdfPTable table, String[] cellTexts,
                                      Font font, int[] colspans, int[] rowspans, Map<String, Object> options) {
        try {
            int col = 0, row = 0;
            for (String cellText : cellTexts) {
                int colspan = colspans != null && colspans.length > col ? colspans[col++] : 1;
                int rowspan = rowspans != null && rowspans.length > row ? rowspans[row++] : 1;
                createCell(d, table, cellText, font, colspan, rowspan, options);
            }
            d.add(table);
        } catch (DocumentException e) {
            logger.error("Error creating cells", e);
        }
    }

    /**
     * Creates a single cell with default colspan and rowspan.
     */
    protected static void createCell(Document d, PdfPTable table, String text,
                                     Font font, Map<String, Object> options) {
        createCell(d, table, text, font, 1, 1, options);
    }

    /**
     * Creates a single cell with specified colspan and rowspan.
     */
    protected static void createCell(Document d, PdfPTable table, String text,
                                     Font font, int colspan, int rowspan, Map<String, Object> options) {
        PdfPCell cell = new PdfPCell();
        options.put(Options.NOT_ADD_PARAGRAPH.name(), true);
        Paragraph p = createParagraph(d, text, font, options);
        cell.addElement(p);
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        if (options.containsKey(Options.BORDER_COLOR.name())) {
            cell.setBorderColor((BaseColor) options.get(Options.BORDER_COLOR.name()));
        }
        if (options.containsKey(Options.CELL_BACK_COLOR.name())) {
            cell.setBackgroundColor((BaseColor) options.get(Options.CELL_BACK_COLOR.name()));
        }
        table.addCell(cell);
    }

    protected int[] colspans(int... colspans) {
        return colspans;
    }

    protected int[] rowspans(int... rowspans) {
        return rowspans;
    }

    protected float[] widthCols(float... widths) {
        return widths;
    }

    protected enum Alignment {
        TOP, LEFT, RIGHT, BOTTOM, CENTER, VERTICAL
    }

    protected enum Options {
        ALIGNMENT, SPACE_AFTER, SPACE_BEFORE, BORDER_COLOR, NOT_ADD_PARAGRAPH, SPACE_LEFT, CELL_BACK_COLOR
    }
}
