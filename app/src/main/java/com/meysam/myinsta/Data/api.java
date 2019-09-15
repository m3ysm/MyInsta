package com.meysam.myinsta.Data;

import com.meysam.myinsta.Models.JsonResponseModel;
import com.meysam.myinsta.Models.postModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface api {


    @FormUrlEncoded
    @POST("reg.php")
    Call<JsonResponseModel> registerUser(@Field("gender") String gender,
                                         @Field("username") String username,
                                         @Field("password") String password);

    @FormUrlEncoded
    @POST("newpost.php")
    Call<JsonResponseModel> newPost(@Field("id") String username,
                                    @Field("des") String description,
                                    @Field("image") String image,
                                    @Field("picname") String picname);

    @FormUrlEncoded
    @POST("verify.php")
    Call<JsonResponseModel> verify(@Field("username") String username,
                                   @Field("os") String os);

    @FormUrlEncoded
    @POST("postComment.php")
    Call<JsonResponseModel> postComment(@Field("username") String username,
                                        @Field("comment") String comment,
                                        @Field("postid") String postid);


    @GET("login.php")
    Call<JsonResponseModel> loginUser(@Query("username") String user,
                                      @Query("password") String pass);

    @GET("getposts.php")
    Call<postModel> getPost();

    @GET("getcomments.php")
    Call<postModel> getComments(@Field("postid") String postid);
}
