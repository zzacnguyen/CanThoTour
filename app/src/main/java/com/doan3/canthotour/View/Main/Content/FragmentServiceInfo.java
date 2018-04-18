package com.doan3.canthotour.View.Main.Content;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doan3.canthotour.R;

/**
 * Created by zzacn on 4/18/2018.
 */

public class FragmentServiceInfo extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_serviceinfo, container, false);

        return view;
    }
}
