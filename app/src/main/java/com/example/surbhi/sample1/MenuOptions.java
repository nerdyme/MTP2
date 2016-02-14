package com.example.surbhi.sample1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


public class MenuOptions extends BaseActionbar {


    //private Context context = null;
    LayoutInflater inflater = null;

    public Button survey=null;
    public Button audio=null;
    public Button message=null;
    public Button contact=null;
    public Button contact_group=null;

	/*@Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
       if(keyCode==KeyEvent.KEYCODE_BACK)
        {
    	   Intent..setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //this.startActivity(new Intent(Menubar.this,Menubar.class));
        }
        return true;
    }  */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_options);
        addListenerOnButton();
    }


    public void addListenerOnButton() {

        audio = (Button) findViewById(R.id.audio);
        audio.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), Recording1.class);

                startActivity(i);

            }
        });

        message = (Button) findViewById(R.id.message);
        message.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view)
            {

                Intent send_msg = new Intent(getApplicationContext(), Message1.class);
                startActivity(send_msg);
	    }
                });


        contact = (Button) findViewById(R.id.contact);
        contact.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),Createcontact1.class);
                startActivity(i);

            }
        });

        survey = (Button) findViewById(R.id.survey);
        survey.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v)
            {
                // TODO Auto-generated method stub
                Intent i = new Intent(getApplicationContext(),Surveyoptions.class);
                startActivity(i);

            }
        });

        contact_group = (Button) findViewById(R.id.createnewgroup);
        contact_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Creategroup1.class);
                startActivity(i);
            }
        });
    }
}
