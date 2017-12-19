package com.doan3.canthotour.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.Favorite;
import com.doan3.canthotour.Model.NearLocation;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavoriteInfo;
import com.doan3.canthotour.View.Main.ActivityPlaceInfo;
import com.doan3.canthotour.View.Search.ActivityNearLocation;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class NearLocationAdapter extends RecyclerView.Adapter<NearLocationAdapter.ViewHolder> {
    ArrayList<NearLocation> nearLocation;
    Context context;
    ArrayList<String> arr = new ArrayList<>();

    public NearLocationAdapter(ArrayList<NearLocation> nearLocation, Context context) {
        this.nearLocation = nearLocation;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //Khi gọi DiaDanhAdapter thì hàm này chạy đầu tiên
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_lancan, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) { //Mỗi 1 lần chạy hàm này tương ứng với load 1 item trong recycler view
        holder.txtTenDD.setText(nearLocation.get(position).getTenDiaDiemLC());
        holder.txtKhoangCach.setText(nearLocation.get(position).getKhoangCachLC());
        holder.imgHinhDD.setImageResource(nearLocation.get(position).getHinhDiaDiemLC());

        try {
            arr = new GetId().execute(ActivityNearLocation.urlNearLocation).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
            @Override
            public void onClick(View view) {
                Intent iLocationInfo = new Intent(context, ActivityPlaceInfo.class);
                iLocationInfo.putExtra("masp", arr.get(position));
                iLocationInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(iLocationInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nearLocation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTenDD, txtKhoangCach;
        ImageView imgHinhDD;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTenDD = itemView.findViewById(R.id.textViewTenLanCan);
            txtKhoangCach = itemView.findViewById(R.id.textViewKhoangCach);
            imgHinhDD = itemView.findViewById(R.id.imageViewLanCan);
            cardView = itemView.findViewById(R.id.cardViewLanCan);
        }
    }

    private class GetId extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> array = new ArrayList<>(),arrayList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(HttpRequestAdapter.httpGet(strings[0]));
                array = JsonHelper.parseJson(jsonArray, Config.JSON_NEAR_LOCATION);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 0; i < array.size(); i+=5){
                arrayList.add(array.get(i));
            }
            return arrayList;
        }
    }
}
