package com.example.bethechange.nanobakingapp.Widget;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bethechange.nanobakingapp.DBUtils;
import com.example.bethechange.nanobakingapp.Model.BakingDBContract;
import com.example.bethechange.nanobakingapp.Model.BakingDBHelper;
import com.example.bethechange.nanobakingapp.Model.Ingredient;
import com.example.bethechange.nanobakingapp.Model.Recipe;
import com.example.bethechange.nanobakingapp.PrefUtils;
import com.example.bethechange.nanobakingapp.R;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 8/10/2017.
 */

class IngredientRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context context;

    private ArrayList<Ingredient> mIngredients;

    IngredientRemoteViewsFactory(Context context) {
        this.context = context;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        BakingDBHelper helper = new BakingDBHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor recipe_cursor = db.query(
                BakingDBContract.Recipes.TABLE_NAME, null, BakingDBContract.Recipes.RECIPE_ID+" = "+ PrefUtils.getFavoriteId(context),
                null, null, null, null);

        Recipe recipe= DBUtils.cursorToRecipe(recipe_cursor);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        views.setTextViewText(R.id.widget_recipe_title,recipe.getName());

        Cursor ingredient_cursor = db.query(
                BakingDBContract.Ingredients.TABLE_NAME, null,
                BakingDBContract.Ingredients.RECIPE_ID + " = "+recipe.getId(),
                null,
                null,
                null,
                null
        );
        mIngredients=DBUtils.cursorToIngredients(ingredient_cursor);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(mIngredients==null)
            return 0;
        else
            return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mIngredients == null || mIngredients.size() == 0)
            return null;

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_ingredient_item);
        views.setTextViewText(R.id.widget_ingredient_title, mIngredients.get(position).getIngredientName());
        views.setTextViewText(R.id.widget_ingredient_quantity, "Quantity: "+mIngredients.get(position).getQuantity());
        views.setTextViewText(R.id.widget_ingredient_measure, "Measure: " + mIngredients.get(position).getMeasure());
        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
