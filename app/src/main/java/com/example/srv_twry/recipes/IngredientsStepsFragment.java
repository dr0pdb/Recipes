package com.example.srv_twry.recipes;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by srv_twry on 4/6/17.
 * The fragment to hold the recycler view of the ingredients and steps of the recipe.
 */

public class IngredientsStepsFragment extends Fragment {

    private ArrayList<Ingredient> ingredientArrayList;
    private ArrayList<Steps> stepsArrayList;


    @BindView(R.id.rv_ingredients_list)
    RecyclerView ingredientsRecyclerView;
    @BindView(R.id.rv_steps_list)
    RecyclerView stepsRecyclerView;
    @BindView(R.id.tv_ingredients_title)
    TextView ingredientsTitle;
    @BindView(R.id.tv_steps_title)
    TextView stepsTitle;

    //Empty constructor
    public IngredientsStepsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_ingredients_steps,container,false);
        ingredientArrayList = getArguments().getParcelableArrayList("Ingredients");
        stepsArrayList = getArguments().getParcelableArrayList("Steps");
        ButterKnife.bind(this,rootView);
        IngredientsRecyclerViewAdapter ingredientsRecyclerViewAdapter=new IngredientsRecyclerViewAdapter(ingredientArrayList);
        ingredientsRecyclerView.setAdapter(ingredientsRecyclerViewAdapter);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        StepsRecyclerViewAdapter stepsRecyclerViewAdapter = new StepsRecyclerViewAdapter(stepsArrayList);
        stepsRecyclerView.setAdapter(stepsRecyclerViewAdapter);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //Underlining the two titles
        ingredientsTitle.setPaintFlags(ingredientsTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        stepsTitle.setPaintFlags(stepsTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        return rootView;
    }

}
