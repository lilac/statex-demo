package com.facebook.react;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactRootView;

/**
 * Copyright (c) 2015-present, Junjun Deng
 * All rights reserved.
 */
public class ReactUtil {

    public static void attach(ReactInstanceManager manager, ReactRootView view)
    {
        manager.attachMeasuredRootView(view);
    }
}
