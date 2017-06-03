package com.example.srv_twry.recipes;

/**
 * Created by srv_twry on 3/6/17.
 * //class to represent a single step in the recipe
 */

public class Steps {
    int stepid;
    String shortDescription;
    String description;
    String videoUrl;
    String thumbnailUrl;

    public Steps(int stepid, String shortDescription ,String description, String videoUrl , String thumbnailUrl){
        this.stepid=stepid;
        this.shortDescription=shortDescription;
        this.description=description;
        this.videoUrl=videoUrl;
        this.thumbnailUrl=thumbnailUrl;
    }
}
