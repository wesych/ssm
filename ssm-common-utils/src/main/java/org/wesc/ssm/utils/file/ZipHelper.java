package org.wesc.ssm.utils.file;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZipHelper.class);

    private ZipHelper() {

    }

    public static void doCompress(String srcFile, String zipFile) {
        doCompress(new File(srcFile), new File(zipFile));
    }

    /**
     * 导出一行数据.
     *
     * @param row  Excel行
     * @param data 导出的数据，支持数值类型、String、Date、Boolean
     */
    public static void addRow(XSSFWorkbook workbook, XSSFRow row, List<Object> data) {

        /** 单元格样式：日期类型. */
        XSSFCellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat((short) 200);

        /** 内容自动换行*/
        XSSFCellStyle varcharStyle = workbook.createCellStyle();
        varcharStyle.setWrapText(true);

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
     * 文件压缩
     *
     * @param srcFile 目录或者单个文件
     * @param zipFile 压缩后的ZIP文件
     */
    public static void doCompress(File srcFile, File zipFile) {
        try (ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipFile))) {
            doCompress(srcFile, out);
        } catch (IOException e) {
            LOGGER.error("Failed to export excel file", e);
        }
    }

    public static void doCompress(String filelName, ZipOutputStream out) {
        doCompress(new File(filelName), out);
    }

    public static void doCompress(File file, ZipOutputStream out) {
        doCompress(file, out, "");
    }

    public static void doCompress(File inFile, ZipOutputStream out, String dir) {
        if (inFile.isDirectory()) {
            File[] files = inFile.listFiles();
            if (files != null && files.length > 0) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir + "/" + name;
                    }
                    doCompress(file, out, name);
                }
            }
        } else {
            doZip(inFile, out, dir);
        }
    }

    public static void doZip(File inFile, ZipOutputStream out, String dir) {
        String entryName;
        if (!"".equals(dir)) {
            entryName = dir + "/" + inFile.getName();
        } else {
            entryName = inFile.getName();
        }
        ZipEntry entry = new ZipEntry(entryName);
        int len;
        byte[] buffer = new byte[1024];
        try {
            out.putNextEntry(entry);
        } catch (IOException e) {
            LOGGER.error("Failed to export excel file", e);
        }
        try (FileInputStream fis = new FileInputStream(inFile);) {
            while ((len = fis.read(buffer)) > 0) {
                out.write(buffer, 0, len);
                out.flush();
            }
            out.closeEntry();
        } catch (IOException e) {
            LOGGER.error("Failed to export excel file", e);
        }

    }

}