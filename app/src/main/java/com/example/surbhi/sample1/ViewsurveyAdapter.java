package com.example.surbhi.sample1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by surbhi on 12/29/15.
 */
public class ViewsurveyAdapter extends BaseAdapter {
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    ImageView img;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;



    public ViewsurveyAdapter(Activity activity, ArrayList<HashMap<String, String>> list)
    {
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.viewsurveyadapter, null);
            txtFirst=(TextView) convertView.findViewById(R.id.formid);
            txtSecond=(TextView) convertView.findViewById(R.id.formquestion);
               }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(Constants.FIRST_COLUMN));
        txtSecond.setText(map.get(Constants.SECOND_COLUMN));
        return convertView;
    }

}
