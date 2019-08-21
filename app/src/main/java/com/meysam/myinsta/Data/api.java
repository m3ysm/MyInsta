package com.meysam.myinsta.Data;

import com.meysam.myinsta.Models.JsonResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface api {


    @FormUrlEncoded
    @POST("reg.php")
    Call<JsonResponseModel> registerUser(@Field("name") String name,
                                         @Field("username") String username,
                                         @Field("password") String password);

    @GET("login.php")
    Call<JsonResponseModel> loginUser(@Query("username") String user,
                                      @Query("password") String pass);
}
