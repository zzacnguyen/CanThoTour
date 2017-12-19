package com.doan3.canthotour.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.doan3.canthotour.Config;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.Hotel;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityHotelInfo;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class ListOfHotelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private Context context;
    private ArrayList<Hotel> hotel;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    ArrayList<String> arr = new ArrayList<>();

    public ListOfHotelAdapter(RecyclerView recyclerView, ArrayList<Hotel> hotel, Context context) {
        this.context = context;
        this.hotel = hotel;

        final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null)
                        onLoadMoreListener.onLoadMore();
                    isLoading = true;
                }
            }
        });
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return hotel.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.custom_diadiem_list, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder) {
            Hotel hotels = hotel.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.txtTenDD.setText(hotels.getTenKS());
            viewHolder.txtDiaChiDD.setText(hotels.getDiaChiKS());
            viewHolder.imgHinhDD.setImageResource(hotels.getHinhKS());

            try {
                arr = new EatAdapter.GetId().execute(Config.URL_HOST + Config.URL_GET_ALL_ID_HOTEL).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iHotelInfo = new Intent(context, ActivityHotelInfo.class);
                    iHotelInfo.putExtra("masp", arr.get(position));
                    iHotelInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(iHotelInfo);
                }
            });
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return hotel.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    // "Loading item" ViewHolder
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        }
    }

    //"Normal item" Viewholder
    private class ViewHolder extends RecyclerView.ViewHolder { //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTenDD, txtDiaChiDD;
        ImageView imgHinhDD;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTenDD = itemView.findViewById(R.id.txtTenDiaDiem);
            imgHinhDD = itemView.findViewById(R.id.imgHinhDiaDiem);
            txtDiaChiDD = itemView.findViewById(R.id.txtDiaChiDD);
            cardView = (CardView) itemView.findViewById(R.id.cardViewDiaDiem);

        }
    }
}
