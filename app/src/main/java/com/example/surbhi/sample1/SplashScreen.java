package com.example.surbhi.sample1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;


public class SplashScreen extends Activity {


        private final String PREF_GCM_REG_ID = "PREF_GCM_REG_ID";
        private final String PREF_APP_VERSION = "PREF_APP_VERSION";
        private int currentAppVersion;


    // Splash screen timer
        private static int SPLASH_TIME_OUT = 3000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash_screen);

            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity

                    // Read saved registration id from shared preferences.
                    SharedPreferences sharedpreferences = getSharedPreferences("Gramvaani", Context.MODE_PRIVATE);
                    Constants.gcmRegId = sharedpreferences.getString(PREF_GCM_REG_ID, "");

                        // Read saved app version
                        Constants.savedAppVersion = sharedpreferences.getInt(PREF_APP_VERSION, Integer.MIN_VALUE);

                        // Get current Application version
                        currentAppVersion = getAppVersion(getApplicationContext());

                        Log.d("Details are :: " ,"GCM ID" + Constants.gcmRegId + "App version" + Constants.savedAppVersion);

                        // register if saved registration id not found
                        if (Constants.gcmRegId.isEmpty() || Constants.savedAppVersion != currentAppVersion) {

                            Intent i =new Intent(SplashScreen.this,Pinverify.class);
                            startActivity(i);
                        } else {
                            Intent menu = new Intent(getApplicationContext(),MenuOptions.class);
                            startActivity(menu);

                        }


                    // close this activity
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
        private static int getAppVersion(Context context) {
            try {
                PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
                return packageInfo.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                // should never happen
                throw new RuntimeException("Could not get package name: " + e);
            }
        }
    }




