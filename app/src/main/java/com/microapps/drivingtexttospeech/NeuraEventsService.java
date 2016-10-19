package com.microapps.drivingtexttospeech;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.neura.standalonesdk.events.NeuraEvent;
import com.neura.standalonesdk.events.NeuraPushCommandFactory;

import java.util.Map;

public class NeuraEventsService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage message) {
        Map data = message.getData();
        Log.i(getClass().getSimpleName(), "Received push");
        if (NeuraPushCommandFactory.getInstance().isNeuraEvent(data)) {
            NeuraEvent event = NeuraPushCommandFactory.getInstance().getEvent(data);
            String eventText = event != null ? event.toString() : "couldn't parse data";
            Log.i(getClass().getSimpleName(), "received Neura event - " + eventText);

            Utils.setLatestEvent(getApplicationContext(), Utils.STARTED_DRIVING.equals(event.getEventName()),
                    event.getEventTimestamp());

            Intent notificationIntent = new Intent(getApplicationContext(), NotificationGeneratorService.class);
            notificationIntent.putExtra(Utils.KEY_EVENT, Utils.STARTED_DRIVING.equals(event.getEventName()));
            getApplicationContext().startService(notificationIntent);
        }
    }
}
