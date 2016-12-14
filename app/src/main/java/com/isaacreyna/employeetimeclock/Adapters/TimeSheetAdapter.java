package com.isaacreyna.employeetimeclock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isaacreyna.employeetimeclock.R;
import com.isaacreyna.employeetimeclock.models.TimeClockSelect.Day;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by isaac on 12/13/2016.
 */

public class TimeSheetAdapter extends BaseAdapter {
    private Context context;
    List<Day> days;

    public TimeSheetAdapter(Context c, List<Day> days){
        context = c;
        this.days = days;
    }

    @Override
    public int getCount() {
        return days.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount(){
        return getCount();
    }

    @Override
    public int getItemViewType(int position){
        return position;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View list;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null){
            list = new View(context);
            list = inflater.inflate(R.layout.timesheet_day, null);

            Day day = days.get(i);


            TextView day_name = (TextView) list.findViewById(R.id.day);
            day_name.setText(day.name);

            TextView total = (TextView) list.findViewById(R.id.daily_total);
            total.setText(day.total + " hrs");

        } else {
            list = (View) view;
        }

        return list;
    }
}
