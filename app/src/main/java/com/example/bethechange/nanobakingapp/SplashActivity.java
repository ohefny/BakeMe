package com.example.bethechange.nanobakingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bethechange.nanobakingapp.Model.BakingDBContract;
import com.example.bethechange.nanobakingapp.Model.BakingDBHelper;
import com.example.bethechange.nanobakingapp.Model.Ingredient;
import com.example.bethechange.nanobakingapp.Model.Recipe;
import com.example.bethechange.nanobakingapp.Model.Step;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import static com.example.bethechange.nanobakingapp.DBUtils.cursorToIngredients;
import static com.example.bethechange.nanobakingapp.DBUtils.cursorToRecipes;
import static com.example.bethechange.nanobakingapp.DBUtils.cursorToSteps;

public class SplashActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Cursor recipeCursor = new BakingDBHelper(this).getReadableDatabase().query(
                BakingDBContract.Recipes.TABLE_NAME,null,null , null,null,null,null);
        ArrayList<Recipe> mRecipes = cursorToRecipes(recipeCursor);
        Cursor stepsCursor= new BakingDBHelper(this).getReadableDatabase().query(
                BakingDBContract.STEP.TABLE_NAME,null,null , null,null,null,null);
        ArrayList<Step> mSteps = cursorToSteps(stepsCursor);
        Cursor ingCursor = new BakingDBHelper(this).getReadableDatabase().query(
                BakingDBContract.Ingredients.TABLE_NAME,null,null , null,null,null,null);
        ArrayList<Ingredient> mIngredients = cursorToIngredients(ingCursor);
        for(Recipe  recipe: mRecipes){
            for (Step step:mSteps){
                if(step.getRecipeID()==recipe.getId())
                    recipe.getSteps().add(step);
            }
            for (Ingredient ingredient:mIngredients){
                if(ingredient.getRecipeID()==recipe.getId())
                    recipe.getIngredients().add(ingredient);
            }
        }
        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra(MainActivity.RECIPES_KEY,new Gson().toJson(mRecipes,new TypeToken<ArrayList<Recipe>>(){}.getType()));
        startActivity(intent);
        finish();
    }



}
