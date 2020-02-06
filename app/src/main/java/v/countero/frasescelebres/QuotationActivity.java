package v.countero.frasescelebres;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class QuotationActivity extends AppCompatActivity {

    private TextView tvQuotation, tvAuthor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        tvQuotation = findViewById(R.id.tvQuotation);
        tvAuthor = findViewById(R.id.tvAuthor);

        String user = getString(R.string.username);
        tvQuotation.setText(tvQuotation.getText().toString().replace("%1s", user));
    }

    public void newQuotation(View view) {
        tvAuthor.setText(getString(R.string.sample_autor));
        tvQuotation.setText(getString(R.string.sample_quotation));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quotation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return super.onOptionsItemSelected(item);
            case R.id.menu_add:
                break;
            case R.id.menu_refresh:
                newQuotation(item.getActionView());
                super.onOptionsItemSelected(item);
        }
        Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
