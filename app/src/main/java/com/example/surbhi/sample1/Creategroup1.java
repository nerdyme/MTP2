package com.example.surbhi.sample1;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Creategroup1 extends BaseActionbar {

    public EditText msgvalue=null;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    Button b1 =null;
    String mymsg=null;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup1);

        msgvalue= (EditText)findViewById(R.id.grp1_value);
        b1 = (Button) findViewById(R.id.creategrp);
        tv1= (TextView) findViewById(R.id.grp1);

        tv1.setTypeface(Typeface.create(tv1.getTypeface(), Typeface.BOLD_ITALIC));
        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak1);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        b1.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                mymsg = msgvalue.getText().toString();

                System.out.println("value of message" + mymsg);

                if(mymsg.equals(null) || mymsg.trim().equals("") ||mymsg.equals("") || mymsg.equalsIgnoreCase(" "))
                {
                    Toast.makeText(getApplicationContext(), "Please enter groupname before creating", Toast.LENGTH_LONG).show();
                }
                else
                {
                   //VolleyRequest

                    createvolleyrequest();

                }
            }
        });

    }

    void createvolleyrequest()
    {
        StringRequest sr = new StringRequest(Request.Method.POST,NetworkConfig.creategroup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("TAG", "Create Group Response: " + response.toString());

                Toast.makeText(getApplicationContext(),"Contact Group Created",Toast.LENGTH_LONG).show();
                finish();
                //pb.setVisibility(View.GONE);
             /*   try {
                    JSONObject ob1= new JSONObject(response);
                    String code = ob1.get("RESPONSE_SUCCESS").toString();
                    String msg = ob1.get("RESPONSE_MESSAGE").toString();

                    if(msg.equalsIgnoreCase("Data not posted!"))
                        Toast.makeText(getApplicationContext(),"Check the input fields",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_LONG).show();

                }

                catch (JSONException e) {
                    e.printStackTrace();
                }*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("TAG", "Not created Group Response: ");

                Toast.makeText(getApplicationContext(),"Contact Group Created!!",Toast.LENGTH_LONG).show();
                finish();

            }
        })  {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",mymsg);


                return params;
            }                                                                                                                                                                                                                                                                                                                                                   

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }
        };

        VolleyApplication.getInstance().getRequestQueue().add(sr);


    }


    /**
     * Showing google speech input dialog
     * */
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    msgvalue.setText(result.get(0));
                }
                break;
            }

        }
    }
}

