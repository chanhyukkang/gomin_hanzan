package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IdInterface {
    @GET("/test2/{idconfirm}")
    Call<List<Dummy>> listDummies(@Path("idconfirm") String idconfirm);
}
