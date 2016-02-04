package com.simplelecture.main.database;

import java.io.Closeable;
import java.util.UUID;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

public class DbSQLiteStatementInputValues implements Closeable {

	private SQLiteStatement statement;
	private int index = 1;

	public DbSQLiteStatementInputValues(SQLiteDatabase database, String query) {
		this.statement = database.compileStatement(query);
	}

	public void bindNull() {
		this.statement.bindNull(index++);
	}

	public void bind(String value) {
		if (value == null)
			bindNull();
		else
			this.statement.bindString(index++, value);
	}

	public void bind(UUID value) {
		if (value == null)
			bindNull();
		else
			this.statement.bindString(index++, value.toString());
	}

	public void bind(Integer value) {
		if (value == null)
			bindNull();
		else
			this.statement.bindLong(index++, value);
	}

	public void bind(int value) {
		this.statement.bindLong(index++, value);
	}

	public void bind(Long value) {
		if (value == null)
			bindNull();
		else
			this.statement.bindLong(index++, value);
	}

	public void bind(long value) {
		this.statement.bindLong(index++, value);
	}

	public void bind(Float value) {
		if (value == null)
			bindNull();
		else
			this.statement.bindDouble(index++, value);
	}

	public void bind(float value) {
		this.statement.bindDouble(index++, value);
	}

	public void bind(Double value) {
		if (value == null)
			bindNull();
		else
			this.statement.bindDouble(index++, value);
	}

	public void bind(double value) {
		this.statement.bindDouble(index++, value);
	}

	public void bind(Boolean value) {
		if (value == null)
			bindNull();
		else
			this.statement.bindLong(index++, value ? 1 : 0);
	}

	public void executeInsert() {
		statement.executeInsert();
	}

	public SQLiteStatement getSqLiteStatement() {
		return statement;
	}

	public void clearBindings() {
		statement.clearBindings();
		index = 1;
	}

	@Override
	public void close() {
		if (this.statement != null) {
			this.statement.close();
			this.statement = null;
		}
	}
}