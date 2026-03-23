package com.example.devvault.network;

import com.example.devvault.model.HNPost;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HackerNewsApi {
    @GET("v0/topstories.json")
    Call<List<Long>> getTopStories();

    @GET("v0/item/{id}.json")
    Call<HNPost> getItem(@Path("id") long id);
}
