package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ReadPostInterface {
    @GET("/post1/{readpost}")
    Call<List<Dummy2>> listDummies(@Path("readpost") String readpost);
}
