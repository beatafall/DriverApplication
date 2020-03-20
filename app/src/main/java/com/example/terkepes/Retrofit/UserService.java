package com.example.terkepes.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import com.example.terkepes.Class.Driver;
import com.example.terkepes.Class.MessageType;
import com.example.terkepes.Class.Messages;
import com.google.gson.JsonObject;

import java.util.List;

public interface UserService {
    String BASE_URL = "http://192.168.0.126:45455/api/";

    @GET("driver")
    Call<List<Driver>> getDrivers();

    @GET("message")
    Call<List<Messages>> getMessages();

    @GET("messageType")
    Call<List<MessageType>> getMessageType();

    @POST("message/")
    @FormUrlEncoded
    Call<Messages> sendMessage(@Field("jelzesId") String messageTypeId,
                               @Field("soforId") String driverId, @Field("vonalId") String line,
                               @Field("buszId") String bus, @Field("datum") String date,
                               @Field("lon") String lon, @Field("lat") String lat);


//
//    @FormUrlEncoded
//    @POST("message/")
//    Call<Messages> sendMessage(@Body Messages messages);

//    @Headers("Content-Type: application/json")
//    @POST("message")
//    Call<Messages> sendMessage(@Body Messages messages);

//    @POST("message")
//    @FormUrlEncoded
//    Call<ResponseBody> send(@Body String message);

}