package mediaplayer.android.opu.myplayer;

import android.arch.lifecycle.ViewModelProviders;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;


public class MainActivity extends AppCompatActivity {

    private static final String mpdUrl = "https://raw.githubusercontent.com/mdfazla/testRepo/master/winter_journey_dash.mpd";//"https://raw.githubusercontent.com/mdfazla/RepoDashMedia/master/Mucize_Teaser_dash.mpd";
    private final String User_Agent = "MyExoPlayer";
    private PlayerViewModel viewModel;
    private com.google.android.exoplayer2.ui.PlayerView playerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_view_main_layout);
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
/*        MediaSource mediaSource = new HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
                mediaDataSourceFactory, mainHandler, null);*/

        //"https://raw.githubusercontent.com/mdfazla/BeautifulBangladesh/master/demo_video.mp4"

        //MediaSource mediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory).createMediaSource(Uri.parse(mpdUrl));

        // MediaSource mediaSource = new DashMediaSource.Factory(new DefaultDashChunkSource.Factory(mediaDataSourceFactory), null)
        //         .createMediaSource(Uri.parse(mpdUrl));
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
}
