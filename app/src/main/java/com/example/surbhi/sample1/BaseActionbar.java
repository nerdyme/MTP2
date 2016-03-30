package com.example.surbhi.sample1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class BaseActionbar extends ActionBarActivity {

    public static final String PREFS_NAME = "LoginPrefs";
    static final int PICK_CONTACT=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_actionbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbarmenu, menu);

        //SearchView searchView = (SearchView) menu.findItem(R.id.menu_action_search).getActionView();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.gramvaani)
        {
            String url = "http://www.gramvaani.org";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
            return true;
        }
        else if (id==R.id.view_gv_contacts)
        {
            //Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            //startActivityForResult(intent, PICK_CONTACT);
            return true;
        }
        else if(id==R.id.logout)
        {
            return true;
        }
        else if (id == R.id.open_windows_explorer)
        {
        	/*Uri selectedUri = Uri.parse(Environment.getExternalStorageDirectory() + "/AudioRecorder/");
        	Intent intent = new Intent(Intent.ACTION_VIEW);
        	intent.setDataAndType(selectedUri, "resource/folder");
        	startActivity(intent);*/
            return true;
        }
       else if (id == R.id.share_app)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
