package com.doan3.canthotour.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Model.Place;
import com.doan3.canthotour.R;

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
    public void onBindViewHolder(ViewHolder holder, int position) { //Mỗi 1 lần chạy hàm này tương ứng với load 1 item trong recycler view
        holder.txtTenDD.setText(place.get(position).getTenDD());
        holder.imgHinhDD.setImageResource(place.get(position).getHinhDD());
    }

    @Override
    public int getItemCount() {
        return place.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTenDD;
        ImageView imgHinhDD;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTenDD = (TextView) itemView.findViewById(R.id.txtTenDiaDiem);
            imgHinhDD = (ImageView) itemView.findViewById(R.id.imgHinhDiaDiem);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
