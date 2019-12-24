package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FavoriteInterface {
    @GET("/favorite/{favorite},{favorite1}")
    Call<List<Dummy>> listDummies(@Path("favorite") String favorite, @Path("favorite1") String favorite1);
}
