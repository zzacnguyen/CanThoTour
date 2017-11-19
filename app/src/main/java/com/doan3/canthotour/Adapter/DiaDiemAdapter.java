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

import com.doan3.canthotour.Model.DiaDiem;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.TrangChu.ActivityChiTietDiaDiem;

import java.util.ArrayList;

/**
 * Created by zzacn on 11/17/2017.
 */

public class DiaDiemAdapter extends RecyclerView.Adapter<DiaDiemAdapter.ViewHolder> {
    ArrayList<DiaDiem> diaDiems;
    Context context;

    public DiaDiemAdapter(ArrayList<DiaDiem> diaDiems, Context context) {
        this.diaDiems = diaDiems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.trangchu_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.txtTenDD.setText(diaDiems.get(position).getTenDD());
        holder.imgHinhDD.setImageResource(diaDiems.get(position).getHinhDD());

//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent_chitietdiadiem = new Intent(context, ActivityChiTietDiaDiem.class);
//                context.startActivity(intent_chitietdiadiem);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return diaDiems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTenDD;
        ImageView imgHinhDD;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTenDD = (TextView) itemView.findViewById(R.id.txtTenDD);
            imgHinhDD = (ImageView) itemView.findViewById(R.id.imgHinhDD);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
