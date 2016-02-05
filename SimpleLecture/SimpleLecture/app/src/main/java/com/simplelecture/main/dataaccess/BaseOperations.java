package com.simplelecture.main.dataaccess;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;

import com.simplelecture.main.database.DataBaseOperations;


public abstract class BaseOperations {
	protected DataBaseOperations dbOperations;

	protected BaseOperations() {
		dbOperations = new DataBaseOperations();
	}

	// Gets the primary keys to Sync the next set of rows
	public List<String> getKeyColumn(String fieldname, String tabname,
			String orderby) {
		List<String> keyList = null;
		try {
			String query = "SELECT DISTINCT " + fieldname + " FROM " + tabname
					+ " Order by  " + orderby;
			Cursor cursor = dbOperations.executeRawQuery(query);
			keyList = getKeyColumnResultSet(cursor);
			cursor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			throw new RuntimeException(e.toString());
		}
		return keyList;
	}

	// Gets the specific set of rows for deletion
	public List<String> getRows(String fieldname, String key, String tabname,
			String value) {
		List<String> keyList = null;
		try {
			String query = "SELECT " + fieldname + " FROM " + tabname
					+ " Where " + key + " in (" + value + ")";
			Cursor cursor = dbOperations.executeRawQuery(query);
			keyList = getKeyColumnResultSet(cursor);
			cursor.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			throw new RuntimeException(e.toString());
		}
		return keyList;
	}

	private List<String> getKeyColumnResultSet(Cursor curObj) {
		List<String> keyList = new ArrayList<String>();
		try {
			while (curObj.moveToNext()) {
				keyList.add(curObj.getString(0));
			}
			return keyList;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}
	}

	public static String getDateformat(String column) {
		return "strftime('%Y-%m-%d %H:%M:%S', " + column + ")";
	}
}
