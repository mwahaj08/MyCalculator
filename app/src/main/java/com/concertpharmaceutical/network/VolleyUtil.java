package com.concertpharmaceutical.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.concertpharmaceutical.app.AppContext;
import com.concertpharmaceutical.preferences.WebServiceConstant;
import com.concertpharmaceutical.utils.JsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class VolleyUtil {
    private static final String HEADER_ACCEPT = "Accept";
    private static final String TAG = "Volley";
    private static final int MY_SOCKET_TIMEOUT_MS = 180000;

    private static RequestQueue requestQueue;
    private static ImageLoader mImageLoader;

    public static StringRequest sendVolleyGetRequest(final String url,
                                                     final VolleySuccessListener successListener,
                                                     final VolleyErrorListener errorListener) {
        RequestQueue requestQueue = getRequestQueue();
        Response.ErrorListener volleyErrorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (errorListener != null) {
                    int status  = -1;
                    Log.i("ErrorResponse",volleyError.toString());
                    if(volleyError.networkResponse != null)
                    {
                        status  = volleyError.networkResponse.statusCode;

                        Log.e(TAG + "Error", volleyError.toString());
                    }
                    errorListener.onErrorResponse(handleVolleyError(volleyError), status);
                }
            }
        };

        Response.Listener volleySuccessListener = new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                try {
                    int errorCode = -1;
                    if (JsonUtil.isValidJSON(response.toString())) {
                        successListener.onSuccessResponse(response.toString(), response.toString());
                    } else {
                        errorListener.onErrorResponse(response.toString(), errorCode);
                    }
                } catch (Exception e) {
                    Log.d(TAG, e.toString());
                    successListener.onSuccessResponse(response.toString(), null);
                }
            }
        };


        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url, volleySuccessListener, volleyErrorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();

                    headerMap.put(HEADER_ACCEPT,  WebServiceConstant.GET_REQUEST_HEADER);

                return headerMap;
            }
        };
        requestQueue.add(jsonObjectRequest);
        return jsonObjectRequest;
    }

    public static JsonObjectRequest sendPostRequest(String url, Map dataMap, final VolleySuccessListener successListener, final VolleyErrorListener errorListener) {
        RequestQueue requestQueue = getRequestQueue();
        if(dataMap == null)
            dataMap = new HashMap();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(

                Request.Method.POST, url, new JSONObject(dataMap),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int errorCode = -1;
                            if (JsonUtil.isValidJSON(response.toString())) {
                                successListener.onSuccessResponse(response.toString(), "true");
                            } else {
                                errorListener.onErrorResponse(response.toString(), 500);                            }
                        } catch (Exception e) {
                            Log.d(TAG, e.toString());
                            successListener.onSuccessResponse(response.toString(), null);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (errorListener != null) {
                    int status  = -1;

                    if(volleyError.networkResponse != null)
                    {
                        status  = volleyError.networkResponse.statusCode;

                        Log.e(TAG + "Error", volleyError.toString());
                    }
                    errorListener.onErrorResponse(handleVolleyError(volleyError), status);
                }
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put(HEADER_ACCEPT,  WebServiceConstant.GET_REQUEST_HEADER);
                return headerMap;
            }
        };

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.i("PostRequestObject" , jsonObjectRequest.toString());
        getRequestQueue().add(jsonObjectRequest);
        return jsonObjectRequest;
    }


    public static StringRequest sendPostFormRequest(String url, final Map dataMap, final VolleySuccessListener successListener, final VolleyErrorListener errorListener) {
        RequestQueue requestQueue = getRequestQueue();


        StringRequest jsonObjRequest = new StringRequest(Request.Method.POST,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            int errorCode = -1;
                            if (JsonUtil.isValidJSON(response.toString())) {
                                successListener.onSuccessResponse(response.toString(), "true");
                            } else {
                                errorListener.onErrorResponse(response.toString(), 500);                            }
                        } catch (Exception e) {
                            Log.d(TAG, e.toString());
                            successListener.onSuccessResponse(response.toString(), null);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (errorListener != null) {
                    int status  = -1;

                    if(volleyError.networkResponse != null)
                    {
                        status  = volleyError.networkResponse.statusCode;

                        Log.e(TAG + "Error", volleyError.toString());
                    }
                    errorListener.onErrorResponse(handleVolleyError(volleyError), status);
                }
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("location_id", dataMap.get("location_id").toString());

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();
                headerMap.put(HEADER_ACCEPT,  WebServiceConstant.GET_REQUEST_HEADER);
                return headerMap;
            }

        };




        jsonObjRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Log.i("PostRequestObject" , jsonObjRequest.toString());
        getRequestQueue().add(jsonObjRequest);
        return jsonObjRequest;
    }

    public static JsonObjectRequest sendPutRequest(String url, Map dataMap,
                                                   final VolleySuccessListener successListener,
                                                   final VolleyErrorListener errorListener) {
        RequestQueue requestQueue = getRequestQueue();



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT, url, new JSONObject(dataMap),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int errorCode = -1;

                            if (JsonUtil.isValidJSON(response.toString())) {
                                successListener.onSuccessResponse(response.toString(), response.toString());
                            } else {
                                errorListener.onErrorResponse(response.toString(), 500);                            }
                        } catch (Exception e) {
                            Log.d(TAG, e.toString());
                            successListener.onSuccessResponse(response.toString(), null);
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (errorListener != null) {
                    int status  = -1;

                    if(volleyError.networkResponse != null)
                    {
                        status  = volleyError.networkResponse.statusCode;

                        Log.e(TAG + "Error", volleyError.toString());
                    }
                    errorListener.onErrorResponse(handleVolleyError(volleyError), status);
                }
            }
        }
        ){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headerMap = new HashMap<>();

                headerMap.put(HEADER_ACCEPT,  WebServiceConstant.GET_REQUEST_HEADER);

                return headerMap;
            }
        };
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getRequestQueue().add(jsonObjectRequest);
        return jsonObjectRequest;
    }

    private static RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(AppContext.getContext());
        }
        return requestQueue;
    }

    public static ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(getRequestQueue(),
                    new ImageLoader.ImageCache() {
                        private final LruCache<String, Bitmap>
                                cache = new LruCache<String, Bitmap>(20);

                        @Override
                        public Bitmap getBitmap(String url) {
                            return cache.get(url);
                        }

                        @Override
                        public void putBitmap(String url, Bitmap bitmap) {
                            cache.put(url, bitmap);
                        }
                    });
        }
        return mImageLoader;
    }

    private static String handleVolleyError(VolleyError error) {
        String errorStr = WebServiceConstant.ERROR_SERVICE_SENDING_REQUEST;
        if (error.networkResponse != null) {
            try {
                String data = new String(error.networkResponse.data);
                if (JsonUtil.isValidJSON(data)) {
                    JsonElement jWraperElement = new JsonParser().parse(data);
                    JsonObject jWraperObject = jWraperElement.getAsJsonObject();
                    errorStr = jWraperObject.get("Message").getAsString();
                } else {
                    errorStr = getResponseString(error.networkResponse.statusCode);
                }
            } catch (Exception e) {
                Log.d(TAG, e.toString());
                errorStr = getResponseString(error.networkResponse.statusCode);
            }

        }
        return errorStr;
    }

    public static String getResponseString(int responseCode) {
        if (responseCode == 403) {
            return "Invalid Email or Password";
        }
        int resCodePrefix = responseCode / 100;
        if (resCodePrefix == 5) {
            return WebServiceConstant.RESPONSE_SERVER_SIDE.replace("??", responseCode + "");
        } else {
            return WebServiceConstant.RESPONSE_CLIENT_SIDE.replace("??", responseCode + "");
        }
    }

    public static boolean isConnectedToNetwork() {
        ConnectivityManager connectivity = (ConnectivityManager) AppContext.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
