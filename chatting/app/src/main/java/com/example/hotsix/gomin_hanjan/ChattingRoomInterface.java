package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChattingRoomInterface {
    @GET("/chat/{chattingroominfo},{chattingroominfo1},{chattingroominfo2},{chattingroominfo3},{chattingroominfo4},{chattingroominfo5},{chattingroominfo6},{chattingroominfo7},{chattingroominfo8},{chattingroominfo9}")
    Call<List<Dummy2>> listDummies(@Path("chattingroominfo") String chattingroominfo, @Path("chattingroominfo1") String chattingroominfo1, @Path("chattingroominfo2") String chattingroominfo2, @Path("chattingroominfo3") String chattingroominfo3, @Path("chattingroominfo4") String chattingroominfo4, @Path("chattingroominfo5") String chattingroominfo5, @Path("chattingroominfo6") String chattingroominfo6, @Path("chattingroominfo7") String chattingroominfo7, @Path("chattingroominfo8") String chattingroominfo8, @Path("chattingroominfo9") String chattingroominfo9);
}