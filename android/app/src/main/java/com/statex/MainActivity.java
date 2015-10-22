package com.statex;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;
import com.facebook.react.ReactUtil;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.facebook.react.shell.MainReactPackage;

import co.rewen.statex.StateXPackage;

public class MainActivity extends Activity implements DefaultHardwareBackBtnHandler {

    private static final String ADD_ACTION = "add";
    private static final String DECREAE_ACTION = "decrease";
    private static final String COUNT_KEY = "count";

    private ReactInstanceManager mReactInstanceManager;
    private ReactRootView mReactRootView;

    private LocalBroadcastManager mBroadcastManager;
    private ReactTextView mCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReactRootView = new ReactRootView(this);

        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(getApplication())
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("events")
                .addPackage(new MainReactPackage())
                .addPackage(new StateXPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.RESUMED)
                .build();

        setContentView(R.layout.main_activity);
        ViewGroup root = (ViewGroup) findViewById(R.id.root);
        root.addView(mReactRootView);
        mReactRootView.startReactApplication(mReactInstanceManager, "App");

        mBroadcastManager = LocalBroadcastManager.getInstance(this);

        TextView countTextView = (TextView) findViewById(R.id.count_textView);
        mCount = new ReactTextView(countTextView, COUNT_KEY);

        ReactUtil.attach(mReactInstanceManager, mReactRootView);
        Button addButton = (Button) findViewById(R.id.add_button);
        ReactContext reactContext = mReactInstanceManager.getCurrentReactContext();
        Util.addAction(reactContext, addButton, ADD_ACTION);

        Button decButton = (Button) findViewById(R.id.decrease_button);
        Util.addAction(reactContext, decButton, DECREAE_ACTION);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && mReactInstanceManager != null) {
            mReactInstanceManager.showDevOptionsDialog();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public void onBackPressed() {
      if (mReactInstanceManager != null) {
        mReactInstanceManager.onBackPressed();
      } else {
        super.onBackPressed();
      }
    }

    @Override
    public void invokeDefaultOnBackPressed() {
      super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onPause();
        }
        mBroadcastManager.unregisterReceiver(mCount);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mReactInstanceManager != null) {
            mReactInstanceManager.onResume(this);
        }
        mBroadcastManager.registerReceiver(mCount, Util.getStateFilter(mCount.getKey()));
    }
}
