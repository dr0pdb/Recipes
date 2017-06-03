package com.example.srv_twry.recipes;

import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by srv_twry on 3/6/17.
 * The adapter class for the recipe list recycler view
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder> {

    private ArrayList<Recipe> recipeArrayList;
    private RecipeListOnClickListenerInteface recipeListOnClickListenerInteface;

    public RecipeListAdapter(ArrayList<Recipe> recipeArrayList,RecipeListOnClickListenerInteface recipeListOnClickListenerInteface){
        this.recipeArrayList=recipeArrayList;
        this.recipeListOnClickListenerInteface=recipeListOnClickListenerInteface;
    }

    @Override
    public RecipeListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view= inflater.inflate(R.layout.recipe_list_view_holder,parent,false);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeListViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }

    //Interface to handle onClickListener on the recyclerview
    public interface RecipeListOnClickListenerInteface{
        void onRecipeItemClicked(ArrayList<Recipe> recipeArrayList,int position);
    }


    public class RecipeListViewHolder extends RecyclerView.ViewHolder {

        ImageView recipeImage;
        TextView recipeName;
        TextView recipeServings;
        Button getStartedButton;

        public RecipeListViewHolder(View view){
            super(view);
            recipeImage = (ImageView) view.findViewById(R.id.iv_image_recipe);
            recipeName = (TextView) view.findViewById(R.id.tv_recipe_name);
            recipeServings = (TextView) view.findViewById(R.id.tv_number_servings);
            getStartedButton = (Button) view.findViewById(R.id.start_cooking);

            getStartedButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    recipeListOnClickListenerInteface.onRecipeItemClicked(recipeArrayList,position);
                }
            });
        }

        public void bind(int position){
            int recipeId = recipeArrayList.get(position).recipeId;
            String name = recipeArrayList.get(position).name;

            //To underline the title
            recipeName.setPaintFlags(recipeName.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            recipeName.setText(name);

            switch (recipeId){
                case 1:
                    recipeImage.setImageResource(R.mipmap.drawable_nutella_pie);
                    break;
                case 2:
                    recipeImage.setImageResource(R.mipmap.drawable_brownie);
                    break;
                case 3:
                    recipeImage.setImageResource(R.mipmap.drawable_yellow_cake);
                    break;
                case 4:
                    recipeImage.setImageResource(R.mipmap.drawable_cheese_cake);
                    break;
                default:
                    recipeImage.setImageResource(R.mipmap.ic_launcher);
            }
            String servingsString = "Serves "+ recipeArrayList.get(position).servings + " People";
            recipeServings.setText(servingsString);
        }

    }

}
