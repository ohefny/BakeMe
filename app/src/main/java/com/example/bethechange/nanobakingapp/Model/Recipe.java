package com.example.bethechange.nanobakingapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;


/**
 * Created by BeTheChange on 3/8/2017.
 */

public class Recipe {
    @SerializedName("id")
    private int mId;
    @SerializedName("steps")
    private ArrayList<Step> mSteps=new ArrayList<>();
    @SerializedName("name")
    private String mName="";
    @SerializedName("ingredients")
    private ArrayList<Ingredient> mIngredients=new ArrayList<>();
    private int mChoice;
    private boolean isFav;
    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public ArrayList<Step> getSteps() {
        return mSteps;
    }

    public void setSteps(ArrayList<Step> mSteps) {
        this.mSteps = mSteps;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public ArrayList<Ingredient> getIngredients() {
        return mIngredients;
    }

    public void setIngredients(ArrayList<Ingredient> mIngredients) {
        this.mIngredients = mIngredients;
    }

    public int getChoice() {
        return mChoice;
    }

    public void setChoice(int mChoice) {
        this.mChoice = mChoice;
    }
    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }
    public Recipe() {
    }

}