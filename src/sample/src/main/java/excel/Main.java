package excel;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Example class demonstrating Excel file generation using Apache POI.
 * Creates a sample spreadsheet with headers and data rows.
 */
public class Main extends UtilExcelPoi {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final String OUTPUT_FILE = EXCEL_FOLDER + "test.xlsx";

    private final int[] rowNumber = new int[1];
    private final int[] colNumber = new int[1];
    private final String[] offset = new String[]{};
    private String[] headers;
    private Row row;

    public Main() {
        rowNumber[0] = 0;
        colNumber[0] = 0;
        STANDARD_HEADER_TEXT_COLOR = IndexedColors.WHITE;
        STANDARD_HEADER_BG = IndexedColors.BLUE;
        STANDARD_TEXT_COLOR = IndexedColors.BLACK;
        STANDARD_BG = IndexedColors.WHITE;
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.generate();
    }

    /**
     * Generates a sample Excel file with forecast costs data.
     */
    public void generate() {
        String sheetName = "Datatypes in Java";

        try (FileOutputStream outputStream = new FileOutputStream(OUTPUT_FILE);
             SXSSFWorkbook workbook = new SXSSFWorkbook()) {

            SXSSFSheet sheet = workbook.createSheet(sheetName);

            // Create cell styles
            CellStyle standardStyle = createStandardStyle(workbook, false,
                    Styles.STANDARD_BORDER, Styles.STANDARD_TEXT);
            CellStyle headerBackgroundStyle = createStandardStyle(workbook, true,
                    Styles.STANDARD_BORDER, Styles.HEADER_TEXT, Styles.HEADER_FOREGROUND, Styles.ALIGN_HORIZONTAL_CENTER);
            CellStyle headerStyle = createStandardStyle(workbook, true,
                    Styles.STANDARD_BORDER, Styles.STANDARD_TEXT);

            // Create merged header row
            headers = ArrayUtils.addAll(offset, "Forecast Costs", "", "", "", "", "");
            createHeaderRow(sheet, rowNumber, headers, 0, headerBackgroundStyle, Options.MERGE_CELL);

            // Create column headers
            headers = ArrayUtils.addAll(offset, "Insert Date", "Insert User", "Supply Type", "Amount", "Status", "Notes");
            createHeaderRow(sheet, rowNumber, headers, 0, headerStyle);

            // Create data row
            row = createRowAndResetColumn(sheet, rowNumber[0]++, colNumber, offset, standardStyle);
            createCellWithValueAndStyle(sheet, row, colNumber, "", standardStyle);
            createCellWithValueAndStyle(sheet, row, colNumber, "data", standardStyle);
            createCellWithValueAndStyle(sheet, row, colNumber, "dddd", standardStyle);
            createCellWithValueAndStyle(sheet, row, colNumber, "description", standardStyle);
            createCellWithValueAndStyle(sheet, row, colNumber, 10, standardStyle);
            createCellWithValueAndStyle(sheet, row, colNumber, 10d, standardStyle);
            createCellWithValueAndStyle(sheet, row, colNumber, "notes", standardStyle, Options.IGNORE_AUTOFIT);

            // Write the workbook to file
            workbook.write(outputStream);
            logger.info("Excel file generated successfully: {}", OUTPUT_FILE);

        } catch (IOException e) {
            logger.error("Failed to generate Excel file: {}", OUTPUT_FILE, e);
        }
    }
}
