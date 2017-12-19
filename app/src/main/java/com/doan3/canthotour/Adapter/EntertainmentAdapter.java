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
import com.doan3.canthotour.Model.Entertainment;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityEntertainmentInfo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class EntertainmentAdapter extends RecyclerView.Adapter<EntertainmentAdapter.ViewHolder> {
    ArrayList<Entertainment> entertain;
    Context context;
    ArrayList<String> arr = new ArrayList<>();

    public EntertainmentAdapter(ArrayList<Entertainment> entertain, Context context) {
        this.entertain = entertain;
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
        holder.txtTenDD.setText(entertain.get(position).getTenVC());
        holder.imgHinhDD.setImageResource(entertain.get(position).getHinhVC());

        try {
            arr = new EatAdapter.GetId().execute(Config.URL_HOST + Config.URL_GET_ALL_ID_ENTERTAINMENT).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
            @Override
            public void onClick(View view) {
                Intent iEntertainmentInfo = new Intent(context, ActivityEntertainmentInfo.class);
                iEntertainmentInfo.putExtra("masp", arr.get(position));
                iEntertainmentInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(iEntertainmentInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entertain.size();
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
