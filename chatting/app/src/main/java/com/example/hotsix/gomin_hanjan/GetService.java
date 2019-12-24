package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetService {
    @GET("/test/{position},{position1}")
    Call<List<Dummy3>> listDummies(@Path("position") String position, @Path("position1") String position1);
}