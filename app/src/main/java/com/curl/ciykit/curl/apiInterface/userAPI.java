package com.curl.ciykit.curl.apiInterface;

import com.curl.ciykit.curl.model.NewsModel;
import com.curl.ciykit.curl.model.ResponseCode;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Akash Chandra on 08-02-2017.
 */

public interface userAPI {

    @FormUrlEncoded
    @POST("/addUser")
    public void addUser(
            @Field("user_name") String user_name,
            @Field("user_email") String user_email,
            @Field("gcm_token") String gcm_token,
            @Field("user_img") String user_img,
            @Field("login_type") String login_type,
            Callback<ResponseCode> callback
    );
    @GET("/getNews")
    public void getNews(Callback<List<NewsModel>> response);
}
