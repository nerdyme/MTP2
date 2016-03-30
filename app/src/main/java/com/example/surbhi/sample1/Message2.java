package com.example.surbhi.sample1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Message2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message2);

        Button b1 = (Button) findViewById(R.id.announcementsurvey);
        Button b2 = (Button) findViewById(R.id.announcementgovtscheme);
        Button b3 = (Button) findViewById(R.id.announcementcamp);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),TemplateAnnouncementSurvey.class);
                startActivity(i);
            }
        });


        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),TemplateAnnouncementGovtscheme.class);
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),TemplateAnnouncementCamp.class);
                startActivity(i);

            }
        });
    }

    }



