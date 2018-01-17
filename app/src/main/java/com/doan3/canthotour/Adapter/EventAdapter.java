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

import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.ObjectClass.Event;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityServiceInfo;

import java.util.ArrayList;


public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private Context context;
    private ArrayList<Event> events;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;

    public EventAdapter(RecyclerView recyclerView, ArrayList<Event> events, Context context) {
        this.context = context;
        this.events = events;

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
        return events.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.custom_notify, parent, false);
            return new ViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            Event event = events.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.txtTenSk.setText(event.getEventName());
            viewHolder.txtNgaySk.setText(event.getEventDate());
            viewHolder.imgHinhSk.setImageBitmap(event.getEventImage());
            viewHolder.cardView.setTag(event.getEventId());

            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iEventInfo = new Intent(context, ActivityServiceInfo.class);
                    iEventInfo.putExtra("id", (int) view.getTag());
                    iEventInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(iEventInfo);
                }
            });
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    // "Loading item" ViewHolder
    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = view.findViewById(R.id.progressBar);
        }
    }

    //"Normal item" Viewholder
    private class ViewHolder extends RecyclerView.ViewHolder {
        //ViewHolder chạy thứ 2, phần này giúp cho recycler view ko bị load lại dữ liệu khi thực hiện thao tác vuốt màn hình
        TextView txtTenSk, txtNgaySk;
        ImageView imgHinhSk;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);

            txtTenSk = itemView.findViewById(R.id.textViewEventName);
            imgHinhSk = itemView.findViewById(R.id.imageViewSuKien);
            txtNgaySk = itemView.findViewById(R.id.textViewEventDate);
            cardView = itemView.findViewById(R.id.cardViewSuKien);
        }
    }
}
