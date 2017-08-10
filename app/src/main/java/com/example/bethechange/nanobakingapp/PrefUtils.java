package com.example.bethechange.nanobakingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by BeTheChange on 8/10/2017.
 */

public class PrefUtils {

    public static int getFavoriteId(Context context){
        final String FAV_KEY=context.getString(R.string.pref_fav_key);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(FAV_KEY,-1);
    }
    public static void setFavoriteId(Context context,int id){
        final String FAV_KEY=context.getString(R.string.pref_fav_key);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt(FAV_KEY,id).apply();
    }
}
