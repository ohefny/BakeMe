package com.example.bethechange.nanobakingapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.bethechange.nanobakingapp.Model.Recipe;
import com.example.bethechange.nanobakingapp.R;

import java.util.ArrayList;

/**
 * Created by BeTheChange on 8/10/2017.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder> {


    private ListItemClickListener mListener;
    private ArrayList<Recipe> recipes=new ArrayList<>();
    private boolean bind;
    private RecipeViewHolder selectedHolder;
    int count=0;

    public RecipesAdapter(ArrayList<Recipe> recipes , ListItemClickListener l) {
        this.recipes = recipes;
        mListener = l;
        count=recipes.size();
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecipeViewHolder holder, final int position) {
        bind=true;
        holder.recipeTitle.setText(recipes.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListItemClicked(position);

            }
        });
        if(!recipes.get(position).isFav())
            holder.favBox.setChecked(false);
        else{
            holder.favBox.setChecked(true);
            selectedHolder=holder;
        }
        holder.favBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                          if(b)
                          {
                              if(recipes.get(position).isFav())
                                  return;
                              mListener.onFavChanged(position);
                          }
                        else {
                            holder.favBox.setChecked(recipes.get(position).isFav());
                        }
            }
        });
        bind=false;
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void setItems(ArrayList<Recipe> items) {
        this.recipes = items;
        if(!bind)
            notifyDataSetChanged();
    }


    class RecipeViewHolder extends RecyclerView.ViewHolder {
        final TextView recipeTitle;
        final RadioButton favBox;
        RecipeViewHolder(View itemView) {
            super(itemView);
            recipeTitle = (TextView)itemView.findViewById(R.id.recipe_title_id);
            favBox=(RadioButton)itemView.findViewById(R.id.favBox);

        }

    }

    public interface ListItemClickListener{
        void onListItemClicked(int index);

        void onFavChanged(int idx);
    }

}

