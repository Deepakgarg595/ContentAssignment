package com.example.content.retrofit;


import com.example.content.constants.Constant;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static APIService apiRestInterfaces ;

    public static APIService getClient() {

        final OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(180, TimeUnit.SECONDS);

            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            okHttpClient.addInterceptor(interceptor);


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (apiRestInterfaces == null) {
            Retrofit client = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(okHttpClient.build())
                    .build();
            apiRestInterfaces = client.create(APIService.class);
        }
        return apiRestInterfaces ;
    }
}