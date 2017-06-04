package com.example.srv_twry.recipes;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by srv_twry on 4/6/17.
 */

public class IngredientsRecyclerViewAdapter extends RecyclerView.Adapter<IngredientsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Ingredient> ingredientArrayList;

    public IngredientsRecyclerViewAdapter(ArrayList<Ingredient> ingredientArrayList){
        this.ingredientArrayList=ingredientArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.ingredients_view_holder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView ingredientName;
        TextView ingredientQuantity;

        public ViewHolder(View view){
            super(view);
            ingredientName = (TextView) view.findViewById(R.id.view_holder_ingredient_name);
            ingredientQuantity = (TextView) view.findViewById(R.id.view_holder_ingredient_quantity);
        }

        public void bind(int position) {
            ingredientName.setText(ingredientArrayList.get(position).ingredients);
            String quantity = ingredientArrayList.get(position).quantity + " "+ ingredientArrayList.get(position).measure;
            ingredientQuantity.setText(quantity);
        }
    }
}
