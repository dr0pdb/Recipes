package com.example.srv_twry.recipes;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/*
* This will contain the fragment for playing the video and detailed description of the step.
* For tablet it won't contain anything.
* */

public class StepDescriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_description);
    }
}
