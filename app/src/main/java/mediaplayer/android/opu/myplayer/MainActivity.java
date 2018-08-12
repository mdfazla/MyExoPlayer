package mediaplayer.android.opu.myplayer;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.util.Util;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_view_main_layout);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initPlayer();
    }

    private void initPlayer() {
        com.google.android.exoplayer2.ui.PlayerView playerView = findViewById(R.id.player_view);
        playerView.requestFocus();

        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        DataSource.Factory mediaDataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "MyPlayer"), (TransferListener<? super DataSource>) bandwidthMeter);

        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);

        DefaultTrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        SimpleExoPlayer player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

        playerView.setPlayer(player);

        player.setPlayWhenReady(true);
/*        MediaSource mediaSource = new HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
                mediaDataSourceFactory, mainHandler, null);*/


        MediaSource mediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory).createMediaSource(Uri.parse("https://raw.githubusercontent.com/mdfazla/BeautifulBangladesh/master/demo_video.mp4"));

        boolean haveStartPosition = 0 != C.INDEX_UNSET;
        //if (haveStartPosition) {
            player.seekTo(0, 0);
        //}

        player.prepare(mediaSource);
    }
}
