package com.example.srv_twry.recipes;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListActivity extends AppCompatActivity implements RecipeListAdapter.RecipeListOnClickListenerInteface {

    private static final String TAG = RecipeListActivity.class.getSimpleName();

    @BindView(R.id.rv_recipe_list)
    RecyclerView recipeListRecyclerView;
    ArrayList<Recipe> recipeArrayList;
    RecipeListAdapter recipeListAdapter;
    CountingIdlingResource countingIdlingResource = new CountingIdlingResource("Check recipe list");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        ButterKnife.bind(this);
        recipeArrayList = new ArrayList<>();
        recipeListAdapter = new RecipeListAdapter(recipeArrayList,this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,getResources().getInteger(R.integer.columns_in_grid));
        recipeListRecyclerView.setAdapter(recipeListAdapter);
        recipeListRecyclerView.setLayoutManager(gridLayoutManager);

        //Start the network request here
        RequestQueue requestQueue = Volley.newRequestQueue(RecipeListActivity.this);
        String url = getResources().getString(R.string.url);
        JsonArrayRequest jsonArrayRequest = getJsonArray(url);
        if (isNetworkAvailable()){
            countingIdlingResource.increment();    //increment it so that expresso test doesn't start
            Log.v(TAG,"started idling resource");
            requestQueue.add(jsonArrayRequest);
        }else{
            Log.e(TAG,"No internet");
            Toast toast = Toast.makeText(this,"Check your Connection!",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private JsonArrayRequest getJsonArray(String url) {
        return new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                recipeArrayList = parseRecipeJson(response);
                recipeListAdapter = new RecipeListAdapter(recipeArrayList,RecipeListActivity.this);
                recipeListRecyclerView.setAdapter(recipeListAdapter);
                recipeListRecyclerView.invalidate();
                countingIdlingResource.decrement();
                Log.v(TAG,"ended idling resource");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG,"Volley error response");
                Toast toast = Toast.makeText(RecipeListActivity.this,"Check your Connection!",Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    private ArrayList<Recipe> parseRecipeJson(JSONArray response) {
        ArrayList<Recipe> returnedRecipeList= new ArrayList<>();

        if (response !=null){
            try{
                int recipeId;
                String name;
                ArrayList<Ingredient> ingredients;
                ArrayList<Steps> steps;
                int servings;

                for (int i=0;i<response.length();i++){
                    JSONObject obj = response.getJSONObject(i);
                    recipeId = obj.getInt("id");
                    name = obj.getString("name");
                    ingredients = new ArrayList<>();
                    steps = new ArrayList<>();
                    JSONArray ingredientsJsonArray = obj.getJSONArray("ingredients");
                    JSONArray stepsJsonArray = obj.getJSONArray("steps");
                    servings = obj.getInt("servings");

                    //Creating Arraylist out of JSONArray
                    for (int j=0;j<ingredientsJsonArray.length();j++){
                        JSONObject ingredientsObject = ingredientsJsonArray.getJSONObject(j);
                        double quantity = ingredientsObject.getDouble("quantity");
                        String measure = ingredientsObject.getString("measure");
                        String ingredient = ingredientsObject.getString("ingredient");
                        ingredients.add(new Ingredient(quantity,measure,ingredient));
                    }

                    for (int j=0;j<stepsJsonArray.length();j++){
                        JSONObject stepsJsonObject = stepsJsonArray.getJSONObject(j);
                        int stepId= stepsJsonObject.getInt("id");
                        String shortDescription = stepsJsonObject.getString("shortDescription");
                        String description = stepsJsonObject.getString("description");
                        String videoUrl = stepsJsonObject.getString("videoURL");
                        String thumbnailUrl = stepsJsonObject.getString("thumbnailURL");
                        steps.add(new Steps(stepId,shortDescription,description,videoUrl,thumbnailUrl));
                    }
                    Log.v("ingredients "+i,ingredients.size()+" ");
                    Log.v("steps "+i,steps.size()+" ");
                    returnedRecipeList.add(new Recipe(recipeId,name,ingredients,steps,servings));
                }
            }catch (JSONException e){
                e.printStackTrace();

            }
        }else{
            Log.e(TAG,"Empty Json response !");
            Toast toast = Toast.makeText(RecipeListActivity.this,"Check your Connection!",Toast.LENGTH_LONG);
            toast.show();
        }
        return returnedRecipeList;
    }

    @Override
    public void onRecipeItemClicked(Recipe recipe) {
        Intent intent = new Intent(RecipeListActivity.this,RecipeDetailActivity.class);
        intent.putExtra("Recipe",recipe);
        startActivity(intent);
    }

    //A helper method to check Internet Connection Status
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
