package org.wesc.ssm.utils.file;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于导入Excel模板的工具类.
 */
public class ImportHelper implements Closeable {

	private static final Logger LOGGER = LoggerFactory.getLogger(ImportHelper.class);
	
	/** excel文件*/
	private File file;
	
	/** sheet序号*/
	private int sheetIndex;
	
	/** 属性列数*/
	private int attributesCount;

	/**
	 * Constructor.
	 * @param file
	 * @param sheetIndex
	 * @param attributesCount
	 */
	public ImportHelper(File file, int sheetIndex, int attributesCount) {
		this.file = file;
		this.sheetIndex = sheetIndex;
		this.attributesCount = attributesCount;
	}

	/**
	 * 对外提供读取excel 的方法
	 */
	public List<List<Object>> readExcel() throws IOException {
		if (file == null) {
			throw new IOException("文件不存在");
		}
		String fileName = file.getName();
		String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName.substring(fileName.lastIndexOf(".") + 1);
		if ("xls".equals(extension)) {
			return read2003Excel(file, sheetIndex);
		} else if ("xlsx".equals(extension)) {
			return read2007Excel(file, sheetIndex);
		} else {
			LOGGER.error("Unsupported excel file");
			throw new IOException("不支持的文件类型");
		}
	}

	/**
	 * 读取 office 2003 excel
	 * 
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private List<List<Object>> read2003Excel(File file, int sheetIndex) throws IOException {
		List<List<Object>> list = new LinkedList<>();
		HSSFWorkbook hwb = new HSSFWorkbook(new FileInputStream(file));
		HSSFSheet sheet = hwb.getSheetAt(sheetIndex);
		Object value;
		HSSFRow row;
		HSSFCell cell;
		int counter = 0;
		for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			} else {
				counter++;
			}
			List<Object> linked = new LinkedList<>();
			for (int j = 0; j < attributesCount; j++) {
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				DecimalFormat nf = new DecimalFormat("0");
				switch (cell.getCellType()) {
				case STRING:
					value = cell.getStringCellValue();
					break;
				case NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					}
					break;
				case BOOLEAN:
					value = cell.getBooleanCellValue();
					break;
				case BLANK:
					value = "";
					break;
				default:
					value = cell.toString();
				}
				if (value == null || "".equals(value)) {
					continue;
				}
				linked.add(value);
			}
			list.add(linked);
		}
		hwb.close();
		return list;
	}

	/**
	 * 读取Office 2007 excel
	 */
	private List<List<Object>> read2007Excel(File file, int sheetIndex) throws IOException {
		List<List<Object>> list = new LinkedList<>();
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = xwb.getSheetAt(sheetIndex);
		Object value;
		XSSFRow row;
		XSSFCell cell;
		int counter = 0;
		for (int i = sheet.getFirstRowNum(); counter < sheet.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			} else {
				counter++;
			}
			List<Object> linked = new LinkedList<>();
			for (int j = 0; j < attributesCount; j++) {
				cell = row.getCell(j);
				if (cell == null) {
					value = "";
					linked.add(value);
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");															
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				DecimalFormat nf = new DecimalFormat("0");
				switch (cell.getCellType()) {
				case STRING:
					value = cell.getStringCellValue();
					break;
				case NUMERIC:
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());
					} else if ("General".equals(cell.getCellStyle().getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell.getNumericCellValue()));
					}
					break;
				case BOOLEAN:
					value = cell.getBooleanCellValue();
					break;
				case BLANK:
					value = "";
					break;
				default:
					value = cell.toString();
				}
				linked.add(value);
			}
			list.add(linked);
		}
		xwb.close();
		return list;
	}
	
	@Override
	public void close() throws IOException {
	}
	
}
