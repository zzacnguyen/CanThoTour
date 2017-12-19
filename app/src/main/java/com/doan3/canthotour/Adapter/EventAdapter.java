package com.doan3.canthotour.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import com.doan3.canthotour.Helper.JsonHelper;
import com.doan3.canthotour.Interface.OnLoadMoreListener;
import com.doan3.canthotour.Model.Event;
import com.doan3.canthotour.R;
import com.doan3.canthotour.View.Main.ActivityPlaceInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


public class EventAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    ArrayList<String> arr = new ArrayList<>();
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
            View view = LayoutInflater.from(context).inflate(R.layout.custom_thongbao, parent, false);
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
            Event event = events.get(position);
            ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.txtTenSk.setText(event.getTenSk());
            viewHolder.txtNgaySk.setText(event.getNgaySk());
            viewHolder.imgHinhSk.setImageResource(event.getHinhSk());

            try {
                arr = new GetId().execute(Config.URL_HOST + Config.URL_GET_ALL_EVENTS).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

            viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent iEventInfo = new Intent(context, ActivityPlaceInfo.class);
                    iEventInfo.putExtra("masp", arr.get(position));
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

            txtTenSk = itemView.findViewById(R.id.textViewTenSk);
            imgHinhSk = itemView.findViewById(R.id.imageViewSuKien);
            txtNgaySk = itemView.findViewById(R.id.textViewNgaySk);
            cardView = itemView.findViewById(R.id.cardViewSuKien);
        }
    }

    private class GetId extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            ArrayList<String> arr, array = new ArrayList<>(), arrayList = new ArrayList<>();
            try {
                arr = JsonHelper.parseJsonNoId(new JSONObject(HttpRequestAdapter.httpGet(strings[0])), Config.JSON_LOAD);
                array = JsonHelper.parseJson(new JSONArray(arr.get(0)), Config.JSON_EVENT);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            for (int i = 4; i < array.size(); i += 6) {
                arrayList.add(array.get(i));
            }
            return arrayList;
        }
    }
}
