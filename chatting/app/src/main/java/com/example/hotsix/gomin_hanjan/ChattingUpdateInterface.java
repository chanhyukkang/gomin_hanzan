package com.example.hotsix.gomin_hanjan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChattingUpdateInterface {
    @GET("/chattingupdate/{chattingupdate},{chattingupdate1},{chattingupdate2},{chattingupdate3},{chattingupdate4},{chattingupdate5},{chattingupdate6},{chattingupdate7},{chattingupdate8},{chattingupdate9}")
    Call<List<Dummy>> listDummies(@Path("chattingupdate") String chattingupdate, @Path("chattingupdate1") String chattingupdate1, @Path("chattingupdate2") String chattingupdate2, @Path("chattingupdate3") String chattingupdate3, @Path("chattingupdate4") String chattingupdate4, @Path("chattingupdate5") String chattingupdate5, @Path("chattingupdate6") String chattingupdate6, @Path("chattingupdate7") String chattingupdate7, @Path("chattingupdate8") String chattingupdate8, @Path("chattingupdate9") String chattingupdate9);
}