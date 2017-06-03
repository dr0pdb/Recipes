package com.example.srv_twry.recipes;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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
        JsonObjectRequest jsonObjectRequest = getJsonObject(url);
        if (isNetworkAvailable()){
            requestQueue.add(jsonObjectRequest);
        }else{
            Log.e(TAG,"No internet");
            Toast toast = Toast.makeText(this,"Check your Connection!",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private JsonObjectRequest getJsonObject(String url) {
        return new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                recipeArrayList = parseRecipeJson(response);
                recipeListAdapter = new RecipeListAdapter(recipeArrayList,RecipeListActivity.this);
                recipeListRecyclerView.setAdapter(recipeListAdapter);
                recipeListRecyclerView.invalidate();
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

    private ArrayList<Recipe> parseRecipeJson(JSONObject response) {
        ArrayList<Recipe> returnedRecipeList= new ArrayList<>();

        if (response !=null){
            
        }else{
            Log.e(TAG,"Empty Json response !");
            Toast toast = Toast.makeText(RecipeListActivity.this,"Check your Connection!",Toast.LENGTH_LONG);
            toast.show();
        }
    }

    @Override
    public void onRecipeItemClicked(ArrayList<Recipe> recipeArrayList, int position) {
        //Handle click of the view here.
    }

    //A helper method to check Internet Connection Status
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
