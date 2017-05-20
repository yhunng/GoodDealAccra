package com.sleekjob.gooddealaccra;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class RecyclerFragment extends Fragment {
    private static final String BUNDLE_RECYCLER_LAYOUT = "classname.recycler.layout";
    RecyclerView mRecyclerView;
    ArrayList<Deal> dealArrayList;
    DealAdapter adapter;
    TextView mCardDescription, mCardContact;
    SwipeRefreshLayout mSwipeRefresh;
    String TAG = "MainA";
    Toolbar toolbar;
    LinearLayout mLinearLayout;
    AVLoadingIndicatorView avi;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }

        View v = inflater.inflate(R.layout.recycler_fragment,container,false);


        mRecyclerView = (RecyclerView) v.findViewById(R.id.mRecyclerView);
        mSwipeRefresh = (SwipeRefreshLayout) v.findViewById(R.id.mSwipeRefresh);
        toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        mCardDescription = (TextView) v.findViewById(R.id.mCardDescription);
        mCardContact = (TextView) v.findViewById(R.id.mCardContact);
        mLinearLayout = (LinearLayout)v.findViewById(R.id.mLinearLayout);
        avi = (AVLoadingIndicatorView) v.findViewById(R.id.avi);

        toolbar = (Toolbar) v.findViewById(R.id.mRecyclerToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
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



       /* String url = "http://10.0.3.2:8000/api/deals";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG, response);
                ArrayList<Deal> dealArrayList = new JsonConverter<Deal>().toArrayList(response, Deal.class);
                DealAdapter adapter = new DealAdapter(getActivity().getApplicationContext(),dealArrayList);
                mRecyclerView.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
                mRecyclerView.setLayoutManager(manager);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(getActivity().getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        }
        );

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
*/
        return v;
    }

    public void bindRefresh(){

        String url = "http://gooddealaccra.sleekjob.com/api/deals";
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Log.d(TAG, response);
                dealArrayList = new JsonConverter<Deal>().toArrayList(response, Deal.class);
                adapter = new DealAdapter(getActivity().getApplicationContext(),dealArrayList);
                mRecyclerView.setAdapter(adapter);
                LinearLayoutManager manager = new LinearLayoutManager(getActivity().getApplicationContext());
                mRecyclerView.setLayoutManager(manager);
                adapter.notifyDataSetChanged();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
                Toast.makeText(getActivity().getApplicationContext(), "Bad Network Connection. Please Try Again", Toast.LENGTH_LONG).show();

            }
        }
        );

        int socketTimeout = 30000; // 30 seconds. You can change it
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(policy);
        MySingleton.getInstance(getActivity().getApplicationContext()).addToRequestQueue(stringRequest);
        mSwipeRefresh.setRefreshing(false);



    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState != null)
        {
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable(BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
    }
    
    void stopAnim(){
        avi.hide();
        // or avi.smoothToHide();
    }


}
