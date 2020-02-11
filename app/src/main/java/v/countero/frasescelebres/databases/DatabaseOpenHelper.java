package v.countero.frasescelebres.databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import v.countero.frasescelebres.pojos.Quotation;

public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static DatabaseOpenHelper databaseOpenHelper;


    private DatabaseOpenHelper(Context context) {
        super(context,"quotation_database", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String id = QuotationContract.ContractBase.ID;
        String quoteColumn = QuotationContract.ContractBase.QUOTE_COLUMN;
        String authorColumn = QuotationContract.ContractBase.AUTHOR_COLUMN;
        db.execSQL("CREATE TABLE quotation_table ("
                + id +" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                + quoteColumn +" TEXT NOT NULL, "
                + authorColumn + " TEXT, UNIQUE(quote));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public synchronized static DatabaseOpenHelper getInstance(Context context) {
        if (databaseOpenHelper == null) {
            databaseOpenHelper =  new DatabaseOpenHelper(context);
        }
        return databaseOpenHelper;
    }

    public ArrayList<Quotation> getQuotesFromDB() {
        String quoteColumn = QuotationContract.ContractBase.QUOTE_COLUMN;
        String authorColumn = QuotationContract.ContractBase.AUTHOR_COLUMN;

        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = database.query("quotation_table", new String[]{quoteColumn, authorColumn}, null, null, null, null, null);

        while(cursor.moveToNext()) {
            cursor.getString(0);
        }
        return null;
    }
}
