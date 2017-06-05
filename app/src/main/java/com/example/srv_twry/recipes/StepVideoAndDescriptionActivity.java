package com.example.srv_twry.recipes;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

//Activity that will contain the video playing fragment on Phone. On tablet it will be empty
public class StepVideoAndDescriptionActivity extends AppCompatActivity {

    VideoAndDescriptionFragment videoAndDescriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            //Get full screen in landscape mode
            requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove title bar
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//Remove notification bar
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();
        }else{
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setContentView(R.layout.activity_step_video_and_description);

        //create a new fragment only when an instance of fragment does not exists
        if (savedInstanceState ==null){
            videoAndDescriptionFragment = new VideoAndDescriptionFragment();
            //Passing the data to the fragment
            videoAndDescriptionFragment.setArguments(getIntent().getExtras());

            android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.frame_fragment_video_description,videoAndDescriptionFragment).commit();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
