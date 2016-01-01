package com.example.surbhi.sample1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Phoneverify extends BaseActionbar {

    Button b1 = null;
    EditText e1 =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverify);

        e1 = (EditText) findViewById(R.id.phonevalue1);
        b1 = (Button) findViewById(R.id.phonesubmit);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // TODO Auto-generated method stub

                b1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

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
                            Toast.makeText(getBaseContext(), "You will receive pin shortly", Toast.LENGTH_LONG).show();
                        }
                    }
                });

               finish();

            }
        });

    }



}
