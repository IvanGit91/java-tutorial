package doc;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFTableCell.XWPFVertAlign;
import org.apache.xmlbeans.XmlCursor;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STHeightRule;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for creating and manipulating Word documents (.docx) using Apache POI.
 * Provides methods for document initialization, text replacement, table creation, and image handling.
 */
public abstract class UtilDoc {

    private static final Logger logger = LoggerFactory.getLogger(UtilDoc.class);
    private static final int TWIPS_PER_INCH = 1440;
    private static final int DEFAULT_SPACING_AFTER = 10;
    private static final int DEFAULT_FONT_SIZE = 11;
    private static final int MAX_VALID_FONT_SIZE = 30000;

    /**
     * Creates a new blank Word document and prepares output stream.
     *
     * @param out        array to store the FileOutputStream (index 0)
     * @param outputFile path for the output file
     * @return the created XWPFDocument, or null if creation failed
     */
    public static XWPFDocument initDocDocument(FileOutputStream[] out, String outputFile) {
        try {
            XWPFDocument document = new XWPFDocument();
            out[0] = new FileOutputStream(outputFile);
            return document;
        } catch (FileNotFoundException e) {
            logger.error("Failed to create output file: {}", outputFile, e);
            return null;
        }
    }

    /**
     * Opens an existing Word document from an input stream and prepares output stream.
     *
     * @param out         array to store the FileOutputStream (index 0)
     * @param outputFile  path for the output file
     * @param inputStream input stream containing the source document
     * @return the loaded XWPFDocument, or null if loading failed
     */
    public static XWPFDocument initDocDocument(FileOutputStream[] out, String outputFile, InputStream inputStream) {
        try {
            XWPFDocument document = new XWPFDocument(inputStream);
            out[0] = new FileOutputStream(outputFile);
            return document;
        } catch (IOException e) {
            logger.error("Failed to load document or create output file: {}", outputFile, e);
            return null;
        }
    }

    /**
     * Writes and closes the document.
     *
     * @param document the document to save
     * @param out      array containing the FileOutputStream (index 0)
     * @return true if document was saved successfully, false otherwise
     */
    public static boolean closeDocDocument(XWPFDocument document, FileOutputStream[] out) {
        try {
            document.write(out[0]);
            out[0].close();
            logger.info("Document written successfully");
            return true;
        } catch (IOException e) {
            logger.error("Failed to write document", e);
            return false;
        }
    }

    /**
     * Adds an image to a run.
     *
     * @param fileLocation path to the image file
     * @param run          the run to add the image to
     * @param width        image width in points
     * @param height       image height in points
     */
    public static void addImgFile(String fileLocation, XWPFRun run, int width, int height)
            throws InvalidFormatException, IOException {
        try (FileInputStream is = new FileInputStream(fileLocation)) {
            run.addBreak();
            run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, fileLocation, Units.toEMU(width), Units.toEMU(height));
        }
    }

    /**
     * Adds an image to a paragraph.
     *
     * @param par          the paragraph to add the image to
     * @param fileLocation path to the image file
     * @param width        image width in points
     * @param height       image height in points
     */
    public static void addImgFileAndPar(XWPFParagraph par, String fileLocation, int width, int height)
            throws InvalidFormatException, IOException {
        XWPFRun run = par.createRun();
        try (FileInputStream is = new FileInputStream(fileLocation)) {
            run.addBreak();
            run.addPicture(is, XWPFDocument.PICTURE_TYPE_JPEG, fileLocation, Units.toEMU(width), Units.toEMU(height));
        }
    }

    /**
     * Adds an image from an input stream to a paragraph.
     *
     * @param par    the paragraph to add the image to
     * @param stream input stream containing the image data
     * @param width  image width in points
     * @param height image height in points
     */
    public static void addImgFileAndPar(XWPFParagraph par, InputStream stream, int width, int height)
            throws InvalidFormatException, IOException {
        XWPFRun run = par.createRun();
        run.addBreak();
        run.addPicture(stream, XWPFDocument.PICTURE_TYPE_JPEG, "", Units.toEMU(width), Units.toEMU(height));
    }

    /**
     * Removes a border from a table cell.
     *
     * @param cell      the cell to modify
     * @param direction which border to remove
     */
    public static void deleteBorderCell(XWPFTableCell cell, Direction direction) {
        CTTcPr tcPr = cell.getCTTc().addNewTcPr();
        tcPr.addNewHMerge().setVal(STMerge.RESTART);

        switch (direction) {
            case RIGHT -> tcPr.addNewTcBorders().addNewRight().setVal(STBorder.NIL);
            case LEFT -> tcPr.addNewTcBorders().addNewLeft().setVal(STBorder.NIL);
            case TOP -> tcPr.addNewTcBorders().addNewTop().setVal(STBorder.NIL);
            case BOTTOM -> tcPr.addNewTcBorders().addNewBottom().setVal(STBorder.NIL);
            default -> logger.warn("Invalid direction for cell border: {}", direction);
        }
    }

    private static XWPFRun setRun(XWPFRun run, String text, boolean isBold, boolean isItalic,
                                  String color, boolean isBreak, String fontFamily, Integer fontSize) {
        run.setText(text);
        if (isBold) run.setBold(true);
        if (isItalic) run.setItalic(true);
        if (color != null) run.setColor(color);
        if (isBreak) run.addBreak();
        if (fontFamily != null) run.setFontFamily(fontFamily);
        if (fontSize != null) run.setFontSize(fontSize);
        return run;
    }

    /**
     * Creates a customized paragraph with text and formatting.
     *
     * @param document     the document to add the paragraph to
     * @param text         the text content
     * @param isBold       whether text should be bold
     * @param isItalic     whether text should be italic
     * @param color        text color (hex string, e.g., "FF0000")
     * @param isBreak      whether to add a line break after text
     * @param fontFamily   font family name
     * @param fontSize     font size in points
     * @param align        paragraph alignment
     * @param spacingAfter spacing after paragraph
     * @return the created paragraph
     */
    public static XWPFParagraph createCstParagraph(XWPFDocument document, String text, boolean isBold,
                                                   boolean isItalic, String color, boolean isBreak,
                                                   String fontFamily, Integer fontSize, Direction align,
                                                   Integer spacingAfter) {
        XWPFParagraph paragraph = document.createParagraph();
        setRun(paragraph.createRun(), text, isBold, isItalic, color, isBreak, fontFamily, fontSize);
        alignPosParagraph(paragraph, align, spacingAfter);
        return paragraph;
    }

    /**
     * Aligns a paragraph and sets spacing.
     *
     * @param paragraph    the paragraph to align
     * @param align        alignment direction
     * @param spacingAfter spacing after paragraph
     * @return the modified paragraph
     */
    public static XWPFParagraph alignPosParagraph(XWPFParagraph paragraph, Direction align, Integer spacingAfter) {
        if (align != null) {
            switch (align) {
                case CENTER -> paragraph.setAlignment(ParagraphAlignment.CENTER);
                case LEFT -> paragraph.setAlignment(ParagraphAlignment.LEFT);
                case RIGHT -> paragraph.setAlignment(ParagraphAlignment.RIGHT);
                default -> { /* No alignment change */ }
            }
        }
        if (spacingAfter != null) {
            paragraph.setSpacingAfter(spacingAfter);
        }
        return paragraph;
    }

    /**
     * Sets spacing after a paragraph.
     */
    public static void setSpacing(XWPFParagraph paragraph, Integer spacingAfter) {
        paragraph.setSpacingAfter(spacingAfter);
    }

    /**
     * Adds a page break before the paragraph.
     */
    public static void nextPage(XWPFParagraph paragraph) {
        paragraph.setPageBreak(true);
    }

    /**
     * Creates a customized table.
     *
     * @param document    the document to add the table to
     * @param hideBorders whether to hide table borders
     * @param align       table alignment
     * @return the created table
     */
    public static XWPFTable createCstTable(XWPFDocument document, Boolean hideBorders, Direction align) {
        XWPFTable table = document.createTable();

        if (align != null) {
            switch (align) {
                case CENTER -> table.setTableAlignment(TableRowAlign.CENTER);
                case LEFT -> table.setTableAlignment(TableRowAlign.LEFT);
                case RIGHT -> table.setTableAlignment(TableRowAlign.RIGHT);
                default -> { /* No alignment change */ }
            }
        }

        if (Boolean.TRUE.equals(hideBorders)) {
            table.getCTTbl().getTblPr().unsetTblBorders();
        }
        table.getCTTbl().addNewTblPr().addNewTblW().setW(BigInteger.valueOf(10000));
        return table;
    }

    /**
     * Creates a row in a table with text cells.
     *
     * @param table          the table to add the row to
     * @param numRow         row number (1-based)
     * @param numCol         number of columns
     * @param repeatedHeader whether this row should repeat as header on each page
     * @param color          cell background color
     * @param align          cell alignment
     * @param texts          text content for each cell
     * @return the modified table
     */
    public static XWPFTable createCstTableRow(XWPFTable table, Integer numRow, Integer numCol,
                                              boolean repeatedHeader, String color, Direction align,
                                              String... texts) {
        if (numCol != texts.length) {
            logger.warn("Column count ({}) doesn't match text count ({})", numCol, texts.length);
            return table;
        }

        XWPFTableRow tableRow = (numRow == 1) ? table.getRow(0) : table.createRow();
        if (repeatedHeader) {
            tableRow.setRepeatHeader(true);
        }

        for (int i = 0; i < numCol; i++) {
            XWPFTableCell cell;
            if (numRow == 1) {
                cell = (i == 0) ? tableRow.getCell(0) : tableRow.addNewTableCell();
            } else {
                cell = tableRow.getCell(i);
            }
            cell.setText(texts[i]);
            if (color != null) {
                cell.setColor(color);
            }
        }
        return table;
    }

    /**
     * Creates a row with paragraph formatting in each cell.
     *
     * @param table          the table to add the row to
     * @param firstRow       whether this is the first row
     * @param numCol         number of columns
     * @param repeatedHeader whether this row should repeat as header
     * @param color          cell background color
     * @param align          cell alignment
     * @param fontSize       font size
     * @param reduceHeight   whether to reduce row height
     * @param reduceWidth    whether to set first cell width to 40%
     * @param texts          text content for each cell
     * @return the modified table
     */
    public static XWPFTable createCstTableRowPar(XWPFTable table, boolean firstRow, Integer numCol,
                                                 boolean repeatedHeader, String color, Direction align,
                                                 Integer fontSize, boolean reduceHeight, boolean reduceWidth,
                                                 String... texts) {
        if (numCol != texts.length) {
            logger.warn("Column count ({}) doesn't match text count ({})", numCol, texts.length);
            return table;
        }

        XWPFTableRow tableRow = firstRow ? table.getRow(0) : table.createRow();
        if (repeatedHeader) {
            tableRow.setRepeatHeader(true);
        }

        if (reduceHeight) {
            tableRow.setHeight(TWIPS_PER_INCH * 6 / 10);
            tableRow.getCtRow().getTrPr().getTrHeightArray(0).setHRule(STHeightRule.EXACT);
        }

        for (int i = 0; i < numCol; i++) {
            XWPFTableCell cell;
            if (firstRow) {
                cell = (i == 0) ? tableRow.getCell(0) : tableRow.addNewTableCell();
            } else {
                cell = tableRow.getCell(i);
            }

            XWPFParagraph paragraph = cell.addParagraph();
            alignPosParagraph(paragraph, align, DEFAULT_SPACING_AFTER);
            setRun(paragraph.createRun(), texts[i], false, false, null, true, null, fontSize);

            if (color != null) {
                cell.setColor(color);
            }
            if (reduceWidth && i == 0) {
                cell.setWidth("40.00%");
            }
            if (align == Direction.VERTICAL) {
                cell.setVerticalAlignment(XWPFVertAlign.CENTER);
            }
        }
        return table;
    }

    /**
     * Copies formatting from one run to another.
     *
     * @param run    source run for formatting
     * @param newRun destination run
     * @param text   text to set in the new run
     * @return the modified new run
     */
    protected static XWPFRun copyRun(XWPFRun run, XWPFRun newRun, String text) {
        newRun.setText(text);
        newRun.setBold(run.isBold());
        newRun.setItalic(run.isItalic());
        newRun.setColor(run.getColor());
        newRun.setFontFamily(run.getFontFamily());

        int fontSize = run.getFontSize();
        newRun.setFontSize(fontSize > MAX_VALID_FONT_SIZE ? DEFAULT_FONT_SIZE : fontSize);

        newRun.setUnderline(run.getUnderline());
        newRun.setTextPosition(run.getTextPosition());
        return newRun;
    }

    /**
     * Checks if a value represents a decimal number.
     *
     * @param value the value to check
     * @return true if the value is a valid double with a decimal point
     */
    public static <T> boolean isDouble(T value) {
        String str = value.toString();
        if (!str.contains(".")) {
            return false;
        }
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Replaces text in a document (paragraphs and tables).
     *
     * @param doc         the document to search
     * @param findText    text to find
     * @param replaceText replacement text
     * @param options     optional: first element is case-insensitive flag
     */
    protected static void replaceText(XWPFDocument doc, String findText, String replaceText, boolean... options) {
        boolean lowerCase = options.length > 0 && options[0];
        String searchText = lowerCase ? findText.toLowerCase() : findText;

        // Process paragraphs
        for (XWPFParagraph p : doc.getParagraphs()) {
            replaceInParagraph(p, searchText, replaceText, lowerCase);
        }

        // Process tables
        for (XWPFTable tbl : doc.getTables()) {
            for (XWPFTableRow row : tbl.getRows()) {
                for (XWPFTableCell cell : row.getTableCells()) {
                    for (XWPFParagraph p : cell.getParagraphs()) {
                        replaceInParagraph(p, searchText, replaceText, lowerCase);
                    }
                }
            }
        }
    }

    private static void replaceInParagraph(XWPFParagraph p, String findText, String replaceText, boolean lowerCase) {
        String parText = p.getText();
        if (parText == null) return;

        String searchParText = lowerCase ? parText.toLowerCase() : parText;
        List<XWPFRun> runs = p.getRuns();

        if (runs == null || runs.isEmpty()) return;

        // Try to replace in individual runs first
        for (XWPFRun r : runs) {
            String text = r.getText(0);
            if (text == null) continue;

            String searchText = lowerCase ? text.toLowerCase() : text;
            if (searchText.contains(findText)) {
                String newText = text.replace(findText, replaceText);
                r.setText(newText, 0);
            }
        }

        // Handle case where placeholder spans multiple runs
        if (searchParText.contains(findText)) {
            for (XWPFRun r : p.getRuns()) {
                r.setText("", 0);
            }
            if (findText != null && !findText.isEmpty() && replaceText != null) {
                String finalReplace = isDouble(replaceText) ? replaceText + " " : replaceText;
                String newParText = parText.replace(findText, finalReplace);
                p.getRuns().get(0).setText(newParText, 0);
            }
        }
    }

    /**
     * Duplicates paragraphs between markers and replaces placeholders, or removes the section.
     *
     * @param doc         the document to modify
     * @param replaceText list of replacement values
     * @param mappaLista  array of placeholder names (without $ delimiters)
     * @param inizio      start marker text
     * @param fine        end marker text
     * @param bools       optional: first element indicates removal mode
     */
    protected static void duplicateAndReplaceTextList(XWPFDocument doc, List<String> replaceText,
                                                      String[] mappaLista, String inizio, String fine,
                                                      Boolean... bools) {
        boolean removePar = bools.length > 0 && Boolean.TRUE.equals(bools[0]);
        boolean capturing = false;
        int index = 0;

        List<XWPFParagraph> capturedParagraphs = new ArrayList<>();
        List<Integer> indicesToRemove = new ArrayList<>();

        for (XWPFParagraph p : doc.getParagraphs()) {
            String parText = p.getText();

            if (parText.equals(inizio)) {
                capturing = true;
            } else if (parText.equals(fine)) {
                capturing = false;
                indicesToRemove.add(index);
            }

            if (capturing) {
                capturedParagraphs.add(p);
                indicesToRemove.add(index);
            }
            index++;
        }

        if (removePar) {
            // Remove paragraphs in reverse order to preserve indices
            for (int i = indicesToRemove.size() - 1; i >= 0; i--) {
                doc.removeBodyElement(indicesToRemove.get(i));
            }
        } else if (!capturedParagraphs.isEmpty()) {
            // Duplicate and replace
            for (XWPFParagraph para : capturedParagraphs) {
                if (para.getText().equals(inizio)) continue;

                XmlCursor cursor = capturedParagraphs.get(0).getCTP().newCursor();
                XWPFParagraph newPar = para.getDocument().insertNewParagraph(cursor);
                newPar.setIndentationLeft(para.getIndentationLeft());
                newPar.setIndentationRight(para.getIndentationRight());

                String text = para.getText();
                for (int i = 0; i < mappaLista.length; i++) {
                    String replacement = replaceText.get(i);
                    if (isDouble(replacement)) {
                        replacement += " ";
                    }
                    text = text.replace("$" + mappaLista[i] + "$", replacement);
                }
                copyRun(para.getRuns().get(0), newPar.createRun(), text);
            }
        }
    }

    /**
     * Replaces placeholders in headers, body, and footers of a document.
     *
     * @param doc         the document to process
     * @param placeHolder the placeholder text to find
     * @param replaceText the replacement text
     * @return the modified document
     */
    public static XWPFDocument replacePOI(XWPFDocument doc, String placeHolder, String replaceText) {
        // Replace in headers
        for (XWPFHeader header : doc.getHeaderList()) {
            replaceAllBodyElements(header.getBodyElements(), placeHolder, replaceText);
        }

        // Replace in body
        replaceAllBodyElements(doc.getBodyElements(), placeHolder, replaceText);

        // Replace in footers
        for (XWPFFooter footer : doc.getFooterList()) {
            replaceAllBodyElements(footer.getBodyElements(), placeHolder, replaceText);
        }

        return doc;
    }

    private static void replaceAllBodyElements(List<IBodyElement> bodyElements, String placeHolder, String replaceText) {
        for (IBodyElement bodyElement : bodyElements) {
            if (bodyElement.getElementType() == BodyElementType.PARAGRAPH) {
                replaceParagraph((XWPFParagraph) bodyElement, placeHolder, replaceText);
            } else if (bodyElement.getElementType() == BodyElementType.TABLE) {
                replaceTable((XWPFTable) bodyElement, placeHolder, replaceText);
            }
        }
    }

    private static void replaceTable(XWPFTable table, String placeHolder, String replaceText) {
        for (XWPFTableRow row : table.getRows()) {
            for (XWPFTableCell cell : row.getTableCells()) {
                for (IBodyElement bodyElement : cell.getBodyElements()) {
                    if (bodyElement.getElementType() == BodyElementType.PARAGRAPH) {
                        replaceParagraph((XWPFParagraph) bodyElement, placeHolder, replaceText);
                    } else if (bodyElement.getElementType() == BodyElementType.TABLE) {
                        replaceTable((XWPFTable) bodyElement, placeHolder, replaceText);
                    }
                }
            }
        }
    }

    private static void replaceParagraph(XWPFParagraph paragraph, String placeHolder, String replaceText) {
        List<XWPFRun> runs = paragraph.getRuns();
        if (runs == null || runs.isEmpty()) return;

        TextSegment found = paragraph.searchText(placeHolder, new PositionInParagraph());
        if (found == null) return;

        if (found.getBeginRun() == found.getEndRun()) {
            // Placeholder is within a single run
            XWPFRun run = runs.get(found.getBeginRun());
            String runText = run.getText(run.getTextPosition());
            if (runText != null) {
                run.setText(runText.replace(placeHolder, replaceText), 0);
            }
        } else {
            // Placeholder spans multiple runs
            StringBuilder builder = new StringBuilder();
            for (int runPos = found.getBeginRun(); runPos <= found.getEndRun(); runPos++) {
                XWPFRun run = runs.get(runPos);
                String text = run.getText(run.getTextPosition());
                if (text != null) {
                    builder.append(text);
                }
            }

            String replaced = builder.toString().replace(placeHolder, replaceText);

            // Set replaced text in first run, clear others
            runs.get(found.getBeginRun()).setText(replaced, 0);
            for (int runPos = found.getBeginRun() + 1; runPos <= found.getEndRun(); runPos++) {
                runs.get(runPos).setText("", 0);
            }
        }
    }

    /**
     * Direction/alignment options for document elements.
     */
    public enum Direction {
        TOP, LEFT, RIGHT, BOTTOM, CENTER, VERTICAL
    }
}
