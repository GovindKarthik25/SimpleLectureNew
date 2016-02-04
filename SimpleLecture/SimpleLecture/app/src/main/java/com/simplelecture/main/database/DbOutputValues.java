package com.simplelecture.main.database;

import java.math.BigDecimal;

import android.database.Cursor;

public class DbOutputValues {

	private Cursor cursor;

	public DbOutputValues(Cursor cursor) {
		this.cursor = cursor;
	}

	public int getColumnCount() {
		return cursor.getColumnCount();
	}

	public byte[] getBlob(String columnName) {
		int idx = getColumnIndex(columnName);
		return idx == -1 ? new byte[0] : cursor.getBlob(idx);
	}

	/**
	 * Original method get a double value from current cursor
	 * 
	 * @return null if the column in the cursor does not exist 0.00 if the value
	 *         of the column in current cursor is null and double value if the
	 *         value in the column in current cursor exists
	 */
	public Double getDouble(String columnName) {
		int idx = getColumnIndex(columnName);
		return idx == -1 ? null : cursor.getDouble(idx);
	}

	/**
	 * Method get a double value from current cursor
	 * 
	 * @return null if the column in the cursor does not exist null if the value
	 *         of the column in current cursor is null and double value if the
	 *         value in the column in current cursor exists
	 */
	public Double getNullableDouble(String columnName) {
		Double result = null;

		int idx = getColumnIndex(columnName);
		if (idx != -1 && !cursor.isNull(idx)) {
			result = cursor.getDouble(idx);
		}
		return result;
	}

	public Float getFloat(String columnName) {
		int idx = getColumnIndex(columnName);
		return idx == -1 ? null : cursor.getFloat(idx);
	}

	/**
	 * Original method get a integer value from current cursor
	 * 
	 * @return null if the column in the cursor does not exist 0 if the value of
	 *         the column in current cursor is null and integer value if the
	 *         value in the column in current cursor exists
	 */
	public Integer getInt(String columnName) {
		int idx = getColumnIndex(columnName);
		return idx == -1 ? null : cursor.getInt(idx);
	}

	/**
	 * Original method get a integer value from current cursor
	 * 
	 * @return null if the column in the cursor does not exist null if the value
	 *         of the column in current cursor is null and integer value if the
	 *         value in the column in current cursor exists
	 */
	public Integer getInteger(String columnName) {
		Integer result = null;
		int idx = getColumnIndex(columnName);
		if (idx != -1 && !cursor.isNull(idx)) {
			result = cursor.getInt(idx);
		}
		return result;
	}

	public Long getLong(String columnName) {
		int idx = getColumnIndex(columnName);
		return idx == -1 ? null : cursor.getLong(idx);
	}

	public Short getShort(String columnName) {
		int idx = getColumnIndex(columnName);
		return idx == -1 ? null : cursor.getShort(idx);
	}

	public String getString(String columnName) {
		int idx = getColumnIndex(columnName);
		return idx == -1 ? null : cursor.getString(idx);
	}

	public Boolean getBoolean(String columnName) {
		int idx = getColumnIndex(columnName);
		return idx == -1 ? null : cursor.getInt(idx) == 1 ? true : false;
	}

	public BigDecimal getBigDecimal(String columnName) {
		int idx = getColumnIndex(columnName);
		if (idx == -1) {
			return null;
		}

		String stringValue = cursor.getString(idx);

		return stringValue == null ? null : new BigDecimal(stringValue);
	}

	private int getColumnIndex(String columnName) {
		return cursor.getColumnIndex(columnName);
	}
}