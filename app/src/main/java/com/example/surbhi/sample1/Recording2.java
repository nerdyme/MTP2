package com.example.surbhi.sample1;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Recording2 extends BaseActionbar {

    Button b1=null,b2=null,b3=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording2);
        final String path= getIntent().getStringExtra("filename");

        b1= (Button)findViewById(R.id.savesend);
        b1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(),ContactOptions.class);
                i.putExtra("path", path);
                startActivity(i);
                //new MyAsyncTask().execute(path);

            }
        });


        b2=(Button)findViewById(R.id.save);
        b2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "File is successfully saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        });





        b3=(Button)findViewById(R.id.discard);
        b3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                File file =new File(path);
                try {
                    boolean b =file.delete();
                    if(b)
                    {
                        Toast.makeText(getApplicationContext(), "File is successfully discarded", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "Unable to discard file", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), "Unable to locate file", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


    private class MyAsyncTask extends AsyncTask<String, Integer, String>{

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            String res=	doFileUpload(params[0]);
            return res;
        }

        protected void onPostExecute(String str){
            //pb.setVisibility(View.GONE);

            if (str != null) {

                if ( str.equalsIgnoreCase( "success"))
                {
                    Toast.makeText(getApplicationContext(), "Upload Successful", Toast.LENGTH_LONG).show();
                    finish();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Error in uploading", Toast.LENGTH_LONG).show();
                }
            }

            else
            {
                Toast.makeText(getApplicationContext(), "Connection Error", Toast.LENGTH_LONG).show();
            }
        }
        protected void onProgressUpdate(Integer... progress){
            //pb.setProgress(progress[0]);
        }


        private String doFileUpload(String filename)

        {

            HttpURLConnection conn = null;
            DataOutputStream dos = null;
            DataInputStream inStream = null;
            String existingFileName = filename;
            String lineEnd = "\r\n";
            String twoHyphens = "--";
            String boundary = "*****";

            int bytesRead, bytesAvailable, bufferSize;

            byte[] buffer;

            int maxBufferSize = 1 * 1024 * 1024;
            String responseFromServer = "";
            String urlString = NetworkConfig.connection_url + "recording";

            try {

                //------------------ CLIENT REQUEST
                File f = new File(existingFileName);
                String fname= f.getName();
                FileInputStream fileInputStream = new FileInputStream(f);
                Log.d("open","File is opened");
                // open a URL connection to the Servlet
                URL url = new URL(urlString);
                // Open a HTTP connection to the URL
                conn = (HttpURLConnection) url.openConnection();
                Log.d("yay","able to open  connection1");
                // Allow Inputs
                conn.setDoInput(true);
                // Allow Outputs
                conn.setDoOutput(true);
                // Don't use a cached copy.
                conn.setUseCaches(false);

                Log.d("yay","able to open  connection2");
                // Use a post method.
                conn.setRequestMethod("POST");
                Log.d("yay","able to open  connection3");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                Log.d("yay","able to open  connection4");
                dos = new DataOutputStream(conn.getOutputStream());
                Log.d("yay","able to open  connection5");
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + fname + "\"" + lineEnd);
                dos.writeBytes(lineEnd);
                Log.d("yay","able to open  connection6");
                // create a buffer of maximum size
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                Log.d("yay","able to open  connection7");
                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                    Log.d("yay","able to open  connection8");
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // close streams
                Log.e("Debug", "File is written");
                fileInputStream.close();
                dos.flush();
                dos.close();
                Log.d("yay","able to open  connection9");

            } catch (MalformedURLException ex) {
                Log.e("Debug", "error: " + ex.getMessage(), ex);
            } catch (IOException ioe) {
                Log.e("Debug", "error: " + ioe.getMessage(), ioe);
            }

            //------------------ read the SERVER RESPONSE

            try {
                if(conn.getResponseCode()==201 || conn.getResponseCode()==200)
                {
                    return "success";

                }
                else
                {
                    return "failure";
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block

                e.printStackTrace();
                return "failure";
            }

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
