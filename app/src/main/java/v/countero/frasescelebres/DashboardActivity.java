package v.countero.frasescelebres;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

    }

    public void pulsarBoton(View view) {
        Button btn = (Button) view;
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_getQuotations:
                intent = new Intent(
                        DashboardActivity.this,
                        QuotationActivity.class
                );
                startActivity(intent);
                break;
            case R.id.btn_favouriteQuotations:
                intent = new Intent(
                        DashboardActivity.this,
                        FavouriteActivity.class
                );
                startActivity(intent);
                break;
            case R.id.btn_settings:
                intent = new Intent(
                        DashboardActivity.this,
                        SettingsActivity.class
                );
                startActivity(intent);
                break;
            case R.id.btn_about:
                intent = new Intent(
                        DashboardActivity.this,
                        AboutActivity.class
                );
                startActivity(intent);
                break;
        }

    }
}
