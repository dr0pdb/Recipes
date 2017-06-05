package com.example.srv_twry.recipes;

import android.content.res.Configuration;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

/**
 * Created by srv_twry on 4/6/17.
 * The fragment which plays the associated video and shows the detailed description associated with a single step.
 */

public class VideoAndDescriptionFragment extends Fragment {

    SimpleExoPlayerView videoStepView;
    TextView descriptionStep;
    String videoUrl;
    String description;

    //Member variables for the exo-player
    long playbackPosition;
    int currentWindow;
    boolean playWhenReady;
    SimpleExoPlayer player;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_video_description,container,false);
        videoStepView = (SimpleExoPlayerView) rootView.findViewById(R.id.view_video_step);

        //get the data from the parent activity about the url and the description
        Bundle bundle = getArguments();
        videoUrl = bundle.getString("Step URL");
        description = bundle.getString("Step Description");

        //Initialize the media player
        initializePlayer();

        //Only for non landscape mode
        if (rootView.findViewById(R.id.tv_title_description_step) != null){
            TextView descriptionStepTitle = (TextView) rootView.findViewById(R.id.tv_title_description_step);
            descriptionStepTitle.setPaintFlags(descriptionStepTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            descriptionStep = (TextView) rootView.findViewById(R.id.tv_description_step);
            descriptionStep.setText(description);
        }

        return rootView;
    }

    private void initializePlayer(){
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        videoStepView.setPlayer(player);

        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);

        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, true, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }
}
