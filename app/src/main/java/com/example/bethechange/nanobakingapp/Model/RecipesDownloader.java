package com.example.bethechange.nanobakingapp.Model;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.example.bethechange.nanobakingapp.NetworkUtils;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by BeTheChange on 8/9/2017.
 */

public class RecipesDownloader extends AsyncTaskLoader<ArrayList<Recipe>> {
    public RecipesDownloader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Recipe> loadInBackground() {
        try {
            return NetworkUtils.fetchRecipes();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

}
