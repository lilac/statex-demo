package com.statex;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import co.rewen.statex.StateXDatabaseSupplier;

/**
 * Copyright (c) 2015-present, Junjun Deng
 * All rights reserved.
 */
public class ReactTextView extends BroadcastReceiver {
    private TextView mTextView;
    private String mKey;

    public ReactTextView(TextView mTextView, String mKey) {
        this.mTextView = mTextView;
        this.mKey = mKey;
        reload(mTextView.getContext());
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        reload(context);
    }

    public void reload(Context context) {
        StateXDatabaseSupplier databaseSupplier = new StateXDatabaseSupplier(context);
        String value = databaseSupplier.getState(mKey);
        if (value != null) {
            mTextView.setText(value);
        }
        databaseSupplier.close();
    }

    public String getKey() {
        return mKey;
    }
}
