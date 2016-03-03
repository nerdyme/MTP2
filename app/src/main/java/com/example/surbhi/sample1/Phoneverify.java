package com.example.surbhi.sample1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Phoneverify extends BaseActionbar {

    Button b1 = null;
    EditText e1 =null;
    ProgressBar pb;
    String phoneno="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverify);

        e1 = (EditText) findViewById(R.id.phonevalue1);
        b1 = (Button) findViewById(R.id.phonesubmit);
        pb = (ProgressBar) findViewById(R.id.progressBar3);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // TODO Auto-generated method stub

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pb.setVisibility(View.VISIBLE);
                        phoneno = e1.getText().toString();

                        if(phoneno.equals("") ||phoneno.equals(null))
                        {
                            Toast.makeText(getBaseContext(), "Phone Number can't be empty", Toast.LENGTH_LONG).show();
                            e1.requestFocus(0);
                        }
                        else if(phoneno.contains("[0-9]+") == false && phoneno.length() !=10)
                        {
                            Toast.makeText(getBaseContext(), "Enter valid phone number", Toast.LENGTH_LONG).show();

                        }
                        else
                        {
                                //Do a volley request


                                HashMap<String, String> loginparams = new HashMap<String, String>();
                                loginparams.put("phone",phoneno);
                                loginparams.put("gcmid",Constants.gcmRegId);

                            StringRequest request1 = new StringRequest(Request.Method.POST, NetworkConfig.pinforget,
                                    new Response.Listener<String>() {


                                        @Override
                                        public void onResponse(String response) {

                                            pb.setVisibility(View.GONE);
                                            try {

                                                JSONObject response1=new JSONObject(response);
                                                String s1 = response1.get("message").toString();
                                                if(s1.equalsIgnoreCase("Pin number is sent on the given phone number."))
                                                {
                                                    Toast.makeText(getBaseContext(), R.string.You_will_receive_pin_shortly, Toast.LENGTH_LONG).show();
                                                    finish();
                                                }
                                                else
                                                {
                                                    Toast.makeText(getBaseContext(), R.string.Enter_valid_phone_number, Toast.LENGTH_LONG).show();
                                                }
                                                finish();
                                                /*Snackbar snackbar = Snackbar
                                                        .make(coordinatorLayout, "Welcome to AndroidHive", Snackbar.LENGTH_LONG);

                                                snackbar.show();*/

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
                                                Toast.makeText(getApplicationContext(),R.string.no_user_registered,Toast.LENGTH_LONG).show();
                                            else
                                                Toast.makeText(getApplicationContext(),R.string.check_your_details,Toast.LENGTH_LONG).show();
                                        }
                                    }){
                                @Override
                                protected Map<String,String> getParams(){
                                    Map<String,String> params = new HashMap<String, String>();
                                    params.put("phone",phoneno);
                                    params.put("gcmid",Constants.gcmRegId);
                                    return params;
                                }

                            };

                               /* JsonObjectRequest request1 = new JsonObjectRequest(NetworkConfig.pinforget, new JSONObject(loginparams),
                                        new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {

                                                pb.setVisibility(View.GONE);
                                                try {


                                                    String s1 = response.get("message").toString();
                                                    if(s1.equals("invalid credentails"))
                                                        Toast.makeText(getApplicationContext(),s1,Toast.LENGTH_LONG).show();
                                                    else
                                                    {
                                                        Toast.makeText(getBaseContext(), "You will receive pin shortly", Toast.LENGTH_LONG).show();
                                                        finish();
                                                    }
                                                    }

                                                catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        },

                                        new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                            }
                                        }
                                );*/

                                VolleyApplication.getInstance().getRequestQueue().add(request1);


                        }
                    }
                });
            }
        });

    }



}
