package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserInterface {
    @GET("/usertitle/{usertitle}")
    Call<List<Dummy3>> listDummies(@Path("usertitle") String usertitle);
}
