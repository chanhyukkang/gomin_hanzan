package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface UserKeyInterface {
    @GET("/userkey/{userkey},{userkey1}")
    Call<List<Dummy>> listDummies(@Path("userkey") String userkey, @Path("userkey1") String userkey1);
}
