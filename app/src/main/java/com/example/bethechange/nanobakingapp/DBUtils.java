package com.example.bethechange.nanobakingapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.example.bethechange.nanobakingapp.Model.BakingDBContract;
import com.example.bethechange.nanobakingapp.Model.Ingredient;
import com.example.bethechange.nanobakingapp.Model.Recipe;
import com.example.bethechange.nanobakingapp.Model.Step;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 8/10/2017.
 */

public class DBUtils {
    @NonNull
    public static ContentValues getIngredientsContentValues(Ingredient ingredient) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BakingDBContract.Ingredients.RECIPE_ID, ingredient.getRecipeID());
        contentValues.put(BakingDBContract.Ingredients.INGRDDIENT_QUANTITY, ingredient.getQuantity());
        contentValues.put(BakingDBContract.Ingredients.INGRDDIENT_MEASURE, ingredient.getMeasure());
        contentValues.put(BakingDBContract.Ingredients.INGRDDIENT_NAME, ingredient.getIngredientName());
        return contentValues;
    }

    @NonNull
    public static ContentValues getRecipeContentValues(Recipe recipe) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BakingDBContract.Recipes.RECIPE_ID, (recipe).getId());
        contentValues.put(BakingDBContract.Recipes.RECIPE_NAME,(recipe).getName());
        contentValues.put(BakingDBContract.Recipes.IS_FAVORITE, recipe.isFav());
        return contentValues;
    }
    @NonNull
    public static ContentValues getStepContentValues(Step step) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(BakingDBContract.STEP._ID, (step).getId());
        contentValues.put(BakingDBContract.STEP.RECIPE_ID, (step).getRecipeID());
        contentValues.put(BakingDBContract.STEP.FULL_DESC, (step).getFullDescription());
        contentValues.put(BakingDBContract.STEP.SHORT_DESC, (step).getSnippetDescription());
        contentValues.put(BakingDBContract.STEP.THUMBNAIL, (step).getThumbnailImg());
        contentValues.put(BakingDBContract.STEP.VIDEO, (step).getVideoLink());
        return contentValues;
    }
    public static ArrayList<Recipe> cursorToRecipes(Cursor cursor) {
        ArrayList<Recipe> results = new ArrayList<>();
        while(cursor.moveToNext()) {
            Recipe recipe = new Recipe();
            recipe.setId(cursor.getInt(cursor.getColumnIndex(BakingDBContract.Recipes.RECIPE_ID)));
            recipe.setName(cursor.getString(cursor.getColumnIndex(BakingDBContract.Recipes.RECIPE_NAME)));
            recipe.setFav(cursor.getInt(cursor.getColumnIndex(BakingDBContract.Recipes.IS_FAVORITE))!=0);
            results.add(recipe);
        }
        cursor.close();
        return results;
    }
    public static Recipe cursorToRecipe(Cursor cursor) {
        Recipe recipe = new Recipe();
        while(cursor.moveToNext()) {

            recipe.setId(cursor.getInt(cursor.getColumnIndex(BakingDBContract.Recipes.RECIPE_ID)));
            recipe.setName(cursor.getString(cursor.getColumnIndex(BakingDBContract.Recipes.RECIPE_NAME)));
            recipe.setFav(cursor.getInt(cursor.getColumnIndex(BakingDBContract.Recipes.IS_FAVORITE))!=0);

        }
        cursor.close();
        return recipe;
    }

    public static ArrayList<Step> cursorToSteps(Cursor cursor) {
        ArrayList<Step> results = new ArrayList<>();
        while(cursor.moveToNext()) {
            Step step = new Step();
            step.setId(cursor.getInt(cursor.getColumnIndex(BakingDBContract.STEP._ID)));
            step.setRecipeID(cursor.getInt(cursor.getColumnIndex(BakingDBContract.STEP.RECIPE_ID)));
            step.setFullDescription(cursor.getString(cursor.getColumnIndex(BakingDBContract.STEP.FULL_DESC)));
            step.setSnippetDescription(cursor.getString(cursor.getColumnIndex(BakingDBContract.STEP.SHORT_DESC)));
            step.setThumbnailImg(cursor.getString(cursor.getColumnIndex(BakingDBContract.STEP.THUMBNAIL)));
            step.setVideoLink(cursor.getString(cursor.getColumnIndex(BakingDBContract.STEP.VIDEO)));
            results.add(step);
        }
        cursor.close();
        return results;
    }

    public static ArrayList<Ingredient> cursorToIngredients(Cursor cursor) {
        ArrayList<Ingredient> results = new ArrayList<>();
        while(cursor.moveToNext()) {
            Ingredient ingredient = new Ingredient();
            ingredient.setRecipeID(cursor.getInt(cursor.getColumnIndex(BakingDBContract.Ingredients.RECIPE_ID)));
            ingredient.setQuantity(cursor.getDouble(cursor.getColumnIndex(BakingDBContract.Ingredients.INGRDDIENT_QUANTITY)));
            ingredient.setMeasure(cursor.getString(cursor.getColumnIndex(BakingDBContract.Ingredients.INGRDDIENT_MEASURE)));
            ingredient.setIngredientName(cursor.getString(cursor.getColumnIndex(BakingDBContract.Ingredients.INGRDDIENT_NAME)));
            results.add(ingredient);
        }
        cursor.close();
        return results;
    }
}
