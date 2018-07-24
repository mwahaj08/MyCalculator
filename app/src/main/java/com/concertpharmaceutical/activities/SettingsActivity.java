package com.concertpharmaceutical.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.concertpharmaceutical.R;
import com.concertpharmaceutical.preferences.AppConstants;
import com.concertpharmaceutical.preferences.PreferenceHandler;
import com.concertpharmaceutical.preferences.WebServiceConstant;

/**
 * Created by Hammad Mukhtar on 9/7/2016.
 */
public class SettingsActivity extends Activity implements View.OnFocusChangeListener,
        CompoundButton.OnCheckedChangeListener, RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    EditText etServerURL;
    SwitchCompat swDispose;
    RadioGroup rgScanner;
    RadioButton rbZxing;
    RadioButton rbScandit;
    View ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etServerURL = (EditText) findViewById(R.id.etServerURL);
        swDispose = (SwitchCompat) findViewById(R.id.swDispose);
        rgScanner = (RadioGroup) findViewById(R.id.rgScanner);
        rbZxing = (RadioButton) findViewById(R.id.zxing);
        rbScandit = (RadioButton) findViewById(R.id.scandit);
        ivBack = findViewById(R.id.back);

        etServerURL.setOnFocusChangeListener(this);
        swDispose.setOnCheckedChangeListener(this);
        rgScanner.setOnCheckedChangeListener(this);
        ivBack.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        etServerURL.setText(PreferenceHandler.getStringPreferences(AppConstants.SERVER_URL));
        swDispose.setChecked(PreferenceHandler.getBooleanPreferences(AppConstants.ENABLE_DISPOSE));
        if(PreferenceHandler.getStringPreferences(AppConstants.SCANNER).equals(AppConstants.ZXING))
            rbZxing.setChecked(true);
        else
            rbScandit.setChecked(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if(!hasFocus) {
            if (URLUtil.isValidUrl(etServerURL.getText().toString())) {
                PreferenceHandler.updatePreferences(AppConstants.SERVER_URL, etServerURL.getText().toString());
                WebServiceConstant.BASE_URL = etServerURL.getText().toString();
                etServerURL.setError(null);
            } else {
                etServerURL.setError("Invalid URL");
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        PreferenceHandler.updatePreferences(AppConstants.ENABLE_DISPOSE, isChecked);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(rbZxing.isChecked())
            PreferenceHandler.updatePreferences(AppConstants.SCANNER, AppConstants.ZXING);
        else
            PreferenceHandler.updatePreferences(AppConstants.SCANNER, AppConstants.SCANDIT);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (URLUtil.isValidUrl(etServerURL.getText().toString())) {
            PreferenceHandler.updatePreferences(AppConstants.SERVER_URL, etServerURL.getText().toString());
            WebServiceConstant.BASE_URL = etServerURL.getText().toString();
            etServerURL.setError(null);
            super.onBackPressed();
        } else {
            etServerURL.setError("Invalid URL");
        }
    }

}
