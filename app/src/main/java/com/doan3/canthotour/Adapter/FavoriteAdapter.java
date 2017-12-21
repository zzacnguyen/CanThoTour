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
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Favorite.ActivityFavoriteInfo;

import java.util.ArrayList;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    ArrayList<Favorite> favorites;
    Context context;
    ArrayList<String> arr = new ArrayList<>();

    public FavoriteAdapter(ArrayList<Favorite> favorite, Context context) {
        this.favorites = favorite;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //Khi gọi DiaDanhAdapter thì hàm này chạy đầu tiên
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_yeuthich, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) { //Mỗi 1 lần chạy hàm này tương ứng với load 1 item trong recycler view
        Favorite favorite = favorites.get(position);
        holder.txtTen.setText(favorite.getTenYT());
        holder.imgHinh.setImageResource(favorite.getHinhYT());
        holder.cardView.setTag(favorite.getMaYT());

        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
            @Override
            public void onClick(View view) {
                Intent iFavoriteInfo = new Intent(context, ActivityFavoriteInfo.class);
                iFavoriteInfo.putExtra("masp", (int) view.getTag());
                iFavoriteInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(iFavoriteInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favorites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTen;
        ImageView imgHinh;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTen = itemView.findViewById(R.id.textViewYeuThich);
            imgHinh = itemView.findViewById(R.id.imageViewYeuThich);
            cardView = itemView.findViewById(R.id.cardViewYeuThich);
        }
    }
}
