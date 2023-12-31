package com.example.investio;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.MediaCodec;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.net.ConnectException;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.http.DELETE;

public class DatabaseClass extends SQLiteOpenHelper {
    SQLiteDatabase db;
    private static final String DATABASE_NAME = "INVESTECH";
    private static final int DATABASE_VERSION = 1;


    public DatabaseClass(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {


        // Create Users table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Users (" +
                "Userid INT PRIMARY KEY " +
//                "Walletid INT, " +
//                "FOREIGN KEY (Walletid) REFERENCES Wallet(Walletid)" +
                ")");


        // Create AmountHistory table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS AmountHistory (" +
//                "walletid INTEGER PRIMARY KEY, " +
                "totalamount DOUBLE, " +
                "day DATETIME PRIMARY KEY " +
//                "FOREIGN KEY (walletid) REFERENCES Wallet(Walletid)" +
                ")");


// Create Wallet table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Wallet (" +
                "Walletid INT PRIMARY KEY, " +
                "totalamount DOUBLE, " +
                "Created_at TIMESTAMP, " +
                "PercentageROI FLOAT, " +
                "Walletlevel INT, " +
//                "StockPortfolio INTEGER, " +
//                "CryptoAsset INTEGER, " +
//                "ForexAsset INTEGER, " +
                "BondsEquities INTEGER, " +
                "IPOPresence INTEGER " +

//                "FOREIGN KEY (StockPortfolio) REFERENCES StockPortfolios(portfolioid), " +
//                "FOREIGN KEY (CryptoAsset) REFERENCES CryptoAsset(cryptoassetid), " +
//                "FOREIGN KEY (ForexAsset) REFERENCES forexasset(fasset_id)" +
                ")");


        //   Create Transactions table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Transactions (" +
                "transaction_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "walletid INTEGER, " +
                "amount DOUBLE, " +
                "transactiontime TEXT, " +
                "FOREIGN KEY (walletid) REFERENCES Wallet(Walletid)" +
                ")");

        // Create PortfolioTransaction table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS PortfolioTransaction (" +
                "transactionid INTEGER PRIMARY KEY AUTOINCREMENT " +
//                "walletid INTEGER, " +
//                "stockportfolio INTEGER, " +
//                "FOREIGN KEY (walletid) REFERENCES Wallet(Walletid), " +
//                "FOREIGN KEY (stockportfolio) REFERENCES StockPortfolios(portfolioid)" +
                ")");


// Create StockPortfolios table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS StockPortfolios (" +
                "portfolioid INTEGER PRIMARY KEY, " +
                "portfoliotype TEXT" +
                ")");

// Create stockslist table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS stockslist (" +
                "stockid INTEGER PRIMARY KEY , " +
//                "portfolioid INTEGER, " +
                "StockName TEXT, " +
                "Stocksymbol TEXT " +
//                "FOREIGN KEY (portfolioid) REFERENCES StockPortfolios(portfolioid)" +
                ")");

// Create forexasset table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS forexasset (" +
                "fasset_id INTEGER PRIMARY KEY, " +
                "Forex_asset_name TEXT, " +
                "time_created TIMESTAMP" +
                ")");

// Create Currencies table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Currencies (" +
                "currencyid INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "forexasset INTEGER, " +
                "currencyname TEXT, " +
                "currencysymbol TEXT " +
//                "FOREIGN KEY (forexasset) REFERENCES forexasset(fasset_id)" +
                ")");

// Create Customportfolio table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Customportfolio (" +
//                "portfolioid INTEGER PRIMARY KEY, " +
                "StockSymbol VARCHAR(255), " +
                "Stockquantity FLOAT, " +
                "purchaseprice FLOAT, " +
                "purchasedate TIMESTAMP " +
//                "stockid INTEGER, " +
//                "FOREIGN KEY (portfolioid) REFERENCES StockPortfolios(portfolioid), " +
//                "FOREIGN KEY (stockid) REFERENCES stockslist(stockid)" +
                ")");

// Create CryptoAsset table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS CryptoAsset (" +
                "cryptoassetid INTEGER PRIMARY KEY, " +
                "time_created TIMESTAMP" +
                ")");

// Create Crypto table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Crypto (" +
                "cryptoid INTEGER PRIMARY KEY, " +
//                "cryptoassetid INTEGER, " +
                "cryptosymbol VARCHAR(255) " +
//                "FOREIGN KEY (cryptoassetid) REFERENCES CryptoAsset(cryptoassetid)" +
                ")");

// Create Metals table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Metals (" +
                "Metalid INTEGER PRIMARY KEY, " +
                "MetalSymbol TEXT, " +
                "MetalPrice REAL" +
                ")");

// Create UserPortoflio table
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS UserPortoflio (" +
                "walletid TEXT PRIMARY KEY, " +
                "stockportfolio INTEGER, " +
                "percentinstock DOUBLE, " +
//                "cryptoasset INTEGER, " +
                "percentincrypto DOUBLE, " +
//                "forexasset INTEGER, " +
                "percentinforex DOUBLE, " +
//                "metalasset INTEGER, " +
                "percentinmetal DOUBLE, " +
//                "customportfolio INTEGER, " +
                "percentincustomport DOUBLE, " +
                "FOREIGN KEY (walletid) REFERENCES Wallet(Walletid), " +
                "FOREIGN KEY (stockportfolio) REFERENCES StockPortfolios(portfolioid) " +
//                "FOREIGN KEY (cryptoasset) REFERENCES CryptoAsset(cryptoassetid), " +
//                "FOREIGN KEY (forexasset) REFERENCES forexasset(fasset_id), " +
//                "FOREIGN KEY (metalasset) REFERENCES Metals(Metalid), " +
//                "FOREIGN KEY (customportfolio) REFERENCES Customportfolio(portfolioid)" +
                ")");


//        sqLiteDatabase.execSQL("CREATE TABLE STOCKSVALUES ("+
//
//
//                 "stockvalue DOUBLE, " +
//                "timestamp TIMESTAMP," +
//                 "portfolioid INTEGER PRIMARY KEY ," +
//                "FOREIGN KEY (portfolioid) REFERENCES StockPortfolios(portfolioid)" +
//                ")"
//                );


        sqLiteDatabase.execSQL("CREATE TABLE PORTFOLIOSAVERAGE (" +


                "previousaverage DOUBLE, " +
                "newaverage DOUBLE," +
                "portfolioid INTEGER PRIMARY KEY" +
                ")"
        );


        // Adding foreign key columns using ALTER TABLE
        sqLiteDatabase.execSQL("ALTER TABLE Users ADD COLUMN Walletid INTEGER REFERENCES Wallet(Walletid)");
        sqLiteDatabase.execSQL("ALTER TABLE AmountHistory ADD COLUMN Walletid INTEGER REFERENCES Wallet(Walletid)");
//        sqLiteDatabase.execSQL("ALTER TABLE Transactions ADD COLUMN Walletid INTEGER REFERENCES Wallet(Walletid)");

        sqLiteDatabase.execSQL("ALTER TABLE PortfolioTransaction ADD COLUMN Walletid INTEGER REFERENCES Wallet(Walletid)");
        sqLiteDatabase.execSQL("ALTER TABLE PortfolioTransaction ADD COLUMN stockportfolio INTEGER REFERENCES StockPortfolios(portfolioid)");
        sqLiteDatabase.execSQL("ALTER TABLE stockslist ADD COLUMN portfolioid INTEGER REFERENCES StockPortfolios(portfolioid)");

        sqLiteDatabase.execSQL("ALTER TABLE Wallet ADD COLUMN StockPortfolio INTEGER REFERENCES StockPortfolios(portfolioid)");
        sqLiteDatabase.execSQL("ALTER TABLE Wallet ADD COLUMN ForexAsset INTEGER REFERENCES forexasset(fasset_id)");
        sqLiteDatabase.execSQL("ALTER TABLE Wallet ADD COLUMN CryptoAsset INTEGER REFERENCES CryptoAsset(cryptoassetid)");


        sqLiteDatabase.execSQL("ALTER TABLE Currencies ADD COLUMN forexasset INTEGER REFERENCES forexasset(fasset_id)");
        sqLiteDatabase.execSQL("ALTER TABLE Customportfolio ADD COLUMN stockid INTEGER REFERENCES stockslist(stockid)");
        sqLiteDatabase.execSQL("ALTER TABLE Customportfolio ADD COLUMN Portfolioid INTEGER REFERENCES StockPortfolios(portfolioid)");
        sqLiteDatabase.execSQL("ALTER TABLE Crypto ADD COLUMN cryptoassetid INTEGER REFERENCES CryptoAsset(cryptoassetid)");


      //  sqLiteDatabase.execSQL("ALTER TABLE UserPortoflio ADD COLUMN Walletid INTEGER REFERENCES Wallet(Walletid)");
      //  sqLiteDatabase.execSQL("ALTER TABLE UserPortoflio ADD COLUMN Stockportfolio INTEGER REFERENCES StockPortfolios(portfolioid)");
      //  sqLiteDatabase.execSQL("ALTER TABLE UserPortoflio ADD COLUMN Cryptoasset INTEGER REFERENCES CryptoAsset(cryptoassetid)");
      // sqLiteDatabase.execSQL("ALTER TABLE UserPortoflio ADD COLUMN Forexasset INTEGER REFERENCES forexasset(fasset_id)");
      //  sqLiteDatabase.execSQL("ALTER TABLE UserPortoflio ADD COLUMN Metalasset INTEGER REFERENCES Metals(Metalid)");
  // here it gives error while compiling      //    sqLiteDatabase.execSQL("ALTER TABLE UserPortoflio ADD COLUMN Customportfolio INTEGER REFERENCES Customportfolio(Portfolioid)");


        // Populate stockslist table method call
        populateStocksList(sqLiteDatabase);

        populateStockPortfolios(sqLiteDatabase);


    }


    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.execSQL("PRAGMA foreign_keys=ON;");
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {


        // Drop all tables if exist
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Users");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Wallet");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Transactions");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS StockPortfolios");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS stockslist");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS forexasset");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Currencies");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Customportfolio");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS CryptoAsset");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Crypto");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS Metals");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS UserPortoflio");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS PortfolioTransaction");
//        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS AmountHistory");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS STOCKVALUES");


        // Recreate all tables
        onCreate(sqLiteDatabase);


    }

    // Method to populate stockslist table
    private void populateStocksList(SQLiteDatabase db) {
        String[] stockNames = {
                "Apple Inc.",
                "Microsoft Corporation",
                "Amazon.com Inc.",
                "Alphabet Inc.",
                "Meta Platforms, Inc.",
                "Tesla, Inc.",
                "Adobe Inc.",
                "NVIDIA Corporation",
                "Salesforce.com Inc.",
                "PayPal Holdings, Inc.",
                "Johnson & Johnson",
                "UnitedHealth Group Incorporated",
                "Pfizer Inc.",
                "Merck & Co., Inc.",
                "Abbott Laboratories",
                "Amgen Inc.",
                "Thermo Fisher Scientific Inc.",
                "Medtronic plc",
                "Gilead Sciences, Inc.",
                "Bristol Myers Squibb Company",
                "Chegg, Inc.",
                "2U, Inc.",
                "Coursera, Inc.",
                "Pluralsight, Inc.",
                "Instructure Holdings, Inc.",
                "GSX Techedu Inc.",
                "New Oriental Education & Technology Group Inc.",
                "TAL Education Group",
                "K12 Inc.",
                "Edison Nation, Inc.",
                "Simon Property Group, Inc.",
                "Prologis, Inc.",
                "AvalonBay Communities, Inc.",
                "Equity Residential",
                "Public Storage",
                "Digital Realty Trust, Inc.",
                "Realty Income Corporation",
                "Welltower Inc.",
                "American Tower Corporation",
                "Ventas, Inc.",
                "JPMorgan Chase & Co.",
                "Bank of America Corporation",
                "Wells Fargo & Co.",
                "Citigroup Inc.",
                "Goldman Sachs Group Inc.",
                "Morgan Stanley",
                "U.S. Bancorp",
                "PNC Financial Services Group Inc.",
                "TD Bank",
                "Truist Financial Corporation",
                "Deere & Company",
                "Monsanto",
                "Syngenta AG",
                "Archer-Daniels-Midland Company",
                "Nutrien Ltd.",
                "Bunge Limited",
                "Corteva, Inc.",
                "Wilmar International Limited",
                "The Mosaic Company",
                "Zoetis Inc.",
                "Alibaba Group Holding Limited",
                "Tencent Holdings Limited",
                "AMeituan Dianping",
                "JD.com, Inc.",
                "Baidu, Inc.",
                "NetEase, Inc.",
                "Pinduoduo Inc.",
                "Xiaomi Corporation",
                "Kuaishou Technology",
                "Trip.com Group Limited",
                "Nestle S.A.",
                "Royal Dutch Shell plc",
                "Unilever PLC",
                "Novo Nordisk A/S",
                "Sanofi",
                "Siemens AG",
                "SAP SE",
                "Roche Holding AG",
                "AstraZeneca PLC",
                "Telstra Corporation Limited",
                "Adial Pharmaceuticals",
                "JanOne",
                "KuuHub",
                "Express",
                "Spin Master",
                "Performance Shipping",
                "Banco Marco",
                "Ultimate Games",
                "Tingo Group",
                "TherapeuticsMD",
                "Itochu-techno Solutions",
                "Broccoli",
                "Great Panther Mining",
                "Allen Pharmaceuticals",
                "Genocea Biosciences",
                "Signature Bank",
                "Zovio",
                "Vinco Ventures",
                "Alpine Summint Energy Partners",
                "Mallinckrodt Pharmaceuticals"
        };

        String[] stockSymbols = {
                "AAPL",
                "MSFT",
                "AMZN",
                "GOOGL",
                "FB",
                "TSLA",
                "ADBE",
                "NVDA",
                "CRM",
                "PYPL",
                "JNJ",
                "UNH",
                "PFE",
                "MRK",
                "ABT",
                "AMGN",
                "TMO",
                "MDT",
                "GILD",
                "BMY",
                "CHGG",
                "TWOU",
                "COUR",
                "PS",
                "INST",
                "GSX",
                "EDU",
                "TAL",
                "LRN",
                "EDNT",
                "SPG",
                "PLD",
                "AVB",
                "EQR",
                "PSA",
                "DLR",
                "O",
                "WELL",
                "AMT",
                "VTR",
                "JPM",
                "BAC",
                "WFC",
                "C",
                "GS",
                "MS",
                "USB",
                "PNC",
                "TD",
                "TFC",
                "DE",
                "BAYRY",
                "SYT",
                "ADM",
                "NTR",
                "BG",
                "CTVA",
                "F34.SI",
                "MOS",
                "ZTS",
                "BABA",
                "TCEHY",
                "3690.HK",
                "JD",
                "BIDU",
                "NTES",
                "PDD",
                "1810.HK",
                "1024.HK",
                "TCOM",
                "NESN",
                "RDS.A, RDS.B",
                "UL",
                "NVO",
                "SNY",
                "SIEGY",
                "SAP",
                "ROG",
                "AZN",
                "TLSYY",
                "ADIL",
                "JAN",
                "KUU.V",
                "EXPR",
                "TOY.TO",
                "PSHG",
                "BMA",
                "ULG.WA",
                "TIO",
                "TXMD",
                "4739.T",
                "2706.T",
                "GPLDF",
                "ALNAQ",
                "GNCA",
                "SBNY",
                "ZVOI",
                "BBIG",
                "ALPSQ",
                "MNK"
        };

        int[] portfolioId = {
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10

        };

        int[] stockIds = new int[100];

        for (int i = 0; i < 100; i++) {
            stockIds[i] = i + 1;
        }


        int portfolioCounter = 0; // Counter for portfolioId

        for (int i = 0; i < stockNames.length; i++) {
            ContentValues values = new ContentValues();
            values.put("StockName", stockNames[i]);
            values.put("Stocksymbol", stockSymbols[i]);
            values.put("stockid", stockIds[i]);

            // Assign portfolioId for every 10 stocks
            if ((i + 1) % 10 == 0) {
                values.put("portfolioid", portfolioId[portfolioCounter]);
                portfolioCounter++; // Increment portfolioCounter after every 10 stocks
            } else {
                values.put("portfolioid", portfolioId[portfolioCounter]);
            }
            db.insert("stockslist", null, values);
        }

    }

    private void populateStockPortfolios(SQLiteDatabase db) {

        String[] Stockcategories = {
                "Tech-USA",
                "Health-USA",
                "Edtech-USA",
                "Real Estate-USA",
                "Bank-USA",
                "Agriculture",
                "Top 10 Dividend Yield Stocks",
                "Top 10 P/E Ratio stocks",
                "Top 10 P/B Ratio",
                "Top ten world tech stocks"
        };

        int[] portfolioid = {
                1,
                2,
                3,
                4,
                5,
                6,
                7,
                8,
                9,
                10

        };


        for (int i = 0; i < 10; i++) {
            ContentValues cv = new ContentValues();
            cv.put("portfoliotype", Stockcategories[i]);
            cv.put("portfolioid", portfolioid[i]);
            db.insert("StockPortfolios", null, cv);
        }


    }


    public ArrayList<CompanyData> readingStockslist(int startIndex) {

        SQLiteDatabase db = this.getReadableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM stockslist LIMIT 10 OFFSET " + startIndex, null);

        ArrayList<CompanyData> companyDataList = new ArrayList<>();

        while (cursor.moveToNext()) {
            CompanyData companyData = new CompanyData();

            // Check if the column exists in the cursor
            int StockCompanyNameIndex = cursor.getColumnIndex("StockName");
            int StockCompanySymbolIndex = cursor.getColumnIndex("Stocksymbol");
            int StockcompanyidIndex = cursor.getColumnIndex("portfolioid");

            if (StockCompanyNameIndex != -1 && StockCompanySymbolIndex != -1 && StockcompanyidIndex != -1) {
                // Get data from the cursor and set it to the CompanyData object
                String companyName = cursor.getString(StockCompanyNameIndex);
                String companySymbol = cursor.getString(StockCompanySymbolIndex);
                int companyID = cursor.getInt(StockcompanyidIndex);

                companyData.setStockName(companyName);
                companyData.setStockSymbol(companySymbol);
                companyData.setStockid(companyID);
                companyData.setClose(null); // You may set the 'close' value based on your requirement

                // Add the CompanyData object to your list or perform any other desired operations
                companyDataList.add(companyData);
            }
        }


        return companyDataList;
    }


    public void adduserid(String userid, String walletid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put("Userid", userid);
        cv.put("Walletid", walletid);

        db.insert("Users", null, cv);
    }


//  public void addamount(double amount,String timestamp,String walletid){
//        SQLiteDatabase db=this.getWritableDatabase();
//
//        ContentValues cv= new ContentValues();
//        cv.put("totalamount",amount);
//        cv.put("day",timestamp);
//        cv.put("Walletid",walletid);
//        db.insert("AmountHistory",null,cv);
//
//
//  }

    // Function to get the current timestamp
    public String getCurrentTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }


    public boolean readwallet() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT Walletid FROM Wallet ", null);

        if (cursor != null && cursor.moveToFirst()) {
            if (!cursor.isNull(0)) {
                return true;

            } else {
                return false;
            }

        } else {
            return false;
        }
    }


    public boolean readuser() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT Userid FROM Users ", null);

        if (cursor != null && cursor.moveToFirst()) {
            if (!cursor.isNull(0)) {
                return true;

            } else {
                return false;
            }

        } else {
            return false;
        }
    }


    public void addWalletid(String walletid, Double totalAmount) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("Walletid", walletid);
//        cv.put("Created_at",timestamp);
        cv.put("totalamount", totalAmount);

        db.insert("Wallet", null, cv);
    }


    public void updatewalletamount(Double Totalamount) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE Wallet SET totalamount= " + Totalamount);

    }


    public Double readwalletamount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT totalamount FROM Wallet ", null);
        if (cursor != null && cursor.moveToFirst()) {
            Double totalamount = cursor.getDouble(0);
            return totalamount;
        }
        return null;
    }


    //storing stocks values


    //getting average of stocks values
    public Double stocksaverage(int Portfolioid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT AVG(stockvalue) FROM STOCKSVALUES WHERE portfolioid='Portfolioid'", null);
        Double avgstocks;
        if (cursor != null && cursor.moveToFirst()) {
            avgstocks = cursor.getDouble(0);
            return avgstocks;
        }
        return null;
    }


    public boolean isnullstocksvalue() {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM STOCKSVALUES ", null);

        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return (count == 0); //set it to 10 later
        }

        return false; // Cursor is null or an error occurred
    }

    public int readportfoliovaluerows(int Portfolioid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM STOCKSVALUES WHERE portfolioid='Portfolioid'", null);

        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return count;
        }
        return 0;

    }


    public String fetchwalletid() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT walletid FROM Wallet LIMIT 1", null);
        String walletid;
        if (cursor != null && cursor.moveToFirst()) {
            walletid = cursor.getString(0);
            return walletid;
        }
        return null;
    }





    public String readFirstStockTime(int Portfolioid) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT timestamp FROM STOCKSVALUES WHERE portfolioid = 'Portfolioid' ORDER BY timestamp ASC LIMIT 1", null);

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("timestamp");
            if (!cursor.isNull(columnIndex)) {
                String firstStockTimestamp = cursor.getString(columnIndex);
                cursor.close();
                return firstStockTimestamp;
            }
            cursor.close();
        }
        return null;
    }


    public void deleteStocksValueData(int Portfolioid) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM STOCKSVALUES WHERE portfolioid='Portfolioid'", null);

    }


    public int difftimestamp(String firsttimestamp, String currenttimestamp) {
        int differenceInMinutes = 0;

        SQLiteDatabase db = this.getReadableDatabase();


        // Fetch the timestamps in seconds and then calculate the difference
        Cursor cursor = db.rawQuery(
                "SELECT (strftime('%s', ?, 'utc') - strftime('%s', ?, 'utc')) / 60 AS diff",
                new String[]{currenttimestamp, firsttimestamp}
        );

        if (cursor != null && cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("diff");
            if (columnIndex != -1) {
                differenceInMinutes = cursor.getInt(columnIndex);
            }
            cursor.close();
        }

        return differenceInMinutes;
    }


    public boolean readStocksValue(int Portfolioid) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT portfolioid FROM STOCKSVALUES WHERE portfolioid='Portfolioid'", null);
        if (cursor != null && cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            cursor.close();
            return (count >= 0 && count <= 2);    //set upper limit to 10
        }

        return false; // Cursor is null or an error occurred


    }


    //storing stocks values
    public void storestocksvalue(Double Stockvalue, String Timestamp, int Portfolioid) {   //when the table is null
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("stockvalue", Stockvalue);
        cv.put("timestamp", Timestamp);
        cv.put("portfolioid", Portfolioid);
        db.insert("STOCKSVALUES", null, cv);
    }


    public void updateStocksValue(int Portfolioid, Double Stockvalue, String timestamp) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("portfolioid", Portfolioid);
        values.put("stockvalue", Stockvalue);
        values.put("timestamp", timestamp);

        db.insert("STOCKSVALUES", null, values);
    }


//    public void readpreviousaverage(int Portfolioid){
//        SQLiteDatabase db=this.getReadableDatabase();
//        Cursor cursor=db.rawQuery("SELECT previousaverage FROM PORTFOLIOSAVERAGE WHERE portfolioid='Portfolioid'",null);
//        if(cursor.moveToFirst()){
//
//        }
//    }
//
//    public void readnewaverage(int Portfolioid){
//        SQLiteDatabase db=this.getReadableDatabase();
//        Cursor cursor=db.rawQuery("SELECT newaverage FROM PORTFOLIOSAVERAGE WHERE portfolioid='Portfolioid'",null);
//        if(cursor.moveToFirst()){
//
//        }
//    }


    public void updatepreviousaverage(int Portfolioid, Double prevaverage) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE PORTFOLIOSAVERAGE SET previousaverage='prevaverage' WHERE portfolioid='Portfolioid'");
    }


    public void updatenewaverage(int Portfolioid, Double newvaverage) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE PORTFOLIOSAVERAGE SET newaverage='newvaverage' WHERE portfolioid='Portfolioid'");
    }


    public boolean findPortfolioid(int Portfolioid) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT portfolioid FROM PORTFOLIOSAVERAGE WHERE portfolioid=?", new String[]{String.valueOf(Portfolioid)});

        if (cursor.moveToFirst()) {
            if (Portfolioid == cursor.getInt(0)) {
                cursor.close(); // Close the cursor when done
                return true;
            } else {
                cursor.close(); // Close the cursor when done
                return false;
            }
        } else {
            cursor.close(); // Close the cursor when done
            return false; // Return false if no matching record is found
        }
    }


    public void insertnewaverage(Double newAverage, int Portfolioid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("newaverage", newAverage);
        cv.put("portfolioid", Portfolioid);
        db.insert("PORTFOLIOSAVERAGE", null, cv);
    }


    public void populatetransactionshistory(String transactime, String walletid, Double amount) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("walletid", walletid);
        cv.put("amount", amount);
        cv.put("transactiontime", transactime);
        db.insert("Transactions", null, cv);


    }

    public Cursor readTransactions() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Transactions", null);
        return cursor;
    }

    public void adduserportfolio(int portfolioID, String walletid, Double percentinstock) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("stockportfolio", portfolioID);
        cv.put("Walletid", walletid);
        cv.put("percentinstock", percentinstock);

        db.insert("UserPortoflio", null, cv);

    }

    public Cursor readUserPortfolio() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Walletid,stockportfolio,percentinstock FROM UserPortoflio ", null);
        return cursor;
    }

}