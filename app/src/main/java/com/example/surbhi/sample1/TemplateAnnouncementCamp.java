package com.example.surbhi.sample1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TemplateAnnouncementCamp extends AppCompatActivity {

    Spinner sp;
    EditText et1,et2,et3;
    Button b1;
    String start,end,campname,venuename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_template_announcement_camp);
        b1= (Button) findViewById(R.id.pick10);
        sp=(Spinner) findViewById(R.id.camp_value);
        et1 = (EditText) findViewById(R.id.selectstartdateforcamp);
        et2=(EditText) findViewById(R.id.selectenddateforcamp);
        et3=(EditText) findViewById(R.id.campvenue_value);


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                campname=sp.getSelectedItem().toString();
                start=et1.getText().toString();
                end=et2.getText().toString();
                venuename=et3.getText().toString();

                if(campname.equals("") || campname.equals(null))
                    Toast.makeText(getBaseContext(), R.string.campnametoast, Toast.LENGTH_LONG).show();
                else if (start.equals("") || start.equals(null))
                    Toast.makeText(getBaseContext(), R.string.startdatetoast, Toast.LENGTH_LONG).show();
                else if (end.equals("") || end.equals(null))
                    Toast.makeText(getBaseContext(), R.string.enddatetoast, Toast.LENGTH_LONG).show();
                else if (venuename.equals("") || venuename.equals(null))
                    Toast.makeText(getBaseContext(), R.string.venuenametoast, Toast.LENGTH_LONG).show();
                else
                {
                    Intent i = new Intent(getApplicationContext(),ContactOptions.class);
                    Bundle bundle = new Bundle();


                    bundle.putString("campname",campname);
                    bundle.putString("start",start);
                    bundle.putString("end",end);
                    bundle.putString("venuename",venuename);
                    bundle.putString("ActivityName","TemplateAnnouncementCamp");

                    //Add the bundle to the intent
                    i.putExtras(bundle);
                    startActivity(i);
                }


            }
        });
       /* Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


}
