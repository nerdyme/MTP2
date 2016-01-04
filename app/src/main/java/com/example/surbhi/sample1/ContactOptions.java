package com.example.surbhi.sample1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class ContactOptions extends BaseActionbar {

    Button b1,b2,b3;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_options);


       bundle=getIntent().getExtras();

        b1 = (Button) findViewById(R.id.b1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),ContactOptions1.class);
                i.putExtras(bundle);
                startActivity(i);

            }
        });

        b2 = (Button) findViewById(R.id.b2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),ContactOptions2.class);
                i.putExtras(bundle);
                startActivity(i);

            }
        });

        b3 = (Button) findViewById(R.id.b3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),ContactOptions.class);
                i.putExtras(bundle);
                startActivity(i);

            }
        });



    }



}
