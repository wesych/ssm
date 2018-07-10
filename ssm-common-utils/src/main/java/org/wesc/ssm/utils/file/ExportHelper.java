package org.wesc.ssm.utils.file;

import java.io.*;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 用于导出Excel文档的工具类.
 */
public class ExportHelper implements Closeable {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(ExportHelper.class);

    /** 准备导出的Workbook. */
    private XSSFWorkbook workbook = new XSSFWorkbook();

    /** 准备导出的Sheet. */
    private XSSFSheet sheet = workbook.createSheet();
    
    /** 标题单元格样式. */
    private XSSFCellStyle headerCellStyle;
    
    /** 单元格样式：日期类型. */
    private XSSFCellStyle dateCellStyle;
    
    /** 内容自动换行*/
    private XSSFCellStyle varcharStyle;

    /** 每一列的宽度(单位为px需要转换). */
    private List<Integer> columnWidths;
    
    /** 最后一行的行号. */
    private int lastRow = 0;
    
    /** 最多列数. */
    private int maxColumnCount = 0;
    
    /**
     * 初始化.
     */
    public ExportHelper() {
        // Date
        XSSFDataFormat fmt = workbook.createDataFormat();
        fmt.putFormat((short) 200, "yyyy-MM-dd");
        
        dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat((short) 200);
        
        varcharStyle = workbook.createCellStyle();
        varcharStyle.setWrapText(true);
        
        // Header
        headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    /**
     * 初始化.  (合并单元格)
     */
    public ExportHelper(int firstRow,int lastRow,int firstCol,int lastCol) {
        // Date
        XSSFDataFormat fmt = workbook.createDataFormat();
        fmt.putFormat((short) 200, "yyyy-MM-dd");

        dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat((short) 200);

        varcharStyle = workbook.createCellStyle();
        varcharStyle.setWrapText(true);
        CellRangeAddress region = new CellRangeAddress(firstRow, // first row
                lastRow, // last row
                firstCol, // first column
                lastCol // last column
        );
        sheet.addMergedRegion(region);
        
        // Header
        headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    @Override
    public void close() throws IOException {
        workbook.close();
    }
    
    /**
     * 设置每一列宽度，单位为px.
     * Excel中单位不为px，ExportHelper将对单位进行转换，但并不能保证完全一致
     * @param widths 每一列的宽度
     */
    public void columnWidths(List<Integer> widths) {
        this.columnWidths = widths;
    }

    /**
     * 导出表格头.
     * @param headers 表格头
     */
    public void addHeaders(List<String> headers) {
        XSSFRow row = sheet.createRow(lastRow++);
        for (int j = 0; j < headers.size(); j++) {
            XSSFCell cell = row.createCell(j);
            cell.setCellValue(headers.get(j));
            cell.setCellStyle(headerCellStyle);
        }
        maxColumnCount = Math.max(maxColumnCount, headers.size());
    }
 
    /**
     * 导出一行数据.
     * @param row  Excel行
     * @param data 导出的数据，支持数值类型、String、Date、Boolean
     */
    private void addRow(XSSFRow row, List<Object> data) {
        maxColumnCount = Math.max(maxColumnCount, data.size());
        for (int j = 0; j < data.size(); j++) {
            XSSFCell cell = row.createCell(j);
            Object value = data.get(j);
            
            if (value == null) {
                
            } else if (value instanceof String) {
                cell.setCellValue((String) value);
                cell.setCellStyle(varcharStyle);
            } else if (value instanceof Integer) {
                cell.setCellValue((Integer) value);
            } else if (value instanceof Long) {
                cell.setCellValue((Long) value);
            } else if (value instanceof Float) {
                cell.setCellValue((Float) value);
            } else if (value instanceof Double) {
                cell.setCellValue((Double) value);
            } else if (value instanceof Date) {
                cell.setCellValue((Date) value);
                cell.setCellStyle(dateCellStyle);
            } else if (value instanceof Boolean) {
                cell.setCellValue(((Boolean) value) ? "是" : "否");
            } else {
                cell.setCellValue(value.toString());
            }
        }
    }
 
    /**
     * 导出一行数据.
     * @param data  导出的数据，支持数值类型、String、Date、Boolean
     * @param lines 行高，单位为行
     */
    public void addRow(List<Object> data, int lines) {
        XSSFRow row = sheet.createRow(lastRow++);
        addRow(row, data);
        row.setHeightInPoints((lines * sheet.getDefaultRowHeightInPoints()));
    }

    /**
     * 导出一行数据.
     * @param data  导出的数据，支持数值类型、String、Date、Boolean
     */
    public void addRow(List<Object> data) {
        XSSFRow row = sheet.createRow(lastRow++);
        addRow(row, data);
    }

    /**
     * 生成Excel文件内容到输出流.
     * @param out               输出流
     */
    public void export(OutputStream out) {
        try {
            for (int i = 0; i < maxColumnCount; i++) {
                if (columnWidths != null && columnWidths.size() > i) {
                    sheet.setColumnWidth(i, columnWidths.get(i) * 25);
                } else {
                    sheet.autoSizeColumn(i);
                }
            }
            
            workbook.write(out);
        } catch (IOException e) {
            LOGGER.error("Failed to export excel file", e);
        }
    }


    /**
     * 生成Excel文件.
     * @param filePath        导出文件路径
     */
    public void export(String filePath) {
        try {
            FileOutputStream fout = new FileOutputStream(filePath);
            export(fout);
            fout.close();
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Failed to export excel file", e);
        } catch (IOException e) {
            LOGGER.error("Failed to export excel file", e);
        }
    }
}
