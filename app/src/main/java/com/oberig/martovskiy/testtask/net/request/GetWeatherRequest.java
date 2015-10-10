package com.oberig.martovskiy.testtask.net.request;

import com.oberig.martovskiy.testtask.net.OpenWeatherMapApi;
import com.oberig.martovskiy.testtask.net.dto.WeatherBody;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

public class GetWeatherRequest extends RetrofitSpiceRequest<WeatherBody, OpenWeatherMapApi> {

    private final double mLatitude;
    private final double mLongitude;

    public GetWeatherRequest(double latitude, double longitude) {
        super(WeatherBody.class, OpenWeatherMapApi.class);
        mLatitude = latitude;
        mLongitude = longitude;
    }

    @Override
    public WeatherBody loadDataFromNetwork() throws Exception {
        return getService().getWeather(mLatitude, mLongitude);
    }
}
