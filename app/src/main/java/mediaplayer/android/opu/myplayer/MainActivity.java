package mediaplayer.android.opu.myplayer;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.dash.DashChunkSource;
import com.google.android.exoplayer2.source.dash.DashMediaSource;
import com.google.android.exoplayer2.source.dash.DefaultDashChunkSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;


public class MainActivity extends AppCompatActivity {

    private static final String mpdUrl = "https://raw.githubusercontent.com/mdfazla/RepoDashMedia/master/winter_journey_dash.mpd";//"https://raw.githubusercontent.com/mdfazla/RepoDashMedia/master/Mucize_Teaser_dash.mpd";
    private final String User_Agent = "MyExoPlayer";
    private PlayerViewModel viewModel;
    private PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.player_view_main_layout);
        Utils.getDeviceResolution(this);
        playerView = findViewById(R.id.player_view);
        playerView.requestFocus();



    }

    @Override
    protected void onResume() {
        super.onResume();

        viewModel = ViewModelProviders.of(this).get(PlayerViewModel.class);
        if (viewModel.getMyPlayer() != null) {
            SimpleExoPlayer player = viewModel.getMyPlayer();
            playerView.setPlayer(player);
            if(!player.getPlayWhenReady())
            player.setPlayWhenReady(true);
            //player.setVideoTextureView(playerView);


        } else
            initPlayer();
    }

    private void initPlayer() {


        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        playerView.setPlayer(player);

        player.setPlayWhenReady(true);
        //player.setVideoTextureView(playerView);

        MediaSource mediaSource = buildDashMediaSource(Uri.parse(mpdUrl));


        boolean haveStartPosition = 0 != C.INDEX_UNSET;
        //if (haveStartPosition) {
        player.seekTo(0, 0);
        //}

        player.prepare(mediaSource);
        viewModel.setMyPlayer(player);
    }

    private MediaSource buildDashMediaSource(Uri uri) {
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory manifestDataSourceFactory = new DefaultHttpDataSourceFactory(User_Agent);
        DashChunkSource.Factory dashChunkSourceFactory = new DefaultDashChunkSource.Factory(new DefaultHttpDataSourceFactory(User_Agent, (TransferListener<? super DataSource>) bandwidthMeter));

        return new DashMediaSource.Factory(dashChunkSourceFactory, manifestDataSourceFactory).createMediaSource(uri);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(viewModel.getMyPlayer()!=null)
        viewModel.getMyPlayer().setPlayWhenReady(false);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        viewModel.getMyPlayer().stop();
        viewModel.getMyPlayer().release();
        viewModel.dispose();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        playerView=null;
    }

    private void addToWindowManager(){

    }
}
