package com.concertpharmaceutical.utils;

import android.util.Log;

/**
 * Created by Hammad Mukhtar on May 18, 2016
 */
public class LOGGER {

    public static void Log(Object obj, Exception e){
        if(obj instanceof String)
            Log.d(obj.toString(), e.getClass().getName());
        else
            Log.d(obj.getClass().getName(), e.getClass().getName());
    }

    public static void Log(Object obj, String logString){
        if(obj instanceof String)
            Log.d(obj.toString(), logString);
        else
            Log.d(obj.getClass().getName(), logString);
    }
}
