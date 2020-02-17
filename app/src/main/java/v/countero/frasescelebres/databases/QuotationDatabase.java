package v.countero.frasescelebres.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import v.countero.frasescelebres.pojos.Quotation;

@Database(entities = {Quotation.class}, version = 1, exportSchema = false)
public abstract class QuotationDatabase extends RoomDatabase {
    private static QuotationDatabase quotationDatabase;
    public abstract QuotationDAO quotationDAO();

    public synchronized static QuotationDatabase getInstance(Context context) {
        if (quotationDatabase == null) {
            quotationDatabase = Room.databaseBuilder(context, QuotationDatabase.class, "quotation_database").build();
        }
        return quotationDatabase;
    }
}
