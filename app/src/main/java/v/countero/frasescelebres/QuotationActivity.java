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

public class QuotationActivity extends AppCompatActivity {

    private TextView tvQuotation, tvAuthor;
    private int nQuotationReceived = 0;
    private MenuItem menuAdd;
    private String quote, author;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("quote", quote);
        outState.putString("author", author);
        outState.putInt("nQuotation", nQuotationReceived);
        outState.putBoolean("addVisible", menuAdd.isVisible());
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
        } else {
            tvQuotation.setText(savedInstanceState.getString("quote"));
            tvAuthor.setText(savedInstanceState.getString("author"));
            //menuAdd.setVisible(savedInstanceState.getBoolean("addVisible"));
            nQuotationReceived = savedInstanceState.getInt("nQuotation");
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
        menuAdd.setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return super.onOptionsItemSelected(item);
            case R.id.menu_add:
                menuAdd.setVisible(false);
                break;
            case R.id.menu_refresh:
                nQuotationReceived++;
                newQuotation(item.getActionView(), nQuotationReceived);
                super.onOptionsItemSelected(item);
        }
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
