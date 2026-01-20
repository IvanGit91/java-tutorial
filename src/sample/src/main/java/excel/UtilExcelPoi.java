package excel;

import models.common.Pair;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.UtilFunction;

import java.nio.file.FileSystems;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class for creating and manipulating Excel files (.xlsx) using Apache POI.
 * Provides methods for cell styling, row creation, and common Excel operations.
 */
public abstract class UtilExcelPoi {

    protected static final String EXCEL_FOLDER = System.getProperty("user.home") +
            FileSystems.getDefault().getSeparator() + "Documents" +
            FileSystems.getDefault().getSeparator() + "Excel" +
            FileSystems.getDefault().getSeparator();
    protected static final boolean[] error = new boolean[1];
    private static final Logger logger = LoggerFactory.getLogger(UtilExcelPoi.class);
    protected static IndexedColors STANDARD_HEADER_TEXT_COLOR;
    protected static IndexedColors STANDARD_HEADER_BG;
    protected static IndexedColors STANDARD_TEXT_COLOR;
    protected static IndexedColors STANDARD_BG;

    /**
     * Ensures the Excel output folder exists.
     */
    protected static void checkExcelFolder() {
        UtilFunction.createDirs(EXCEL_FOLDER);
    }

    /**
     * Creates a new row in the sheet.
     *
     * @param sheet     the sheet to add the row to
     * @param rowNumber the row number (0-based)
     * @return the created row
     */
    protected static Row createRow(SXSSFSheet sheet, int rowNumber) {
        return sheet.createRow(rowNumber);
    }

    /**
     * Creates a list from variable arguments.
     */
    protected static List<Object> objectList(Object... objects) {
        return Arrays.asList(objects);
    }

    /**
     * Creates an integer list from variable arguments.
     */
    protected static List<Integer> integerList(Integer... integers) {
        return Arrays.asList(integers);
    }

    @SafeVarargs
    protected static Pair<Styles, List<Object>>[] stylePairs(Pair<Styles, List<Object>>... styles) {
        return styles;
    }

    @SafeVarargs
    protected static List<Pair<List<Integer>, CellStyle>> styleList(Pair<List<Integer>, CellStyle>... styles) {
        return Arrays.asList(styles);
    }

    @SafeVarargs
    protected static List<Pair<Integer, Options>> optionList(Pair<Integer, Options>... options) {
        return Arrays.asList(options);
    }

    /**
     * Converts a boolean status to a display string.
     *
     * @param status the validation status
     * @return "validated" if true, "pending validation" if false
     */
    public static String parseStatus(boolean status) {
        return status ? "validated" : "pending validation";
    }

    /**
     * Converts "0" to "Total", otherwise returns the original string.
     */
    public static String parseTotal(String value) {
        return "0".equals(value) ? "Total" : value;
    }

    /**
     * Maps a Colors enum to an IndexedColors value.
     */
    protected static IndexedColors getColor(Object color) {
        if (!(color instanceof Colors c)) {
            return STANDARD_TEXT_COLOR;
        }

        return switch (c) {
            case GRAY -> IndexedColors.GREY_50_PERCENT;
            case LIGHT_GREEN -> IndexedColors.LIGHT_GREEN;
            case WHITE -> IndexedColors.WHITE;
            case BLUE -> IndexedColors.BLUE;
            case ORANGE -> IndexedColors.ORANGE;
            case YELLOW -> IndexedColors.YELLOW;
            case BLACK -> IndexedColors.BLACK;
        };
    }

    /**
     * Creates a new row and resets column counter, applying offset cells.
     *
     * @param sheet     the sheet to add the row to
     * @param rowNumber the row number
     * @param colNumber array holding current column number (will be reset to 0)
     * @param offset    array of offset cell values to prepend
     * @param style     cell style to apply to offset cells
     * @return the created row
     */
    protected static Row createRowAndResetColumn(SXSSFSheet sheet, int rowNumber, int[] colNumber,
                                                 String[] offset, CellStyle style) {
        colNumber[0] = 0;
        Row row = sheet.createRow(rowNumber);
        for (String offsetValue : offset) {
            createCellWithValueAndStyle(sheet, row, colNumber, offsetValue, style);
        }
        return sheet.createRow(rowNumber);
    }

    /**
     * Creates a cell in the specified row.
     */
    protected static Cell createCell(Row row, int columnNumber) {
        return row.createCell(columnNumber);
    }

    /**
     * Applies default currency format to a cell style.
     */
    protected static void applyDefaultCurrencyFormat(CellStyle style) {
        style.setDataFormat((short) 0x7);
    }

    /**
     * Sets the value and style of a cell based on the field type.
     *
     * @param cell  the cell to modify
     * @param field the value to set (String, Integer, or Double)
     * @param style the style to apply
     */
    protected static <T> void setValueAndStyle(Cell cell, T field, CellStyle style) {
        if (field instanceof String) {
            cell.setCellValue(UtilFunction.valueOrEmpty(field));
        } else if (field instanceof Integer) {
            cell.setCellValue((Integer) UtilFunction.valueOrEmptyNum((Integer) field));
        } else if (field instanceof Double) {
            applyDefaultCurrencyFormat(style);
            cell.setCellValue((Double) UtilFunction.valueOrEmptyNum((Double) field));
        } else {
            logger.warn("Unhandled field type. Value: {}, Class: {}", field, field.getClass());
        }

        if (style != null) {
            cell.setCellStyle(style);
        }
    }

    /**
     * Enables auto-sizing for a column.
     */
    protected static void autoFitColumn(SXSSFSheet sheet, int column) {
        sheet.trackColumnForAutoSizing(column);
        sheet.autoSizeColumn(column);
    }

    /**
     * Gets the style for a specific column from a style list.
     */
    protected static <F> F getStyleForColumn(List<Pair<List<Integer>, F>> styles, Integer column, F defaultStyle) {
        return styles.stream()
                .filter(s -> s != null && s.getFirst().contains(column))
                .findFirst()
                .map(Pair::getSecond)
                .orElse(defaultStyle);
    }

    private static Options getOptionForColumn(List<Pair<List<Integer>, Options>> options, Integer column) {
        if (options == null) return null;

        return options.stream()
                .filter(s -> s != null && s.getFirst().contains(column))
                .findFirst()
                .map(Pair::getSecond)
                .orElse(null);
    }

    /**
     * Creates cells with values, styles, and auto-fit from a list of fields.
     */
    protected static <T> void createCellsWithValuesAndStyles(SXSSFSheet sheet, Row row, int[] colNumber,
                                                             List<T> fields, CellStyle defaultStyle,
                                                             List<Pair<List<Integer>, CellStyle>> styles,
                                                             List<Pair<List<Integer>, Options>> options) {
        int columnIndex = 1;
        for (T field : fields) {
            CellStyle style = getStyleForColumn(styles, columnIndex, defaultStyle);
            Options option = getOptionForColumn(options, columnIndex++);
            createCellWithValueAndStyle(sheet, row, colNumber, field, style, option);
        }
    }

    /**
     * Creates cells with values and styles from a list of fields.
     */
    protected static <T> void createCellsWithValuesAndStyles(SXSSFSheet sheet, Row row, int[] colNumber,
                                                             List<T> fields, CellStyle defaultStyle,
                                                             Options... options) {
        fields.forEach(field -> createCellWithValueAndStyle(sheet, row, colNumber, field, defaultStyle, options));
    }

    /**
     * Creates a cell with value, applies style, auto-fits column, and increments column counter.
     *
     * @param sheet     the sheet containing the row
     * @param row       the row to add the cell to
     * @param colNumber array holding current column number (will be incremented)
     * @param field     the value to set
     * @param style     the style to apply
     * @param options   optional flags (e.g., IGNORE_AUTOFIT)
     */
    protected static <T> void createCellWithValueAndStyle(SXSSFSheet sheet, Row row, int[] colNumber,
                                                          T field, CellStyle style, Options... options) {
        Cell cell = createCell(row, colNumber[0]);
        if (!UtilFunction.present(options, Options.IGNORE_AUTOFIT)) {
            autoFitColumn(sheet, colNumber[0]);
        }
        setValueAndStyle(cell, UtilFunction.nullZero(field), style);
        colNumber[0]++;
    }

    /**
     * Creates a header row from an array of strings with custom styles per column.
     */
    protected static void createHeaderRow(SXSSFSheet sheet, int[] rowNumber, String[] headers,
                                          Integer startColumn, CellStyle defaultStyle,
                                          List<Pair<List<Integer>, CellStyle>> styles,
                                          Options... options) {
        Row row = createRow(sheet, rowNumber[0]++);
        int[] colNumber = {startColumn != null ? startColumn : 0};

        int columnIndex = 1;
        for (String header : headers) {
            CellStyle style = getStyleForColumn(styles, columnIndex++, defaultStyle);
            createCellWithValueAndStyle(sheet, row, colNumber, header, style);
        }

        applyMergeOptions(sheet, rowNumber[0] - 1, headers.length, options);
    }

    /**
     * Creates a header row from an array of strings with a single style.
     */
    protected static void createHeaderRow(SXSSFSheet sheet, int[] rowNumber, String[] headers,
                                          Integer startColumn, CellStyle style, Options... options) {
        Row row = createRow(sheet, rowNumber[0]++);
        int[] colNumber = {startColumn != null ? startColumn : 0};

        for (String header : headers) {
            createCellWithValueAndStyle(sheet, row, colNumber, header, style);
        }

        applyMergeOptions(sheet, rowNumber[0] - 1, headers.length, options);
    }

    private static void applyMergeOptions(SXSSFSheet sheet, int rowIndex, int columnCount, Options... options) {
        if (!UtilFunction.present(options, Options.MERGE_CELL)) {
            return;
        }

        if (UtilFunction.present(options, Options.MERGE_CELL_ALTERNATE)) {
            int start = UtilFunction.present(options, Options.MERGE_CELL_SKIP_FIRST) ? 1 : 0;
            if (columnCount > 2) {
                for (int col = start; col < columnCount - 1; col += 2) {
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, col, col + 1));
                }
            }
        } else {
            sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 0, columnCount - 1));
        }
    }

    /**
     * Creates a standard cell style with the specified options.
     *
     * @param workbook the workbook to create the style in
     * @param bold     whether text should be bold
     * @param styles   style options to apply
     * @return the created cell style
     */
    protected static CellStyle createStandardStyle(SXSSFWorkbook workbook, boolean bold, Styles... styles) {
        CellStyle style = workbook.createCellStyle();

        for (Styles styleOption : styles) {
            switch (styleOption) {
                case STANDARD_BORDER -> applyBorder(style, BorderStyle.THIN, IndexedColors.BLACK);
                case HEADER_FOREGROUND ->
                        applyForegroundColor(style, STANDARD_HEADER_BG, FillPatternType.SOLID_FOREGROUND);
                case HEADER_TEXT -> applyFontAndColor(workbook, style, STANDARD_HEADER_TEXT_COLOR, bold);
                case STANDARD_FOREGROUND -> applyForegroundColor(style, STANDARD_BG, FillPatternType.SOLID_FOREGROUND);
                case STANDARD_TEXT -> applyFontAndColor(workbook, style, STANDARD_TEXT_COLOR, bold);
                case MULTI_LINE -> applyMultiLine(style);
                case ALIGN_HORIZONTAL_CENTER -> applyHorizontalAlignment(style, HorizontalAlignment.CENTER);
                case ALIGN_VERTICAL_CENTER -> applyVerticalAlignment(style, VerticalAlignment.CENTER);
            }
        }
        return style;
    }

    /**
     * Creates a custom cell style with parameterized options.
     */
    @SafeVarargs
    protected static CellStyle createCustomStyle(SXSSFWorkbook workbook, boolean bold,
                                                 Pair<Styles, List<Object>>... styles) {
        CellStyle style = workbook.createCellStyle();

        for (Pair<Styles, List<Object>> styleOption : styles) {
            Styles styleType = styleOption.getFirst();
            List<Object> params = styleOption.getSecond();

            switch (styleType) {
                case STANDARD_BORDER -> applyBorder(style, BorderStyle.THIN, IndexedColors.BLACK);
                case STANDARD_FOREGROUND ->
                        applyForegroundColor(style, getColor(params.get(0)), FillPatternType.SOLID_FOREGROUND);
                case STANDARD_TEXT -> applyFontAndColor(workbook, style, getColor(params.get(0)), bold);
                case MULTI_LINE -> applyMultiLine(style);
                case ALIGN_HORIZONTAL_CENTER -> applyHorizontalAlignment(style, HorizontalAlignment.CENTER);
                case ALIGN_VERTICAL_CENTER -> applyVerticalAlignment(style, VerticalAlignment.CENTER);
                default -> { /* No action for other styles */ }
            }
        }
        return style;
    }

    /**
     * Applies borders to a cell style.
     */
    protected static CellStyle applyBorder(CellStyle style, BorderStyle border, IndexedColors color) {
        style.setBorderRight(border);
        style.setRightBorderColor(color.getIndex());
        style.setBorderBottom(border);
        style.setBottomBorderColor(color.getIndex());
        style.setBorderLeft(border);
        style.setLeftBorderColor(color.getIndex());
        style.setBorderTop(border);
        style.setTopBorderColor(color.getIndex());
        return style;
    }

    /**
     * Applies foreground color to a cell style.
     */
    protected static CellStyle applyForegroundColor(CellStyle style, IndexedColors color, FillPatternType pattern) {
        style.setFillForegroundColor(color.getIndex());
        style.setFillPattern(pattern);
        return style;
    }

    /**
     * Applies font and color to a cell style.
     */
    protected static CellStyle applyFontAndColor(SXSSFWorkbook workbook, CellStyle style,
                                                 IndexedColors color, boolean bold) {
        Font font = workbook.createFont();
        font.setColor(color.getIndex());
        font.setBold(bold);
        style.setFont(font);
        return style;
    }

    /**
     * Enables text wrapping for multi-line content.
     * Note: Remember to set the row height with row.setHeightInPoints(2 * sheet.getDefaultRowHeightInPoints())
     */
    protected static CellStyle applyMultiLine(CellStyle style) {
        style.setWrapText(true);
        return style;
    }

    /**
     * Applies horizontal alignment to a cell style.
     */
    protected static CellStyle applyHorizontalAlignment(CellStyle style, HorizontalAlignment alignment) {
        style.setAlignment(alignment);
        return style;
    }

    /**
     * Applies vertical alignment to a cell style.
     */
    protected static CellStyle applyVerticalAlignment(CellStyle style, VerticalAlignment alignment) {
        style.setVerticalAlignment(alignment);
        return style;
    }

    /**
     * Available colors for cell styling.
     */
    public enum Colors {
        BLUE, ORANGE, YELLOW, GRAY, LIGHT_GREEN, WHITE, BLACK
    }

    /**
     * Style options for cells.
     */
    protected enum Styles {
        STANDARD_BORDER,
        HEADER_FOREGROUND, HEADER_TEXT,
        STANDARD_FOREGROUND, STANDARD_TEXT,
        MULTI_LINE, ALIGN_HORIZONTAL_CENTER, ALIGN_VERTICAL_CENTER
    }

    /**
     * Options for cell operations.
     */
    protected enum Options {
        IGNORE_AUTOFIT, MERGE_CELL, MERGE_CELL_ALTERNATE, MERGE_CELL_SKIP_FIRST
    }
}
