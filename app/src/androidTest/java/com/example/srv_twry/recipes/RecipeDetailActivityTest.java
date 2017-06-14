package com.example.srv_twry.recipes;

import android.content.Intent;
import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.hamcrest.core.IsInstanceOf;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by srv_twry on 14/6/17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityTestRule = new ActivityTestRule<>(RecipeDetailActivity.class,true,false);

    @Before
    public void setmActivityTestRule(){
        ArrayList<Ingredient> ingredientArrayList = new ArrayList<>();
        ingredientArrayList.add(new Ingredient(0.1,"fsd","fssdf"));
        ArrayList<Steps> stepsArrayList = new ArrayList<>();
        stepsArrayList.add(new Steps(1,"sdfs","SDfs","sfsd","rjt"));
        Intent intent = new Intent();
        intent.putExtra("Recipe",new Recipe(2,"kjfdsk",ingredientArrayList,stepsArrayList,23));
        mActivityTestRule.launchActivity(intent);
    }

    @Test
    public void checkForIngredients(){
        ViewInteraction textView = onView(
                allOf(withId(R.id.tv_ingredients_title), withText("Ingredients"), isDisplayed()));
        textView.check(matches(withText("Ingredients")));
    }

    @Test
    public void checkForRecyclerViews(){
        ViewInteraction textView2 = onView(
                allOf(withId(R.id.tv_steps_title), withText("Recipe Steps"),
                        isDisplayed()));
        textView2.check(matches(isDisplayed()));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.rv_ingredients_list),
                        isDisplayed()));
        recyclerView.check(matches(isDisplayed()));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.rv_steps_list),
                        isDisplayed()));
        recyclerView2.check(matches(isDisplayed()));

    }

}