package com.example.surbhi.sample1;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Viewsurvey extends BaseActionbar {

    private ArrayList<HashMap<String, String>> list;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewsurvey);

        Bundle bundle = getIntent().getExtras();
        String form_id= bundle.getString("form_id");

        String view_survey_url=NetworkConfig.view_survey1+form_id;

        list=new ArrayList<HashMap<String,String>>();
        listView=(ListView)findViewById(R.id.formquestionlist);

        JsonObjectRequest request1 = new JsonObjectRequest(view_survey_url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ar1= (JSONArray) response.get("objects");
                            int len=ar1.length();

                            for(int i=0;i<len;++i)
                            {
                                JSONObject info = (JSONObject) ar1.get(i);


                                JSONObject js1= (JSONObject) info.get("question");
                                String s1= js1.get("text").toString();

                                HashMap<String,String> temp=new HashMap<String, String>();
                                temp.put(Constants.FIRST_COLUMN, String.valueOf(i+1));
                                temp.put(Constants.SECOND_COLUMN, s1);

                                list.add(temp);

                            }
                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        ViewsurveyAdapter adapter=new ViewsurveyAdapter(Viewsurvey.this, list);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
                        {
                            @Override
                            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
                            {
                                int pos=position+1;
                                //Toast.makeText(Viewsurvey.this, Integer.toString(pos) + " Clicked", Toast.LENGTH_SHORT).show();
                            }

                        });

                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        VolleyApplication.getInstance().getRequestQueue().add(request1);

    }




}
