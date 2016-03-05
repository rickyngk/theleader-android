package com.theleader.app.activity.main.fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.theleader.app.R;
import com.theleader.app.activity.main.MainActivity;
import com.theleader.app.collection.FeedbackCollection;
import com.theleader.app.customview.FeedbackListItem;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by duynk on 3/5/16.
 *
 */
public class FeedbackFragment extends BaseFragment {
    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feedback, container, false);
        ButterKnife.bind(this, rootView);

        loadData();
        swipeLayout.setRefreshing(true);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return rootView;
    }

    private class LoadDataAsync extends AsyncTask<Void, Void, Void> {
        protected Void doInBackground(Void...params) {
            FeedbackCollection.getInstance().load(FeedbackFragment.this.getContext(), new Callback() {
                @Override
                public void onCompleted(final Context context, CallbackResult result) {
                    if (context instanceof MainActivity) {
                        MainActivity act = (MainActivity) context;
                        if (!act.isFinishing()) {
                            BaseActivity.timeout(new Runnable() {
                                @Override
                                public void run() {
                                    swipeLayout.setRefreshing(false);
                                    recyclerView.setAdapter(new CustomRecyclerViewAdapter(context));
                                }
                            });


                        }
                    }
                }
            });
            return null;
        }
    }

    private void loadData() {
        new LoadDataAsync().execute();
    }

    public static class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder> {
        Context context;
        public CustomRecyclerViewAdapter(Context context) {
            this.context = context;
        }

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public View view;
            public ViewHolder(View v) {
                super(v);
                view = v;
            }
        }

        @Override
        public CustomRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = new FeedbackListItem(context);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            View view = holder.view;
            FeedbackListItem feedbackView = (FeedbackListItem)view;
            if (feedbackView != null) {
                feedbackView.setData(FeedbackCollection.getInstance().getData(position));
            }
        }

        @Override
        public int getItemCount() {
            return FeedbackCollection.getInstance().size();
        }
    }
}
