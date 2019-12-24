package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EntranceInterface {
    @GET("/chat1/{chattingroomentrance},{chattingroomentrance1},{chattingroomentrance2}")
    Call<List<Dummy1>> listDummies(@Path("chattingroomentrance") String chattingroomentrance, @Path("chattingroomentrance1") String chattingroomentrance1, @Path("chattingroomentrance2") String chattingroomentrance2);
}