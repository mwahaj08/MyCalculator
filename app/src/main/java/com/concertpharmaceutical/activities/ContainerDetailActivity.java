package com.concertpharmaceutical.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.concertpharmaceutical.R;
import com.concertpharmaceutical.app.AppContext;
import com.concertpharmaceutical.models.Container;
import com.concertpharmaceutical.models.Location;
import com.concertpharmaceutical.network.OnResultListener;
import com.concertpharmaceutical.network.Result;
import com.concertpharmaceutical.preferences.AppConstants;
import com.concertpharmaceutical.preferences.PreferenceHandler;
import com.concertpharmaceutical.preferences.WebServiceConstant;
import com.concertpharmaceutical.utils.DateUtil;
import com.concertpharmaceutical.utils.LOGGER;

import android.widget.AdapterView.OnItemSelectedListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by Ayesha Ahmad on 3/4/2016.
 */
public class ContainerDetailActivity extends Activity implements OnItemSelectedListener, View.OnClickListener {

    TextView txt_LotNo;
    TextView txt_Substance;
    Spinner  spinner_Location;
    TextView txt_Size;
    TextView txt_CAS;
    TextView txt_Supplier;
    TextView txt_CatalogNo;
    TextView txt_ExpirationDate;
    TextView txt_ContainerID;
    TextView txt_ContainerDesc;
    View tvDispose;
    View ivSave;
    View ivDispose;


    ArrayAdapter<String> adapter_locations;
    ArrayList<Location> locationArrayList = null;
    String[] arrLocation;
    Location objSelectedLocation;
    Container container;

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container_detail);
        Bundle b = this.getIntent().getExtras();
        String id = b.getString("containerId");
        getContainerDetails(id);
        findViewById(R.id.btnScan).setOnClickListener(this);
        findViewById(R.id.ivDispose).setOnClickListener(this);
        spinner_Location = (Spinner) findViewById(R.id.spinner_Location);
        spinner_Location.setOnItemSelectedListener(this);
        tvDispose = findViewById(R.id.tvDispose);
        ivDispose = findViewById(R.id.ivDispose);
        ivSave = findViewById(R.id.save);
        ivSave.setOnClickListener(this);
        if(!PreferenceHandler.getBooleanPreferences(AppConstants.ENABLE_DISPOSE)) {
            findViewById(R.id.ivDispose).setVisibility(View.GONE);
        }

    }

    private void getContainerDetails(String id) {
        //WebServiceConstant.BASE_URL = PreferenceHandler.getStringPreferences(AppConstants.SERVER_URL);
        String url = WebServiceConstant.BASE_URL + WebServiceConstant.CONTAINER_DETAILS + "?uid=" + id;
        //String url = "https://api.myjson.com/bins/1w4kp";
        //new connectAsyncTask(url).execute();
        showWaitDialog();
        AppContext.dataProvider.getContainerDetail(this, url, new OnResultListener() {
            @Override
            public void onResult(Result result, int errorCode, String errorString) {

                if (result != null) {
                    container = (Container) result.getResultObj();
                    if (container != null) {
                        displayContainerDetail(container);
                        getLocations();
                        if (!container.getDisposed_at().equals("")) {
                            tvDispose.setVisibility(View.VISIBLE);
                            ivDispose.setVisibility(View.GONE);
                            ivSave.setVisibility(View.GONE);
                            txt_ExpirationDate.setOnClickListener(null);
                            spinner_Location.setEnabled(false);
                        }
                        hideWaitDialog();
                    } else {
                        hideWaitDialog();
                        showAlertDialog("Invalid Barcode. Please try again.");
                    }
                } else if (errorString != null) {
                    hideWaitDialog();
                    try {
                        Toast.makeText(ContainerDetailActivity.this, errorString, Toast.LENGTH_LONG).show();
                        finish();
                    } catch (Exception e) {
                        Log.d("myApp", e.toString());
                        Toast.makeText(ContainerDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    hideWaitDialog();
                    Toast.makeText(ContainerDetailActivity.this, "Unable to connect to Server. Please try later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void disposeContainer() {
        String url = WebServiceConstant.BASE_URL + WebServiceConstant.DISPOSE_CONTAINER /*+ "3485"*/ + container.getId();
        //String url = "https://api.myjson.com/bins/597g5";
        showWaitDialog();
        AppContext.dataProvider.disposeContainer(this, url, new OnResultListener() {
            @Override
            public void onResult(Result result, int errorCode, String errorString) {

                if (result != null) {
                    container = (Container) result.getResultObj();
                    if (container != null) {
                        if (!container.getDisposed_at().equals("")) {
                            tvDispose.setVisibility(View.VISIBLE);
                            ivDispose.setVisibility(View.GONE);
                            ivSave.setVisibility(View.GONE);
                            txt_ExpirationDate.setOnClickListener(null);
                            spinner_Location.setEnabled(false);
                        } else {
                            Toast.makeText(ContainerDetailActivity.this, "Container couldn't be disposed", Toast.LENGTH_SHORT).show();
                        }
                        hideWaitDialog();
                    } else {
                        hideWaitDialog();
                        showAlertDialog("Something went wrong. Please Try Again.");
                    }
                } else if (errorString != null) {
                    hideWaitDialog();
                    try {
                        Toast.makeText(ContainerDetailActivity.this, errorString, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d("myApp", e.toString());
                        Toast.makeText(ContainerDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    hideWaitDialog();
                    Toast.makeText(ContainerDetailActivity.this, "Unable to connect to Server. Please try later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setExpirationDate(String id, String expiration_date) {
        String url = WebServiceConstant.BASE_URL +WebServiceConstant.SET_EXPIRATION_DATE + id + "?expiration_date="+ expiration_date;
        Map<String, String> paramHashMap = new HashMap<String, String>();
        //paramHashMap.put("expiration_date", expiration_date);
        showWaitDialog();
        AppContext.dataProvider.setExpirationDate(this, url, paramHashMap, new OnResultListener() {
            @Override
            public void onResult (Result result,int errorCode, String errorString){

                if (result != null) {
                    String url = (String) result.getResultObj();
                    if (url != null && !url.equals("")) {
                        Toast.makeText(ContainerDetailActivity.this, "Expiration Date Changed", Toast.LENGTH_SHORT).show();
                        hideWaitDialog();
                    } else {
                        hideWaitDialog();
                        showAlertDialog("Something went wrong. Please Try Again.");
                    }
                } else if (errorString != null) {
                    hideWaitDialog();
                    try {
                        Toast.makeText(ContainerDetailActivity.this, errorString, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d("myApp", e.toString());
                        Toast.makeText(ContainerDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    hideWaitDialog();
                    Toast.makeText(ContainerDetailActivity.this, "Unable to connect to Server. Please try later", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void onDatePicking() {
        Date dt = new Date();
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR), month = today.get(Calendar.MONTH), day = today.get(Calendar.DAY_OF_MONTH);

        final String dateStr = "" + txt_ExpirationDate.getText();

        if (dateStr != null && dateStr.length() > 0) {
            try {
                Date date = DateUtil.parseDate(dateStr, "yyyy-MM-dd");//new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault()).parse(dateStr);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
            } catch (Exception e) {
                LOGGER.Log(this, e);
            }
        }

        DatePickerDialog dlg = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int years, int monthOfYear,
                                  int dayOfMonth) {
                monthOfYear++;
                String dateStr = "" + years + "-" + monthOfYear + "-" + dayOfMonth;
                Date date = DateUtil.parseDate(dateStr, "yyyy-MM-dd");
                dateStr = DateUtil.formatDate(date, "yyyy-MM-dd");
                txt_ExpirationDate.setText(dateStr);
                container.setExpiration_date(dateStr);
                setExpirationDate(String.valueOf(container.getId()), dateStr);


            }
        }, year, month, day);

        dlg.getDatePicker().setMinDate(DateUtil.getCurrentTime());
        dlg.show();
    }

    private void displayContainerDetail(Container objContainer){
        try {

            txt_LotNo = (TextView) findViewById(R.id.val_LotNo);
            txt_Substance = (TextView) findViewById(R.id.val_substance);
            txt_Size = (TextView) findViewById(R.id.val_Size);
            txt_CAS = (TextView) findViewById(R.id.val_CAS);
            txt_Supplier = (TextView) findViewById(R.id.val_Supplier);
            txt_CatalogNo = (TextView) findViewById(R.id.val_CatalogNo);
            txt_ExpirationDate = (TextView) findViewById(R.id.val_ExpirationDate);
            txt_ExpirationDate.setOnClickListener(this);
            txt_ContainerID = (TextView) findViewById(R.id.val_ContainerID);
            txt_ContainerDesc = (TextView) findViewById(R.id.val_Description);

            if(objContainer.getLot_no()!= null){
                txt_LotNo.setText(objContainer.getLot_no());
            }
            if(objContainer.getSubstance()!=null){
                txt_Substance.setText(objContainer.getSubstance());
            }

            txt_Size.setText(String.valueOf(objContainer.getSize())+ objContainer.getUom());

            if(objContainer.getCas()!= null){
                txt_CAS.setText(objContainer.getCas());
            }

            if(objContainer.getSupplier()!= null){
                txt_Supplier.setText(objContainer.getSupplier());
            }
            if(objContainer.getCatalog_no() != null){
                txt_CatalogNo.setText(objContainer.getCatalog_no());
            }
            if(objContainer.getExpiration_date() != null){
                txt_ExpirationDate.setText(objContainer.getExpiration_date());
            }

            txt_ContainerID.setText(String.valueOf(objContainer.getUid()));
            if(objContainer.getDescription()!= null){
                txt_ContainerDesc.setText(objContainer.getDescription());
            }



        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void displayContainerWithEmptyValues(){
        try {

            txt_LotNo = (TextView) findViewById(R.id.val_LotNo);
            txt_Substance = (TextView) findViewById(R.id.val_substance);
            txt_Size = (TextView) findViewById(R.id.val_Size);
            txt_CAS = (TextView) findViewById(R.id.val_CAS);
            txt_Supplier = (TextView) findViewById(R.id.val_Supplier);
            txt_CatalogNo = (TextView) findViewById(R.id.val_CatalogNo);
            txt_ExpirationDate = (TextView) findViewById(R.id.val_ExpirationDate);
            txt_ContainerID = (TextView) findViewById(R.id.val_ContainerID);
            txt_ContainerDesc = (TextView) findViewById(R.id.val_Description);

            txt_LotNo.setText("");
            txt_Substance.setText("");
            txt_Size.setText("");
            txt_CAS.setText("");
            txt_Supplier.setText("");
            txt_CatalogNo.setText("");
            txt_ExpirationDate.setText("");
            txt_ContainerID.setText("");
            txt_ContainerDesc.setText("");

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }


    private void getLocations(){
        String url = WebServiceConstant.BASE_URL + WebServiceConstant.LOCATIONS;

        AppContext.dataProvider.getLocations(this, url, new OnResultListener() {
            @Override
            public void onResult(Result result, int errorCode, String errorString) {

                if (result != null) {
                    locationArrayList = (ArrayList<Location>) result.getResultObj();
                    if (locationArrayList != null) {

                        getLocationAdapterArray(locationArrayList);
                        adapter_locations = new ArrayAdapter<String>(ContainerDetailActivity.this,
                                android.R.layout.simple_spinner_item, arrLocation);
                        adapter_locations.setDropDownViewResource(R.layout.spinner_item);
                        spinner_Location.setAdapter(adapter_locations);
                        spinner_Location.setSelection(adapter_locations.getPosition(container.getLocation().toString()));

                    }
                } else if (errorString != null) {
                    try {
                        Toast.makeText(ContainerDetailActivity.this, errorString, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Log.d("myApp", e.toString());
                        Toast.makeText(ContainerDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ContainerDetailActivity.this, "Unable to connect to Server. Please try later", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void getLocationAdapterArray(ArrayList<Location> paramLocationArrayList){
        try{

            arrLocation = new String[paramLocationArrayList.size()];
            for(int i=0;i<paramLocationArrayList.size();i++){
                arrLocation[i] = paramLocationArrayList.get(i).getName();
            }

        } catch(Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        if(locationArrayList != null) {
            objSelectedLocation = locationArrayList.get(position);
            container.setLocation_id(objSelectedLocation.getId());
            container.setLocation(objSelectedLocation.getName());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    public void scrollToTop(View v) {
        saveContainer();
    }

    private void saveContainer() {
        if(container != null) {
            String url = WebServiceConstant.BASE_URL + WebServiceConstant.SAVE_CONTAINER + container.getId() + "?location_id=" +container.getLocation_id();
            Map<String, Integer> paramHashMap = new HashMap<String, Integer>();
            //paramHashMap.put("location_id", container.getLocation_id());

            AppContext.dataProvider.saveContainer(this, url, paramHashMap, new OnResultListener() {
                @Override
                public void onResult(Result result, int errorCode, String errorString) {
                    if (result != null) {
                        Toast.makeText(ContainerDetailActivity.this, "Record Updated Successfully", Toast.LENGTH_LONG).show();

                    } else if (errorString != null) {
                        try {
                            Toast.makeText(ContainerDetailActivity.this, errorString, Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            Toast.makeText(ContainerDetailActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(ContainerDetailActivity.this, "Unable to connect to Server. Please try later", Toast.LENGTH_LONG).show();
                    }
                }
            });

        } else {
            showAlertDialog("Please Scan Correct Container BarCode");
        }
    }

    private void dispose() {
        showDisposeAlert();
    }

    public void showDisposeAlert() {

        final Activity context = this;
        try {
            AlertDialog.Builder builder =
                    new android.support.v7.app.AlertDialog.Builder(this);
            builder.setTitle("Confirm Dispose");
            builder.setMessage("Are you sure you want to dispose?");

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    disposeContainer();
                    dialog.dismiss();
                }
            });
            builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setCancelable(false);

            AlertDialog alertDialog = builder.create();

            alertDialog.show();
        } catch (Exception e) {
            Log.d("ContainerDetailActivity", e.toString());
        }
    }

    /********************************** BUTTON CLICK ********************************/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnScan:
                try {
                    Intent intent = new Intent(this, ScannerActivity.class);
                    startActivity(intent);
                    //finish();

                } catch (Exception e) {
                    Log.i("myApp", e.toString());
                    Toast.makeText(getApplicationContext(), "ERROR:" + e, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.ivDispose:
                dispose();
                break;
            case R.id.save:
                saveContainer();
                break;
            case R.id.val_ExpirationDate:
                onDatePicking();
                break;
        }
    }


    private void showWaitDialog() {
        progressDialog= ProgressDialog.show(ContainerDetailActivity.this,
                    "Concert Pharmaceuticals", "Please Wait! Processing..");
    }

    private void hideWaitDialog(){
        progressDialog.dismiss();
    }


    private void showAlertDialog(String message){
        try{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                    ContainerDetailActivity.this);

            alertDialog.setTitle("Concert Pharmaceuticals");
            alertDialog.setCancelable(false);
            alertDialog.setMessage(message);
            alertDialog.setIcon(R.drawable.error);
            alertDialog.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ContainerDetailActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
            alertDialog.show();
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }

}

