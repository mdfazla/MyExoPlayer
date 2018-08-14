package mediaplayer.android.opu.myplayer;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;

public class Utils {
    private static Point res;

    public static Point getDeviceResolution(AppCompatActivity activity) {
        res = new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(res);
        return res;
    }

    public static Point getRes() {
        return res;
    }
}
