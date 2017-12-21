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

import com.doan3.canthotour.Model.ObjectClass.Eat;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityEatInfo;

import java.util.ArrayList;


public class EatAdapter extends RecyclerView.Adapter<EatAdapter.ViewHolder> {
    ArrayList<Eat> eats;
    Context context;
    ArrayList<String> arr = new ArrayList<>();

    public EatAdapter(ArrayList<Eat> eat, Context context) {
        this.eats = eat;
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
        Eat eat = eats.get(position);
        holder.txtTenDD.setText(eat.getTenAU());
        holder.imgHinhDD.setImageResource(eat.getHinhAU());
        holder.cardView.setTag(eat.getMaAU());

        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
            @Override
            public void onClick(View view) {
                Intent iPlaceInfo = new Intent(context, ActivityEatInfo.class);
                iPlaceInfo.putExtra("masp", (int) view.getTag());
                iPlaceInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(iPlaceInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTenDD;
        ImageView imgHinhDD;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTenDD = itemView.findViewById(R.id.txtTen);
            imgHinhDD = itemView.findViewById(R.id.imgHinh);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
