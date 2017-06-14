package com.example.srv_twry.recipes;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


/**
 * Created by srv_twry on 4/6/17.
 * The fragment which plays the associated video and shows the detailed description associated with a single step.
 */

public class VideoAndDescriptionFragment extends Fragment implements ExoPlayer.EventListener {

    private static final String TAG = "VideoFragment";

    SimpleExoPlayerView videoStepView;

    String videoUrl;
    String description;
    String thumbnailUrl;

    //Member variables for the exo-player
    long playbackPosition;
    int currentWindow;
    boolean playWhenReady;
    SimpleExoPlayer player;
    private MediaSessionCompat mediaSessionCompat;
    private PlaybackStateCompat.Builder mStateBuilder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_video_description,container,false);

        //get the data from the parent activity about the url and the description
        Bundle bundle = getArguments();
        videoUrl = bundle.getString("Step URL");
        description = bundle.getString("Step Description");
        thumbnailUrl = bundle.getString("Step thumbnail");

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
            //if thumbnail url is not empty
            if (!thumbnailUrl.equals("")){
                final ImageView thumbnail = (ImageView) rootView.findViewById(R.id.iv_thumbnail_description_step);
                thumbnail.setVisibility(View.VISIBLE);

                Picasso.with(getContext()).load(thumbnailUrl).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        thumbnail.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        thumbnail.setVisibility(View.GONE);  //Set the visibility to GONE in case of error.
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
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
            initializeMediaSession();
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

    private void initializeMediaSession(){
        mediaSessionCompat = new MediaSessionCompat(getContext(),TAG);
        mediaSessionCompat.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        );

        mediaSessionCompat.setMediaButtonReceiver(null);
        mStateBuilder = new PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY |
        PlaybackStateCompat.ACTION_PAUSE | PlaybackStateCompat.ACTION_PLAY_PAUSE | PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS);

        mediaSessionCompat.setPlaybackState(mStateBuilder.build());

        mediaSessionCompat.setCallback(new MyMediaSessionCallback());

        mediaSessionCompat.setActive(true);
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
        if(mediaSessionCompat !=null){
            mediaSessionCompat.setActive(false);        //added null checking after code review.
        }
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
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady ){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,player.getCurrentPosition(),1f);
        }else if(playbackState == ExoPlayer.STATE_READY){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,player.getCurrentPosition(),1f);
        }

        mediaSessionCompat.setPlaybackState(mStateBuilder.build());
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
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

    private class MyMediaSessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            player.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            player.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            player.seekTo(0);
        }
    }
}
