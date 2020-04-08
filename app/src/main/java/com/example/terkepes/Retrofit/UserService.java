package com.example.terkepes.Retrofit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

import com.example.terkepes.Class.BusLineDriver;
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

    @GET("BusLineDriverApi")
    Call<List<BusLineDriver>> getAllBusLineDriver();

    @DELETE("BusLineDriverApi/{vonalbuszsoforId}/")
    Call<BusLineDriver> deleteBLD(@Path("vonalbuszsoforId") int id);
}