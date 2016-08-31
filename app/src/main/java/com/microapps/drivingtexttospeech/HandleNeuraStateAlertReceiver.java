package com.microapps.drivingtexttospeech;

import android.content.Context;

import com.neura.android.statealert.SensorsManager;
import com.neura.standalonesdk.util.NeuraStateAlertReceiver;

public class HandleNeuraStateAlertReceiver extends NeuraStateAlertReceiver {

    @Override
    public void onDetectedMissingPermission(Context context, String permission) {
    }

    @Override
    public void onDetectedMissingPermissionAfterUserPressedNeverAskAgain(Context context, String permission) {
    }

    @Override
    public void onSensorDisabled(Context context, SensorsManager.Type sensorType) {
    }
}
