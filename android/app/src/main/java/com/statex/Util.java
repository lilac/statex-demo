package com.statex;

import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import co.rewen.statex.StateX;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

/**
 * Copyright (c) 2015-present, Junjun Deng
 * All rights reserved.
 * <p/>
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */
public class Util {
    public static void sendEvent(@NonNull ReactInstanceManager manager,
                                 String eventName,
                                 @NonNull Bundle bundle) {
        WritableMap map = Arguments.fromBundle(bundle);
        sendEvent(manager, eventName, map);
    }

    public static void sendEvent(@NonNull ReactInstanceManager manager,
                                 @NonNull String eventName,
                                 @Nullable WritableMap params) {
        ReactContext reactContext = manager.getCurrentReactContext();
        if (reactContext != null) {
            reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit(eventName, params);
        }
    }

    public static IntentFilter getStateFilter(@NonNull final String key) {
        IntentFilter filter = new IntentFilter(StateX.ACTION_STATE_CHANGED);
        filter.addDataScheme(StateX.SCHEME);
        filter.addDataAuthority(StateX.AUTHORITY, null);
        filter.addDataPath("/" + key, PatternMatcher.PATTERN_PREFIX);
        return filter;
    }

    public static void addAction(@NonNull ReactInstanceManager manager, Button button, String action) {
        button.setOnClickListener(new Action(manager, action));
    }

    public static class Action implements View.OnClickListener {
        ReactInstanceManager manager;
        private String action;

        public Action(@NonNull ReactInstanceManager manager, String action) {
            this.manager = manager;
            this.action = action;
        }

        @Override
        public void onClick(View view) {
            sendEvent(manager, action, (WritableMap) null);
        }
    }
}
