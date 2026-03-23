package com.example.devvault.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.devvault.R;
import com.example.devvault.model.HNPost;
import com.example.devvault.network.HackerNewsApi;
import com.example.devvault.ui.adapters.FeedAdapter;
import com.example.devvault.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FeedActivity extends AppCompatActivity {
    private static final String TAG = "FeedActivity";
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FeedAdapter adapter;
    private final List<HNPost> postList = new ArrayList<>();
    private HackerNewsApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        recyclerView = findViewById(R.id.recyclerViewFeed);
        progressBar = findViewById(R.id.progressBarFeed);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new FeedAdapter(postList);
        recyclerView.setAdapter(adapter);

        // Back to home
        LinearLayout navHome = findViewById(R.id.feedNavHome);
        navHome.setOnClickListener(v -> {
            startActivity(new Intent(FeedActivity.this, MainActivity.class));
            finish();
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.HN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(HackerNewsApi.class);
        fetchTopStories();
    }

    private void fetchTopStories() {
        progressBar.setVisibility(View.VISIBLE);
        api.getTopStories().enqueue(new Callback<List<Long>>() {
            @Override
            public void onResponse(Call<List<Long>> call, Response<List<Long>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Long> ids = response.body();
                    int limit = Math.min(ids.size(), 20);
                    for (int i = 0; i < limit; i++) fetchItem(ids.get(i));
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<List<Long>> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Error fetching stories", t);
            }
        });
    }

    private void fetchItem(long id) {
        api.getItem(id).enqueue(new Callback<HNPost>() {
            @Override
            public void onResponse(Call<HNPost> call, Response<HNPost> response) {
                if (response.isSuccessful() && response.body() != null) {
                    postList.add(response.body());
                    adapter.notifyItemInserted(postList.size() - 1);
                }
            }

            @Override
            public void onFailure(Call<HNPost> call, Throwable t) {
                Log.e(TAG, "Error fetching item " + id, t);
            }
        });
    }
}