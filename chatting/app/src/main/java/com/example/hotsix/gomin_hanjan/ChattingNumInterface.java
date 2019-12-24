package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChattingNumInterface {
    @GET("/chattingnum/{chattingnum}")
    Call<List<Dummy>> listDummies(@Path("chattingnum") String chattingnum);
}