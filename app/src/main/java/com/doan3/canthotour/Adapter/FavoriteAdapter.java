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


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    ArrayList<Place> place;
    Context context;

    public FavoriteAdapter(ArrayList<Place> place, Context context) {
        this.place = place;
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
        holder.txtTenDD.setText(place.get(position).getTenDD());
        holder.imgHinhDD.setImageResource(place.get(position).getHinhDD());

//        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
//            @Override
//            public void onClick(View view) {
//                Intent iPlaceInfo = new Intent(context, ActivityPlaceInfo.class);
//                iPlaceInfo.putExtra("masp", position+1+"");
//                iPlaceInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                context.startActivity(iPlaceInfo);
//            }
//        });
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

            txtTenDD = itemView.findViewById(R.id.textViewYeuThich);
            imgHinhDD = itemView.findViewById(R.id.imageViewYeuThich);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
