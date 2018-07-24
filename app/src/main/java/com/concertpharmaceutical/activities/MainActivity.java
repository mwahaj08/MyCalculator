package com.concertpharmaceutical.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.concertpharmaceutical.R;
import com.concertpharmaceutical.preferences.AppConstants;
import com.concertpharmaceutical.preferences.PreferenceHandler;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Ayesha Ahmad on 3/7/2016.
 * Modified by Hammad Mukhtar on 9/7/2016.
 */
public class MainActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View btnSettings = findViewById(R.id.btnSettings);
        findViewById(R.id.btnScan).setOnClickListener(this);
        btnSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnScan:
                try {
                    Intent intent = new Intent(this, ScannerActivity.class);
                    startActivity(intent);

                } catch (Exception e) {
                    Log.i("myApp", e.toString());
                    Toast.makeText(getApplicationContext(), "ERROR:" + e, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnSettings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
    }
}
