package com.example.bethechange.nanobakingapp.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ahmed on 6/3/2017.
 */

public class BakingDBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Recipes.db";

    public BakingDBHelper(Context con){
        super(con , DATABASE_NAME , null , DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String RECIPE_TABLE_STATEMENT = "CREATE TABLE "+ BakingDBContract.Recipes.TABLE_NAME
                + " ( "+ BakingDBContract.Recipes.RECIPE_ID + " INTEGER PRIMARY KEY , "
                + BakingDBContract.Recipes.RECIPE_NAME + " TEXT NOT NULL , "
                + BakingDBContract.Recipes.IS_FAVORITE + " BOOLEAN NOT NULL "
                + " );" ;

        final String INGREDIENTS_TABLE_STATEMENT = "CREATE TABLE "+ BakingDBContract.Ingredients.TABLE_NAME
                + " ( "+ BakingDBContract.Ingredients.RECIPE_ID + " TEXT , "
                + BakingDBContract.Ingredients.INGRDDIENT_QUANTITY + " REAL NOT NULL ,"
                + BakingDBContract.Ingredients.INGRDDIENT_MEASURE + " TEXT NOT NULL ,"
                + BakingDBContract.Ingredients.INGRDDIENT_NAME + " TEXT NOT NULL "

                + " , FOREIGN KEY (" + BakingDBContract.Ingredients.RECIPE_ID + " ) "
                +"REFERENCES " + BakingDBContract.Recipes.TABLE_NAME + " ( " + BakingDBContract.Recipes.RECIPE_ID
                + " )"

                + " );";
        final String STEPS_TABLE_STATEMENT = "CREATE TABLE "+ BakingDBContract.STEP.TABLE_NAME
                + " ( "
                + BakingDBContract.STEP.RECIPE_ID + " INTEGER NOT NULL , "
                + BakingDBContract.STEP.THUMBNAIL + " TEXT ,"
                + BakingDBContract.STEP.FULL_DESC + " TEXT,"
                + BakingDBContract.STEP.SHORT_DESC + " TEXT,"
                + BakingDBContract.STEP.VIDEO + " TEXT,"
                + BakingDBContract.STEP._ID + " INTEGER  ,"
                + "  FOREIGN KEY (" + BakingDBContract.Ingredients.RECIPE_ID + " ) "
                +"REFERENCES " + BakingDBContract.Recipes.TABLE_NAME + " ( " + BakingDBContract.Recipes.RECIPE_ID
                + " )"

                + " );";
        db.execSQL(RECIPE_TABLE_STATEMENT);
        db.execSQL(INGREDIENTS_TABLE_STATEMENT);
        db.execSQL(STEPS_TABLE_STATEMENT);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BakingDBContract.Ingredients.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BakingDBContract.STEP.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + BakingDBContract.Recipes.TABLE_NAME);
        onCreate(db);
    }
}
