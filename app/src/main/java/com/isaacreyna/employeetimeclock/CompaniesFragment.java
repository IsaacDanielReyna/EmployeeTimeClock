package com.isaacreyna.employeetimeclock;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.internal.Streams;
import com.isaacreyna.employeetimeclock.Adapters.CompanyListAdapter;
import com.isaacreyna.employeetimeclock.interfaces.Service;
import com.isaacreyna.employeetimeclock.models.Alert;
import com.isaacreyna.employeetimeclock.models.Company.Companies;
import com.isaacreyna.employeetimeclock.models.Company.Company;
import com.isaacreyna.employeetimeclock.models.Company.Result;
import com.isaacreyna.employeetimeclock.models.Invite;
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
        //showInvites(view);
        //inviteEmployee(view);
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

    public void showInvites(final View view){
        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("token", user.token);
        fields.put("option", "invites");
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

    public void inviteEmployee(final View view){
        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("token", user.token);
        fields.put("option", "employees");
        fields.put("task", "insert");
        fields.put("username", "idreyna");

        Service.Factory.getInstance().inviteEmployee(fields).enqueue(new Callback<Invite>() {
            @Override
            public void onResponse(Call<Invite> call, Response<Invite> response) {
                if (response.body().result)
                    Log.i("SYSTEMAPI", response.body().messages.toString());
                else
                    Log.i("SYSTEMAPI", response.body().messages.toString());
            }

            @Override
            public void onFailure(Call<Invite> call, Throwable t) {
                Log.i("SYSTEMAPI", "onFailure");
            }
        });
    }

    public void PopulateTable(View view, final List<Company> companies){
        CompanyListAdapter adapter = new CompanyListAdapter(getActivity(), companies);
        ListView listView = (ListView) getView().findViewById(R.id.company_list);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Company company = companies.get(position);
                Snackbar.make(view, "Switched to " + company.name, Snackbar.LENGTH_LONG).show();
                getActivity().setTitle(company.name);
                /**
                SharedPreferences Settings;
                Settings = getActivity().getSharedPreferences("Settings", Context.MODE_PRIVATE);
                Gson gson = new Gson();
                String json = Settings.getString("user", "");
                return gson.fromJson(json, User.class);
                /**/
                Log.i("API", company.id + " " + company.name);


            }
        });

    }
}
