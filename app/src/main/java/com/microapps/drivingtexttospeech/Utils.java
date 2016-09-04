package com.microapps.drivingtexttospeech;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.microapps.drivingtexttospeech.history.EventHistoryItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class Utils {

    public static final String STARTED_DRIVING = "userStartedDriving";
    public static final String FINISHED_DRIVING = "userFinishedDriving";

    public static final String KEY_EVENT = "keyEvent";

    //Saving history for a set of timestamps that events 'started driving' was received
    private static final String KEY_STARTED_DRIVING_TIMES = "keyStartedDrivingTimes";
    //Saving history for a set of timestamps that events 'finished driving' was received
    private static final String KEY_FINISHED_DRIVING_TIMES = "keyFisnishedDrivingTimes";

    public static void setLatestEvent(Context context, boolean driving, long timestamp) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        //Adding to 'history' the timestamp for the latest event.
        Set<String> timestamps = preferences.getStringSet(
                driving ? KEY_STARTED_DRIVING_TIMES : KEY_FINISHED_DRIVING_TIMES, new HashSet<String>());
        timestamps.add(String.valueOf(timestamp));
        preferences.edit().putStringSet(driving ? KEY_STARTED_DRIVING_TIMES : KEY_FINISHED_DRIVING_TIMES, timestamps).commit();
    }

    /**
     * @param context
     * @return arriving and leaving home events from the beginning of logging in.
     */
    public static ArrayList<EventHistoryItem> getHistoryEvents(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Set<String> startedSet = preferences.getStringSet(KEY_STARTED_DRIVING_TIMES, new HashSet<String>());
        Set<String> finishedSet = preferences.getStringSet(KEY_FINISHED_DRIVING_TIMES, new HashSet<String>());

        ArrayList<String> startedTimestamps = new ArrayList<>(Arrays.asList(startedSet.toArray(new String[startedSet.size()])));
        ArrayList<String> finishedTimestamps = new ArrayList<>(Arrays.asList(finishedSet.toArray(new String[finishedSet.size()])));

        ArrayList<EventHistoryItem> events = new ArrayList<>();
        for (String time : startedTimestamps) {
            events.add(new EventHistoryItem(true, Long.parseLong(time)));
        }
        for (String time : finishedTimestamps) {
            events.add(new EventHistoryItem(false, Long.parseLong(time)));
        }

        Collections.sort(events, new Comparator<EventHistoryItem>() {
            @Override
            public int compare(EventHistoryItem lhs, EventHistoryItem rhs) {
                return (int) (lhs.getEventTimestamp() - rhs.getEventTimestamp());
            }
        });

        return events;
    }

    public static String getDefaultTime(long timestamp) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp);
        SimpleDateFormat sdf = new SimpleDateFormat("MMddyyyy HH:mm:ss");
        return sdf.format(cal.getTime());
    }
}
