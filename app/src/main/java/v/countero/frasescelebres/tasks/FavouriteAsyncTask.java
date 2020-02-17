package v.countero.frasescelebres.tasks;

import android.content.Context;
import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import v.countero.frasescelebres.FavouriteActivity;
import v.countero.frasescelebres.databases.QuotationDatabase;
import v.countero.frasescelebres.databases.QuotationSQLiteOpenHelper;
import v.countero.frasescelebres.pojos.Quotation;

public class FavouriteAsyncTask extends AsyncTask<Boolean,Void, List<Quotation>> {

    private WeakReference<FavouriteActivity> reference;

    public FavouriteAsyncTask(FavouriteActivity favouriteActivity) {
        reference = new WeakReference<>(favouriteActivity);
    }

    @Override
    protected void onPostExecute(List<Quotation> quotations) {
        super.onPostExecute(quotations);
        if (reference.get() != null) {
            reference.get().addQuotesRefresh(quotations);
        }
    }

    @Override
    protected List<Quotation> doInBackground(Boolean... booleans) {
        List<Quotation> data = new ArrayList<>();
        if (booleans[0]){
            data = QuotationSQLiteOpenHelper.getInstance(reference.get()).getQuotations();
        } else {
            data = QuotationDatabase.getInstance(reference.get()).quotationDAO().getQuotations();
        }
        return data;
    }
}
