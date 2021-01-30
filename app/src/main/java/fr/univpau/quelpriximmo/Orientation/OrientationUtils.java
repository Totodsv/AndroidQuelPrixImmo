package fr.univpau.quelpriximmo.Orientation;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.view.Surface;
import android.view.WindowManager;

/** Static methods related to device orientation. */
public class OrientationUtils {
    private OrientationUtils() {}

    /** Locks the device window in landscape mode. */
    public static void lockOrientationLandscape(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /** Locks the device window in portrait mode. */
    public static void lockOrientationPortrait(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /** Locks the device window in actual screen mode. */
    @SuppressLint("WrongConstant")
    public static void lockOrientation(Activity activity) {
        final int orientation = activity.getResources().getConfiguration().orientation;
        final int rotation = ((WindowManager) activity.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();

        // Copied from Android docs, since we don't have these values in Froyo 2.2
        int SCREEN_ORIENTATION_REVERSE_LANDSCAPE = 8;
        int SCREEN_ORIENTATION_REVERSE_PORTRAIT = 9;


        if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_90){
            if (orientation == Configuration.ORIENTATION_PORTRAIT){
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
            else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            }
        }
        else if (rotation == Surface.ROTATION_180 || rotation == Surface.ROTATION_270)
        {
            if (orientation == Configuration.ORIENTATION_PORTRAIT){
                activity.setRequestedOrientation(SCREEN_ORIENTATION_REVERSE_PORTRAIT);
            }
            else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
                activity.setRequestedOrientation(SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
        }
    }

    /** Unlocks the device window in user defined screen mode. */
    public static void unlockOrientation(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
    }

}
