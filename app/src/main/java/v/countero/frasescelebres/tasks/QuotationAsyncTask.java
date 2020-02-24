package v.countero.frasescelebres.tasks;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import v.countero.frasescelebres.QuotationActivity;
import v.countero.frasescelebres.R;
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
            reference.get().quotationReceivedFromWebService(quotation);
        }
    }

    @Override
    protected Quotation doInBackground(Void... voids) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("api.forismatic.com");
        builder.appendPath("api");
        builder.appendPath("1.0");
        builder.appendPath("");
        builder.appendQueryParameter("method", "getQuote");
        builder.appendQueryParameter("format", "json");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(reference.get());
        String language = prefs.getString("language", "en");
        builder.appendQueryParameter("lang", language);
        Quotation quotation = null;

        try {
            URL url = new URL(builder.build().toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);

            if (connection.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                Gson gson = new Gson();
                quotation = gson.fromJson(reader, Quotation.class);
                reader.close();
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return quotation;
    }
}