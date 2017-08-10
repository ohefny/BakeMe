package com.example.bethechange.nanobakingapp;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by BeTheChange on 8/10/2017.
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    SpacesItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {

        outRect.top = space;
        outRect.right=space;



    }
}

