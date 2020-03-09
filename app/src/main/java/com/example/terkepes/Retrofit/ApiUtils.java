package com.example.terkepes.Retrofit;

public class ApiUtils {
    private ApiUtils() {}

    public static final String BASE_URL = "http://webtraffic.conveyor.cloud/api/";

    public static UserService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(UserService.class);
    }


}
