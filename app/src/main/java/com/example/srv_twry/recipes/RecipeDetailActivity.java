package com.example.srv_twry.recipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

/*
* Activity which shows the List of all ingredients and steps in the recipe for phone and on tablet it will show a multipane layout with step details and video.
* */

public class RecipeDetailActivity extends AppCompatActivity implements IngredientsStepsFragment.IngredientStepsOnClickListener {

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    Recipe recipe;
    IngredientsStepsFragment ingredientsStepsFragment;
    VideoAndDescriptionFragment videoAndDescriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

        //For tablet
        if (findViewById(R.id.frame_fragment_video_description) !=null){
            videoAndDescriptionFragment = new VideoAndDescriptionFragment();
            Bundle bundleVideo = new Bundle();
            bundleVideo.putString("Step URL",stepsArrayList.get(0).videoUrl);
            bundleVideo.putString("Step Description",stepsArrayList.get(0).description);
            videoAndDescriptionFragment.setArguments(bundleVideo);

            android.support.v4.app.FragmentTransaction videoFragmentTransaction = getSupportFragmentManager().beginTransaction();
            videoFragmentTransaction.add(R.id.frame_fragment_video_description,videoAndDescriptionFragment).commit();
        }
    }

    @Override
    public void onIngredientStepItemClicked(Bundle bundle) {
        //For phone
        if (findViewById(R.id.frame_fragment_video_description) ==null){
            Intent intent = new Intent(this,StepVideoAndDescriptionActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            //for tablets
            videoAndDescriptionFragment = new VideoAndDescriptionFragment();
            videoAndDescriptionFragment.setArguments(bundle);
            android.support.v4.app.FragmentTransaction videoFragmentTransaction = getSupportFragmentManager().beginTransaction();
            videoFragmentTransaction.replace(R.id.frame_fragment_video_description,videoAndDescriptionFragment).commit();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
