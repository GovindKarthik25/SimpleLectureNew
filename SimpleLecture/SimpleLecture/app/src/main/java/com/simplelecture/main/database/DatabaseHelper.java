/**
 * Module: YAP
 * Package Name : com.webtraits.yap
 * Source File : DatabaseHelper.java 
 * Author: karthik.rao, SMNetserv, Bangalore
 *
 */
package com.simplelecture.main.database;


import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.simplelecture.main.constants.Constants;
import com.simplelecture.main.splash.Controller;

/**
 * @since Yap 1.0 Description: Class used for database and table creation.
 * 
 */
public class DatabaseHelper extends SQLiteOpenHelper {
	public static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = Constants.DATABASE_NAME;

	public static final String USERDETAILS_TABLE = Constants.USERDETAILS_TABLE_NAME;
	public static final String ADDTOCART_TABLE = Constants.ADDTOCART_TABLE_NAME;


	private final String DATABASE_CREATE_USERDETAILS = "CREATE TABLE IF NOT EXISTS "
			+ USERDETAILS_TABLE
			+ " ( "
			+ DBColumns.id
			+ " integer primary key autoincrement, "
			+ DBColumns.displaysequence
			+ " integer null, "
			+ DBColumns.pagesectionid
			+ " integer null, "
			+ DBColumns.displaytext
			+ "  VARCHAR null, "
			+ DBColumns.name
			+ " VARCHAR null)";


	private final String DATABASE_CREATE_ADDTOCART = "CREATE TABLE IF NOT EXISTS "
			+ ADDTOCART_TABLE
			+ " ( "
			+ DBColumns.id
			+ " integer primary key autoincrement, "
			+ DBColumns.displaysequence
			+ " integer null, "
			+ DBColumns.pagesectionid
			+ " integer null, "
			+ DBColumns.displaytext
			+ "  VARCHAR null, "
			+ DBColumns.name
			+ " VARCHAR null)";



	public DatabaseHelper() {
		super(Controller.context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/*
	 * Creates a Table in the DatBase
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(DATABASE_CREATE_USERDETAILS);
		db.execSQL(DATABASE_CREATE_ADDTOCART);

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Log.w("DatabaseHelper", "Upgrading database from version " +
		// oldVersion + " to " + newVersion +
		// ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_USERDETAILS);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_ADDTOCART);


		onCreate(db);
	}

}
