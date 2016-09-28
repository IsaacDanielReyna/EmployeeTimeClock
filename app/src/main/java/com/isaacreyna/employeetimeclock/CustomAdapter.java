package com.isaacreyna.employeetimeclock;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by IDR on 6/11/2016.
 */
public class CustomAdapter extends ArrayAdapter<String> {

    Context c;
    String[] players;
    int[] images;
    LayoutInflater inflater;

    public CustomAdapter(Context context, String[] players, int[] images) {
        super(context, R.layout.rowmodel, players);

        this.c=context;
        this.players=players;
        this.images=images;
    }

    // This inner class shall hold our viewws
    public class ViewHolder
    {
        TextView    nameTv;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if (convertView==null)
        {
            inflater= (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.rowmodel, null);
        }
        ViewHolder holder = new ViewHolder();
        holder.nameTv = (TextView) convertView.findViewById(R.id.nameTv);
        holder.img= (ImageView) convertView.findViewById(R.id.rowImage);
        holder.nameTv.setText(players[position]);
        holder.img.setImageResource(images[position]);

        return convertView;
    }
}
