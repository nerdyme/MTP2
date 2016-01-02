package com.example.surbhi.sample1;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class Createcontact1 extends BaseActionbar {

    ArrayAdapter<String> adapter;
    List <String> groupNames;
    Button submit = null;
    Button cancel = null;
    EditText name = null;
    EditText age = null;
    EditText phone = null;
    Spinner gender = null;
    Spinner dis = null;
    Spinner state = null;
    Spinner city = null;
    Spinner contactgroup = null;
    ImageButton btnSpeak;


    String nv = null, pv = null, av = null, gv = null, dv = null, sv = null, cv = null, cgv = "";
    private final int REQ_CODE_SPEECH_INPUT = 100;
    // String tok = MainActivity.token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createcontact1);
        name = (EditText) findViewById(R.id.name_value);
        age = (EditText) findViewById(R.id.age_value);
        phone = (EditText) findViewById(R.id.phone_value);
        gender = (Spinner) findViewById(R.id.gender_value);
        dis = (Spinner) findViewById(R.id.district_value);
        city = (Spinner) findViewById(R.id.city_value);
        state = (Spinner) findViewById(R.id.state_value);
        contactgroup = (Spinner) findViewById(R.id.contactgroup_value);

        populatespinner();

        contactgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String country = parent.getItemAtPosition(position).toString();

                Toast.makeText(Createcontact1.this, country, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnSpeak = (ImageButton) findViewById(R.id.btnSpeak1);

        btnSpeak.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                finish();
            }
        });

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                //Put validations here



                nv = name.getText().toString();
                av = age.getText().toString();
                pv = phone.getText().toString();

                gv = gender.getSelectedItem().toString();
                dv = dis.getSelectedItem().toString();
                cv = city.getSelectedItem().toString();
                sv = state.getSelectedItem().toString();
                cgv = contactgroup.getSelectedItem().toString();
		
		/*gender.setOnItemSelectedListener(new OnItemSelectedListener(AdapterView<?> parent, View view, int pos, long id) 
		{
			gv = parent.getItemAtPosition(pos).toString();
		});*/

                int set = 0;
                Log.w("send", "Call to async task");

                if (nv.equals("") || nv.equals(null)) {
                    Toast.makeText(getBaseContext(), "Name can't be empty", Toast.LENGTH_LONG).show();
                } else if (nv.length() < 5) {
                    Toast.makeText(getBaseContext(), "Name must have atleast 5 characters", Toast.LENGTH_LONG).show();
                } else if (pv.equals("") || pv.equals(null)) {
                    Toast.makeText(getBaseContext(), "Phone Number can't be empty", Toast.LENGTH_LONG).show();

                } else if (pv.contains("[0-9]+") == false && pv.length() != 10) {
                    Toast.makeText(getBaseContext(), "Enter valid phone number", Toast.LENGTH_LONG).show();

                } else if (Integer.parseInt(av) < 0 || Integer.parseInt(av) > 150) {
                    Toast.makeText(getBaseContext(), "Enter valid age(0-150)", Toast.LENGTH_LONG).show();
                } else    //if(pv.contains("[0-9]+") == true && pv.length() ==10)
                {

                    Log.w("send", "Call to async task2");
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Createcontact1.this);
                    builder1.setMessage("Press Confirm to send, Press edit to change");
                    builder1.setCancelable(false);
                    builder1.setTitle("Add Contact Details");
                    builder1.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    JsonObjectRequest request1 = new JsonObjectRequest(NetworkConfig.createcontact1, null,
                                            new Response.Listener<JSONObject>() {

                                                @Override
                                                public void onResponse(JSONObject response) {

                                                    try {
                                                        JSONArray ar1 = (JSONArray) response.get("objects");
                                                        int len = ar1.length();

                                                        for (int i = 0; i < len; ++i) {
                                                            JSONObject info = (JSONObject) ar1.get(i);

                                                            String s1 = info.get("id").toString();
                                                            String s2 = info.get("name").toString();
                                                            JSONObject js1 = (JSONObject) info.get("form");
                                                            String s3 = js1.get("id").toString();

                                                            HashMap<String, String> temp = new HashMap<String, String>();
                                                            temp.put(Constants.FIRST_COLUMN, s1);
                                                            temp.put(Constants.SECOND_COLUMN, s2);
                                                            temp.put(Constants.THIRD_COLUMN, s3);
                                                            // list.add(temp);

                                                        }
                                                        //pb.setVisibility(View.GONE);
                                                    } catch (JSONException e) {
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
                            });
                    builder1.setNegativeButton("Edit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });
    }


    void populatespinner() {

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(
                NetworkConfig.contact_groups1, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray cast = response.getJSONArray("objects");

                            groupNames = new ArrayList<String>();


                            for (int i = 0; i < cast.length(); i++) {
                                JSONObject actor = cast.getJSONObject(i);
                                String name = actor.getString("name");
                                groupNames.add(name);
                            }
                            adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, groupNames);
                            adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);

                            contactgroup.setAdapter(adapter);

                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        Log.d("Tag", response.toString());
                        // pDialog.hide();
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {


            @Override
            public void onErrorResponse(VolleyError error) {

                VolleyLog.d("Tag", "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error in fetching contacts", Toast.LENGTH_LONG).show();
                // hide the progress dialog
                //pDialog.hide();
            }
        });
    }


    /**
     * Showing google speech input dialog
     */
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
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    name.setText(result.get(0));
                }
                break;
            }

        }
    }
}