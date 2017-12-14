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

import com.doan3.canthotour.Model.Favorite;
import com.doan3.canthotour.Model.NearLocation;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavoriteInfo;

import java.util.ArrayList;


public class NearLocationAdapter extends RecyclerView.Adapter<NearLocationAdapter.ViewHolder> {
    ArrayList<NearLocation> nearLocation;
    Context context;

    public NearLocationAdapter(ArrayList<NearLocation> nearLocation, Context context) {
        this.nearLocation = nearLocation;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //Khi gọi DiaDanhAdapter thì hàm này chạy đầu tiên
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_yeuthich, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) { //Mỗi 1 lần chạy hàm này tương ứng với load 1 item trong recycler view
        holder.txtTenDD.setText(nearLocation.get(position).getTenDiaDiemLC());
        holder.imgHinhDD.setImageResource(nearLocation.get(position).getHinhDiaDiemLC());
    }

    @Override
    public int getItemCount() {
        return nearLocation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTenDD;
        ImageView imgHinhDD;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTenDD = itemView.findViewById(R.id.textViewYeuThich);
            imgHinhDD = itemView.findViewById(R.id.imageViewYeuThich);
            cardView = itemView.findViewById(R.id.cardViewYeuThich);
        }
    }
}
