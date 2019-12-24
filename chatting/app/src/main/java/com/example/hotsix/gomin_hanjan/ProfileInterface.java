package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProfileInterface {
    @GET("/profile/{profile}")
    Call<List<Dummy1>> listDummies(@Path("profile") String profile);
}
