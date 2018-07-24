package com.concertpharmaceutical.preferences;

public class WebServiceConstant {

 //*********Web Services ************//
 //******** Web Services URLs ********//
 public static String BASE_URL = "";
 public static final String  CONTAINER_DETAILS = "/containers";
 public static final String  LOCATIONS = "/locations";
 public static final String  CONTAINERS = "/containers";
 public static final String GET_REQUEST_HEADER = "application/json";
 public static final String  SAVE_CONTAINER = "/containers/";
 public static final String  DISPOSE_CONTAINER = "/containers/dispose/";
 public static final String  SET_EXPIRATION_DATE = "/containers/";





 //******** Web Services Error ********//
 public static final String  ERROR_WIFI_NOT_CONNECTED = "WiFi is not enabled";
 public static final String  ERROR_INTERNET_NOT_FOUND = "Not connected to WiFi network";
 public static final String  ERROR_SERVICE_NOT_RESPONDE = "Server did not respond. Please try later.";
 public static final String  ERROR_SERVICE_REQUEST_RESPONDE = "Request error. Please try later.";
 public static final String  ERROR_SERVICE_NO_INTERNET	= "Unable to connect to server. Please check your internet connection.";
 public static final String  ERROR_SERVICE_SENDING_REQUEST = "Unable to send request. Please try later.";
 public static final String  ERROR_SERVICE_REQUEST_AUTHENTICATION = "Authentication failure.";
 public final static String CACHE_CONTROL_NO_CACHE  = "no-cache";
 public final static String CACHE_CONTROL_PRIVATE  = "private";

 public static final int NETWORK_STATUS_SUCCESS  = 1;
 public static final int NETWORK_STATUS_FAILED	= 2;

//******** Web Services RESPONSE ********//
 public static final String  RESPONSE_SERVER_SIDE = "Server error. Please try later. If problem persists, contact your administrator. Error code: ??.";
 public static final String  RESPONSE_CLIENT_SIDE = "Unable to connect to Server. Please try later. If problem persists, contact your administrator. Error code: ??.";


}