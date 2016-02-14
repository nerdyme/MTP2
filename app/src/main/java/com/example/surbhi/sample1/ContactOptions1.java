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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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


