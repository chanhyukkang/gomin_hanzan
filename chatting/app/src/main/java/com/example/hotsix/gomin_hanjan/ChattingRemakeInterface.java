package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChattingRemakeInterface {
    @GET("/chattingremake/{chattingremake}")
    Call<List<Dummy7>> listDummies(@Path("chattingremake") String chattingremake);
}