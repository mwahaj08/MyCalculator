package com.concertpharmaceutical.utils;

import android.util.Log;

import com.concertpharmaceutical.models.Location;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class JsonUtil {

	private static String TAG = "JsonUtil";

	private static Gson INSTANCE = null;

	public static Gson getInstance() {

		if (INSTANCE == null) {
			INSTANCE = new Gson();
		}

		return INSTANCE;
	}

	public static String toJson(Object object) {
		String jsonString = "";

		try {
			jsonString = getInstance().toJson(object);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}

		return jsonString;
	}

	public static <T> Object fromJson(String jsonString, Class<T> clazz) {
		T object = null;
		try {
			object = getInstance().fromJson(jsonString, clazz);
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}

		return object;
	}

	public static ArrayList<Location> fromJsonList(JsonArray arrJson, Type listType){

		ArrayList<Location> lst = new ArrayList<Location>();
        JsonObject objJson;
        Location objLocation;
		try {
             for(int i=0; i < arrJson.size() ; i++) {
                 objJson = arrJson.get(i).getAsJsonObject();
                 objLocation =  getInstance().fromJson(objJson, Location.class);
                 lst.add(objLocation);
             }

		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}

		return lst;				 
	}

	public static boolean isValidJSON(String jsonString){
		if(!StringUtil.isEmpty(jsonString)){
			if(jsonString.startsWith("[")|| jsonString.startsWith("{")){
				return true;
			}
		}
		return false;
	}
	
	
}
