package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EmotionInterface {
    @GET("/emotion/{emotion},{emotion1}")
    Call<List<Dummy>> listDummies(@Path("emotion") String emotion, @Path("emotion1") String emotion1);
}
