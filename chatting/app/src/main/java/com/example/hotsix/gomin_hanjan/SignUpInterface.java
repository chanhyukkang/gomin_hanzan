package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface SignUpInterface {
    @GET("/test1/{signup},{signup1},{signup2},{signup3},{signup4},{signup5}")
    Call<List<Dummy>> listDummies(@Path("signup") String signup, @Path("signup1") String signup1, @Path("signup2") String signup2, @Path("signup3") String signup3, @Path("signup4") String signup4, @Path("signup5") String signup5);
}
