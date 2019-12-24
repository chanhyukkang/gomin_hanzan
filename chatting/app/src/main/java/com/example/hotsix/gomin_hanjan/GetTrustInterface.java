package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetTrustInterface {
    @GET("/trust1/{trust1}")
    Call<List<Dummy>> listDummies(@Path("trust1") String trust1);
}
