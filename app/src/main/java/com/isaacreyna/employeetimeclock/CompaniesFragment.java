package com.isaacreyna.employeetimeclock;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.isaacreyna.employeetimeclock.interfaces.Service;
import com.isaacreyna.employeetimeclock.models.Alert;
import com.isaacreyna.employeetimeclock.models.Company.Companies;
import com.isaacreyna.employeetimeclock.models.Company.Company;
import com.isaacreyna.employeetimeclock.models.Company.Result;
import com.isaacreyna.employeetimeclock.models.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompaniesFragment extends Fragment {
    public User user;

    public CompaniesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        user = setUser();
        View view = inflater.inflate(R.layout.fragment_companies, container, false);
        ListCompanies(view);
        return view;
    }

    public User setUser()
    {
        SharedPreferences Settings;
        Settings = this.getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = Settings.getString("user", "");
        return gson.fromJson(json, User.class);
    }

    public void ListCompanies(final View view){
        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("token", user.token);
        fields.put("option", "companies");
        fields.put("task", "select");

        Service.Factory.getInstance().ListCompanies(fields).enqueue(new Callback<Companies>() {
            @Override
            public void onResponse(Call<Companies> call, Response<Companies> response) {
                if (response.body().result)
                    PopulateTable(view, response.body().companies);
                else
                    Log.i("companiesAPI", "No companies");
            }

            @Override
            public void onFailure(Call<Companies> call, Throwable t) {

            }
        });
    }

    public void PopulateTable(View view, List<Company> companies){
        TableLayout tableLayout = (TableLayout) view.findViewById(R.id.company_list);
        for(Company company : companies) {
            TextView textView = new TextView(getActivity());
            textView.setText(company.name);
            textView.setTextColor(Color.BLACK);
            textView.setTypeface(null, Typeface.BOLD);

            TableRow tableRow = new TableRow(getActivity());
            tableRow.addView(textView);

            tableLayout.addView(tableRow);
        }
    }
}
