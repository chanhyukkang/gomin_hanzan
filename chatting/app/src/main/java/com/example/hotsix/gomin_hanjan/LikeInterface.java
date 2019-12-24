package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LikeInterface {
    @GET("/like/{like},{like1}")
    Call<List<Dummy>> listDummies(@Path("like") String like, @Path("like1") String like1);
}
