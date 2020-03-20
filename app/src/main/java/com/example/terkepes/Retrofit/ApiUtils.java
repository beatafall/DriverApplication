package com.example.terkepes.Retrofit;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://192.168.0.126:45455/api/";

    public static UserService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }


}
