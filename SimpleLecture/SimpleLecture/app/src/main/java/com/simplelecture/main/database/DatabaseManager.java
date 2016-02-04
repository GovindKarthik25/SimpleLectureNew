package com.simplelecture.main.database;

import android.database.sqlite.SQLiteDatabase;

public class DatabaseManager
{
	private int openCounter;

	private static DatabaseManager instance;
	private static DatabaseHelper databaseHelper;
	private SQLiteDatabase database;

	public static synchronized void upgradeDb()
	{
		DatabaseManager databaseManager = new DatabaseManager();
		databaseManager.openDatabase();

		DatabaseHelper helper = new DatabaseHelper();
		helper.onCreate(databaseManager.database);

		databaseManager.closeDatabase();
	}

	public static synchronized void initializeInstance(DatabaseHelper helper)
	{
		if (instance == null)
		{
			instance = new DatabaseManager();
			databaseHelper = helper;
		}
	}

	public static synchronized DatabaseManager getInstance()
	{
		if (instance == null)
		{
			// throw new IllegalStateException(DatabaseManager.class.getSimpleName()
			// + " is not initialized, call initializeInstance(..) method first.");
			return null;
		}
		return instance;
	}

	public synchronized SQLiteDatabase openDatabase()
	{
		openCounter++;
		if (openCounter == 1)
		{
			// Opening new database
			database = databaseHelper.getWritableDatabase();
			// database = SQLiteDatabase.openOrCreateDatabase("/data/data/"
			// + Controller.context.getPackageName() + "/database.sqlite", null);
		}
		return database;
	}

	public synchronized void closeDatabase()
	{
		openCounter = 0;
		if (openCounter == 0)
		{
			// Closing database
			if (database != null)
				database.close();

		}
	}
}
