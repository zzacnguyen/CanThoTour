package com.doan3.canthotour.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.Model.Hotel;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityHotelInfo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by zzacn on 11/21/2017.
 */

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    ArrayList<Hotel> hotel;
    Context context;
    ArrayList<String> arr = new ArrayList<>();

    public HotelAdapter(ArrayList<Hotel> hotel, Context context) {
        this.hotel = hotel;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //Khi gọi DiaDanhAdapter thì hàm này chạy đầu tiên
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_trangchu_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) { //Mỗi 1 lần chạy hàm này tương ứng với load 1 item trong recycler view
        holder.txtTenDD.setText(hotel.get(position).getTenKS());
        holder.imgHinhDD.setImageResource(hotel.get(position).getHinhKS());

        try {
            arr = new EatAdapter.GetId().execute(Config.URL_HOST + Config.URL_GET_ALL_ID_HOTEL).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
            @Override
            public void onClick(View view) {
                Intent iHotelInfo = new Intent(context, ActivityHotelInfo.class);
                iHotelInfo.putExtra("masp", arr.get(position));
                iHotelInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(iHotelInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotel.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTenDD;
        ImageView imgHinhDD;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTenDD = itemView.findViewById(R.id.txtTenDD);
            imgHinhDD = itemView.findViewById(R.id.imgHinhDD);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
