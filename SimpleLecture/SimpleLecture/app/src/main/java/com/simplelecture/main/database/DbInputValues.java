package com.simplelecture.main.database;

import android.content.ContentValues;

public class DbInputValues {

	private ContentValues cv;

	public DbInputValues() {
		this.cv = new ContentValues();
	}

	public void put(String column, Boolean value) {
		this.cv.put(column, value);
	}

	public void put(String column, Byte value) {
		this.cv.put(column, value);
	}

	public void put(String column, byte[] value) {
		this.cv.put(column, value);
	}

	public void put(String column, Double value) {
		this.cv.put(column, value);
	}

	public void put(String column, Float value) {
		this.cv.put(column, value);
	}

	public void put(String column, Integer value) {
		this.cv.put(column, value);
	}

	public void put(String column, Long value) {
		this.cv.put(column, value);
	}

	public void put(String column, Short value) {
		this.cv.put(column, value);
	}

	public void put(String column, String value) {
		this.cv.put(column, value);
	}

	public void put(String column, boolean value) {
		this.cv.put(column, value ? 1 : 0);
	}

	public ContentValues getContentValues() {
		return cv;
	}
}