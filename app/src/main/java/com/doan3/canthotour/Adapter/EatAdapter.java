package com.doan3.canthotour.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Model.Eat;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityEatInfo;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class EatAdapter extends RecyclerView.Adapter<EatAdapter.ViewHolder> {
    ArrayList<Eat> eat;
    Context context;
    ArrayList<String> arr = new ArrayList<>();

    public EatAdapter(ArrayList<Eat> eat, Context context) {
        this.eat = eat;
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
        holder.txtTenDD.setText(eat.get(position).getTenAU());
        holder.imgHinhDD.setImageResource(eat.get(position).getHinhAU());

        try {
            arr = new GetId().execute(Config.URL_HOST + Config.URL_GET_ALL_ID_EAT).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        holder.cardView.setOnClickListener(new View.OnClickListener() {  //Bắt sự kiện click vào 1 item cardview
            @Override
            public void onClick(View view) {
                Intent iPlaceInfo = new Intent(context, ActivityEatInfo.class);
                iPlaceInfo.putExtra("masp", arr.get(position));
                iPlaceInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(iPlaceInfo);
            }
        });
    }

    @Override
    public int getItemCount() {
        return eat.size();
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


    public static class GetId extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> array = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(HttpRequestAdapter.httpGet(strings[0]));
                array = JsonHelper.parseJson(jsonArray, new ArrayList<String>());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return array;
        }
    }
}
