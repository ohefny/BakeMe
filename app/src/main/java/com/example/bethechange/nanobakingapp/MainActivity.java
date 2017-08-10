package com.example.bethechange.nanobakingapp;

import android.app.ProgressDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;

import com.example.bethechange.nanobakingapp.Adapters.RecipesAdapter;
import com.example.bethechange.nanobakingapp.Model.Recipe;
import com.example.bethechange.nanobakingapp.Model.RecipesDownloader;
import com.example.bethechange.nanobakingapp.Model.RecipesInsertionTask;
import com.example.bethechange.nanobakingapp.Widget.RecipeWidget;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Recipe>>,RecipesAdapter.ListItemClickListener {

    public static final String RECIPES_KEY = "RECIPES_KEY";
    public static final String INDEX_KEY = "INDEX_KEY";
    private static final int LOADER_ID = 505;

    RecyclerView mRecyclerView;
    RecipesAdapter mAdapter;
    ProgressDialog mProgressDialog;
    private ArrayList<Recipe> mRecipes = new ArrayList<>();
    boolean isTablet;
    private int favID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupProgress();

        mRecipes=new Gson().fromJson(getIntent().getStringExtra(RECIPES_KEY),new TypeToken<ArrayList<Recipe>>(){}.getType());
        favID=PrefUtils.getFavoriteId(this);
        markFavItem();
        if(mRecipes.isEmpty())
            mProgressDialog.show();
        LinearLayoutManager mLayoutManger = new GridLayoutManager(this ,calculateNoOfColumns(this,300));
        mAdapter = new RecipesAdapter(mRecipes ,this);
        mRecyclerView = (RecyclerView)findViewById(R.id.recipes_recycler_view);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(mLayoutManger);
        mRecyclerView.addItemDecoration(new SpacesItemDecoration(30));
        LoaderManager loaderManager = getSupportLoaderManager();
        loaderManager.destroyLoader(LOADER_ID);
        if(NetworkUtils.isNetworkAvailable(this))
            loaderManager.initLoader(LOADER_ID, null , this);

        else {
            mProgressDialog.dismiss();
            showErrorView(R.string.offline);
        }


    }

    private void setupProgress() {
        mProgressDialog=new ProgressDialog(this);
        mProgressDialog.setTitle("");
        mProgressDialog.setMessage(getString(R.string.fetch_recipes));
        mProgressDialog.setCancelable(false); // disable dismiss by tapping outside of the dialog
    }

    private void showErrorView(int error_msg) {

        Snackbar mySnackbar = Snackbar.make(findViewById(R.id.main_container), getString(error_msg), Snackbar.LENGTH_LONG);
        mySnackbar.setAction(getString(R.string.refresh), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   refresh();
                }
            });
        mySnackbar.show();
    }


    @Override
    public Loader<ArrayList<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipesDownloader(this);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Recipe>> loader, ArrayList<Recipe> data) {
        if(data!=null){
            if(mRecipes == null || mRecipes.size()!= data.size()){
                new RecipesInsertionTask(this, data).execute();
            }
            if(favID==-1){
                data.get(0).setFav(true);
                favID=data.get(0).getId();
                PrefUtils.setFavoriteId(this,favID);
            }
            mRecipes=data;
            markFavItem();
            mAdapter.setItems(mRecipes);
            mProgressDialog.dismiss();
        }
        else{
            mProgressDialog.dismiss();
            showErrorView(R.string.data_cant_load);

        }

    }

    private void markFavItem() {
        for(Recipe r:mRecipes)
            r.setFav(r.getId() == favID);



    }


    @Override
    public void onLoaderReset(Loader<ArrayList<Recipe>> loader) {

    }

    @Override
    public void onListItemClicked(int index) {
        Intent intent = new Intent(this , StepsListActivity.class);
        intent.putExtra(StepsListActivity.RECIPE_KEY,new Gson().toJson(mRecipes.get(index),new TypeToken<Recipe>(){}.getType()));
        startActivity(intent);
    }

    @Override
    public void onFavChanged(int idx) {
        PrefUtils.setFavoriteId(this,mRecipes.get(idx).getId());
        favID=mRecipes.get(idx).getId();
        markFavItem();
        mAdapter.setItems(mRecipes);
        Intent intent = new Intent(this,RecipeWidget.class);
        intent.setAction("android.appwidget.action.APPWIDGET_UPDATE");
        int[] appWidgetIds = AppWidgetManager.getInstance(this).getAppWidgetIds(new ComponentName(this,RecipeWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,appWidgetIds);
        sendBroadcast(intent);
    }

    public void refresh(){
        recreate();
    }
    public static int calculateNoOfColumns(Context context, int colSize) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;
        return (int) (dpWidth / colSize);
    }

}
