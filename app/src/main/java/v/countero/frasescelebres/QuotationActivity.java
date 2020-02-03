package v.countero.frasescelebres;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

}
