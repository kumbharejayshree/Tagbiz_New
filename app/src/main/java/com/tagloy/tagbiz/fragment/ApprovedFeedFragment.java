package com.tagloy.tagbiz.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tagloy.tagbiz.interfaces.ILoadMore;
import com.tagloy.tagbiz.R;
import com.tagloy.tagbiz.adapter.ApproveGridAdapter;
import com.tagloy.tagbiz.models.Feed;
import com.tagloy.tagbiz.utils.AppConfig;
import com.tagloy.tagbiz.utils.BackgroundClass;
import com.tagloy.tagbiz.utils.GridSpacingItemDecoration;
import com.tagloy.tagbiz.utils.PreferenceHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApprovedFeedFragment extends Fragment implements View.OnClickListener {

    private RecyclerView feedRecyclerView;
    private Button loadMore;
    private ProgressBar feedProgress;
    private List<Feed> feedList = new ArrayList<>();
    private ApproveGridAdapter gridAdapter;
    private Context mContext;
    BackgroundClass backgroundClass;
    private TextView errorText;
    private String ParseResponse;
    private int page;
    private SwipeRefreshLayout approvedFeedRefresh;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_approved_feed, container, false);
        mContext = getActivity();
        page = 0;
        approvedFeedRefresh = view.findViewById(R.id.approvedFeedRefresh);
        backgroundClass = new BackgroundClass(mContext);
        feedRecyclerView = view.findViewById(R.id.approvedFeedRecyclerView);
        feedProgress = view.findViewById(R.id.approvedFeedProgress);
        loadMore = view.findViewById(R.id.approvedLoadMore);
        errorText = view.findViewById(R.id.errorTextApprove);
        loadMore.setOnClickListener(this);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        feedRecyclerView.setLayoutManager(layoutManager);
        feedRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, backgroundClass.dpToPx(5), true));
        feedRecyclerView.setItemAnimator(new DefaultItemAnimator());
        if (backgroundClass.isNetworkConnected()) {
            getApprovedFeeds(page);
        }else {
            Toast.makeText(mContext, "Check network connection!", Toast.LENGTH_LONG).show();
        }

        approvedFeedRefresh.setColorSchemeResources(android.R.color.holo_blue_dark,android.R.color.holo_green_light,
                android.R.color.holo_orange_light,android.R.color.holo_red_light);
        approvedFeedRefresh.setOnRefreshListener(() -> {
            getApprovedFeeds(page);
            new Handler().postDelayed(() -> approvedFeedRefresh.setRefreshing(false), 5000);
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void getApprovedFeeds(int page) {
        try {
            String org_id = PreferenceHelper.getValueString(mContext, AppConfig.ORG_ID);
            final String token = PreferenceHelper.getValueString(mContext, AppConfig.USER_TOKEN);
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("page",page);
            jsonObject.put("venue_id", org_id);
            Log.d("venue",org_id);
            RequestQueue queue = Volley.newRequestQueue(mContext);
            feedProgress.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AppConfig.PUBLISHED_FEEDS_URL, response -> {
                ParseResponse = response;
                new LoadFeeds(mContext).execute();
            }, error -> {
                feedProgress.setVisibility(View.GONE);
                if (error == null || error.networkResponse == null) {
                    Log.d("Get Approved feeds API","Fail");
                    return;
                }
                String body;
                try {
                    body = new String(error.networkResponse.data, "UTF-8");
                    Log.d("Error", body);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Log.d("Get feeds volley","Failure");
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> parameter = new HashMap<>();
                    parameter.put("Content-Type", "application/json");
                    parameter.put("Authorization", token);
                    return parameter;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    final String request = jsonObject.toString();
                    try {
                        return request.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", request, "utf-8");
                        return null;
                    }
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
        } catch (JSONException je) {
            je.printStackTrace();
        }
    }

    public class LoadFeeds extends AsyncTask<Void,Void,Void> {
        Context context;

        public LoadFeeds(Context context){
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            feedProgress.setVisibility(View.VISIBLE);
            loadMore.setVisibility(View.GONE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Feed feed;
                JSONObject stringResponse = new JSONObject(ParseResponse);
                boolean success = stringResponse.getBoolean("is_success");
                String requestCode = stringResponse.getString("status_code");
                if (success) {
                    if (requestCode.equals("200")) {
                        Log.d("Get feeds","Success");
                        String result = stringResponse.getString("result");
                        JSONArray resultArray = new JSONArray(result);
                        for (int i = 0; i < resultArray.length(); i++) {
                            feed = new Feed();
                            JSONObject resultObject = resultArray.getJSONObject(i);
                            String feed_image = resultObject.getString("feed_image");
                            JSONArray feedImageArray = new JSONArray(feed_image);
                            JSONObject feedObject = feedImageArray.getJSONObject(0);
                            String feed_url = feedObject.getString("url");
                            String time = resultObject.getString("feed_recieved_at");
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");
                            long upTime = Long.parseLong(time);
                            feed.setFeed_id(resultObject.getInt("feed_id"));
                            feed.setImgUri(feed_url);
                            feed.setUser_name(resultObject.getString("cust_name"));
                            feed.setFeed_message(resultObject.getString("hash_tag"));
                            feed.setUpload_time(dateFormat.format(new Date(upTime)));
                            feedList.add(feed);
                        }
                    }else{
                        Log.d("Get feeds","Failure");
                    }
                } else {
                    Log.d("Is success","False");
                    Toast.makeText(mContext, "Please Re-login!", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException je) {
                je.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (feedList.size() > 0){
                if (feedList.size() <= 12){
                    feedProgress.setVisibility(View.GONE);
                    loadMore.setVisibility(View.VISIBLE);
                    gridAdapter = new ApproveGridAdapter(feedRecyclerView,context,feedList);
                    gridAdapter.notifyDataSetChanged();
                    feedRecyclerView.setAdapter(gridAdapter);
                    gridAdapter.setiLoadMore(() -> {
                        gridAdapter.notifyItemChanged(feedList.size()-1);
                        new Handler().postDelayed(() -> {
                            page++;
                            getApprovedFeeds(page);
                            gridAdapter.notifyDataSetChanged();
                            gridAdapter.setLoaded();
                        },5000);
                    });
                }else {
                    feedProgress.setVisibility(View.GONE);
                }
            }else{
                errorText.setVisibility(View.VISIBLE);
                feedProgress.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.approvedLoadMore) {
            page = page + 1;
            getApprovedFeeds(page);
        }
    }
}
