package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PostInterface {
    @GET("/post/{writepost},{writepost1}")
    Call<List<Dummy>> listDummies(@Path("writepost") String writepost, @Path("writepost1") String writepost1);
}
