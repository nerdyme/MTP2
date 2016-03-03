package com.example.surbhi.sample1;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Createcontact1 extends BaseActionbar implements AdapterView.OnItemClickListener {

    ArrayAdapter<String> adapter;
    List <String> groupNames;
    List <String> groupIDs;
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

    String Grouplist;

    MyAdapter ma;

    ProgressBar pb ;

    int grpsize=0;
    String nv = null, pv = null, av = null, gv = null, dv = null, sv = null, cv = null, cgv = "";
    private final int REQ_CODE_SPEECH_INPUT = 100;
    // String tok = MainActivity.token;

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        ma.toggle(arg2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ma = new MyAdapter();

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
        pb = (ProgressBar) findViewById(R.id.progressBar10);
        pb.setVisibility(View.GONE);

        groupNames = new ArrayList<String>();
        groupIDs = new ArrayList<String>();

        populatespinner();
        groupNames.add("Create New Group");
        //groupNames.add("Surbhi");
        //groupNames.add("Ajay");
        //groupNames.add("palak");
        //groupNames.add("Megha");

        //adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_multiple_choice, groupNames);
        //adapter = new ArrayAdapter<String>(Createcontact1.this, R.layout.spinner_item, groupNames);
        //adapter.setDropDownViewResource(android.R.layout.simple_list_item_multiple_choice);

        Log.d("TAG", groupNames.toString());
        if(groupNames!=null)
        System.out.print("Data is " + groupNames.size());

        contactgroup.setAdapter(ma);
        ma.notifyDataSetChanged();

        if(groupNames!=null)
        grpsize=groupNames.size();

        contactgroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String country = parent.getItemAtPosition(position).toString();
                if(position ==(grpsize-1))
                {
                    AlertDialog.Builder alert =  new AlertDialog.Builder(Createcontact1.this);
                    final EditText edittext = new EditText(Createcontact1.this);
                    alert.setMessage("Enter the group name");
                    alert.setTitle("Create New Group");
                    alert.setIcon(R.drawable.gramvaani);
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.MATCH_PARENT);
                    edittext.setLayoutParams(lp);


                    alert.setView(edittext);

                    alert.setPositiveButton("Yes Option", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {

                            //What ever you want to do with the value
                            Editable YouEditTextValue = edittext.getText();
                            //OR

                            String groupname1 = edittext.getText().toString();
                            if(groupname1==""||groupname1.length()<3) {
                                Toast toast = Toast.makeText(Createcontact1.this, "Please Enter GroupName!", Toast.LENGTH_LONG);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                            }
                            else {
                                //Code to add group in GV
                                createNewGroup(groupname1);
                                groupNames.clear();
                                groupNames.add("Create New Group");
                                populatespinner();
                            }
                        }
                    });

                    alert.setNegativeButton("No Option", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                                        dialog.cancel();
                        }
                    });

                    alert.show();
                }
                else
                {
                    populatecontactgroup();
                    //String Company    = ((TextView) v.findViewById(R.id.company)).getText().toString();
                    //Toast.makeText(Createcontact1.this, country, Toast.LENGTH_SHORT).show();
                }
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

                                    pb.setVisibility(View.VISIBLE);
                                    StringRequest sr = new StringRequest(Request.Method.POST, NetworkConfig.createcontact, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            Log.d("TAG", "Register Response: " + response.toString());

                                            pb.setVisibility(View.GONE);
                                            try {
                                                JSONObject ob1 = new JSONObject(response);

                                                String msg = ob1.get("message").toString();

                                                if (msg.equalsIgnoreCase("Contact Created!"))
                                                    Toast.makeText(getApplicationContext(), "Contact created successfully", Toast.LENGTH_LONG).show();
                                                else
                                                    Toast.makeText(getApplicationContext(), "Connection Error, Try Again", Toast.LENGTH_LONG).show();

                                                ma.notifyDataSetChanged();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() {
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("name", nv);
                                            params.put("number", pv);
                                            params.put("gender", gv);
                                            params.put("gcmid", Constants.gcmRegId);
                                            params.put("clist_ids", nv);
                                            return params;
                                        }

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }*/
                                    };

                                    VolleyApplication.getInstance().getRequestQueue().add(sr);
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
                            for (int i = 0; i < cast.length(); i++) {
                                JSONObject actor = cast.getJSONObject(i);
                                String name = actor.getString("name");
                                int id =actor.getInt("id");
                                groupNames.add(name);
                                groupIDs.add(String.valueOf(id));
                                Log.d("Contactgroups", name);
                            }

                            Log.d("Contactgroups",groupNames.toString());


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
        VolleyApplication.getInstance().getRequestQueue().add(jsonObjReq);
    }

    void createNewGroup(final String groupname)
    {
        StringRequest sr = new StringRequest(Request.Method.POST,NetworkConfig.creategroup, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("TAG","Create Group Response: " + response.toString());

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

            }
        })  {
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put("name",groupname);


                return params;
            }

            /*@Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("Content-Type","application/x-www-form-urlencoded");
                return params;
            }*/
        };

        VolleyApplication.getInstance().getRequestQueue().add(sr);


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

    class MyAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener
    {
        private SparseBooleanArray mCheckStates;
        LayoutInflater mInflater;
        TextView tv1,tv;
        CheckBox cb;
        MyAdapter()
        {
            if(groupNames!=null)
            mCheckStates = new SparseBooleanArray(groupNames.size());

            mInflater = (LayoutInflater)Createcontact1.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if(groupNames!=null)
            return groupNames.size();

            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub

            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi=convertView;
            if(convertView==null)
                vi = mInflater.inflate(R.layout.spinner_item, null);
            tv= (TextView) vi.findViewById(R.id.textview10);

            cb = (CheckBox) vi.findViewById(R.id.checkBox10);
            tv.setText("Group Name :"+ groupNames.get(position));

            cb.setTag(position);
            cb.setChecked(mCheckStates.get(position, false));
            cb.setOnCheckedChangeListener(this);

            return vi;
        }
        public boolean isChecked(int position) {
            return mCheckStates.get(position, false);
        }

        public void setChecked(int position, boolean isChecked) {
            mCheckStates.put(position, isChecked);
        }

        public void toggle(int position) {
            setChecked(position, !isChecked(position));
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            // TODO Auto-generated method stub

            mCheckStates.put((Integer) buttonView.getTag(), isChecked);
        }
    }
    void populatecontactgroup()
    {
        StringBuilder checkedcontacts= new StringBuilder();
        int set=0;

        if(groupNames!=null)
        for(int i = 0; i < groupNames.size(); i++)

        {
            if(ma.mCheckStates.get(i)==true)
            {
                set=1;
                checkedcontacts.append(groupNames.get(i).toString());
                checkedcontacts.append(",");

            }
            else
            {

            }


        }
        if(set==1)
        {
            Grouplist= checkedcontacts.toString();

            Grouplist = Grouplist.substring(0, Grouplist.length()-1);

            //new MyAsyncTask().execute(mymsg,contactlist);
        }
        else
            Toast.makeText(Createcontact1.this, "Select atleast one contact", Toast.LENGTH_SHORT).show();
        //Toast.makeText(Display.this, checkedcontacts,1000).show();
    }

}