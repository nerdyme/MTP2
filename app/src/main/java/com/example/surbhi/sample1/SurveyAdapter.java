package com.example.surbhi.sample1;


/**
 * Created by surbhi on 12/28/15.
 */

      /*  import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.ImageView;
        import android.widget.TextView;



public class SurveyAdapter extends ArrayAdapter<String> {
    private final Context context;
    private final String[] values;

    public SurveyAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.surveylistgroup, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.label);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        textView.setText(values[position]);
        // change the icon for Windows and iPhone
        String s = values[position];

            imageView.setImageResource(R.drawable.ic_launcher);


        return rowView;
    }
}  */


/*import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SurveyAdapter extends BaseExpandableListAdapter {

    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    ImageView img;
    TextView txtFirst;
    TextView txtSecond;
    TextView txtThird;



    public SurveyAdapter(Activity activity,ArrayList<HashMap<String, String>> list)
    {
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.surveylistgroup, null);

            img = (ImageView) convertView.findViewById(R.id.icon);
            txtFirst=(TextView) convertView.findViewById(R.id.surveyid);
            txtSecond=(TextView) convertView.findViewById(R.id.surveyname);
            txtThird=(TextView) convertView.findViewById(R.id.select);


        }

        HashMap<String, String> map=list.get(position);

        img.setImageResource(R.drawable.ic_launcher);   // Apply condition to change image on selection
        txtFirst.setText(map.get(Constants.FIRST_COLUMN));
        txtSecond.setText(map.get(Constants.SECOND_COLUMN));
        txtThird.setText(map.get(Constants.THIRD_COLUMN));

        return convertView;
    }

}
*/


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class SurveyAdapter extends BaseExpandableListAdapter {

    public Context _context;
    public ArrayList<HashMap<String, String>> list;
    Activity activity;
    ImageView img;
    TextView txtFirst;
    TextView txtSecond;
    Button viewsurvey;
    Button launchsurvey;
    int gp;

    String form_id="";
    HashMap<String, String> map;

    public SurveyAdapter(Activity activity,ArrayList<HashMap<String, String>> list)
    {
        super();
        this.activity=activity;
        this._context=activity;
        this.list=list;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        //return this.list.get(this.list.get(groupPosition)).get(childPosititon);
        return this.list.get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        //final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.surveysublist, null);

            }



        return convertView;
    }

   @Override
    public int getChildrenCount(int groupPosition) {
        //return this._listDataChild.get(this._listDataHeader.get(groupPosition)).size();

       return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.list.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.list.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        //String headerTitle = (String) getGroup(groupPosition);

        if (convertView == null) {

            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.surveylistgroup, null);
            img = (ImageView) convertView.findViewById(R.id.icon);
            txtFirst=(TextView) convertView.findViewById(R.id.surveyid);
            txtSecond=(TextView) convertView.findViewById(R.id.surveyname);
        }

        HashMap<String, String> map=list.get(groupPosition);

        img.setImageResource(R.drawable.ic_launcher);   // Apply condition to change image on selection
        txtFirst.setText(map.get(Constants.FIRST_COLUMN));
        txtSecond.setText(map.get(Constants.SECOND_COLUMN));

        /*TextView lblListHeader = (TextView) convertView.findViewById(R.id.surveyname);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);*/

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}