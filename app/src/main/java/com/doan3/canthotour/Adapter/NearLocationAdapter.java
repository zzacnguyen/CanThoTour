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

import com.doan3.canthotour.Model.ObjectClass.NearLocation;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityPlaceInfo;

import java.util.ArrayList;


public class NearLocationAdapter extends RecyclerView.Adapter<NearLocationAdapter.ViewHolder> {
    private ArrayList<NearLocation> nearLocations;
    Context context;
    ArrayList<String> arr = new ArrayList<>();

    public NearLocationAdapter(ArrayList<NearLocation> nearLocation, Context context) {
        this.nearLocations = nearLocation;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) { //Khi gọi DiaDanhAdapter thì hàm này chạy đầu tiên
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.custom_lancan, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) { //Mỗi 1 lần chạy hàm này tương ứng với load 1 item trong recycler view
        NearLocation nearLocation = nearLocations.get(position);
        holder.txtTen.setText(nearLocation.getTenDiaDiemLC());
        holder.txtKhoangCach.setText(nearLocation.getKhoangCachLC());
        holder.imgHinh.setImageResource(nearLocation.getHinhDiaDiemLC());
        holder.cardView.setTag(nearLocation.getMaDiaDiemLC());

        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
            @Override
            public void onClick(View view) {
                Intent iLocationInfo = new Intent(context, ActivityPlaceInfo.class);
                iLocationInfo.putExtra("masp", (int) view.getTag());
                iLocationInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(iLocationInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nearLocations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder { //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTen, txtKhoangCach;
        ImageView imgHinh;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTen = itemView.findViewById(R.id.textViewTenLanCan);
            txtKhoangCach = itemView.findViewById(R.id.textViewKhoangCach);
            imgHinh = itemView.findViewById(R.id.imageViewLanCan);
            cardView = itemView.findViewById(R.id.cardViewLanCan);
        }
    }
}
