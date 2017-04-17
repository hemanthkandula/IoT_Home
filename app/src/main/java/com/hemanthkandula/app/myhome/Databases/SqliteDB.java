package com.hemanthkandula.app.myhome.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;



import java.util.ArrayList;
import java.util.HashMap;

public class SqliteDB extends SQLiteOpenHelper {

	public SqliteDB(Context applicationcontext) {
		super(applicationcontext, "participants.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		String query;

        query = "CREATE TABLE 	Sensors ( temp TEXT ,humid TEXT,time TEXT)";
		database.execSQL(query);


	}
	@Override
	public void onUpgrade(SQLiteDatabase database, int version_old, int current_version) {
		String query;
		query = "DROP TABLE IF EXISTS users";
		database.execSQL(query);
		onCreate(database);
	}

	public void onchange() {
		SQLiteDatabase database = this.getWritableDatabase();
		String query;
		query = "DELETE  FROM Sensors ";
		database.execSQL(query);

	}
	public void insertUser(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();

		values.put("temp", queryValues.get("temp"));
		values.put("humid", queryValues.get("humid"));
		values.put("time", queryValues.get("time"));

		database.insert("Sensors", null, values);
		database.close();
	}
	public void updateUser(HashMap<String, String> queryValues) {

		SQLiteDatabase database = this.getWritableDatabase();
System.out.println(queryValues);
		String updateQuery = "Update participants set NAME = '"+ queryValues.get("NAME") +"' , GEN = '"+ queryValues.get("GEN") +"'  ,CLG = '"+ queryValues.get("CLG") +"', MOB = '"+ queryValues.get("MOB") +"' ,EMAIL = '"+ queryValues.get("EMAIL") +"' , ACCOM = '"+ queryValues.get("ACCOM") +"', EVENTNO = '"+ queryValues.get("EVENTNO") +"', ORGANIZER = '"+ queryValues.get("ORGANIZER") +"' , GROUPNAME = '"+ queryValues.get("GROUPNAME") +"', GROUPTITLE = '"+ queryValues.get("GROUPTITLE") +"', TITLE = '"+ queryValues.get("TITLE") +"', GROUPRANK = '"+ queryValues.get("GROUPRANK") +"', PLACE = '"+ queryValues.get("PLACE") +"', udpateStatus = '"+"no"+"'    where KSID = '"+ queryValues.get("KSID") +"' AND  EVENTNO = '"+queryValues.get("EVENTNO")+"'";
Log.d("Update Query",updateQuery);
		Log.d("query",updateQuery);
		database.execSQL(updateQuery);
		database.close();
	}

	public void updateGroupUser(HashMap<String, String> queryValues) {

		SQLiteDatabase database = this.getWritableDatabase();
		System.out.println(queryValues);
		String updateQuery = "Update participants set NAME = '"+ queryValues.get("NAME") +"' , GEN = '"+ queryValues.get("GEN") +"'  ,CLG = '"+ queryValues.get("CLG") +"', MOB = '"+ queryValues.get("MOB") +"' ,EMAIL = '"+ queryValues.get("EMAIL") +"' , ACCOM = '"+ queryValues.get("ACCOM") +"', EVENTNO = '"+ queryValues.get("EVENTNO") +"', ORGANIZER = '"+ queryValues.get("ORGANIZER") +"' , GROUPNAME = '"+ queryValues.get("GROUPNAME") +"', GROUPTITLE = '"+ queryValues.get("GROUPTITLE") +"', TITLE = '"+ queryValues.get("TITLE") +"', GROUPRANK = '"+ queryValues.get("GROUPRANK") +"', PLACE = '"+ queryValues.get("PLACE") +"', udpateStatus = '"+"no"+"'    where KSID = '"+ queryValues.get("KSID") +"' AND  EVENTNO = '"+queryValues.get("EVENTNO")+"' AND GROUPNAME ='"+ queryValues.get("GROUPNAME") +"'   ";
		Log.d("Update Query",updateQuery);
		Log.d("query",updateQuery);
		database.execSQL(updateQuery);
		database.close();
	}




	public ArrayList<HashMap<String, String>> getAllEvents(){


		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM ClusterEvents";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("EVENTNO", cursor.getString(0));
				map.put("EVENTNAME", cursor.getString(1));
				map.put(("Gflag"),cursor.getString(3));
				wordList.add(map);
			} while (cursor.moveToNext());
		}
		database.close();
		return wordList;
	}


    public ArrayList<HashMap<String, String>> getallclgs(){


        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT  * FROM Colleges";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("CLG", cursor.getString(1));
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
        return wordList;
    }

	public void InsertEvents(HashMap<String, String> queryValues) {
		SQLiteDatabase database = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("EVENTNAME", queryValues.get("EVENTNAME"));
		values.put("udpateStatus", "no");
		values.put("EVENTNO",queryValues.get("EVENTNO"));
		values.put("Gflag",queryValues.get("Gflag"));

		database.insert("ClusterEvents", null, values);
		database.close();
	}

    public void Insertclgs(String clg) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("CLG",clg);


        database.insert("Colleges", null, values);
        database.close();
    }

	public String getEventNO(String e){
		String selectQuery = "SELECT  EVENTNO FROM ClusterEvents where EVENTNAME = '"+e+"'";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		String EventNo =new String();
		if (cursor.moveToFirst()) {
			EventNo = cursor.getString(0);
		}
		return EventNo;
	}





	public void dropDB()
	{
		// db.delete(String tableName, String whereClause, String[] whereArgs);
		// If whereClause is null, it will delete all rows.
		//DELETE FROM COMPANY WHERE ID = 7;
		SQLiteDatabase database = this.getWritableDatabase();
		System.out.println("i droped it");
//        String query;
//        query = "DELETE  FROM studs";
//        database.execSQL(query);
//        onCreate(database);

		database.delete("participants",null,null);
		database.close();
	}

	public ArrayList<HashMap<String, String>> getAllUsers() {
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  * FROM Sensors";
		//String selectQuery = "SELECT  * FROM participants  ";

		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
						map.put("temp", cursor.getString(0));
				map.put("humid", cursor.getString(1));
				map.put("time", cursor.getString(2));






				wordList.add(map);
			} while (cursor.moveToNext());
		}
		database.close();
		System.out.println(wordList);
		System.out.println("wordlist");
		return wordList;
	}



	public ArrayList<HashMap<String, String>> getAllGroupUsers(String EventNo, String GroupName) {
		ArrayList<HashMap<String, String>> wordList;
		wordList = new ArrayList<HashMap<String, String>>();
		String selectQuery = "SELECT  NAME,KSID,GROUPTITLE,TITLE,PLACE FROM participants WHERE EventNo="+"'"+ EventNo +"' and GroupName =  "+"'"+ GroupName +  "'";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				HashMap<String, String> map = new HashMap<String, String>();
				//map.put("updateStatus", cursor.getString(7));
				map.put("KSID", cursor.getString(1));
				map.put("NAME", cursor.getString(0));
				map.put("GROUPTITLE", cursor.getString(2));
				map.put("TITLE", cursor.getString(3));map.put("PLACE", cursor.getString(4));



				wordList.add(map);
			} while (cursor.moveToNext());
		}
		database.close();
		return wordList;
	}


	public void updateRank(String EventNo, String ksid, String Rank){
		SQLiteDatabase database = this.getWritableDatabase();

		String updateQuery = "Update participants set PLACE = '"+ Rank +"', udpateStatus = 'no' where KSID="+"'"+ ksid +"' and EVENTNO = "+" '"+ EventNo+"'    ";
		Log.d("query",updateQuery);
		database.execSQL(updateQuery);
		database.close();
	}


    public String getcollname(String ksid){
        SQLiteDatabase database = this.getWritableDatabase();
String id = ksid.substring(2,5);
        String selectQuery = "SELECT  CLG FROM Colleges where ID = "+ id  ;
        Cursor cursor = database.rawQuery(selectQuery, null);
String a = "";
        if (cursor.moveToFirst()) {
            a = cursor.getString(0);

        }
System.out.println(selectQuery);
        System.out.println(id);
        System.out.println(a);
        Log.d("clg",a);
        Log.d("ksid",ksid);
        return  a;
    }

	public void GroupRank(String EventNo, String GN, String Rank){
		SQLiteDatabase database = this.getWritableDatabase();

		String updateQuery = "Update participants set GROUPRANK = '"+ Rank +"', udpateStatus = 'no' where GROUPNAME="+"'"+ GN +"' and EVENTNO = "+" '"+ EventNo+"'    ";
		Log.d("query",updateQuery);
		database.execSQL(updateQuery);
		database.close();
	}
	public void updatetitle(String EventNo, String ksid, String Rank){
		SQLiteDatabase database = this.getWritableDatabase();

		String updateQuery = "Update participants set TITLE = '"+ Rank +"', udpateStatus = 'no' where KSID="+"'"+ ksid +"' and EVENTNO = "+" '"+ EventNo+"'    ";
		Log.d("query",updateQuery);
		database.execSQL(updateQuery);
		database.close();
	}

	public void updateGroupalltitle(String EventNo, String Rank, String Groupname){
		SQLiteDatabase database = this.getWritableDatabase();

		String updateQuery = "Update participants set GROUPTITLE = '"+ Rank +"', udpateStatus = 'no' where GROUPNAME="+"'"+ Groupname +"' and EVENTNO = "+" '"+ EventNo+"'    ";
		Log.d("query",updateQuery);
		database.execSQL(updateQuery);
		database.close();
	}


	public String getGender(String EventNo, String KSID){

		String gen="";
		//String selectQuery = "SELECT  * FROM participants where udpateStatus = '"+"no"+"'   ";

		String selectQuery = "SELECT  * FROM participants where KSID = '"+KSID+"'  and EVENTNO = '"+ EventNo +"' ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				gen = cursor.getString(2);
				if(cursor.isNull(2)){gen="";}
			} while (cursor.moveToNext());
		}
		database.close();
		System.out.println("KKKKSSSSS  GEn   "+gen);
		return gen;
	}

	public String getRank(String EventNo, String KSID){

		String gen="";
		//String selectQuery = "SELECT  * FROM participants where udpateStatus = '"+"no"+"'   ";

		String selectQuery = "SELECT  PLACE FROM participants where KSID = '"+KSID+"'  and EVENTNO = '"+ EventNo +"' ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				gen = cursor.getString(0);
				if(cursor.isNull(0)){gen="";}
			} while (cursor.moveToNext());
		}
		database.close();
		System.out.println("KKKKSSSSS  CLG   "+gen);
		return gen;
	}

	public String getGroupRank(String EventNo, String KSID){

		String gen="";
		//String selectQuery = "SELECT  * FROM participants where udpateStatus = '"+"no"+"'   ";

		String selectQuery = "SELECT  GROUPRANK FROM participants where GROUPNAME = '"+KSID+"'  and EVENTNO = '"+ EventNo +"' ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				gen = cursor.getString(0);
				if(cursor.isNull(0)){gen="";}
			} while (cursor.moveToNext());
		}
		database.close();
		System.out.println("KKKKSSSSS  CLG   "+gen);
		return gen;
	}

	public String getTitle1(String EventNo, String KSID){

		String gen="";
		//String selectQuery = "SELECT  * FROM participants where udpateStatus = '"+"no"+"'   ";

		String selectQuery = "SELECT  TITLE FROM participants where KSID = '"+KSID+"'  and EVENTNO = '"+ EventNo +"' ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				gen = cursor.getString(0);
				if(cursor.isNull(0)){gen="";}
			} while (cursor.moveToNext());
		}
		database.close();
		System.out.println("KKKKSSSSS  CLG   "+gen);
		return gen;
	}

	public String getGrouptitle(String EventNo, String KSID){

		String gen="";
		//String selectQuery = "SELECT  * FROM participants where udpateStatus = '"+"no"+"'   ";

		String selectQuery = "SELECT  GROUPTITLE FROM participants where GROUPNAME = '"+KSID+"'  and EVENTNO = '"+ EventNo +"' ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				gen = cursor.getString(0);
				if(cursor.isNull(0)){gen="";}
			} while (cursor.moveToNext());
		}
		database.close();
		System.out.println("KKKKSSSSS  CLG   "+gen);
		return gen;
	}


	public String getCollege(String EventNo, String KSID){

		String gen="";
		//String selectQuery = "SELECT  * FROM participants where udpateStatus = '"+"no"+"'   ";

		String selectQuery = "SELECT  * FROM participants where KSID = '"+KSID+"'  and EVENTNO = '"+ EventNo +"' ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				gen = cursor.getString(3);
				if(cursor.isNull(3)){gen="";}
			} while (cursor.moveToNext());
		}
		database.close();
		System.out.println("KKKKSSSSS  CLG   "+gen);
		return gen;
	}

	public String getMobile(String EventNo, String KSID){

		String gen="";
		//String selectQuery = "SELECT  * FROM participants where udpateStatus = '"+"no"+"'   ";

		String selectQuery = "SELECT  * FROM participants where KSID = '"+KSID+"'  and EVENTNO = '"+ EventNo +"' ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {
				gen = cursor.getString(4);
if(cursor.isNull(4)){gen="";}
			} while (cursor.moveToNext());
		}
		database.close();
		System.out.println("KKKKSSSSS  MOB   "+gen);
		return gen;
	}


	public String getGroupCollege(String EventNo, String GroupName){

		String gen="";
		//String selectQuery = "SELECT  * FROM participants where udpateStatus = '"+"no"+"'   ";

		String selectQuery = "SELECT  KSID FROM participants where GROUPNAME = '"+GroupName+"'  and EVENTNO = '"+ EventNo +"' ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do { if(!cursor.isNull(0) ) {
				gen = cursor.getString(0);
			}else gen="";

			} while (cursor.moveToNext());
		}
		database.close();
		System.out.println("KKKKSSSSS  CLG   "+gen);
        if (gen.substring(0,2).equals("ks")) {

            gen= getcollname(gen);}else gen = "SASTRA University";

        System.out.println(gen);
		return gen;
	}

	public String getEmail(String EventNo, String KSID){

		String gen="";
		//String selectQuery = "SELECT  * FROM participants where udpateStatus = '"+"no"+"'   ";

		String selectQuery = "SELECT  EMAIL FROM participants where KSID = '"+KSID+"'  and EVENTNO = '"+ EventNo +"' ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {if(!cursor.isNull(0) ){
				gen = cursor.getString(0);
			}else gen="";

			} while (cursor.moveToNext());
		}
		database.close();
		System.out.println("KKKKSSSSS  GEn   "+gen);
		return gen;
	}

	public String getAccom(String EventNo, String KSID){

		String gen="";
		//String selectQuery = "SELECT  * FROM participants where udpateStatus = '"+"no"+"'   ";

		String selectQuery = "SELECT  ACCOM FROM participants where KSID = '"+KSID+"'  and EVENTNO = '"+ EventNo +"' ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		if (cursor.moveToFirst()) {
			do {if(!cursor.isNull(0)){
				if (cursor.getString(0).equals("1")){
				gen = "Yes";}
				else {gen= "No";}}
			} while (cursor.moveToNext());
		}
		database.close();
		System.out.println("KKKKSSSSS  GEn   "+gen);
		return gen;
	}

    public ArrayList<HashMap<String, String>> getAllGroups(String EventNo) {
        ArrayList<HashMap<String, String>> wordList;
        wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT DISTINCT  GROUPNAME,GROUPRANK,GROUPTITLE  FROM participants WHERE EVENTNO="+"'"+ EventNo +"'";
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                //map.put("userId", cursor.getString(0));
                map.put("GROUPNAME", cursor.getString(0));
				map.put("GROUPTITLE", cursor.getString(2));
				map.put("GROUPRANK", cursor.getString(1));




                wordList.add(map);
            } while (cursor.moveToNext());
        }
        database.close();
		System.out.println(wordList);
        return wordList;
    }




    public String getSyncStatus(){
		String msg = null;
		if(this.dbSyncCount() == 0){
			msg = "in Sync!";
		}else{
			msg = "Sync needed\n";
		}
		return msg;
	}


	public int dbSyncCount(){
		int count = 0;
		String selectQuery = "SELECT  * FROM Sensors ";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		count = cursor.getCount();
		database.close();
		return count;
	}

	public ArrayList getksid(String EventNo){
		int count = 0;
		String selectQuery = "SELECT  KSID FROM participants WHERE EVENTNO="+"'"+ EventNo +"'";
		//String selectQuery = "SELECT  KSID FROM participants where udpateStatus = '"+"no"+"'";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		ArrayList<String> values = new ArrayList<>();
		if (cursor != null && cursor.getCount() != 0) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {

				values.add(cursor.getString(0));

				cursor.moveToNext();
			}
	}

	return  values;
	}
	public ArrayList getksidnull(String EventNo){
		int count = 0;
		String selectQuery = "SELECT  KSID FROM participants WHERE  EVENTNO="+"'"+ EventNo +"'";

		//String selectQuery = "SELECT  KSID FROM participants WHERE NAME IS NULL AND  EVENTNO="+"'"+ EventNo +"'";
		System.out.println(selectQuery);
		//String selectQuery = "SELECT  KSID FROM participants where udpateStatus = '"+"no"+"'";
		SQLiteDatabase database = this.getWritableDatabase();
		Cursor cursor = database.rawQuery(selectQuery, null);
		ArrayList<String> values = new ArrayList<>();
		if (cursor != null && cursor.getCount() != 0) {
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {

				values.add(cursor.getString(0));

				cursor.moveToNext();
			}
		}

		return  values;
	}
	public void updateSyncStatus(String id, String status, String EventNo){
		SQLiteDatabase database = this.getWritableDatabase();
		String updateQuery = "Update participants set  udpateStatus = '"+ status +"' where KSID="+"'"+ id +"' AND EVENTNO="+"'"+ EventNo +"'  ";
		Log.d("query",updateQuery);
		database.execSQL(updateQuery);
		database.close();
	}

	public void SetRank(String RegiNo, String Rank) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update users set Rank = '"+ Rank +"' where userName="+"'"+ RegiNo +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();

	}

    public void SetGroupRank(String GroupName, String Rank) {
        SQLiteDatabase database = this.getWritableDatabase();
        String updateQuery = "Update users set Rank = '"+ Rank +"' where GroupName="+"'"+ GroupName +"'";
        Log.d("query",updateQuery);
        database.execSQL(updateQuery);
        database.close();
    }




}
