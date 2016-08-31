package com.microapps.drivingtexttospeech;

import android.content.Context;
import android.preference.PreferenceManager;

public class Utils {

    public static final String STARTED_DRIVING = "userStartedDriving";
    public static final String FINISHED_DRIVING = "userFinishedDriving";

    /**
     * Preferences definitions - saving the current state of a user's driving mode.
     */
    private static final String KEY_DRIVING = "KeyDriving";

    public static void setIsDriving(Context context, boolean driving) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().
                putBoolean(KEY_DRIVING, driving).commit();
    }

    public static boolean isDriving(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_DRIVING, false);
    }
}
