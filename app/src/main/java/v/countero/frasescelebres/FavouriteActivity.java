package v.countero.frasescelebres;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.net.URLEncoder;
import java.util.ArrayList;

import v.countero.frasescelebres.adapters.RecyclerAdapter;
import v.countero.frasescelebres.databases.QuotationDatabase;
import v.countero.frasescelebres.databases.QuotationSQLiteOpenHelper;
import v.countero.frasescelebres.pojos.Quotation;

public class FavouriteActivity extends AppCompatActivity {

    RecyclerView recycler;
    private RecyclerAdapter adapter = null;
    private String databaseMethod = "SQLiteOpenHelper";

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("db", databaseMethod);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        recycler = findViewById(R.id.recyclerFavourite);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);

        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(decoration);

        if (savedInstanceState == null) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            String user = prefs.getString("username", "");
            if (user.equals("")) {
                user = getString(R.string.username);
            }
        } else {
            databaseMethod = savedInstanceState.getString("db");
        }

        ArrayList<Quotation> data = null;

        switch (databaseMethod) {
            case "SQLiteOpenHelper":
                data = QuotationSQLiteOpenHelper.getInstance(this).getQuotations();
                break;
            case "Room":
                data = (ArrayList<Quotation>) QuotationDatabase.getInstance(this).quotationDAO().getQuotations();
                break;
        }


        RecyclerAdapter.OnItemClickListener clickListener = new RecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position) {
                showWikipediaAuthorByPosition(position);
            }

        };
        RecyclerAdapter.OnItemLongClickListner longClickListener = new RecyclerAdapter.OnItemLongClickListner() {
            @Override
            public void onItemLongClickListener(int position) {
                showRemovedDialog(position);
            }
        };
        adapter = new RecyclerAdapter(data, clickListener, longClickListener);
        recycler.setAdapter(adapter);

    }

    private void showRemovedDialog(final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_delete_confirmation);
        builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
            }
        });
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (databaseMethod) {
                    case "SQLiteOpenHelper":
                        QuotationSQLiteOpenHelper.getInstance(getBaseContext()).removeQuotation(adapter.getQuotationByPosition(position).getQuoteText());
                        break;
                    case "Room":
                        QuotationDatabase.getInstance(getBaseContext()).quotationDAO().deleteQuotation(adapter.getQuotationByPosition(position));
                        break;
                }
                adapter.removeElementByPosition(position);
            }
        });
        builder.create().show();
    }

    private void showWikipediaAuthorByPosition(int position) {
        Quotation quote = adapter.getQuotationByPosition(position);
        if (quote != null && quote.getQuoteAuthor().compareTo("") != 0 && quote.getQuoteAuthor() != null) {
            searchAuthor(quote.getQuoteAuthor());
        } else {
            Toast.makeText(getApplicationContext(),R.string.toast_cannot_obtain_information , Toast.LENGTH_SHORT).show();
        }
    }

    public void searchAuthor(String author) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + author));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
        setInvisibleClearAll(menu.findItem(R.id.menu_clear_all));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return super.onOptionsItemSelected(item);
            case R.id.menu_clear_all:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(android.R.drawable.stat_sys_warning);
                builder.setTitle(R.string.menu_favourite_dialog_title);
                builder.setMessage(R.string.menu_favourite_dialog_message);
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteData();
                        setInvisibleClearAll(item);
                    }
                });
                builder.create().show();
        }
        return true;
    }

    public ArrayList<Quotation> getMockQuotations() {
        ArrayList<Quotation> list = new ArrayList<Quotation>();
        for (int i = 10; i < 30; i++) {
            Quotation quote = new Quotation(i + "",i + "");
            list.add(quote);
        }
        Quotation quote = new Quotation("cita 1","Albert Einstein");
        list.add(quote);
        quote = new Quotation("cita 2","Leonhard Euler");
        list.add(quote);
        quote = new Quotation("cita 2","");
        list.add(quote);
        return list;
    }

    public void deleteData() {
        adapter.removeAllData();
        switch (databaseMethod) {
            case "SQLiteOpenHelper":
                QuotationSQLiteOpenHelper.getInstance(this).removeAll();
                break;
            case "Room":
                QuotationDatabase.getInstance(this).quotationDAO().deleteAllQuotations();
                break;
        }
    }

    public void setInvisibleClearAll(MenuItem item) {
        if (adapter.getItemCount() <= 0) {
            item.setVisible(false);
        }
    }
}
