/**
 * Module: simplelecture
 * Package Name : com.simplelecture.main.dataaccess
 * Source File : UserOperations.java
 * Author: karthik.rao, Bangalore
 *
 */
package com.simplelecture.main.dataaccess;

import android.database.Cursor;

import com.simplelecture.main.database.DBColumns;
import com.simplelecture.main.database.DatabaseHelper;
import com.simplelecture.main.database.DbInputValues;
import com.simplelecture.main.database.DbOutputValues;
import com.simplelecture.main.database.DbSQLiteStatementInputValues;
import com.simplelecture.main.model.ReturnCodes;
import com.simplelecture.main.model.UserModel;


public class UserOperations extends BaseOperations {

	private String USER_TABLE = DatabaseHelper.USERDETAILS_TABLE;
	
	private final String QUERY_USER = "Select * from " + USER_TABLE;

	private final String QUERY_COUNTBY_USER = "select " + DBColumns.userid
			
			+ " from " + USER_TABLE 
			
			+ " where " + DBColumns.userid + " =? ";

	public UserModel getUserData() {
		try {
			UserModel userModel = new UserModel();
			Cursor cursor = dbOperations.executeRawQuery(QUERY_USER);
			DbOutputValues ov = new DbOutputValues(cursor);
			while (cursor.moveToNext()) {
				userModel = createUser(ov);
			}
			cursor.close();
			return userModel;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private UserModel createUser(DbOutputValues ov) {
		try {
			UserModel userModel = new UserModel();
			userModel.setUserID(ov.getString(DBColumns.userid));
			userModel.setUserName(ov.getString(DBColumns.username));
			userModel.setFirstName(ov.getString(DBColumns.firstname));
			userModel.setLastName(ov.getString(DBColumns.lastname));
			userModel.setEmailID(ov.getString(DBColumns.emailid));
			userModel.setPassword(ov.getString(DBColumns.password));

			return userModel;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create a new Customer using the data provided. If the Customer is
	 * successfully inserted return the new rowId for inserted data, otherwise return
	 * a -1 to indicate failure.
	 */
	public int insertUser(UserModel userDataObj) {

		String query_INSERT = "INSERT INTO " + USER_TABLE + " ("
				+ DBColumns.userid + ", " + DBColumns.username + ", "
				+ DBColumns.firstname + ", " + DBColumns.lastname + ", "
				+ DBColumns.emailid + ", " + DBColumns.userstatus + ", "
				+ DBColumns.apikey + ", " + DBColumns.synctime + ", "
				+ DBColumns.password + ") " + "VALUES (?,?,?,?,?,?,?,?, ?)";

		final DbSQLiteStatementInputValues insertAlertMessageStatement = new DbSQLiteStatementInputValues(
				dbOperations.database, query_INSERT);

		dbOperations.database.beginTransaction();

		try {
			insertAlertMessageStatement.clearBindings();
			insertAlertMessageStatement.bind(userDataObj.getUserID());
			insertAlertMessageStatement.bind(userDataObj.getUserName());
			insertAlertMessageStatement.bind(userDataObj.getFirstName());
			insertAlertMessageStatement.bind(userDataObj.getLastName());
			insertAlertMessageStatement.bind(userDataObj.getEmailID());
			insertAlertMessageStatement.bind(userDataObj.getPassword());

			insertAlertMessageStatement.executeInsert();
			dbOperations.database.setTransactionSuccessful();
			return ReturnCodes.R0000;
		} finally {
			insertAlertMessageStatement.close();
			dbOperations.database.endTransaction();
		}
	}

	/**
	 * Update the User Details if CustomerAddress already present in the database with the specified customeraddres_id.
	 * @param
	 * @return Number of rows affected 
	 */
	public int updateUser(UserModel userDataObj) {

		DbInputValues iv = new DbInputValues();
		try {

			iv.put(DBColumns.userid, userDataObj.getUserID());	

			Cursor check = dbOperations.executeRawQuery(QUERY_COUNTBY_USER, String.valueOf(userDataObj.getUserID()));

			if(check.getCount() == 0) {
				insertUser(userDataObj);
			} else {
				return dbOperations.update(USER_TABLE, iv.getContentValues(), DBColumns.userid + "=" + userDataObj.getUserID(), null) > 0 ? ReturnCodes.R0000: ReturnCodes.R0001;
			}

		} catch(Exception e) {
			e.printStackTrace();
		}

		return ReturnCodes.R0000;
	}

	/**
	 * Create a new settings using the data provided. If the settings is
	 * successfully inserted return the new rowId for inserted data, otherwise return
	 * a -1 to indicate failure.
	 * 
	 * @return rowId or -1 if failed
	 */
	public int deleteAllUser() {
	
		return dbOperations.delete(USER_TABLE, null, null) > 0 ? ReturnCodes.R0000: ReturnCodes.R0001;

	}
}
