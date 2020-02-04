package v.countero.frasescelebres;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

import v.countero.frasescelebres.adapters.RecyclerAdapter;
import v.countero.frasescelebres.pojos.Quotation;

public class FavouriteActivity extends AppCompatActivity {

    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        recycler = findViewById(R.id.recyclerFavourite);

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);

        DividerItemDecoration decoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recycler.setLayoutManager(manager);
        recycler.addItemDecoration(decoration);

        ArrayList<Quotation> data = getMockQuotations();
        RecyclerAdapter adapter = new RecyclerAdapter(data);
        recycler.setAdapter(adapter);

    }

    public void searchAuthor(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + "Valencia"));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favourite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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

                    }
                });
                break;
        }
        return true;
    }

    public ArrayList<Quotation> getMockQuotations() {
        ArrayList<Quotation> list = new ArrayList<Quotation>();
        for (int i = 0; i < 20; i++) {
            Quotation quote = new Quotation(i + "",i + "");
            list.add(quote);
        }
        return list;
    }
}
