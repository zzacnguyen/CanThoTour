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

import com.doan3.canthotour.Model.ObjectClass.Hotel;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityHotelInfo;

import java.util.ArrayList;

/**
 * Created by zzacn on 11/21/2017.
 */

public class HotelAdapter extends RecyclerView.Adapter<HotelAdapter.ViewHolder> {
    ArrayList<Hotel> hotels;
    Context context;
    ArrayList<String> arr = new ArrayList<>();

    public HotelAdapter(ArrayList<Hotel> hotel, Context context) {
        this.hotels = hotel;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //Khi gọi DiaDanhAdapter thì hàm này chạy đầu tiên
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_trangchu_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) { //Mỗi 1 lần chạy hàm này tương ứng với load 1 item trong recycler view
        Hotel hotel = hotels.get(position);
        holder.txtTen.setText(hotel.getTenKS());
        holder.imgHinh.setImageResource(hotel.getHinhKS());
        holder.cardView.setTag(hotel.getMaKS());

        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
            @Override
            public void onClick(View view) {
                Intent iHotelInfo = new Intent(context, ActivityHotelInfo.class);
                iHotelInfo.putExtra("masp", (int) view.getTag());
                iHotelInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(iHotelInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return hotels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTen;
        ImageView imgHinh;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTen = itemView.findViewById(R.id.txtTen);
            imgHinh = itemView.findViewById(R.id.imgHinh);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
