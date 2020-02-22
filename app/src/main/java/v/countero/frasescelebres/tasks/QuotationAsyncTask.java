package v.countero.frasescelebres.tasks;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import v.countero.frasescelebres.QuotationActivity;
import v.countero.frasescelebres.pojos.Quotation;

public class QuotationAsyncTask extends AsyncTask<Void, Void, Quotation> {

    private WeakReference<QuotationActivity> reference;

    public QuotationAsyncTask(QuotationActivity quotationActivity) {
        reference = new WeakReference<>(quotationActivity);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (reference.get() != null) {
            reference.get().hideActionBarShowProgressBar();
        }
    }

    @Override
    protected void onPostExecute(Quotation quotation) {
        super.onPostExecute(quotation);
        if (reference.get() != null) {
            reference.get().quotationReceivedFromWebService(new Quotation("hola", "prueba"));
        }
    }

    @Override
    protected Quotation doInBackground(Void... voids) {
        return new Quotation("","");
    }
}