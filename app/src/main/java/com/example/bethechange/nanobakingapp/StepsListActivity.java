package com.example.bethechange.nanobakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.bethechange.nanobakingapp.Model.Ingredient;
import com.example.bethechange.nanobakingapp.Model.Recipe;
import com.example.bethechange.nanobakingapp.Model.Step;
import com.google.gson.Gson;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link StepDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class StepsListActivity extends AppCompatActivity {

    public static final String RECIPE_KEY ="RECIPE_KEY" ;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        if(savedInstanceState==null)
            mRecipe=new Gson().fromJson(getIntent().getExtras().getString(RECIPE_KEY),Recipe.class);
        else{
            mRecipe=new Gson().fromJson(savedInstanceState.getString(RECIPE_KEY),Recipe.class);
        }
        View recyclerView = findViewById(R.id.recipe_details_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
        if (findViewById(R.id.recipe_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
        setTitle(mRecipe.getName());
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(mRecipe));
        recyclerView.addItemDecoration(new SpacesItemDecoration(30));

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(RECIPE_KEY,getIntent().getExtras().getString(RECIPE_KEY));
        super.onSaveInstanceState(outState);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final Recipe mValue;
        private final int INGREDIENT_VIEWTYPE=1;
        private final int STEP_VIEWTYPE=2;


        public SimpleItemRecyclerViewAdapter(Recipe item) {
            mValue = item;
        }
        @Override
        public int getItemViewType(int position) {
            if(position<mValue.getIngredients().size())
                return INGREDIENT_VIEWTYPE;
            else
                return STEP_VIEWTYPE;

        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if(viewType==INGREDIENT_VIEWTYPE){
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.ingredient_item, parent, false);
                return new IngredientsViewHolder(view);
            }
            else{
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.step_item, parent, false);
                return new StepViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
            if(getItemViewType(position)==INGREDIENT_VIEWTYPE){
                final IngredientsViewHolder ingeredientHolder=(IngredientsViewHolder)holder;
                ingeredientHolder.item=mRecipe.getIngredients().get(position);
                ingeredientHolder.ingredientTitle.setText(ingeredientHolder.item.getIngredientName());
                ingeredientHolder.ingredientQuantity.setText("Quantity : "+ingeredientHolder.item.getQuantity()+"");
                ingeredientHolder.ingredientMeasure.setText("Measure : "+ingeredientHolder.item.getMeasure());
            }
            else{
                final StepViewHolder stepHolder=(StepViewHolder)holder;
                stepHolder.mItem=mValue.getSteps().get(position-mRecipe.getIngredients().size());
                stepHolder.mStepTitle.setText(stepHolder.mItem.getSnippetDescription());
                stepHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mTwoPane) {
                            Bundle arguments = new Bundle();
                            arguments.putString(StepDetailFragment.ARG_STEP, new Gson().toJson(stepHolder.mItem,Step.class));
                            StepDetailFragment fragment = new StepDetailFragment();
                            fragment.setArguments(arguments);
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.recipe_detail_container, fragment)
                                    .commit();
                        } else {
                            Context context = v.getContext();
                            Intent intent = new Intent(context, StepDetailActivity.class);
                            intent.putExtra(StepDetailFragment.ARG_STEP, new Gson().toJson(stepHolder.mItem,Step.class));
                            context.startActivity(intent);
                        }
                    }
                });
            }



        }

        @Override
        public int getItemCount() {
            return mValue.getSteps().size()+mValue.getIngredients().size();
        }

        public class IngredientsViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public  Ingredient item;
            public final TextView ingredientTitle;
            public final TextView ingredientQuantity;
            public final TextView ingredientMeasure;
            public IngredientsViewHolder(View view) {
                super(view);
                mView = view;
                ingredientTitle = (TextView) view.findViewById(R.id.ingredient_title);
                ingredientQuantity = (TextView) view.findViewById(R.id.ingredient_quantity);
                ingredientMeasure=(TextView) view.findViewById(R.id.ingredient_measure);
            }

        }
        public class StepViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final TextView mStepTitle;
            public  Step mItem;

            public StepViewHolder(View view) {
                super(view);
                mView = view;
                mStepTitle = (TextView) view.findViewById(R.id.step_title);
                
            }
        }
    }
}
