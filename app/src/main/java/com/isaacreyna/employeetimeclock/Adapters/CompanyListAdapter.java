package com.isaacreyna.employeetimeclock.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.isaacreyna.employeetimeclock.R;
import com.isaacreyna.employeetimeclock.models.Company.Company;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by isaac on 12/13/2016.
 */

public class CompanyListAdapter extends BaseAdapter {
    private Context context;
    List<Company> companies;

    public CompanyListAdapter(Context c, List<Company> companies){
        context = c;
        this.companies = companies;
    }

    @Override
    public int getCount() {
        return companies.size();
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

        if (view == null) {
            list = new View(context);
            list = inflater.inflate(R.layout.list_single_item__company, null);
            TextView textView = (TextView) list.findViewById(R.id.list_item_company_title);
            textView.setText(companies.get(i).name);
        } else {
            list = (View) view;
        }
        return list;
    }
}
