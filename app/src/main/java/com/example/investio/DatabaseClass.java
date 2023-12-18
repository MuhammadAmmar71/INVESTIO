package com.example.investio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseClass extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="fintech";
    private static final int DATABASE_VERSION =1;
    private static final String TABLE_NAME="TechUSA";
    private static final String COLUMN_COMPANY="COMPANY_NAME";
    private static final String COLUMN_SYMBOL="Ticker_Symbols";
    private static final String ROW_ID="ROW_ID";


    public DatabaseClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);




    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "("
                + ROW_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                +COLUMN_COMPANY + " TEXT,"
                +COLUMN_SYMBOL + " TEXT" + ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);



    }

    // Check if data already exists in the table
    private boolean isDataExists(SQLiteDatabase db, String company_name) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_COMPANY + "=?", new String[]{company_name});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    void addTechUSA(String company_name, String company_symbol) {


        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_COMPANY, company_name);
        contentValues.put(COLUMN_SYMBOL, company_symbol);

        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);




    }

    // Inside DatabaseClass

    public ArrayList<CompanyData> getAllTechUSA() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME ,null);

        ArrayList<CompanyData> companyDataList = new ArrayList<>();

        while (cursor.moveToNext()) {
            CompanyData companyData = new CompanyData();

            // Check if the column exists in the cursor
            int companyNameIndex = cursor.getColumnIndex(COLUMN_COMPANY);
            int companySymbolIndex = cursor.getColumnIndex(COLUMN_SYMBOL);

            if (companyNameIndex != -1 && companySymbolIndex != -1) {
                // Get data from the cursor and set it to the CompanyData object
                String companyName = cursor.getString(companyNameIndex);
                String companySymbol = cursor.getString(companySymbolIndex);

                companyData.setCompanyName(companyName);
                companyData.setCompanySymbol(companySymbol);
                companyData.setClose(null); // You may set the 'close' value based on your requirement

                // Add the CompanyData object to your list or perform any other desired operations
                companyDataList.add(companyData);
            }
        }



        return companyDataList;
    }


}
