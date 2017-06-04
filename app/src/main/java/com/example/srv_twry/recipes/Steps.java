package com.example.srv_twry.recipes;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by srv_twry on 3/6/17.
 * //class to represent a single step in the recipe
 */


public class Steps implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.stepid);
        dest.writeString(this.shortDescription);
        dest.writeString(this.description);
        dest.writeString(this.videoUrl);
        dest.writeString(this.thumbnailUrl);
    }

    protected Steps(Parcel in) {
        this.stepid = in.readInt();
        this.shortDescription = in.readString();
        this.description = in.readString();
        this.videoUrl = in.readString();
        this.thumbnailUrl = in.readString();
    }

    public static final Parcelable.Creator<Steps> CREATOR = new Parcelable.Creator<Steps>() {
        @Override
        public Steps createFromParcel(Parcel source) {
            return new Steps(source);
        }

        @Override
        public Steps[] newArray(int size) {
            return new Steps[size];
        }
    };
}
