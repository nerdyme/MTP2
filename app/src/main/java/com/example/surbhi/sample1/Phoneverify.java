package com.example.surbhi.sample1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Phoneverify extends BaseActionbar {

    Button b1 = null;
    EditText e1 =null;
    ProgressBar pb;
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
                        String phoneno = e1.getText().toString();

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
                            loginparams.put("gcmid",Pinverify.gcmRegId);

                            JsonObjectRequest request1 = new JsonObjectRequest(NetworkConfig.pinforget, new JSONObject(loginparams),
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
                            );

                            VolleyApplication.getInstance().getRequestQueue().add(request1);


                        }
                    }
                });
            }
        });

    }



}
