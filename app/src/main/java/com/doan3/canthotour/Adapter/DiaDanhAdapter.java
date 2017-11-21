package com.doan3.canthotour.Adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Model.DiaDanh;
import com.doan3.canthotour.R;

import java.util.ArrayList;

/**
 * Created by zzacn on 11/17/2017.
 */

public class DiaDanhAdapter extends RecyclerView.Adapter<DiaDanhAdapter.ViewHolder> {
    ArrayList<DiaDanh> diaDanhs;
    Context context;

    public DiaDanhAdapter(ArrayList<DiaDanh> diaDanhs, Context context) {
        this.diaDanhs = diaDanhs;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //Khi gọi DiaDanhAdapter thì hàm này chạy đầu tiên
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.trangchu_list_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) { //Mỗi 1 lần chạy hàm này tương ứng với load 1 item trong recycler view
        holder.txtTenDD.setText(diaDanhs.get(position).getTenDD());
        holder.imgHinhDD.setImageResource(diaDanhs.get(position).getHinhDD());

//        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
//            @Override
//            public void onClick(View view) {
//                Intent intent_chitietdiadiem = new Intent(context, ActivityChiTietDiaDiem.class);
//                context.startActivity(intent_chitietdiadiem);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return diaDanhs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{ //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
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
