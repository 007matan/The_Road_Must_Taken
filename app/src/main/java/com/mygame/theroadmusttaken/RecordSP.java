package com.mygame.theroadmusttaken;

import android.content.Context;
import android.content.SharedPreferences;

public class RecordSP {
    private static final String DB_FILE = "DB_FILE";


    private static RecordSP recordSP = null;
    private SharedPreferences preferences;

    public static RecordSP getInstance() {
        return recordSP;
    }

    public static void init(Context context) {
        if (recordSP == null) {
            recordSP = new RecordSP(context);
        }
    }

    private RecordSP(Context context) {

        preferences = context.getSharedPreferences(DB_FILE, Context.MODE_PRIVATE);
    }

    public void putInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getInt(String key, int def) {
        return preferences.getInt(key, def);
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String def) {
        return preferences.getString(key, def);
    }

}
