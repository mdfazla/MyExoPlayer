package mediaplayer.android.opu.myplayer;

import android.arch.lifecycle.ViewModel;

import com.google.android.exoplayer2.SimpleExoPlayer;

/**
 * Created by Md.Fazla Rabbi OPu on 8/13/2018.
 */

public class PlayerViewModel extends ViewModel {
    private SimpleExoPlayer myPlayer;

    public void setMyPlayer(SimpleExoPlayer myPlayer) {
        this.myPlayer = myPlayer;
    }

    public SimpleExoPlayer getMyPlayer() {
        return myPlayer;
    }


    public void dispose(){
        myPlayer=null;
    }
}
