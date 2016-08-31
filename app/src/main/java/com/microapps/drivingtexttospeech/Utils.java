package com.microapps.drivingtexttospeech;

import android.content.Context;
import android.preference.PreferenceManager;

public class Utils {

    public static final String STARTED_DRIVING = "userStartedDriving";
    public static final String FINISHED_DRIVING = "userFinishedDriving";

    /**
     * Preferences definitions - saving the current state of a user's driving mode.
     */
    public static final String KEY_LATEST_EVENT = "keyLatestEvent";
    public static final String KEY_LATEST_EVENT_TIME = "keyLatestEventTime";

    public static void setLatestEvent(Context context, boolean driving, long timestamp) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(KEY_LATEST_EVENT, driving).commit();
        PreferenceManager.getDefaultSharedPreferences(context).edit().
                putLong(KEY_LATEST_EVENT_TIME, timestamp).commit();
    }

    public static boolean isDriving(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(KEY_LATEST_EVENT, false);
    }

    public static long getLatestEventTime(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(KEY_LATEST_EVENT_TIME, 0);
    }
}
