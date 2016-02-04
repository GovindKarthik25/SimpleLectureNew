/**
 * 
 */
package com.simplelecture.main.database;

import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Wrapper for Opening and Closing the Database
 * 
 * @author udaykumar.bh
 * 
 */
public class DataBaseOperations
{
	// Database Fields
	public SQLiteDatabase database;

	public DataBaseOperations()
	{
		if (DatabaseManager.getInstance() == null)
			DatabaseManager.initializeInstance(new DatabaseHelper());
		database = DatabaseManager.getInstance().openDatabase();
	}

	public void closeDB()
	{
		DatabaseManager.getInstance().closeDatabase();
	}

	public void dropTable(String tableName)
	{
		try
		{
			database.execSQL("DROP TABLE '" + tableName + "'");
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}
	}

	public long update(String table, ContentValues values, String whereClause,
			String[] whereArgs)
	{
		try
		{
			return database.update(table, values, whereClause, whereArgs);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}
	}

	public long insert(String table, ContentValues contentValues)
	{
		try
		{
			return database.insertOrThrow(table, null, contentValues);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}
	}

	public void execSql(String query)
	{
		try
		{
			database.execSQL(query);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}
	}

	public Cursor executeRawQuery(String query)
	{
		try
		{
			return database.rawQuery(query, null);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}
	}

	public Cursor executeRawQuery(String query, String... args)
	{
		try
		{
			return database.rawQuery(query, args);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}
	}

	public  Cursor executeRawQuery(String query, List<String> args)
	{
		String[] argsArray = new String[args.size()];
		return executeRawQuery(query, args.toArray(argsArray));
	}

	public int delete(String table, String whereClause, String[] whereArgs)
	{
		try
		{
			return database.delete(table, whereClause, whereArgs);
		} catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.toString());
		}
	}

}
