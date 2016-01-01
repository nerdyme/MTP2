package com.example.surbhi.sample1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;


public class Pinverify extends BaseActionbar {

    private final String PREF_GCM_REG_ID = "PREF_GCM_REG_ID";
    private final String PREF_APP_VERSION = "PREF_APP_VERSION";

    private final String TAG = "GCM Demo Activity";
    private final int ACTION_PLAY_SERVICES_DIALOG = 100;

    private GoogleCloudMessaging gcm;
    private SharedPreferences prefs;
    public static String gcmRegId;

    private int currentAppVersion;

    Button b1=null;
    EditText e1 = null,e2=null;
    TextView tv1 = null;
    String pinno = "",phoneno="";

    void maintainlogin()
    {

        e1 = (EditText) findViewById(R.id.pinvalue);
        e2= (EditText) findViewById(R.id.phonevalue);

        b1 = (Button) findViewById(R.id.pinsubmit);
        tv1 = (TextView) findViewById(R.id.forgetpin);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               pinno= e1.getText().toString();
                phoneno =e2.getText().toString();


                if(phoneno.equals("") ||phoneno.equals(null))
                {
                    Toast.makeText(getBaseContext(), "Phone Number can't be empty", Toast.LENGTH_LONG).show();
                    e1.requestFocus(0);
                }
                else if(phoneno.contains("[0-9]+") == false && phoneno.length() !=10)
                {
                    Toast.makeText(getBaseContext(), "Enter valid phone number", Toast.LENGTH_LONG).show();

                }

              else if(pinno.equals("") ||pinno.equals(null))
                {
                    Toast.makeText(getBaseContext(), "PIN Number can't be empty", Toast.LENGTH_LONG).show();
                    e1.requestFocus(0);
                }


                else if (pinno.length()!=4)
                {
                    Toast.makeText(getBaseContext(), "Enter valid PIN Number", Toast.LENGTH_LONG).show();
                }
                else
                {
                   String req = pinno+"  "+phoneno+ "  " + gcmRegId;
                   Log.d("TAG","Register Response: " + req);
                   Toast.makeText(getApplicationContext(),req,Toast.LENGTH_LONG).show();
                   Intent menu = new Intent(getApplicationContext(),Pinverify.class);
                    startActivity(menu);

                }
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent phone = new Intent(getApplicationContext(),Phoneverify.class);
                startActivity(phone);
            }
        });

    }

  /* public void forgetpin(View v)
    {
        Intent phone = new Intent(getApplicationContext(),Phoneverify.class);
        startActivity(phone);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);

            // Read saved registration id from shared preferences.
            Pinverify.gcmRegId = getSharedPreferences().getString(PREF_GCM_REG_ID, "");

            // Read saved app version
            int savedAppVersion = getSharedPreferences().getInt(
                    PREF_APP_VERSION, Integer.MIN_VALUE);

            // Get current Application version
            currentAppVersion = getAppVersion(getApplicationContext());

            // register if saved registration id not found
            if (Pinverify.gcmRegId.isEmpty() || savedAppVersion != currentAppVersion) {

                setContentView(R.layout.activity_pinverify);
                String msg = "Obtaining GCM Registartion Id for device" + Pinverify.gcmRegId;
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                System.out.println("*************Obtained GCM ID is********** :: " + Pinverify.gcmRegId);
                Log.d("TAG","*************Obtained GCM ID is********** :: " + Pinverify.gcmRegId);
                registerInBackground();
                maintainlogin();
            } else {

                setContentView(R.layout.activity_menu_options);
                String msg = "Got GCM Registartion Id from Shared Preferences." + Pinverify.gcmRegId;
                Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
                System.out.println("*************GCM ID is********** :: " + Pinverify.gcmRegId);
                Intent menu = new Intent(getApplicationContext(),MenuOptions.class);
                startActivity(menu);
               // etMessage.setVisibility(View.VISIBLE);
                //btnSend.setVisibility(View.VISIBLE);
            }
        } else {
            String msg = "Google Play Services not available";
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

        }


    }

    private void registerInBackground() {
        new RegistrationTask().execute(null, null, null);
    }

    // Async task to register in background
    private class RegistrationTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(Void... params) {
            String msg = "";
            try {
                if (gcm == null) {
                    gcm = GoogleCloudMessaging
                            .getInstance(getApplicationContext());
                }
                Pinverify.gcmRegId = gcm.register(Constants.GCM_SENDER_ID);

				/*
				 * At this point, you have gcm registration id. Save this
				 * registration id in your users table against this user. This
				 * id will be used to send push notifications from server.
				 */

                msg = "Successfully Registered for GCM";

            } catch (IOException ex) {
                ex.printStackTrace();
                msg = "Error while registering for GCM.";
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            //tvStatus.setText(msg);

            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            if (!Pinverify.gcmRegId.isEmpty()) {
               // etMessage.setVisibility(View.VISIBLE);
                //btnSend.setVisibility(View.VISIBLE);

                // save gcm reg id and registered app version in shared
                // preferences
                SharedPreferences.Editor editor = getSharedPreferences().edit();
                editor.putString(PREF_GCM_REG_ID, Pinverify.gcmRegId);
                editor.putInt(PREF_APP_VERSION, currentAppVersion);
                editor.commit();
                System.out.print("Registration ID is " + Pinverify.gcmRegId);
            }

        }
    }


    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        ACTION_PLAY_SERVICES_DIALOG).show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    private SharedPreferences getSharedPreferences() {
        if (prefs == null) {
            prefs = getApplicationContext().getSharedPreferences("GcmDemoApp",
                    Context.MODE_PRIVATE);
        }
        return prefs;
    }


    /**
     * @return Application's version code from the {@code PackageManager}.
     */
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
