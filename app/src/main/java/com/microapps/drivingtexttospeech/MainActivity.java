package com.microapps.drivingtexttospeech;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.iid.FirebaseInstanceId;
import com.microapps.drivingtexttospeech.history.EventHistoryItem;
import com.microapps.drivingtexttospeech.history.HistoryAdapter;
import com.neura.resources.authentication.AuthenticateCallback;
import com.neura.resources.authentication.AuthenticateData;
import com.neura.sdk.object.AuthenticationRequest;
import com.neura.sdk.object.Permission;
import com.neura.sdk.service.SubscriptionRequestCallbacks;
import com.neura.standalonesdk.service.NeuraApiClient;
import com.neura.standalonesdk.util.Builder;
import com.neura.standalonesdk.util.SDKUtils;

import java.util.ArrayList;

/**
 * While driving, generating text to speech notifications.
 * This application will receive events when user arrives and leaves home, using Neura sdk.
 * The flow is as follows :
 * 1. Registering Neura client
 * 2. Login to Neura
 * 3. Subscribing to events (done in {@link #authenticateWithNeura()}
 * 4. Receiving push event for {@link Utils#FINISHED_DRIVING} and {@link Utils#STARTED_DRIVING}
 * which will be received on {@link NeuraEventsService}.
 * Here's a full guide on declaring receiving push events from Neura :
 * <a ref="https://dev.theneura.com/docs/guide/android/pushnotification">Push notification integration</a>
 * You need to :
 * ____a. set Server Key to your application on the Neura dev site.
 * ____b. Declare permissions in the AndroidManifest.
 * ____c. Build BroadcastReceiver which will handle the push {@link NeuraEventsService}
 * ____d. Call {@link NeuraApiClient#registerPushServerApiKey(Activity, String)} right after
 * _______authenticating with Neura(this is done on {@link #authenticateWithNeura()}
 */
public class MainActivity extends AppCompatActivity {

    private Button mLoginButton;

    private NeuraApiClient mNeuraApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //http://stackoverflow.com/a/38945375/5130239
        try {
            FirebaseApp.getInstance();
        } catch (IllegalStateException ex) {
            FirebaseApp.initializeApp(this, FirebaseOptions.fromResource(this));
        }

        mLoginButton = (Button) findViewById(R.id.action_button);

        initNeuraConnection();

        if (SDKUtils.isConnected(this, mNeuraApiClient))
            mLoginButton.setVisibility(View.GONE);
        else
            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    authenticateWithNeura();
                }
            });
    }

    /**
     * Setting current driving state - in case we've received a driving event on
     * {@link NeuraEventsService} when the application was open in the background,
     * or in case it was just opened.
     */
    @Override
    protected void onStart() {
        super.onStart();
        if (!SDKUtils.isConnected(this, mNeuraApiClient))
            return;

        ArrayList<EventHistoryItem> events = Utils.getHistoryEvents(this);
        if (events.isEmpty())
            findViewById(R.id.no_events).setVisibility(View.VISIBLE);
        else {
            HistoryAdapter adapter = new HistoryAdapter(this, R.layout.history_item, Utils.getHistoryEvents(this));
            ListView list = (ListView) findViewById(R.id.events_list);
            list.setAdapter(adapter);
        }
    }

    private void initNeuraConnection() {
        Builder builder = new Builder(getApplicationContext());
        mNeuraApiClient = builder.build();
        mNeuraApiClient.setAppUid(getResources().getString(R.string.app_uid));
        mNeuraApiClient.setAppSecret(getResources().getString(R.string.app_secret));
        mNeuraApiClient.connect();
    }

    /**
     * Mandatory for integration with Neura, 1st step - authenticating.
     */
    private void authenticateWithNeura() {
        AuthenticationRequest request = new AuthenticationRequest(
                Permission.list(new String[]{Utils.FINISHED_DRIVING, Utils.STARTED_DRIVING}));
        mNeuraApiClient.authenticate(request, new AuthenticateCallback() {
            @Override
            public void onSuccess(AuthenticateData authenticateData) {
                Log.i(getClass().getSimpleName(), "Successfully authenticate with neura." + "Token : " + authenticateData.getAccessToken());

                mNeuraApiClient.subscribeToEvent(Utils.FINISHED_DRIVING, "identifier_" + Utils.FINISHED_DRIVING, true, subscriptionRequestCallbacks);
                mNeuraApiClient.subscribeToEvent(Utils.STARTED_DRIVING, "identifier_" + Utils.STARTED_DRIVING, true, subscriptionRequestCallbacks);

                mNeuraApiClient.registerFirebaseToken(MainActivity.this, FirebaseInstanceId.getInstance().getToken());

                mLoginButton.setVisibility(View.GONE);
                findViewById(R.id.no_events).setVisibility(View.VISIBLE);
            }

            @Override
            public void onFailure(int errorCode) {
                Log.e(getClass().getSimpleName(), "Failed to authenticate with neura. Reason : "
                        + SDKUtils.errorCodeToString(errorCode));
            }
        });
    }

    private SubscriptionRequestCallbacks subscriptionRequestCallbacks = new SubscriptionRequestCallbacks() {
        @Override
        public void onSuccess(String s, Bundle bundle, String s1) {
            Log.i(getClass().getSimpleName(), "Successfully subscribed " + s);
        }

        @Override
        public void onFailure(String s, Bundle bundle, int i) {
            Log.i(getClass().getSimpleName(), "Failed to subscribe " + s + " Reason : " + SDKUtils.errorCodeToString(i));
        }
    };

}
