package com.example.srv_twry.recipes;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by srv_twry on 7/6/17.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    @Rule
    public ActivityTestRule<RecipeListActivity> mActivityTestRule = new ActivityTestRule<>(RecipeListActivity.class);


    @Test
    public void clickingRecipeOpensDetail(){
        Espresso.registerIdlingResources(mActivityTestRule.getActivity().countingIdlingResource);
        onView(withId(R.id.rv_recipe_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));


        // onData(anything()).inAdapterView(withId(R.id.rv_recipe_list)).atPosition(0).perform(click());
        //The above commented code was giving error, would like to know why

    }

}
