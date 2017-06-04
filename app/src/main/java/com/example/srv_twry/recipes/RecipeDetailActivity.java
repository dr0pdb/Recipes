package com.example.srv_twry.recipes;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/*
* Activity which shows the List of all ingredients and steps in the recipe for phone and on tablet it will show a multipane layout with step details and video too.
* */

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    Recipe recipe;
    IngredientsStepsFragment ingredientsStepsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipe = getIntent().getParcelableExtra("Recipe");
        ArrayList<Ingredient> ingredientArrayList = recipe.ingredientsArrayList;
        ArrayList<Steps> stepsArrayList = recipe.stepsArrayList;

        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("Ingredients",ingredientArrayList);
        bundle.putParcelableArrayList("Steps",stepsArrayList);
        ingredientsStepsFragment = new IngredientsStepsFragment();
        ingredientsStepsFragment.setArguments(bundle);

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_fragment_ingredients_steps,ingredientsStepsFragment).commit();

    }
}
