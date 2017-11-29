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
import android.widget.Toast;

import com.doan3.canthotour.Model.Place;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityPlaceInfo;

import java.util.ArrayList;


public class ListOfPlaceAdapter extends RecyclerView.Adapter<ListOfPlaceAdapter.ViewHolder> {
    ArrayList<Place> place;
    Context context;

    public ListOfPlaceAdapter(ArrayList<Place> place, Context context) {
        this.place = place;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //Khi gọi DiaDanhAdapter thì hàm này chạy đầu tiên
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_diadiem_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) { //Mỗi 1 lần chạy hàm này tương ứng với load 1 item trong recycler view
        holder.txtTenDD.setText(place.get(position).getTenDD());
        holder.imgHinhDD.setImageResource(place.get(position).getHinhDD());
        holder.txtDiaChiDD.setText(place.get(position).getMoTaDD());

        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
            @Override
            public void onClick(View view) {
                Intent iPlaceInfo = new Intent(context, ActivityPlaceInfo.class);
                iPlaceInfo.putExtra("masp", position+1+"");
                iPlaceInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(iPlaceInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return place.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTenDD, txtDiaChiDD;
        ImageView imgHinhDD;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTenDD = itemView.findViewById(R.id.txtTenDiaDiem);
            imgHinhDD = itemView.findViewById(R.id.imgHinhDiaDiem);
            txtDiaChiDD = itemView.findViewById(R.id.txtDiaChiDD);
            cardView = (CardView) itemView.findViewById(R.id.cardViewDiaDiem);

        }
    }
}
