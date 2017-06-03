package com.example.srv_twry.recipes;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by srv_twry on 3/6/17.
 * The class to represent a single recipe. It uses the "Parceler" library to cast Recipe as a parcel.
 * Everything inside this class has to be public as the library cannot detect private fields!
 */

@Parcel
public class Recipe {

    int recipeId;
    String name;
    ArrayList<Ingredient> ingredientsArrayList;
    ArrayList<Steps> stepsArrayList;
    int servings;


    //Empty constructor necessary for the library
    public Recipe(){}

    public Recipe(int recipeId, String name,ArrayList<Ingredient> ingredientsArrayList,ArrayList<Steps> stepsArrayList,int servings){
        this.recipeId=recipeId;
        this.name=name;
        this.ingredientsArrayList=ingredientsArrayList;
        this.stepsArrayList=stepsArrayList;
        this.servings=servings;
    }
}
