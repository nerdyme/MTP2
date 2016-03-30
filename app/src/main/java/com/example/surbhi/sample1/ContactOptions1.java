package com.example.surbhi.sample1;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ContactOptions1 extends BaseActionbar implements OnItemClickListener{

    List<String> name1 = new ArrayList<String>();
    List<String> phno1 = new ArrayList<String>();
    String mymsg = "";
    String contactlist="";
    Bundle bundle;
    String actname="",form_id="",audiofile="";

    ProgressDialog progressDialog = null;


    Phonecontactlistadapter ma;
    Button select;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_options1);

         bundle=getIntent().getExtras();
         actname=bundle.getString("ActivityName");


        //enteredValue.setText(passedArg);

        getAllContacts(this.getContentResolver());
        Collections.sort(name1, String.CASE_INSENSITIVE_ORDER);

        ListView lv= (ListView) findViewById(R.id.lv1);
        ma = new Phonecontactlistadapter(name1,phno1,getApplicationContext());
        lv.setAdapter(ma);
        lv.setOnItemClickListener(this);
        lv.setItemsCanFocus(false);
        lv.setTextFilterEnabled(true);
        // adding
        select = (Button) findViewById(R.id.button1);
        select.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v) {
                StringBuilder checkedcontacts= new StringBuilder();
                int set=0;

                for(int i = 0; i < name1.size(); i++)

                {
                    if(ma.mCheckStates.get(i)==true)
                    {
                        set=1;
                        checkedcontacts.append(phno1.get(i).toString());
                        checkedcontacts.append(",");

                    }
                    else
                    {

                    }
                }
                if(set==1)
                {
                    contactlist= checkedcontacts.toString();

                    contactlist = contactlist.substring(0, contactlist.length()-1);

                    // Do Volley request
                    if(actname.equalsIgnoreCase("TemplateAnnouncementCamp")==true)
                    {


                        JSONObject jsonObject = new JSONObject();
                        try {

                            jsonObject.put("campname", bundle.getString("campname"));
                            jsonObject.put("start",bundle.getString("start"));
                            jsonObject.put("end",bundle.getString("end"));
                            jsonObject.put("venuename",bundle.getString("venuename"));

                            //json = jsonObject.toString();
                            //sendmessage(json);

                            //jsonObject1.put("message_args", json);
                            //jsonObject1.put("authenticity_token",MainActivity.token);

                            //json1 = jsonObject1.toString();

                        }
                        catch(JSONException e)
                        {

                        }
                        sendmessage(jsonObject);
                        Toast.makeText(getApplicationContext(),"Message is successfully delivered",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else if(actname.equalsIgnoreCase("TemplateAnnouncementGovtscheme")==true)
                    {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("date", bundle.getString("date"));
                        }
                        catch(JSONException e)
                        {

                        }
                        sendmessage(jsonObject);
                        Toast.makeText(getApplicationContext(),"Message is successfully delivered",Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else if(actname.equalsIgnoreCase("TemplateAnnouncementSurvey")==true)
                    {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("date", bundle.getString("date"));
                        }
                        catch(JSONException e)
                        {

                        }
                        sendmessage(jsonObject);
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
                    Toast.makeText(ContactOptions1.this, "Select atleast one contact",Toast.LENGTH_LONG).show();

            }
        });


    }
    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        ma.toggle(arg2);
    }

    public  void getAllContacts(ContentResolver cr) {

        Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        while (phones.moveToNext())
        {
            String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            name1.add(name);
            phno1.add(phoneNumber);
        }

        phones.close();
    }


    class Phonecontactlistadapter  extends BaseAdapter implements CompoundButton.OnCheckedChangeListener
    {
        private SparseBooleanArray mCheckStates;
        LayoutInflater mInflater;
        TextView tv1,tv;
        CheckBox cb;
        Context cnt;
        List <String> name1;
        List <String> phno1;

        Phonecontactlistadapter(List<String> name1, List<String> phno1, Context cnt)
        {
            mCheckStates = new SparseBooleanArray(name1.size());
            this.name1=name1;
            this.phno1=phno1;
            this.cnt=cnt;
            mInflater = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return name1.size();
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
            tv.setText("Name :"+ name1.get(position));
            tv1.setText("Phone No :"+ phno1.get(position));
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

    void sendmessage(JSONObject json)
    {
        //Do a volley request


        StringRequest request1 = new StringRequest(Request.Method.POST, NetworkConfig.launchmessage,
                new Response.Listener<String>() {


                    @Override
                    public void onResponse(String response) {

                       // pb.setVisibility(View.GONE);
                        try {

                            JSONObject response1=new JSONObject(response);
                            Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
                            /*String s1 = response1.get("message").toString();
                            if(s1.equalsIgnoreCase("Pin number is sent on the given phone number."))
                            {
                                Toast.makeText(getBaseContext(), R.string.You_will_receive_pin_shortly, Toast.LENGTH_LONG).show();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(getBaseContext(), R.string.Enter_valid_phone_number, Toast.LENGTH_LONG).show();
                            }
                            finish();*/
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

                        Toast.makeText(getBaseContext(),"Error oh no :: " + error, Toast.LENGTH_LONG).show();
                        if(error.toString().equalsIgnoreCase("com.android.volley.AuthFailureError"))
                            Toast.makeText(getApplicationContext(),R.string.no_user_registered,Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(getApplicationContext(),R.string.check_your_details,Toast.LENGTH_LONG).show();
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                String json="";

                try {
                    JSONObject js1=new JSONObject();
                    js1.put("gcmid",Constants.gcmRegId);
                    js1.put("message_id",24);
                    js1.put("caller_ids","9718658816,9891127941");
                    js1.put("gropu_ids","");

                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("club", "Hello all");
                    jsonObject.put("date", "15.07.2015");
                    jsonObject.put("callback_calls", 20);
                    jsonObject.put("club_contribs", 40);

                    js1.put("message_args",jsonObject);
                    json=js1.toString();

                } catch(JSONException e)
                {

                }
                params.put("message",json);

                /*params.put("gcmid",Constants.gcmRegId);
                params.put("message_id","24");  //to be updated based on the template id which we get
                try {

                    JSONObject jsonObject = new JSONObject();
                    JSONObject jsonObject1 = new JSONObject();
                    jsonObject.put("club", "Hello all");
                    jsonObject.put("date", "15.07.2015");
                    jsonObject.put("callback_calls", 20);
                    jsonObject.put("club_contribs", 40);


                    json = jsonObject.toString();


                    params.put("message_args",json);
                    params.put("caller_ids","9718658816,9891127941");
                    params.put("group_ids","");
                }
                 catch(JSONException e)
                 {

                 }*/
                return params;
            }

        };



        VolleyApplication.getInstance().getRequestQueue().add(request1);


    }
    void sendaudio()
    {

    }

    void sendsurvey()
    {

    }
}


