package com.example.srv_twry.recipes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by srv_twry on 4/6/17.
 */

public class StepsRecyclerViewAdapter extends RecyclerView.Adapter<StepsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Steps> stepsArrayList;

    public StepsRecyclerViewAdapter(ArrayList<Steps> stepsArrayList){
        this.stepsArrayList=stepsArrayList;
    }

    @Override
    public StepsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.steps_view_holder,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return stepsArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView stepShortDescription;

        public ViewHolder(View view){
            super(view);
            stepShortDescription = (TextView) view.findViewById(R.id.view_holder_steps_title);
        }

        public void bind(int position) {
            stepShortDescription.setText(stepsArrayList.get(position).shortDescription);
        }
    }
}
