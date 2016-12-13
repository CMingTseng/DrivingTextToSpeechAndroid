package com.microapps.drivingtexttospeech;

import android.content.Context;

import com.neura.resources.sensors.SensorType;
import com.neura.standalonesdk.util.NeuraStateAlertReceiver;

public class HandleNeuraStateAlertReceiver extends NeuraStateAlertReceiver {

    @Override
    public void onDetectedMissingPermission(Context context, String permission) {
    }

    @Override
    public void onDetectedMissingPermissionAfterUserPressedNeverAskAgain(Context context, String permission) {
    }

    @Override
    public void onSensorStateChanged(Context context, SensorType sensorType, boolean b) {

    }

}
