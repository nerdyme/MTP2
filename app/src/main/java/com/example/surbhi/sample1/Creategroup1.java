package com.example.surbhi.sample1;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Creategroup1 extends BaseActionbar {

    public EditText msgvalue=null;
    private ImageButton btnSpeak;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    Button b1 =null;
    String contactgroupname=null;
    TextView tv1;
    ProgressBar pb=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creategroup1);

        msgvalue= (EditText)findViewById(R.id.grp1_value);
        b1 = (Button) findViewById(R.id.creategrp);
        tv1= (TextView) findViewById(R.id.grp1);
        pb=(ProgressBar) findViewById(R.id.progressBar20);
        pb.setVisibility(View.GONE);

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
                contactgroupname = msgvalue.getText().toString();

                System.out.println("value of message" + contactgroupname);

                if(contactgroupname.equals(null) || contactgroupname.trim().equals("") ||contactgroupname.equals("") || contactgroupname.equalsIgnoreCase(" "))
                {
                    Toast.makeText(getApplicationContext(), R.string.Please_enter_groupname_before_creating, Toast.LENGTH_LONG).show();
                }
                else
                {
                   //VolleyRequest
                    pb.setVisibility(View.VISIBLE);
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

                pb.setVisibility(View.GONE);
                Log.d("TAG", "Create Group Response: " + response.toString());
                try {
                    JSONObject js=new JSONObject(response.toString());
                    String msg=js.getString("message");
                    if (msg.equalsIgnoreCase("Contact List Created!")==true)
                    {
                        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getApplicationContext());
                        dlgAlert.setMessage(R.string.Contact_Group_Created);
                        //dlgAlert.setTitle("App Title");
                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //dismiss the dialog
                                        finish();
                                    }
                                });
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                        //Toast.makeText(getApplicationContext(),R.string.Contact_Group_Created,Toast.LENGTH_LONG).show();
                        //finish();

                    }
                    else
                    {AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getApplicationContext());
                        dlgAlert.setMessage(R.string.Error_in_creating_Contact_Group);
                        //dlgAlert.setTitle("App Title");
                        dlgAlert.setPositiveButton("Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        //dismiss the dialog
                                        finish();
                                    }
                                });
                        dlgAlert.setCancelable(true);
                        dlgAlert.create().show();
                        Toast.makeText(getApplicationContext(),R.string.Error_in_creating_Contact_Group,Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),R.string.Error_in_creating_Contact_Group,Toast.LENGTH_LONG).show();
                }

                //pb.setVisibility(View.GONE);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(getApplicationContext());
                dlgAlert.setMessage(R.string.Error_in_creating_Contact_Group);
                //dlgAlert.setTitle("App Title");
                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                //dismiss the dialog
                                finish();
                            }
                        });
                dlgAlert.setCancelable(true);
                dlgAlert.create().show();
                Log.d("TAG", "Not created Group Response: " + error.toString());
                //Toast.makeText(getApplicationContext(),R.string.Error_in_creating_Contact_Group,Toast.LENGTH_LONG).show();
                //finish();

            }
        })  {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",contactgroupname);
                params.put("gcmid",Constants.gcmRegId);
                return params;
            }                                                                                                                                                                                                                                                                                                                                                   

           /* @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/json");
                return params;
            }*/
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

