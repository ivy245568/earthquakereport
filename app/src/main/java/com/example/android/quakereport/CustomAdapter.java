package com.example.android.quakereport;

import android.content.ClipData;
import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ivy on 8/2/18.
 */

public class CustomAdapter extends ArrayAdapter<QueryUtils.Earthquake> {
    private final String TAG = CustomAdapter.class.getName();
    private List<QueryUtils.Earthquake> earthquakes = new ArrayList<>();
    private Date dateOject;
    private SimpleDateFormat formatDate = new SimpleDateFormat("MMM DD, YYYY");
    private SimpleDateFormat formatTime = new SimpleDateFormat("h mm a");
    private DecimalFormat decimalFormat = new DecimalFormat("0.0");
    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<QueryUtils.Earthquake> objects) {
        super(context, resource, objects);
        earthquakes = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            view = layoutInflater.inflate(R.layout.earthquake_item, null);
        }
        if (getItem(position) != null) {
            QueryUtils.Earthquake earthquake = earthquakes.get(position);
            TextView level = (TextView) view.findViewById(R.id.level);
            TextView near = (TextView) view.findViewById(R.id.near);
            TextView location = (TextView) view.findViewById(R.id.location);
            TextView date = (TextView) view.findViewById(R.id.date);
            TextView time = (TextView) view.findViewById(R.id.time);

            level.setText(decimalFormat.format(earthquake.level));
            GradientDrawable levelCircleIcon = (GradientDrawable) level.getBackground();
            levelCircleIcon.setColor(selectLevelBackgroundColor(earthquake.level));
            String earthquakeLocation = earthquake.location;
            int ofIndex = earthquakeLocation.indexOf("of");
            if(ofIndex != -1) {
                //String[] result = earthquakeLocation.split("of");
                near.setText(earthquakeLocation.substring(0, ofIndex+2));
                location.setText(earthquakeLocation.substring(ofIndex+2, earthquakeLocation.length()-1));
            } else {
                location.setText(earthquake.location);
            }

            dateOject = new Date(earthquake.date);
            date.setText(formatDate.format(dateOject));
            time.setText(formatTime.format(dateOject));
        }
        return view;

    }

    private int selectLevelBackgroundColor(double magnitude) {
        int rgbColor;
        switch (((int)Math.floor(magnitude))) {
            case 1 :
                rgbColor = R.color.magnitude1;
                break;
            case 2 :
                rgbColor = R.color.magnitude2;
                break;
            case 3 :
                rgbColor = R.color.magnitude3;
                break;
            case 4 :
                rgbColor = R.color.magnitude4;
                break;
            case 5 :
                rgbColor = R.color.magnitude5;
                break;
            case 6 :
                rgbColor = R.color.magnitude6;
                break;
            case 7 :
                rgbColor = R.color.magnitude7;
                break;
            case 8 :
                rgbColor = R.color.magnitude8;
                break;
            case 9 :
                rgbColor = R.color.magnitude9;
                break;
            case 10 :
                rgbColor = R.color.magnitude10plus;
                break;
            default:
                rgbColor = R.color.magnitude1;
                Log.d(TAG, "invalid magnitude, default 1");
                break;
        }
        return ContextCompat.getColor(getContext(), rgbColor);
    }
}
