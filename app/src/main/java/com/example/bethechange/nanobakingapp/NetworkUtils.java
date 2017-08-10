package com.example.bethechange.nanobakingapp;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.bethechange.nanobakingapp.Model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by Ahmed on 5/31/2017.
 */

public final class NetworkUtils {
    private static final String JSON_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";


    public static ArrayList<Recipe> fetchRecipes () throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(JSON_URL)
                .build();
        Response response = client.newCall(request).execute();
        try {
            return new Gson().fromJson(response.body().string(),new TypeToken<ArrayList<Recipe>>(){}.getType());
        }
        catch (Exception ex){
            ex.printStackTrace();
            return new ArrayList<>();
        }


    }
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
