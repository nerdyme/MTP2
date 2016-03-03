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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Pinverify extends BaseActionbar {

    private final String PREF_GCM_REG_ID = "PREF_GCM_REG_ID";
    private final String PREF_APP_VERSION = "PREF_APP_VERSION";

    private final String TAG = "GCM Demo Activity";
    private final int ACTION_PLAY_SERVICES_DIALOG = 100;

    private GoogleCloudMessaging gcm;
    private SharedPreferences prefs;
    String gcmRegId=Constants.gcmRegId;

    private int currentAppVersion;

    ProgressBar pb;

    Button b1=null;
    EditText e1 = null,e2=null;
    TextView tv1 = null;
    String pinno = "",phoneno="";

    void maintainlogin()
    {
        pb.setVisibility(View.GONE);

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
                    Toast.makeText(getBaseContext(), R.string.Phone_Number_cant_be_empty, Toast.LENGTH_LONG).show();
                    e1.requestFocus(0);
                }
                else if(phoneno.contains("[0-9]+") == false && phoneno.length() !=10)
                {
                    Toast.makeText(getBaseContext(), R.string.Enter_valid_phone_number, Toast.LENGTH_LONG).show();

                }

                else if(pinno.equals("") ||pinno.equals(null))
                {
                    Toast.makeText(getBaseContext(), R.string.PIN_Number_cant_be_empty, Toast.LENGTH_LONG).show();
                    e1.requestFocus(0);
                }


                else if (pinno.contains("[0-9]+") == false && pinno.length()!=6)
                {
                    Toast.makeText(getBaseContext(), R.string.Enter_valid_PIN_Number, Toast.LENGTH_LONG).show();
                }
                else
                {
                   pb.setVisibility(View.VISIBLE);
                   String req = pinno+"  "+phoneno+ "  " + gcmRegId;
                   Log.d("TAG","Register Response: " + req);

                    HashMap<String, String> loginparams = new HashMap<String, String>();
                    loginparams.put("pin", pinno);
                    loginparams.put("phone",phoneno);
                    loginparams.put("gcmid",gcmRegId);

                    StringRequest request1 = new StringRequest(Request.Method.POST, NetworkConfig.login,
                            new Response.Listener<String>() {


                                @Override
                                public void onResponse(String response) {

                                    pb.setVisibility(View.GONE);
                                    try {

                                        JSONObject response1=new JSONObject(response);
                                        String s1 = response1.get("message").toString();
                                        if(s1.equalsIgnoreCase("Successfully logged in"))
                                        {
                                            Intent i= new Intent(getApplicationContext(),MenuOptions.class);
                                            finish();

                                            startActivity(i);
                                        }
                                        else
                                        Toast.makeText(getApplicationContext(),s1,Toast.LENGTH_LONG).show();

                                        //pb.setVisibility(View.GONE);
                                    }

                                    catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                    if(error.toString().equalsIgnoreCase("com.android.volley.AuthFailureError"))
                                    Toast.makeText(getApplicationContext(),R.string.check_your_details_phone,Toast.LENGTH_LONG).show();
                                    else if (error.toString().equalsIgnoreCase("com.android.volley.ServerError"))
                                        Toast.makeText(getApplicationContext(),R.string.check_your_details_pin,Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(getApplicationContext(),R.string.check_your_details,Toast.LENGTH_LONG).show();
                                }
                            }){
                        @Override
                        protected Map<String,String> getParams(){
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("pin",pinno);
                            params.put("phone",phoneno);
                            params.put("gcmid",gcmRegId);
                            return params;
                        }

                    };

                    VolleyApplication.getInstance().getRequestQueue().add(request1);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinverify);

           pb= (ProgressBar) findViewById(R.id.progressBar2);
            if (checkPlayServices()) {
            gcm = GoogleCloudMessaging.getInstance(this);
            // Get current Application version
            currentAppVersion = getAppVersion(getApplicationContext());

            registerInBackground();

            // Put values in Constant variables
            Constants.gcmRegId = getSharedPreferences().getString(PREF_GCM_REG_ID, "");
            Constants.savedAppVersion=getSharedPreferences().getInt(PREF_APP_VERSION,Integer.MIN_VALUE);

            maintainlogin();
            String msg = "Obtained new GCM Registartion Id " + Constants.gcmRegId;
            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            Log.d("GCM ID is " , Constants.gcmRegId);
        }
        else
        {

            Toast.makeText(getApplicationContext(),R.string.Google_Play_Services_not_available,Toast.LENGTH_LONG).show();

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
                Constants.gcmRegId = gcm.register(Constants.GCM_SENDER_ID);

				/*
				 * At this point, you have gcm registration id. Save this
				 * registration id in your users table against this user. This
				 * id will be used to send push notifications from server.
				 */

                msg = "Successfully Registered for GCM";
                Log.d("Success in GCM","Success while registering for GCM." + Constants.gcmRegId);

            } catch (IOException ex) {
                ex.printStackTrace();
                msg = "Error while registering for GCM.";
                Log.d("Error in GCm","Error while registering for GCM.");
            }
            return msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            //tvStatus.setText(msg);

            //Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();
            if (!Constants.gcmRegId.isEmpty()) {
                // save gcm reg id and registered app version in shared
                // preferences
                SharedPreferences.Editor editor = getSharedPreferences().edit();
                editor.putString(PREF_GCM_REG_ID, Constants.gcmRegId);
                editor.putInt(PREF_APP_VERSION, currentAppVersion);
                editor.commit();
                System.out.print(" GCM Registration ID is " + Constants.gcmRegId);
                Log.d("GCM ID is " , Constants.gcmRegId);
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
            prefs = getApplicationContext().getSharedPreferences("Gramvaani",
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
