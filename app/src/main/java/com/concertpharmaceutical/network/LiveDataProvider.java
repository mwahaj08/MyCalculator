package com.concertpharmaceutical.network;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.concertpharmaceutical.app.AppContext;
import com.concertpharmaceutical.models.Container;
import com.concertpharmaceutical.models.Location;
import com.concertpharmaceutical.preferences.WebServiceConstant;
import com.concertpharmaceutical.utils.JsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonArray;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LiveDataProvider {
    ProgressDialog mProgressDialog;
    static AppContext objAppContext;

    public LiveDataProvider(AppContext app) {
        objAppContext = app;
    }

    /*
      PURPOSE : TO GET THE CONTAINER DETAIL AGAINST THE CONTAINER UID
     */
    public  void getContainerDetail(Context context,  String url,
                              final OnResultListener listener){
        VolleySuccessListener successListener = new VolleySuccessListener() {
            @Override
            public void onSuccessResponse(Object response, String data) {
                try{
                    JsonElement jWraperElement = new JsonParser().parse(response.toString());
                    if(jWraperElement.getAsJsonArray().size() > 0) {
                        JsonObject jWraperObject = (JsonObject) jWraperElement.getAsJsonArray().get(0);
                        Container container = (Container) JsonUtil.fromJson(jWraperObject.toString(), Container.class);
                        listener.onResult(new Result(container), 0, null);
                    } else {
                        listener.onResult(new Result(null), 0, null);

                    }


                }catch (Exception e){
                    listener.onResult(new Result(null), 0, null);
                }
            }
        };

        VolleyErrorListener errorListener = new VolleyErrorListener() {
            @Override
            public void onErrorResponse(String error, int errorCode) {
                listener.onResult(null, errorCode, error);
            }
        };

        VolleyUtil.sendVolleyGetRequest(url, successListener, errorListener);
    }

    /*
     PURPOSE : TO DISPOSE THE CONTAINER AGAINST THE CONTAINER UID
    */
    public  void disposeContainer(Context context,  String url,
                                    final OnResultListener listener){
        VolleySuccessListener successListener = new VolleySuccessListener() {
            @Override
            public void onSuccessResponse(Object response, String data) {
                try{
                    if(data!=null){
                        JsonElement jWraperElement = new JsonParser().parse(response.toString());
                        Container container = (Container) JsonUtil.fromJson(jWraperElement.toString(), Container.class);
                        listener.onResult(new Result(container), 0, null);
                    }else{
                        listener.onResult(new Result(null), 0, null);
                    }


                }catch (Exception e){
                    listener.onResult(new Result(null), 0, null);
                }
            }
        };

        VolleyErrorListener errorListener = new VolleyErrorListener() {
            @Override
            public void onErrorResponse(String error, int errorCode) {
                listener.onResult(null, errorCode, error);
            }
        };

        VolleyUtil.sendPostRequest(url, null, successListener, errorListener);
        //VolleyUtil.sendVolleyGetRequest(url, successListener, errorListener);
    }

    /*
      PURPOSE : TO GET THE LIST OF LOCATIONS
     */
    public void getLocations(Context context,  String url,
                                    final OnResultListener listener){

        VolleySuccessListener successListener = new VolleySuccessListener() {
            @Override
            public void onSuccessResponse(Object response, String data) {
                try{
                    if(data!=null){
                        JsonElement jWraperElement = new JsonParser().parse(response.toString());
                        JsonArray arrWrapperObject = jWraperElement.getAsJsonArray();
                        ArrayList<Location> locationList = (ArrayList<Location>) JsonUtil.fromJsonList(arrWrapperObject, Location.class);
                        listener.onResult(new Result(locationList), 0, null);

                    }
                    else{
                        listener.onResult(new Result(null), 0, null);
                    }

                }catch (Exception e){
                    listener.onResult(new Result(null), 0, null);
                }
            }
        };

        VolleyErrorListener errorListener = new VolleyErrorListener() {
            @Override
            public void onErrorResponse(String error, int errorCode) {
                listener.onResult(null, errorCode, error);
            }
        };

        VolleyUtil.sendVolleyGetRequest(url, successListener, errorListener);
    }

      /*
         PURPOSE : TO UPDATE THE LOCATION ID AGAINST THE CONTAINER ID
        */
    public void saveContainer(Context context,  String url, Map<String,Integer> dataMap,
                              final OnResultListener listener){

        VolleySuccessListener successListener = new VolleySuccessListener() {
            @Override
            public void onSuccessResponse(Object response, String data) {
                try{
                    if(data!=null){
                        listener.onResult(new Result(data), 0, null);
                    }

                }catch (Exception e){
                    listener.onResult(new Result(null), 0, null);
                }
            }
        };

        VolleyErrorListener errorListener = new VolleyErrorListener() {
            @Override
            public void onErrorResponse(String error, int errorCode) {
                listener.onResult(null, errorCode, error);
            }
        };

        VolleyUtil.sendPostRequest(url, dataMap, successListener, errorListener);
    }

    /*
            PURPOSE : TO UPDATE THE EXPIRATION DATE AGAINST THE CONTAINER ID
           */
    public void setExpirationDate(Context context,  String url, Map<String,String> dataMap,
                              final OnResultListener listener){

        VolleySuccessListener successListener = new VolleySuccessListener() {
            @Override
            public void onSuccessResponse(Object response, String data) {
                try{
                    if(data!=null){
                        JSONObject jsonobject = new JSONObject(data.toString());
                        String url = "";
                        if (jsonobject != null) {

                            url = jsonobject.optString("location_url");
                            listener.onResult(new Result(url), 0, null);
                        }
                        else{
                            listener.onResult(new Result(""), 0 , null);
                        }
                    }

                }catch (Exception e){

                }


            }
        };

        VolleyErrorListener errorListener = new VolleyErrorListener() {
            @Override
            public void onErrorResponse(String error, int errorCode) {
                listener.onResult(null, errorCode, error);
            }
        };

        VolleyUtil.sendPutRequest(url,dataMap,successListener,errorListener);
    }


}

