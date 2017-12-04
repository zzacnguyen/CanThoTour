package com.doan3.canthotour.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.doan3.canthotour.Model.Entertainment;
import com.doan3.canthotour.R;

import java.util.ArrayList;


public class ListOfEntertainmentAdapter extends RecyclerView.Adapter<ListOfEntertainmentAdapter.ViewHolder> {
    ArrayList<Entertainment> entertain;
    Context context;

    public ListOfEntertainmentAdapter(ArrayList<Entertainment> entertain, Context context) {
        this.entertain = entertain;
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
        holder.txtTenDD.setText(entertain.get(position).getTenVC());
        holder.imgHinhDD.setImageResource(entertain.get(position).getHinhVC());
    }

    @Override
    public int getItemCount() {
        return entertain.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTenDD;
        ImageView imgHinhDD;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTenDD = itemView.findViewById(R.id.txtTenDiaDiem);
            imgHinhDD = itemView.findViewById(R.id.imgHinhDiaDiem);
            cardView = itemView.findViewById(R.id.cardViewDiaDiem);
        }
    }
}
