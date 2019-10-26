package com.example.dinereminder2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class MeetingAdapter extends ArrayAdapter<Meeting> {

    private Context mContext;
    private int mResource;

    public MeetingAdapter(Context context, int resource, ArrayList<Meeting> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the persons information
        String meetPurpose = getItem(position).getMeetPurpose();
        String date = getItem(position).getDate();
        String time = getItem(position).getTime();
        String place = getItem(position).getPlace();

        //Create the person object with the information
        Meeting  meeting = new Meeting(meetPurpose, date, time, place);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView tv_meetPurpose = (TextView) convertView.findViewById(R.id.textView1);
        TextView tv_date = (TextView) convertView.findViewById(R.id.textView2);
        TextView tv_time = (TextView) convertView.findViewById(R.id.textView3);
        TextView tv_place = (TextView) convertView.findViewById(R.id.textView4);


        tv_meetPurpose.setText(meetPurpose);
        tv_date.setText(date);
        tv_time.setText(time);
        tv_place.setText(place);

        return convertView;
    }

}
