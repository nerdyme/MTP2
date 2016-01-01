package com.example.surbhi.sample1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class Phoneverify extends BaseActionbar {

    Button b1 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phoneverify);

        b1 = (Button) findViewById(R.id.pinsubmit);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                // TODO Auto-generated method stub
               finish();

            }
        });

    }



}
