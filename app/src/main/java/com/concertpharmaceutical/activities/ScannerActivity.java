package com.concertpharmaceutical.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.concertpharmaceutical.preferences.AppConstants;
import com.concertpharmaceutical.preferences.PreferenceHandler;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

/**
 * Created by Ayesha Ahmad on 3/4/2016.
 * Modified by Hammad Mukhtar on 9/7/2016.
 */
public class ScannerActivity extends Activity {
    public static final int REQUEST_SCANDIT = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launchScanner();
    }

    private void launchScanner(){
        try {

            String scanner = PreferenceHandler.getStringPreferences(AppConstants.SCANNER);
            if(scanner.equals(AppConstants.ZXING)){
                IntentIntegrator objIntentIntegrator = new IntentIntegrator(this);
                objIntentIntegrator.setCaptureActivity(CaptureActivityAnyOrientation.class);
                objIntentIntegrator.setOrientationLocked(false);
                objIntentIntegrator.initiateScan();
            }else{
                Intent intent = new Intent(this, ScanditActivity.class);
                startActivityForResult(intent, REQUEST_SCANDIT);
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR:" + e, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch(requestCode) {
            case IntentIntegrator.REQUEST_CODE:
            {
                if (resultCode == RESULT_OK){
                    IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                    String containerId = scanResult.getContents();
                    Intent i = new Intent(this, ContainerDetailActivity.class);
                    i.putExtra("containerId", containerId);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                finish();
                break;
            }
            case REQUEST_SCANDIT:
                if (resultCode == RESULT_OK){
                    String containerId = intent.getStringExtra("result");
                    Intent i = new Intent(this, ContainerDetailActivity.class);
                    i.putExtra("containerId", containerId);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                finish();
                break;
        } }


}
