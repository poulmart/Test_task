package com.oberig.martovskiy.testtask.service;

import com.google.gson.Gson;
import com.oberig.martovskiy.testtask.net.OpenWeatherMapApi;
import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

public class OpenWeatherMapService extends RetrofitGsonSpiceService {

    public static final String BASE_API_URL = "http://api.openweathermap.org/data/2.5";
    public static final String APP_ID ="5e502c6c9104e0b076473d739fbef17f";

    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(OpenWeatherMapApi.class);
    }

    @Override
    protected RestAdapter.Builder createRestAdapterBuilder() {
        return new RestAdapter.Builder()
                .setEndpoint(BASE_API_URL)
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        request.addQueryParam("APPID", APP_ID);
                    }
                })
                .setConverter(new GsonConverter(new Gson()));
    }

    @Override
    protected String getServerUrl() {
        return BASE_API_URL;
    }

}