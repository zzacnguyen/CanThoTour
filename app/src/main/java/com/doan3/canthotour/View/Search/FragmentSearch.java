package com.doan3.canthotour.View.Search;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.doan3.canthotour.Adapter.HttpRequestAdapter;
import com.doan3.canthotour.Adapter.ListOfServiceAdapter;
import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ModelService;
import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.doan3.canthotour.View.Main.MainActivity.fragment;
import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by zzacn on 4/18/2018.
 */

public class FragmentSearch extends Fragment {

    ArrayList<String> finalArr = new ArrayList<>();
    EditText etSearch;
    Button btnCancel;
    TextView txtSearchHistory;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        btnCancel = view.findViewById(R.id.btnCancel);
        txtSearchHistory = view.findViewById(R.id.textViewSearchHistory);

        etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if ((keyEvent.getAction() == KeyEvent.ACTION_DOWN) &&
                        (i == KeyEvent.KEYCODE_ENTER)) {
                    if (!etSearch.getText().toString().equals("")) {
                        load(Config.URL_HOST + Config.URL_SEARCH_ALL +
                                etSearch.getText().toString().replaceAll(" ", "\\+"));
                    } else {
                        Toast.makeText(getApplicationContext(), "Chưa nhập", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getFragmentManager().beginTransaction().remove(fragment).commit();
            }
        });

        return view;
    }

    private void load(String url) {

        final ListOfServiceAdapter listOfServiceAdapter;
        final RecyclerView recyclerView;
        recyclerView = getView().findViewById(R.id.RecyclerView_SearchList);
        recyclerView.setHasFixedSize(true); //Tối ưu hóa dữ liệu, k bị ảnh hưởng bởi nội dung trong adapter

        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        ArrayList<Service> services = new ModelService().getFavoriteList(new File(""), url);

        listOfServiceAdapter = new ListOfServiceAdapter(recyclerView, services, getApplicationContext());
        recyclerView.setAdapter(listOfServiceAdapter);
        listOfServiceAdapter.notifyDataSetChanged();

        final ArrayList<Service> finalListService = services;
        try {
            finalArr = JsonHelper.parseJsonNoId(new JSONObject
                    (new HttpRequestAdapter.httpGet().execute(url).get()), Config.GET_KEY_JSON_LOAD);
        } catch (JSONException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        //set load more listener for the RecyclerView adapter
        listOfServiceAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void onLoadMore() {
                if (finalListService.size() < Integer.parseInt(finalArr.get(2))) {
                    finalListService.add(null);
                    recyclerView.post(new Runnable() {
                        public void run() {
                            listOfServiceAdapter.notifyItemInserted(finalListService.size() - 1);
                        }
                    });
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            finalListService.remove(finalListService.size() - 1);
                            listOfServiceAdapter.notifyItemRemoved(finalListService.size());

                            ArrayList<Service> serviceArrayList = new ModelService().
                                    getFavoriteList(new File(""), finalArr.get(1));
                            for (int i = 0; i < serviceArrayList.size(); i++) {
                                finalListService.add(serviceArrayList.get(i));
                            }
                            try {
                                finalArr = JsonHelper.parseJsonNoId(new JSONObject
                                        (new HttpRequestAdapter.httpGet().execute(finalArr.get(1)).get()), Config.GET_KEY_JSON_LOAD);
                            } catch (JSONException | InterruptedException | ExecutionException e) {
                                e.printStackTrace();
                            }

                            listOfServiceAdapter.notifyDataSetChanged();
                            listOfServiceAdapter.setLoaded();
                        }
                    }, 1000);
                }
            }
        });
    }
}
