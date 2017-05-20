package com.sleekjob.gooddealaccra;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    /*RecyclerView mRecyclerView;
    TextView mCardDescription, mCardContact;
    SwipeRefreshLayout mSwipeRefresh;
    String TAG = "MainA";
    Toolbar toolbar;*/
    BottomBar bottomBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FrameLayout deal = (FrameLayout) findViewById(R.id.DealFrame);
        final FrameLayout add = (FrameLayout) findViewById(R.id.AddFrame);

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
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
        });
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
