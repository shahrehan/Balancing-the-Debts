package com.example.jasvir.spendingtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jasvir on 10/24/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String Database_Name = "Spending.db";
    public static final int DATABASE_VERSION = 1;
    public static final String Table_Name = "Spending";
    public static final String Column_one = "Id";
    public static final String Column_two = "Category";
    public static final String Column_three = "Date";
    public static final String Column_four = "Amount";

    public  final String Query = "CREATE TABLE "
            + Table_Name + "(" + Column_one + " INTEGER PRIMARY KEY, "
            + Column_two + " TEXT, " + Column_three + " TEXT, "
            + Column_four + " TEXT" + ")";



    public DatabaseHelper(Context context) {
        super(context, Database_Name, null, DATABASE_VERSION);

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(Query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS"+ Table_Name);
        onCreate(db);

    }

    public boolean insertData (String Category, String Date, String Amount)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Column_two,Category);
        contentValues.put(Column_three,Date);
        contentValues.put(Column_four, Amount);
        Long Result =  db.insert(Table_Name, null,contentValues);

        if (Result == -1)

            return  false;
        else

            return  true;

    }

    public int deleteDailyDetails(int TxnID) {

        SQLiteDatabase db = this.getWritableDatabase();
        int count =db.delete(Table_Name, Column_one + " = ?",new String[] {Integer.toString(TxnID)});;
        return count;
    }


    public Cursor getData (){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor output = db.rawQuery("select * from " + Table_Name, null);
        return output;
    }

    public int expenseAmount()
    {
        String[] projection = { "SUM(Amount)" };
        Cursor c;
        int Expenseamount=0;
        SQLiteDatabase db = this.getReadableDatabase();
        c  = db.query(false,
                Table_Name,  // The table to query
                projection,                               // The columns to return
                null,                                // The columns for the WHERE clause
                null,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null,null                            // The sort order
        );
        c.moveToFirst();
        if(c.isAfterLast() == false)
        {
            Expenseamount = c.getInt(0);
        }
        return Expenseamount;
    }


}
