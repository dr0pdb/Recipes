package com.example.srv_twry.recipes;

/**
 * Created by srv_twry on 3/6/17.
 * class to represent the ingredients of a recipe
 */

public class Ingredient {
    double quantity;
    String measure;
    String ingredients;

    public Ingredient(double quantity , String measure ,String ingredients){
        this.quantity = quantity;
        this.measure = measure;
        this.ingredients = ingredients;
    }
}
