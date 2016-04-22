package kz.aibol.app.vkfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKCallback;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.api.VKError;

public class MainActivity extends AppCompatActivity {

    private static final String[] sMyScope = new String[]{
            VKScope.FRIENDS
    };

    private CoordinatorLayout mCoordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initToolBar();

        VKSdk.login(this, sMyScope);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, new VKCallback<VKAccessToken>() {
            @Override
            public void onResult(VKAccessToken res) {
                    startFriendsActivity();
            }

            @Override
            public void onError(VKError error) {
                Snackbar.make(mCoordinatorLayout, "Error authorizing. Check your credentials", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        })) {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
    }

    private void startFriendsActivity() {
        startActivity(new Intent(this, FriendsActivity.class));
    }


}
