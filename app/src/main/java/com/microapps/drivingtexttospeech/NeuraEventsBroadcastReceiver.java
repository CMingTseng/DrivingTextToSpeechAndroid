package com.microapps.drivingtexttospeech;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.neura.standalonesdk.events.NeuraEvent;
import com.neura.standalonesdk.events.NeuraGCMCommandFactory;

public class NeuraEventsBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.i(getClass().getSimpleName(), "Received push");
        if (NeuraGCMCommandFactory.getInstance().isNeuraEvent(intent)) {
            final NeuraEvent event = NeuraGCMCommandFactory.getInstance().getEvent(intent);
            String eventText = event != null ? event.toString() : "couldn't parse data";
            Log.i(getClass().getSimpleName(), "received Neura event - " + eventText);
            Utils.setLatestEvent(context, Utils.STARTED_DRIVING.equals(event.getEventName()),
                    event.getEventTimestamp());

            Intent notificationIntent = new Intent(context, NotificationGeneratorService.class);
            notificationIntent.putExtra(Utils.KEY_EVENT, Utils.STARTED_DRIVING.equals(event.getEventName()));
            context.startService(notificationIntent);
        }
    }
}
