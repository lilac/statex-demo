package com.statex;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

import java.util.Map;

import co.rewen.statex.StateX;
import co.rewen.statex.StateXDatabaseSupplier;

/**
 * Copyright (c) 2015-present, Junjun Deng
 * All rights reserved.
 * <p/>
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree.
 */
public class Util {
    public static void sendEvent(ReactContext reactContext,
                                 String eventName,
                                 @NonNull Bundle bundle) {
        WritableMap map = Arguments.fromBundle(bundle);
        sendEvent(reactContext, eventName, map);
    }

    public static void sendEvent(ReactContext reactContext,
                                 String eventName,
                                 @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit(eventName, params);
    }

    public static IntentFilter getStateFilter(final String key) {
        IntentFilter filter = new IntentFilter(StateX.ACTION_STATE_CHANGED);
        filter.addDataScheme(StateX.SCHEME);
        filter.addDataAuthority(StateX.AUTHORITY, null);
        filter.addDataPath("/" + key, PatternMatcher.PATTERN_PREFIX);
        return filter;
    }

    public static void addAction(ReactContext context, Button button, String action) {
        button.setOnClickListener(new Action(context, action));
    }

    public static class Action implements View.OnClickListener {
        ReactContext context;
        private String action;

        public Action(ReactContext context, String action) {
            this.context = context;
            this.action = action;
        }

        @Override
        public void onClick(View view) {
            sendEvent(context, action, (WritableMap) null);
        }
    }
}
