package mediaplayer.android.opu.myplayer;

import android.graphics.Point;
import android.os.Build;
import android.os.PowerManager;
import android.support.v7.app.AppCompatActivity;

import static android.content.Context.POWER_SERVICE;

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

    public static boolean isScreenLock(AppCompatActivity activity) {
        PowerManager powerManager = (PowerManager) activity.getSystemService(POWER_SERVICE);
        boolean isScreenOn;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            isScreenOn = powerManager.isInteractive();
        } else {
            isScreenOn = powerManager.isScreenOn();
        }

        return isScreenOn;
    }
}
