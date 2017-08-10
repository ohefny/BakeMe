package com.example.bethechange.nanobakingapp.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import com.example.bethechange.nanobakingapp.DBUtils;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 8/9/2017.
 */
public class RecipesInsertionTask extends AsyncTask<Void,Void,Void> {
    private Context mContext;
    private ArrayList<Recipe> mRecipes;

    public RecipesInsertionTask(Context context, ArrayList<Recipe> recipe) {
        this.mRecipes = recipe;
        mContext = context;
    }

    @Override
    protected Void doInBackground(Void... params) {
        if (mRecipes != null&&mRecipes.size()>0) {
            BakingDBHelper dbHelper = new BakingDBHelper(mContext);
            SQLiteDatabase writableDatabase = dbHelper.getWritableDatabase();
            for (int i = 0; i < mRecipes.size(); i++) {
                try {
                    long id=writableDatabase.insert(BakingDBContract.Recipes.TABLE_NAME, null, DBUtils.getRecipeContentValues(mRecipes.get(i)));
                    if(id<0)
                        continue;
                    int ingredientsSz=mRecipes.get(i).getIngredients().size();
                    for (int j = 0; j <ingredientsSz; j++) {
                        Ingredient ingredient = mRecipes.get(i).getIngredients().get(j);
                        ingredient.setRecipeID(mRecipes.get(i).getId());
                        writableDatabase.insert(BakingDBContract.Ingredients.TABLE_NAME, null, DBUtils.getIngredientsContentValues(ingredient));
                    }
                    for (int j = 0; j <mRecipes.get(i).getSteps().size(); j++) {
                        Step step = mRecipes.get(i).getSteps().get(j);
                        step.setRecipeID(mRecipes.get(i).getId());
                        writableDatabase.insertOrThrow(BakingDBContract.STEP.TABLE_NAME, null, DBUtils.getStepContentValues(step));
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();

                }

            }

        }
        return null;
    }



}


