/*******************************************************************************
 * Created by Carlos Yaconi
 * Copyright 2015 Prey Inc. All rights reserved.
 * License: GPLv3
 * Full license at "/LICENSE"
 ******************************************************************************/
package com.seyupo.activities;

import com.seyupo.SeyupoVerify;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;

import com.seyupo.SEYUPOConfig;

public class LoginActivity extends Activity {

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        startup();

    }

    @Override
    protected void onStart() {
        super.onStart();
        startup();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startup();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        startup();
    }

    private void startup() {
        Intent intent = null;
        boolean ready=SeyupoConfig.getSeyupoConfig(this).getProtectReady();
        if (isThisDeviceAlreadyRegisteredWithSeyupo()) {
            SeyupoVerify.getInstance(this);
        }
        if (isThereBatchInstallationKey()&&!ready) {
                showLoginBatch();
        } else {
                showLogin();
        }
    }

    private void showLogin() {
        Intent intent = null;
        intent = new Intent(LoginActivity.this, WelcomeActivity.class);
        startActivity(intent);
        finish();
    }


    private void showLoginBatch() {
        Intent intent = null;
        intent = new Intent(LoginActivity.this, WelcomeBatchActivity.class);
        startActivity(intent);
        finish();
    }


    private boolean isThisDeviceAlreadyRegisteredWithSeyupo() {
        return SeyupoConfig.getSeyupoConfig(LoginActivity.this).isThisDeviceAlreadyRegisteredWithSeyupo(false);
    }

    private void showFeedback(Context ctx) {
        Intent popup = new Intent(ctx, FeedbackActivity.class);
        popup.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(popup);
    }

    private boolean isThereBatchInstallationKey() {
        String apiKeyBatch = SeyupoConfig.getSeyupoConfig(LoginActivity.this).getApiKeyBatch();
        return (apiKeyBatch != null && !"".equals(apiKeyBatch));
    }

}

