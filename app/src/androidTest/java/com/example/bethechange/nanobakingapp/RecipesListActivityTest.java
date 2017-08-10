package com.example.bethechange.nanobakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import com.example.bethechange.nanobakingapp.Model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

/**
 * Created by Ahmed on 6/5/2017.
 */
@RunWith(AndroidJUnit4.class)
public class RecipesListActivityTest{

    public static final String ITEM_NAME = "Yellow Cake";
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    //Clicks on a RecyclerView Recipe item and checks if new activity's toolbar matches recipe
    @Test
    public void clickRecyclerViewItem_OpensDetailActivity() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.recipes_recycler_view))
                .perform(RecyclerViewActions.actionOnItem(
                        hasDescendant(withText(ITEM_NAME)), click()));

        matchingToolbarTitle(ITEM_NAME);
    }


    private static ViewInteraction matchingToolbarTitle(CharSequence title) {
        return onView(isAssignableFrom(Toolbar.class))
                .check(matches(toolbarTitle(is(title))));
    }

    private static Matcher<Object> toolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }
            @Override public void describeTo(Description description) {
                description.appendText("toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }
}
