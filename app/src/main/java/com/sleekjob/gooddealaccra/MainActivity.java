package com.sleekjob.gooddealaccra;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.kosalgeek.android.json.JsonConverter;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    RecyclerView mRecyclerView;
    ArrayList<Deal> dealArrayList, newList;
    DealAdapter adapter;
    private EndlessRecyclerViewScrollListener scrollListener;
    TextView mCardDescription, mCardContact, oPTag, oPrice;
    SwipeRefreshLayout mSwipeRefresh;
    String TAG = "MainA";
    Toolbar toolbar;
    //int page = 2;
    LinearLayout mLinearLayout;
    AVLoadingIndicatorView avi;
    ProgressBar mProgress;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefresh);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mCardDescription = (TextView) findViewById(R.id.mCardDescription);
        mCardContact = (TextView) findViewById(R.id.mCardContact);
//        oPTag = (TextView) findViewById(R.id.oPTag);
//        oPrice = (TextView) findViewById(R.id.oPrice);
        mLinearLayout = (LinearLayout) findViewById(R.id.mLinearLayout);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        mProgress = (ProgressBar) findViewById(R.id.mProgress);

        toolbar = (Toolbar) findViewById(R.id.mRecyclerToolbar);



        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(manager);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.clearAll();
                        scrollListener.resetState();
                        bindRefresh(1);
                    }
                }, 3000);

            }
        });

        bindRefresh(1);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddDealActivity.class);
                startActivity(i);
            }
        });


        scrollListener = new EndlessRecyclerViewScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                mProgress.setVisibility(View.VISIBLE);
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadMore(page);
                ++page;
            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);


    }


    public void bindRefresh(int page) {

        String url = "http://gooddealaccra.sleekjob.com/api/deals?page=" + page;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Responses", response);
                dealArrayList = new JsonConverter<Deal>().toArrayList(response, Deal.class);
                adapter = new DealAdapter(getApplicationContext(), dealArrayList);
                mRecyclerView.setAdapter(adapter);
                newList = dealArrayList;
                adapter.notifyDataSetChanged();
                stopAnim();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(getApplicationContext(), "Bad Network Connection. Please Try Again", Toast.LENGTH_LONG).show();
                stopAnim();

            }
        }
        );

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        mSwipeRefresh.setRefreshing(false);


    }

    public void loadMore(int page) {

        String url = "http://gooddealaccra.sleekjob.com/api/deals?page=" + page;
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ArrayList<Deal> deals = new JsonConverter<Deal>().toArrayList(response, Deal.class);
                adapter.addAll(deals);
                mProgress.setVisibility(View.GONE);
                //adapter = new DealAdapter(getApplicationContext(), dealArrayList);
                //mRecyclerView.setAdapter(adapter);
//                newList = deals;
//                adapter.notifyItemRangeInserted(newList.size(), dealArrayList.size());
//                stopAnim();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(getApplicationContext(), "Bad Network Connection. Please Try Again", Toast.LENGTH_LONG).show();
                mProgress.setVisibility(View.GONE);
            }
        }
        );

        int socketTimeout = 10000; // 10 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);


    }

    void stopAnim() {
        avi.hide();
    }


}
