package com.example.srv_twry.recipes;

import android.content.res.Configuration;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;


/**
 * Created by srv_twry on 4/6/17.
 * The fragment which plays the associated video and shows the detailed description associated with a single step.
 */

public class VideoAndDescriptionFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String TAG = "VideoFragment";

    SimpleExoPlayerView videoStepView;

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

        //get the data from the parent activity about the url and the description
        Bundle bundle = getArguments();
        videoUrl = bundle.getString("Step URL");
        description = bundle.getString("Step Description");

        //Only for portrait-phone and tablet
        if (rootView.findViewById(R.id.tv_title_description_step) !=null){
            TextView descriptionStepTitle = (TextView) rootView.findViewById(R.id.tv_title_description_step);
            descriptionStepTitle.setPaintFlags(descriptionStepTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
            if (!description.equals("")){
                TextView descriptionStep = (TextView) rootView.findViewById(R.id.tv_description_step);
                descriptionStep.setText(description);
            }else{
                descriptionStepTitle.setVisibility(View.GONE);
            }

        }

        videoStepView = (SimpleExoPlayerView) rootView.findViewById(R.id.view_video_step);


        if (savedInstanceState !=null){
            currentWindow = savedInstanceState.getInt("currentWindow");
            playWhenReady = savedInstanceState.getBoolean("playWhenReady");
            playbackPosition = savedInstanceState.getLong("playbackPosition");
        }

        if (videoUrl.equals("")){
            videoStepView.setVisibility(View.GONE);

            //For landscape mode with no video, we will show a toast as their won't be any description too.
            if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                Toast toast = Toast.makeText(getContext(),"No Video Available",Toast.LENGTH_LONG);
                toast.show();
            }
        }else{
            //Initialize the media player
            initializePlayer();
            player.addListener(this);
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

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
        Toast.makeText(getContext(), "Unable to play video", Toast.LENGTH_SHORT).show();
        Log.e(TAG,"Exoplayer error");
        error.printStackTrace();
    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("currentWindow",currentWindow);
        outState.putBoolean("playWhenReady",playWhenReady);
        outState.putLong("playbackPosition",playbackPosition);
    }
}
