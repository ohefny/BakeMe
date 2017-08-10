package com.example.bethechange.nanobakingapp.Widget;

import android.content.Context;
import android.content.Intent;
import com.example.bethechange.nanobakingapp.DBUtils;
import com.example.bethechange.nanobakingapp.Model.BakingDBContract;
import com.example.bethechange.nanobakingapp.Model.BakingDBHelper;
import com.example.bethechange.nanobakingapp.Model.Ingredient;
import com.example.bethechange.nanobakingapp.Model.Recipe;
import com.example.bethechange.nanobakingapp.PrefUtils;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.bethechange.nanobakingapp.R;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 10/8/2017.
 */

public class IngredientWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new IngredientRemoteViewsFactory(this.getApplicationContext() );
    }
}
