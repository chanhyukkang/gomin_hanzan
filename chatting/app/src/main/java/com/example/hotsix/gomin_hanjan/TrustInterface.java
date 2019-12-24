package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TrustInterface {
    @GET("/trust/{trust},{trust1},{trust2}")
    Call<List<Dummy>> listDummies(@Path("trust") String trust, @Path("trust1") String trust1, @Path("trust2") String trust2);
}
