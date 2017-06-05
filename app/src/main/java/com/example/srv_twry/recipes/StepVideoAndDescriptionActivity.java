package com.example.srv_twry.recipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//Activity that will contain the video playing fragment on Phone. On tablet it will be empty
public class StepVideoAndDescriptionActivity extends AppCompatActivity {

    VideoAndDescriptionFragment videoAndDescriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_video_and_description);

        videoAndDescriptionFragment = new VideoAndDescriptionFragment();
        //Passing the data to the fragment
        videoAndDescriptionFragment.setArguments(getIntent().getExtras());

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.frame_fragment_video_description,videoAndDescriptionFragment).commit();
    }
}
