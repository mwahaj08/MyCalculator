package com.concertpharmaceutical.utils;


import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.util.Log;
import android.webkit.URLUtil;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * StringUtil.java
 */
@SuppressLint("SimpleDateFormat")
public abstract class StringUtil extends ContentProvider{
    /*
     * Checks whether the Value String is Empty or Not
     */
    public static boolean isEmpty(String value) {
        if (value == null || "".equals(value.trim())) {
            return true;
        }
        return false;
    }
}
