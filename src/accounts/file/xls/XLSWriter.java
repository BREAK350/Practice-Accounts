package accounts.file.xls;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.UnderlineStyle;
import jxl.write.Formula;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import accounts.file.FileWriter;
import accounts.model.AccountsData;

public class XLSWriter implements FileWriter {
	private WritableCellFormat timesBoldUnderline;
	private WritableCellFormat times;

	public void writeIn(AccountsData accsData, File file) {
		WorkbookSettings wbSettings = new WorkbookSettings();

		wbSettings.setLocale(new Locale("en", "EN"));

		WritableWorkbook workbook;
		try {
			workbook = Workbook.createWorkbook(file, wbSettings);
			workbook.createSheet("Report", 0);
			WritableSheet excelSheet = workbook.getSheet(0);
			createLabel(excelSheet);
			createContent(excelSheet);

			workbook.write();
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		}
	}

	private void createLabel(WritableSheet sheet) throws WriteException {
		// Lets create a times font
		WritableFont times10pt = new WritableFont(WritableFont.TIMES, 10);
		// Define the cell format
		times = new WritableCellFormat(times10pt);
		// Lets automatically wrap the cells
		times.setWrap(true);

		// create create a bold font with unterlines
		WritableFont times10ptBoldUnderline = new WritableFont(
				WritableFont.TIMES, 10, WritableFont.BOLD, false,
				UnderlineStyle.SINGLE);
		timesBoldUnderline = new WritableCellFormat(times10ptBoldUnderline);
		// Lets automatically wrap the cells
		timesBoldUnderline.setWrap(true);

		CellView cv = new CellView();
		cv.setFormat(times);
		cv.setFormat(timesBoldUnderline);
		cv.setAutosize(true);

		// Write a few headers
		addCaption(sheet, 0, 0, "ПІБ");
		addCaption(sheet, 1, 0, "Відпрацьовані дні");
		addCaption(sheet, 2, 0, "За свій рахунок");
		addCaption(sheet, 3, 0, "Лікарняні");
		addCaption(sheet, 4, 0, "Зарплата, eur");
		addCaption(sheet, 5, 0, "Сума, eur");
		addCaption(sheet, 6, 0, "Сума, грн");

	}

	private void createContent(WritableSheet sheet) throws WriteException,
			RowsExceededException {
		// Write a few number
		for (int i = 1; i < 10; i++) {
			// First column
			addNumber(sheet, 0, i, i + 10);
			// Second column
			addNumber(sheet, 1, i, i * i);
		}
		// Lets calculate the sum of it
		StringBuffer buf = new StringBuffer();
		buf.append("SUM(A2:A10)");
		Formula f = new Formula(0, 10, buf.toString());
		sheet.addCell(f);
		buf = new StringBuffer();
		buf.append("SUM(B2:B10)");
		f = new Formula(1, 10, buf.toString());
		sheet.addCell(f);

		// now a bit of text
		for (int i = 12; i < 20; i++) {
			// First column
			addLabel(sheet, 0, i, "Boring text " + i);
			// Second column
			addLabel(sheet, 1, i, "Another text");
		}
	}

	private void addCaption(WritableSheet sheet, int column, int row, String s)
			throws RowsExceededException, WriteException {
		Label label;
		label = new Label(column, row, s, timesBoldUnderline);
		sheet.addCell(label);
	}

	private void addNumber(WritableSheet sheet, int column, int row,
			Integer integer) throws WriteException, RowsExceededException {
		Number number;
		number = new Number(column, row, integer, times);
		sheet.addCell(number);
	}

	private void addLabel(WritableSheet sheet, int column, int row, String s)
			throws WriteException, RowsExceededException {
		Label label;
		label = new Label(column, row, s, times);
		sheet.addCell(label);
	}

}
