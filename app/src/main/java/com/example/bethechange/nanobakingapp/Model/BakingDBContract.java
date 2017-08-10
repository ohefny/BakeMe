package com.example.bethechange.nanobakingapp.Model;

import android.provider.BaseColumns;

/**
 * Created by Ahmed on 6/3/2017.
 */

public final class BakingDBContract {
    private BakingDBContract() {

    }


    public static class Recipes implements BaseColumns {
        public static final String TABLE_NAME = "recipes";
        public static final String RECIPE_ID = "id";
        public static final String RECIPE_NAME= "name";
        public static final String IS_FAVORITE= "favorite";

    }

    public static class Ingredients implements BaseColumns {
        public static final String RECIPE_ID= "recipeID";
        public static final String INGRDDIENT_NAME= "name";
        public static final String TABLE_NAME = "ingredients";
        public static final String INGRDDIENT_QUANTITY = "quantity";
        public static final String INGRDDIENT_MEASURE= "measure";

    }
    public static class STEP  implements BaseColumns {
        public static final String _ID= "id";
        public static final String RECIPE_ID= "recipeID";
        public static final String FULL_DESC= "desc";
        public static final String SHORT_DESC = "short_desc";
        public static final String THUMBNAIL = "thumbnail";
        public static final String VIDEO= "video";
        public static final String TABLE_NAME ="steps" ;
    }
}
