package pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * PDF page event handler for adding content at the end of each page.
 * Demonstrates how to use PdfPageEventHelper to add headers/footers.
 */
public class MyEvent extends PdfPageEventHelper {

    private static final Font TIMES_ROMAN_12_BOLD = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    private boolean canAdd = false;

    public boolean isCanAdd() {
        return canAdd;
    }

    public void setCanAdd(boolean canAdd) {
        this.canAdd = canAdd;
    }

    @Override
    public void onEndPage(final PdfWriter writer, final Document document) {
        if (canAdd) {
            try {
                Paragraph title = new Paragraph("PAGE FOOTER TEXT", TIMES_ROMAN_12_BOLD);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(10f);
                document.add(title);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }
}
