package kz.aibol.app.vkfriends;

import android.app.Application;

import com.vk.sdk.VKSdk;

/**
 * Created by aibol on 4/21/16.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VKSdk.initialize(getApplicationContext());
    }
}
