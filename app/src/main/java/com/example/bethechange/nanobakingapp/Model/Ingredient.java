package com.example.bethechange.nanobakingapp.Model;

import com.google.gson.annotations.SerializedName;


/**
 * Created by BeTheChange on 3/8/2017.
 */


public class Ingredient{

    int recipeID;
    @SerializedName("ingredient")
    String mIngredientName;
    @SerializedName("measure")
    String mMeasure;
    @SerializedName("quantity")
    double mQuantity;

    public Ingredient() {
    }

    public String getIngredientName() {
        return mIngredientName;
    }

    public void setIngredientName(String mIngredient) {
        this.mIngredientName = mIngredient;
    }

    public String getMeasure() {
        return mMeasure;
    }

    public void setMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public double getQuantity() {
        return mQuantity;
    }

    public void setQuantity(double mQuantity) {
        this.mQuantity = mQuantity;
    }
    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        this.recipeID = recipeID;
    }

}