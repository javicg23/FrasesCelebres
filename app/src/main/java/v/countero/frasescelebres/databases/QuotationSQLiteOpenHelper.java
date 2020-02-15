package v.countero.frasescelebres.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import v.countero.frasescelebres.pojos.Quotation;

public class QuotationSQLiteOpenHelper extends SQLiteOpenHelper {

    private static QuotationSQLiteOpenHelper quotationSQLiteOpenHelper;


    private QuotationSQLiteOpenHelper(Context context) {
        super(context,"quotation_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String id = QuotationContract.ContractBase.ID;
        String quoteColumn = QuotationContract.ContractBase.QUOTE_COLUMN;
        String authorColumn = QuotationContract.ContractBase.AUTHOR_COLUMN;
        db.execSQL("CREATE TABLE quotation_table ("
                + id + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + quoteColumn + " TEXT NOT NULL, "
                + authorColumn + " TEXT, UNIQUE(" + quoteColumn + "));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized static QuotationSQLiteOpenHelper getInstance(Context context) {
        if (quotationSQLiteOpenHelper == null) {
            quotationSQLiteOpenHelper =  new QuotationSQLiteOpenHelper(context);
        }
        return quotationSQLiteOpenHelper;
    }

    public ArrayList<Quotation> getQuotations() {
        String quoteColumn = QuotationContract.ContractBase.QUOTE_COLUMN;
        String authorColumn = QuotationContract.ContractBase.AUTHOR_COLUMN;

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("quotation_table", new String[]{quoteColumn, authorColumn}, null, null, null, null, null);

        ArrayList<Quotation> quotes = new ArrayList<>();
        while(cursor.moveToNext()) {
            Quotation quote = new Quotation(cursor.getString(0), cursor.getString(1));
            quotes.add(quote);
        }
        cursor.close();
        database.close();
        return quotes;
    }

    public boolean existQuotation(String quote) {
        String quoteColumn = QuotationContract.ContractBase.QUOTE_COLUMN;

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("quotation_table", null, quoteColumn + "=?", new String[]{quote}, null, null, null, null);

        boolean res = cursor. getCount() > 0;
        cursor.close();
        database.close();

        return res;
    }

    public boolean insertQuotation(String quote, String author) {
        boolean res = false;

        if (!existQuotation(quote)) {
            String quoteColumn = QuotationContract.ContractBase.QUOTE_COLUMN;
            String authorColumn = QuotationContract.ContractBase.AUTHOR_COLUMN;
            SQLiteDatabase database = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(quoteColumn,quote);
            values.put(authorColumn,author);
            database.insert("quotation_table", null, values);
            res = true;

            database.close();
        }
        return res;
    }

    public boolean removeAll() {
        SQLiteDatabase database = getWritableDatabase();
        database.delete("quotation_table",null,null);
        database.close();
        return true;
    }

    public boolean removeQuotation(String quote) {
        boolean res = false;
        if (existQuotation(quote)) {
            String quoteColumn = QuotationContract.ContractBase.QUOTE_COLUMN;
            SQLiteDatabase database = getWritableDatabase();

            database.delete("quotation_table", quoteColumn + "=?", new String[] {quote});
            database.close();
            res = true;
        }
        return res;

    }
}
