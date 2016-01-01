package com.example.surbhi.sample1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.surbhi.sample1.BaseActionbar;
import com.example.surbhi.sample1.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Createcontact extends BaseActionbar {

    Button submit = null;
    Button cancel =null;
    EditText name = null;
    EditText age =null;
    EditText phone =null;
    Spinner gender = null;
    Spinner dis= null;
    Spinner state = null;
    Spinner city = null;
    String nv=null,pv=null,av=null,gv=null,dv=null,sv=null,cv=null;

    String tok=MainActivity.token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createcontact);

        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                finish();
            }
        });

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub


                //Put validations here

                name	= (EditText)findViewById(R.id.name_value);
                age = (EditText)findViewById(R.id.age_value);
                phone = (EditText) findViewById(R.id.phone_value);
                gender = (Spinner) findViewById(R.id.gender_value);
                dis  = (Spinner) findViewById(R.id.district_value);
                city = (Spinner) findViewById(R.id.city_value);
                state = (Spinner) findViewById(R.id.state_value);


                nv = name.getText().toString();
                av = age.getText().toString();
                pv = phone.getText().toString();

                gv = gender.getSelectedItem().toString();
                dv= dis.getSelectedItem().toString();
                cv = city.getSelectedItem().toString();
                sv = state.getSelectedItem().toString();
		
		/*gender.setOnItemSelectedListener(new OnItemSelectedListener(AdapterView<?> parent, View view, int pos, long id) 
		{
			gv = parent.getItemAtPosition(pos).toString();
		});*/

                int set=0;
                Log.w("send","Call to async task");

                if(nv.equals("") ||nv.equals(null))
                {
                    Toast.makeText(getBaseContext(), "Name can't be empty", Toast.LENGTH_LONG).show();
                }
                else if(nv.length() < 5)
                {
                    Toast.makeText(getBaseContext(), "Name must have atleast 5 characters", Toast.LENGTH_LONG).show();
                }
                else if (pv.equals("") || pv.equals(null))
                {
                    Toast.makeText(getBaseContext(), "Phone Number can't be empty", Toast.LENGTH_LONG).show();

                }
                else if(pv.contains("[0-9]+") == false && pv.length() !=10)
                {
                    Toast.makeText(getBaseContext(), "Enter valid phone number", Toast.LENGTH_LONG).show();

                }
                else if (Integer.parseInt(av) <0 || Integer.parseInt(av) > 150)
                {
                    Toast.makeText(getBaseContext(), "Enter valid age(0-150)", Toast.LENGTH_LONG).show();
                }
                else 	//if(pv.contains("[0-9]+") == true && pv.length() ==10)
                {

                    Log.w("send","Call to async task2");
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(Createcontact.this);
                    builder1.setMessage("Press Confirm to send, Press edit to change");
                    builder1.setCancelable(false);
                    builder1.setTitle("Add Contact Details");
                    builder1.setPositiveButton("Confirm",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new MyAsyncTask().execute(nv,gv,pv,av,dv,cv,sv);
                                    dialog.cancel();
                                }
                            });
                    builder1.setNegativeButton("Edit",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }
            }
        });
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String res=	postData(params[0],params[1],params[2],params[3],params[4],params[5],params[6]);
            return res;
        }

        protected void onPostExecute(String str){

            String token=null,message=null;
            int status = -1;
            if (str=="error")
            {
                Log.d("Working","------------");
                Toast.makeText(getApplicationContext(), "Check Internet. Connection Error", Toast.LENGTH_LONG).show();
            }
            else if(str!=null)
            {
						/*	JSONObject jObject=null;
							try {
								jObject = new JSONObject(str);
								
								
								 message = jObject.getString("message");
								//status = jObject.getInt("status");
								//String h=String.valueOf(status);
								//Log.d("+++++++++++++++++++++++status+++++",h);
								} 
							catch (JSONException e) 
									{
									e.printStackTrace();
									Toast.makeText(getApplicationContext(), "Error at Server", Toast.LENGTH_LONG).show();
									}
							catch (Exception e)
							{
								e.printStackTrace();
								Log.w("error",e.getMessage());
							}
							 
								if(message.equalsIgnoreCase("user created succesfully"))
								{
								Toast.makeText(getApplicationContext(), "Contact saved successfully", Toast.LENGTH_LONG).show();
								finish();
								}
								
								else 
								{
									Toast.makeText(getApplicationContext(), "Unable to save contact, Try again", Toast.LENGTH_LONG).show();
								}*/

                Toast.makeText(getApplicationContext(), "Contact saved successfully", Toast.LENGTH_LONG).show();
                finish();

            }
            else
            {
                Log.d("Working","------------");
                Toast.makeText(getApplicationContext(), "Check Internet. Connection Error", Toast.LENGTH_LONG).show();
            }

        }
        protected void onProgressUpdate(Integer... progress){

        }

        public String postData(String nv, String gv, String pv, String av, String dv, String cv, String sv ) {
            // Create a new HttpClient and Post Header
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(MainActivity.connection_url + "user");


            String str=null;
            try {

                String json = "";
                String json1 = "";
                JSONObject jsonObject = new JSONObject();
                JSONObject jsonObject1 = new JSONObject();
                jsonObject.put("name", nv);
                jsonObject.put("gender", gv);
                jsonObject.put("contact", pv);
                jsonObject.put("age", av);
                jsonObject.put("district", dv);
                jsonObject.put("city", cv);
                jsonObject.put("state", sv);


                json = jsonObject.toString();

                //List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                //nameValuePairs.add(new BasicNameValuePair("user",json));
                //nameValuePairs.add(new BasicNameValuePair("authenticity_token",MainActivity.token));
                Log.d("Working","4");
                //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                jsonObject1.put("user", json);
                jsonObject1.put("authenticity_token",MainActivity.token);

                json1 = jsonObject1.toString();

                StringEntity se = new StringEntity(json1);
                httppost.setEntity(se);



                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");


                HttpResponse response= httpclient.execute(httppost);

                int status =response.getStatusLine().getStatusCode();

                if(status == 200)
                {
                    str = inputStreamToString(response.getEntity().getContent()).toString();
                    Log.w("SENCIDE", str);
                    return str;
                }
                else
                {
                    return "error";
                }

            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                Log.w("error",e.getMessage());

            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.w("error",e.getMessage());

            }
            catch (Exception e){
                Log.w("error",e.getMessage());
            }

            return str;
        }

    }

    private StringBuilder inputStreamToString(InputStream is) {
        String line = "";
        StringBuilder total = new StringBuilder();
        // Wrap a BufferedReader around the InputStream
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        // Read response until the end
        try {
            while ((line = rd.readLine()) != null) {
                total.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return full string
        return total;
    }
}

