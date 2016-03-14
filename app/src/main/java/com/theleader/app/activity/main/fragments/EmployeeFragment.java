package com.theleader.app.activity.main.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.theleader.app.R;
import com.theleader.app.activity.ContactListActivity;
import com.theleader.app.activity.main.MainActivity;
import com.theleader.app.collection.EmployeeCollection;
import com.theleader.app.entity.EmployeeEntity;

import R.helper.BaseActivity;
import R.helper.BaseFragment;
import R.helper.Callback;
import R.helper.CallbackResult;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by duynk on 3/2/16.
 *
 */
public class EmployeeFragment extends BaseFragment {

    @Bind(R.id.recycler_view)
    RecyclerView recyclerView;

    @Bind(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;

    @Bind(R.id.btn_add_employee)
    FloatingActionButton btnAddEmployee;

    public static final int CONTACT_PICKER_RESULT = 1001;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_employee, container, false);
        ButterKnife.bind(this, rootView);

        btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ContactListActivity.class);
                getActivity().startActivityForResult(intent, CONTACT_PICKER_RESULT);
            }
        });

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
            EmployeeCollection.getInstance().load(EmployeeFragment.this.getContext(), new Callback() {
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

    public void onSelectedContactList(String data) {
        System.out.println(data);
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
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cv_employee_list_item, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            View view = holder.view;
            EmployeeEntity r = EmployeeCollection.getInstance().getData(position);
            if (r != null) {
                ((TextView) view.findViewById(R.id.txt_name)).setText(r.getFirstName() + " " + r.getLastName());
                ((TextView) view.findViewById(R.id.txt_phone)).setText(r.getEmail());
            }
        }


        @Override
        public int getItemCount() {
            return EmployeeCollection.getInstance().size();
        }
    }
}