package com.doan3.canthotour.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.doan3.canthotour.Model.ObjectClass.Geolocation;
import com.doan3.canthotour.R;

import java.util.ArrayList;

/**
 * Created by zzacn on 4/19/2018.
 */

public class GeolocationAdapter extends BaseAdapter {

    Context context;
    int myLayout;
    ArrayList<Geolocation> geolocations;

    public GeolocationAdapter(Context context, int myLayout, ArrayList<Geolocation> arrayList) {
        this.context = context;
        this.myLayout = myLayout;
        this.geolocations = arrayList;
    }

    @Override
    public int getCount() {
        return geolocations.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) { //Chỗ này giống với onBindViewHolder của các Adapter khác

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(myLayout, null);

        TextView txtName = view.findViewById(R.id.textViewSpinnerItem);

        Geolocation geolocation = geolocations.get(position);
        txtName.setText(geolocation.getName());

        return view;
    }
}
