package com.sleekjob.gooddealaccra;

import android.content.Intent;
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
    ArrayList<Deal> dealArrayList;
    DealAdapter adapter;
    TextView mCardDescription, mCardContact;
    SwipeRefreshLayout mSwipeRefresh;
    String TAG = "MainA";
    Toolbar toolbar;
    LinearLayout mLinearLayout;
    AVLoadingIndicatorView avi;
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
        mLinearLayout = (LinearLayout) findViewById(R.id.mLinearLayout);
        avi = (AVLoadingIndicatorView) findViewById(R.id.avi);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        toolbar = (Toolbar) findViewById(R.id.mRecyclerToolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.app_name);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bindRefresh();
                    }
                }, 2000);

            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                stopAnim();
            }
        },4000);

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                bindRefresh();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), AddDealActivity.class);
                startActivity(i);
            }
        });


        /*bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_deals) {
                    RecyclerFragment fragment = (RecyclerFragment) getSupportFragmentManager().findFragmentByTag("Deals");
                    if (fragment == null) {
                        fragment = new RecyclerFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.DealFrame, fragment, "Deals").commit();
                        add.setVisibility(View.GONE);
                        deal.setVisibility(View.VISIBLE);
                    }else {
                        add.setVisibility(View.GONE);
                        deal.setVisibility(View.VISIBLE);
                    }
                }
                else if (tabId == R.id.tab_add) {
                    AddDealFragment fragment = (AddDealFragment) getSupportFragmentManager().findFragmentByTag("Add");
                    if (fragment == null) {
                        fragment = new AddDealFragment();
                        getSupportFragmentManager().beginTransaction().add(R.id.AddFrame, fragment, "Add").commit();
                        deal.setVisibility(View.GONE);
                        add.setVisibility(View.VISIBLE);
                    }
                    deal.setVisibility(View.GONE);
                    add.setVisibility(View.VISIBLE);
                }
            }
        });*/
    }



    public void bindRefresh(){

        String url = "http://gooddealaccra.sleekjob.com/api/deals";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG, response);
                dealArrayList = new JsonConverter<Deal>().toArrayList(response, Deal.class);
                adapter = new DealAdapter(getApplicationContext(),dealArrayList);
                mRecyclerView.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(manager);
                adapter.notifyDataSetChanged();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(getApplicationContext(), "Bad Network Connection. Please Try Again", Toast.LENGTH_LONG).show();

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

    void stopAnim(){
        avi.hide();
        // or avi.smoothToHide();
    }


        /*mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mSwipeRefresh = (SwipeRefreshLayout) findViewById(R.id.mSwipeRefresh);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mCardDescription = (TextView) findViewById(R.id.mCardDescription);
        mCardContact = (TextView) findViewById(R.id.mCardContact);

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        bindRefresh();
                    }
                }, 2000);

            }
        });






        String url = "http://10.0.3.2:8000/api/deals";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG, response);
                ArrayList<Deal> dealArrayList = new JsonConverter<Deal>().toArrayList(response, Deal.class);
                DealAdapter adapter = new DealAdapter(getApplicationContext(),dealArrayList);
                mRecyclerView.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(manager);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        }
    );

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);



    }
    public void bindRefresh(){

        String url = "http://10.0.3.2:8000/api/deals";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG, response);
                ArrayList<Deal> dealArrayList = new JsonConverter<Deal>().toArrayList(response, Deal.class);
                DealAdapter adapter = new DealAdapter(getApplicationContext(),dealArrayList);
                mRecyclerView.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(manager);
                adapter.notifyDataSetChanged();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        }
        );

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
        mSwipeRefresh.setRefreshing(false);*/






}
