package com.ilyjava.android.yourphotos.app;

import android.content.Intent;

import com.ilyjava.android.yourphotos.activity.MainActivity;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;

/**
 * Created by Никита on 05.05.2018.
 */

public class Application extends android.app.Application{

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                Intent intent = new Intent(Application.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }
    };

    public void onCreate(){
        super.onCreate();

        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);

        VKSdk.initialize(this);
    }
}
