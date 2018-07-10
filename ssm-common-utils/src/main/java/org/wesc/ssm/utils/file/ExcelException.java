package org.wesc.ssm.utils.file;

import java.util.List;

/**
 * Excel错误信息列表
 * @author zhangwei
 */
public class ExcelException {
	
	public static final String ERROR_MSG_BLANK_ROW = "整行数据为空";
	public static final String ERROR_MSG_BLANK_FIELD = "属性列字段为空";
	public static final String ERROR_MSG_DATE_FORMAT = "日期格式错误";
	
	private Integer row;
	
	private Integer column;
	
	private String attribute;
	
	private String message;
	
	public ExcelException(Integer row, Integer column, String attribute, String message) {
		this.row = row;
		this.column = column;
		this.attribute = attribute;
		this.message = message;
	}

	/**
	 * @return the row
	 */
	public Integer getRow() {
		return row;
	}

	/**
	 * @param row the row to set
	 */
	public void setRow(Integer row) {
		this.row = row;
	}

	/**
	 * @return the column
	 */
	public Integer getColumn() {
		return column;
	}

	/**
	 * @param column the column to set
	 */
	public void setColumn(Integer column) {
		this.column = column;
	}

	/**
	 * @return the attribute
	 */
	public String getAttribute() {
		return attribute;
	}

	/**
	 * @param attribute the attribute to set
	 */
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 显示错误信息
	 * @param list
	 * @return
	 */
	public static String getErrorMessage(List<ExcelException> list) {
		StringBuilder sb = new StringBuilder();
		for(ExcelException msg : list) {
			sb.append("{错误位置：" + msg.attribute + "（" + msg.row + "行" + msg.column + "列 ）， " + "错误原因：" + msg.message + "}</br>");
		}
		
		return sb.toString();
	}

}
