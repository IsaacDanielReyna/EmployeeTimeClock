package com.isaacreyna.employeetimeclock;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.isaacreyna.employeetimeclock.Adapters.TimeSheetAdapter;
import com.isaacreyna.employeetimeclock.interfaces.Service;
import com.isaacreyna.employeetimeclock.models.TimeClockSelect.TimeClockSelect;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class TimeSheetsFragment extends Fragment {
    private SharedPreferences Settings;

    public TimeSheetsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        String company = getArguments().getString("company");
        String token = getArguments().getString("token");
        Request(company, token);

        return inflater.inflate(R.layout.fragment_time_sheets, container, false);
    }

    public void Request(String company, String token){
        HashMap<String, String> fields = new HashMap<>();
        fields.put("option", "timeclock");
        fields.put("task", "select");
        fields.put("token", token);
        fields.put("company", company);
        fields.put("date_start", "2016-12-01");

        Service.Factory.getInstance().TimeClockSelect(fields).enqueue(new Callback<TimeClockSelect>() {
            @Override
            public void onResponse(Call<TimeClockSelect> call, Response<TimeClockSelect> response) {
                if(response.body().result){
                    Log.i("API", "Size: " + response.body().days.size());
                    setup_timesheet(response.body());
                }
            }

            @Override
            public void onFailure(Call<TimeClockSelect> call, Throwable t) {
                Log.i("API", "onFailure: TimeSheetsFragment.java");
            }
        });
        Log.i("API", "Default company id: " + company + ", and token: " + token);
    }

    private void setup_timesheet(TimeClockSelect body) {
        /**/
        TimeSheetAdapter adapter = new TimeSheetAdapter(getActivity(), body.days);
        ListView listView = (ListView) getView().findViewById(R.id.timesheet_list);
        listView.setAdapter(adapter);

        TextView timesheet_date = (TextView) getView().findViewById(R.id.timesheet_date);
        timesheet_date.setText("Week of " + body.date + " - Total Hours: " + body.grandTotal);
        /**/
    }

}
