package com.example.surbhi.sample1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


public class Pinverify extends BaseActionbar {

    Button b1=null;
    EditText e1 = null;
    TextView tv1 = null;
    String pinno = "";

    void maintainlogin()
    {

        e1 = (EditText) findViewById(R.id.pinvalue);

        b1 = (Button) findViewById(R.id.pinsubmit);
        tv1 = (TextView) findViewById(R.id.forgetpin);

        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

               pinno= e1.getText().toString();


                if(pinno.equals("") ||pinno.equals(null))
                {
                    Toast.makeText(getBaseContext(), "PIN Number can't be empty", Toast.LENGTH_LONG).show();
                    e1.requestFocus(0);
                }


                else if (pinno.length()!=4)
                {
                    Toast.makeText(getBaseContext(), "Enter valid PIN Number", Toast.LENGTH_LONG).show();
                }
                else
                {

                   Intent menu = new Intent(getApplicationContext(),MenuOptions.class);
                    startActivity(menu);

                }
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent phone = new Intent(getApplicationContext(),Phoneverify.class);
                startActivity(phone);
            }
        });

    }

  /* public void forgetpin(View v)
    {
        Intent phone = new Intent(getApplicationContext(),Phoneverify.class);
        startActivity(phone);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinverify);
        maintainlogin();
    }



}
