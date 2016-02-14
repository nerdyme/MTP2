package com.example.surbhi.sample1;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ContactOptions2 extends BaseActionbar implements AdapterView.OnItemClickListener {

    List<String> groupNames;
    List<String> groupIDs;
    MyAdapter ma;
    String mymsg = "";
    Button select;
    String grouplist="";
    ProgressBar pb;
    Bundle bundle;
    String actname="",form_id="",audiofile="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contactoptions2);

        bundle=getIntent().getExtras();
        actname=bundle.getString("ActivityName");

        groupNames = new ArrayList<String>();
        groupIDs = new ArrayList<String>();
        pb = (ProgressBar)findViewById(R.id.progressBar11);
        pb.setVisibility(View.VISIBLE);
        populatespinner();

        ListView lv= (ListView) findViewById(R.id.lv2);
        ma = new MyAdapter();
        lv.setAdapter(ma);
        lv.setOnItemClickListener(this);
        lv.setItemsCanFocus(false);
        lv.setTextFilterEnabled(true);
        // adding
        select = (Button) findViewById(R.id.button2);
        select.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                StringBuilder checkedcontacts= new StringBuilder();
                int set=0;

                for(int i = 0; i < groupNames.size(); i++)

                {
                    if(ma.mCheckStates.get(i)==true)
                    {
                        set=1;
                        checkedcontacts.append(groupIDs.get(i).toString());
                        checkedcontacts.append(",");

                    }
                    else
                    {

                    }


                }
                if(set==1)
                {
                    grouplist= checkedcontacts.toString();

                    grouplist = grouplist.substring(0, grouplist.length()-1);

                    //Add volley request
                    // Do Volley request
                    if(actname.equalsIgnoreCase("Message1")==true)
                    {
                        mymsg = bundle.getString("msg");
                        sendmessage();
                        Toast.makeText(getApplicationContext(),"Message is successfully delivered",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else if (actname.equalsIgnoreCase("LaunchSurvey")==true)
                    {
                        form_id=bundle.getString("form_id");
                        sendsurvey();
                        Toast.makeText(getApplicationContext(),"Survey is successfully launched",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else if (actname.equalsIgnoreCase("SendAudio")==true)
                    {
                        audiofile=bundle.getString("filepath");
                        sendaudio();
                        Toast.makeText(getApplicationContext(),"Audio is successfully delivered",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    {

                    }
                }
                else
                    Toast.makeText(ContactOptions2.this, "Select atleast one contact group",Toast.LENGTH_SHORT).show();

            }
        });


    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        ma.toggle(arg2);
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
                                int id = actor.getInt("id");
                                groupNames.add(name);
                                groupIDs.add(String.valueOf(id));
                                Log.d("Contactgroups", name);
                            }

                            Log.d("Contactgroups",groupNames.toString());
                            pb.setVisibility(View.GONE);

                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        Log.d("Tag", response.toString());
                        // pDialog.hide();
                        //Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
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


class MyAdapter extends BaseAdapter implements CompoundButton.OnCheckedChangeListener
{
    private SparseBooleanArray mCheckStates;
    LayoutInflater mInflater;
    TextView tv1,tv;
    CheckBox cb;
    MyAdapter()
    {
        mCheckStates = new SparseBooleanArray(groupNames.size());
        mInflater = (LayoutInflater)ContactOptions2.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return groupNames.size();
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
            vi = mInflater.inflate(R.layout.phonecontactadapter, null);

        tv= (TextView) vi.findViewById(R.id.textView1);
        tv1= (TextView) vi.findViewById(R.id.textView2);
        cb = (CheckBox) vi.findViewById(R.id.checkBox1);
        tv.setText("Group Name :"+ groupNames.get(position));
        tv1.setText("Group ID :"+ groupIDs.get(position));
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

    void sendmessage()
    {
        //Do a volley request
    }
    void sendaudio()
    {

    }

    void sendsurvey()
    {

    }
}
