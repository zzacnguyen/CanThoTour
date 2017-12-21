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

import com.doan3.canthotour.Model.ObjectClass.Service;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityEntertainmentInfo;

import java.util.ArrayList;


public class EntertainmentAdapter extends RecyclerView.Adapter<EntertainmentAdapter.ViewHolder> {
    ArrayList<Service> entertainments;
    Context context;
    ArrayList<String> arr = new ArrayList<>();

    public EntertainmentAdapter(ArrayList<Service> entertainments, Context context) {
        this.entertainments = entertainments;
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
        Service entertainment = entertainments.get(position);
        holder.txtTen.setText(entertainment.getTen());
        holder.imgHinh.setImageResource(entertainment.getHinh());
        holder.cardView.setTag(entertainment.getId());

        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
            @Override
            public void onClick(View view) {
                Intent iServiceInfo = new Intent(context, ActivityEntertainmentInfo.class);
                iServiceInfo.putExtra("masp", (int) view.getTag());
                iServiceInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(iServiceInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return entertainments.size();
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
