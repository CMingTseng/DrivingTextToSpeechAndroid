package com.microapps.drivingtexttospeech.history;

public class EventHistoryItem {

    private boolean mIsStartedDriving;
    private long mEventTimestamp;

    public EventHistoryItem(boolean isStartedDriving, long eventTimestamp) {
        mIsStartedDriving = isStartedDriving;
        mEventTimestamp = eventTimestamp;
    }

    public boolean isStartedDriving() {
        return mIsStartedDriving;
    }

    public long getEventTimestamp() {
        return mEventTimestamp;
    }
}
