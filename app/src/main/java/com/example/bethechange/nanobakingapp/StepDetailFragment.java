package com.example.bethechange.nanobakingapp;

import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bethechange.nanobakingapp.Model.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;

/**
 * A fragment representing a single Recipe detail screen.
 * This fragment is either contained in a {@link StepsListActivity}
 * in two-pane mode (on tablets) or a {@link StepDetailActivity}
 * on handsets.
 */
public class StepDetailFragment extends Fragment {
    public static final String ARG_STEP = "item_id";

    private Step mItem;
    private SimpleExoPlayer simpleExoPlayer;
    private SimpleExoPlayerView playerView;

    public StepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_STEP)) {
            mItem = new Gson().fromJson(getArguments().getString(ARG_STEP),Step.class);
            if(!getActivity().getResources().getBoolean(R.bool.w900))
                getActivity().setTitle(mItem.getSnippetDescription());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.step_detail_fragment, container, false);
        TextView description=rootView.findViewById(R.id.recipe_detail);
        description.setText(mItem.getFullDescription());
        playerView = (SimpleExoPlayerView) rootView.findViewById(R.id.exoplayer_view);
        initializePlayer(Uri.parse(mItem.getVideoLink()));
        return rootView;
    }
    private void initializePlayer(Uri mediaUri) {
        if(mediaUri ==null ){
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
            playerView.setPlayer(simpleExoPlayer);

            playerView.setDefaultArtwork(BitmapFactory.decodeResource
                    (getResources(), R.drawable.not_available));
            return;
        }
        if (simpleExoPlayer == null ) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), trackSelector, loadControl);
            playerView.setPlayer(simpleExoPlayer);

            String userAgent = Util.getUserAgent(this.getContext(), mItem.getSnippetDescription());
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    this.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            simpleExoPlayer.prepare(mediaSource);
            simpleExoPlayer.setPlayWhenReady(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void releasePlayer() {
        if(simpleExoPlayer==null)
            return;
        simpleExoPlayer.stop();
        simpleExoPlayer.release();
        simpleExoPlayer = null;
    }

}
