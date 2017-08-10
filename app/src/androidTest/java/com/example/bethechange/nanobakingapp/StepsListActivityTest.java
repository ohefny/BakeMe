package com.example.bethechange.nanobakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.example.bethechange.nanobakingapp.Model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by Ahmed on 6/5/2017.
 */
@RunWith(AndroidJUnit4.class)
public class StepsListActivityTest {

    // parameter number 3 (false) means that the activity isn't started automatically
    @Rule
    public ActivityTestRule<StepsListActivity> RecipeDetailActivityTestRule
            = new ActivityTestRule<>(StepsListActivity.class, false , false);


    @Test
    public void clickRecipeStepRecyclerViewItem_ShowsDescibtion() {

        new GetAllRecipes().execute();
    }

    class GetAllRecipes extends AsyncTask {

        @Override
        protected Object doInBackground(Object[] params) {
            try {
                return NetworkUtils.fetchRecipes();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            Intent intent = new Intent();
            ArrayList<Recipe> mRecipes = (ArrayList<Recipe>) o;
            intent.putExtra(StepsListActivity.RECIPE_KEY,new Gson().toJson(mRecipes.get(0),new TypeToken<Recipe>(){}.getType()));
            RecipeDetailActivityTestRule.launchActivity(intent);
            Recipe currentRecipe = mRecipes.get(0);
            int pos = currentRecipe.getIngredients().size()-1;
            onView(withId(R.id.recipe_details_list))
                    .perform(RecyclerViewActions.actionOnItemAtPosition(pos, click()));

            onView(withId(R.id.recipe_detail)).
                    check(matches(withText(currentRecipe.getSteps().get(pos).getFullDescription())));

        }
    }
}
