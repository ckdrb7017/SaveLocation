package com.jakchang.savelocation.Network;

import com.jakchang.savelocation.Interface.RetrofitInterface;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Service {
    Retrofit retrofit;
    RetrofitInterface service;
    Retrofit2Service retrofit2Service;
    public Retrofit2Service(){
        retrofit  = new Retrofit.Builder()
                .baseUrl("https://ckdrb7067.cafe24.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(createOkHttpClient())
                .build();
    }

    public Retrofit2Service getInstance(){
        if(retrofit2Service==null){
            retrofit2Service = new Retrofit2Service();
        }
        return  retrofit2Service;
    }
    public RetrofitInterface getService(){
        if(service==null){
            service= retrofit.create(RetrofitInterface.class);
        }
        return service;
    }


    private OkHttpClient createOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel( HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(interceptor);
        return builder.build();
    }

}
