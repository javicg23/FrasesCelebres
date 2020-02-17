package v.countero.frasescelebres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import v.countero.frasescelebres.databases.QuotationDatabase;
import v.countero.frasescelebres.databases.QuotationSQLiteOpenHelper;
import v.countero.frasescelebres.pojos.Quotation;

public class QuotationActivity extends AppCompatActivity {

    private TextView tvQuotation, tvAuthor;
    private int nQuotationReceived = 0;
    private MenuItem menuAdd;
    private String quote, author;
    private boolean addVisible;
    private String databaseMethod = "SQLiteOpenHelper";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("quote", quote);
        outState.putString("author", author);
        outState.putInt("nQuotation", nQuotationReceived);
        outState.putBoolean("addVisible", menuAdd.isVisible());
        outState.putString("db", databaseMethod);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        tvQuotation = findViewById(R.id.tvQuotation);
        tvAuthor = findViewById(R.id.tvAuthor);

        if (savedInstanceState == null) {

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

            String user = prefs.getString("username", "");
            if (user.equals("")) {
                user = getString(R.string.username);
            }
            tvQuotation.setText(tvQuotation.getText().toString().replace("%1s", user));
            addVisible = false;
        } else {
            quote = savedInstanceState.getString("quote");
            author = savedInstanceState.getString("author");
            tvQuotation.setText(quote);
            tvAuthor.setText(author);
            addVisible = savedInstanceState.getBoolean("addVisible");
            nQuotationReceived = savedInstanceState.getInt("nQuotation");
            databaseMethod = savedInstanceState.getString("db");
        }
    }

    public void newQuotation(View view, int nQuotation) {
        quote = getString(R.string.sample_quotation).replace("%1$d", nQuotation+"");
        author = getString(R.string.sample_autor).replace("%1$d", nQuotation+"");
        tvAuthor.setText(author);
        tvQuotation.setText(quote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quotation, menu);
        menuAdd = menu.findItem(R.id.menu_add);
        menuAdd.setVisible(addVisible);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return super.onOptionsItemSelected(item);
            case R.id.menu_add:
                switch (databaseMethod) {
                    case "SQLiteOpenHelper":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                QuotationSQLiteOpenHelper.getInstance(getBaseContext()).insertQuotation(quote, author);
                            }
                        }).start();
                       break;
                    case "Room":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                QuotationDatabase.getInstance(getBaseContext()).quotationDAO().insertQuotation(new Quotation(quote, author));
                            }
                        }).start();
                        break;
                }
                menuAdd.setVisible(false);
                break;
            case R.id.menu_refresh:
                nQuotationReceived++;
                newQuotation(item.getActionView(), nQuotationReceived);
                switch (databaseMethod) {
                    case "SQLiteOpenHelper":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (QuotationSQLiteOpenHelper.getInstance(getBaseContext()).existQuotation(quote)) {
                                    addVisible = false;
                                } else {
                                    addVisible = true;
                                }
                            }
                        }).start();
                        break;
                    case "Room":
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Quotation textQuotationExistence = QuotationDatabase.getInstance(getBaseContext()).quotationDAO().getQuotationText(tvQuotation.getText().toString());
                                if (!textQuotationExistence.getQuoteText().equals("")) {
                                    addVisible = false;
                                } else {
                                    addVisible = true;
                                }
                            }
                        }).start();
                        break;
                }
                menuAdd.setVisible(addVisible);
                super.onOptionsItemSelected(item);
        }
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
