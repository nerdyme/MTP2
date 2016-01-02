package com.example.surbhi.sample1;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class Surveyoptions extends BaseActionbar {

    Button viewsurvey;
    Button launchsurvey;
    HashMap<String, String> map;
    String form_id="";
    int gp;

    private ArrayList<HashMap<String, String>> list;
    ExpandableListView exlistView;
    ProgressBar pb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surveyoptions);

        exlistView=(ExpandableListView)findViewById(R.id.exlistview1);

        list=new ArrayList<HashMap<String,String>>();
        pb=(ProgressBar)findViewById(R.id.progressBar1);
        pb.setVisibility(View.VISIBLE);

        JsonObjectRequest request1 = new JsonObjectRequest(NetworkConfig.fetch_survey1, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ar1= (JSONArray) response.get("objects");
                            int len=ar1.length();

                            for(int i=0;i<len;++i)
                            {
                                JSONObject info = (JSONObject) ar1.get(i);

                                String s1 = info.get("id").toString();
                                String s2 = info.get("name").toString();
                                JSONObject js1= (JSONObject) info.get("form");
                                String s3= js1.get("id").toString();

                                HashMap<String,String> temp=new HashMap<String, String>();
                                temp.put(Constants.FIRST_COLUMN, s1);
                                temp.put(Constants.SECOND_COLUMN, s2);
                                temp.put(Constants.THIRD_COLUMN, s3);
                                list.add(temp);

                            }
                            pb.setVisibility(View.GONE);
                        }

                        catch (JSONException e) {
                            e.printStackTrace();
                        }

                        SurveyAdapter adapter=new SurveyAdapter(Surveyoptions.this, list);
                        exlistView.setAdapter(adapter);
                       // exlistView.setEmptyView(View);

                        // Listview Group click listener

                    }
                },

                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        VolleyApplication.getInstance().getRequestQueue().add(request1);

        exlistView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        exlistView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        list.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        exlistView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        list.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        exlistView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                // TODO Auto-generated method stub
                return false;
            }
        });
    }



}
