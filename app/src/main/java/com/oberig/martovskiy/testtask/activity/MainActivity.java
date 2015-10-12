package com.oberig.martovskiy.testtask.activity;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.oberig.martovskiy.testtask.R;
import com.oberig.martovskiy.testtask.net.dto.WeatherBody;
import com.oberig.martovskiy.testtask.net.request.GetWeatherRequest;
import com.oberig.martovskiy.testtask.service.OpenWeatherMapService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private final SpiceManager mSpiceManager;


    private TextView mNameTextView;
    private TextView mCountryTextView;
    private TextView mCoordinateTextView;
    private TextView mMainTextView;
    private TextView mDescriptionTextView;
    private ImageView mWeatherImageView;

    private GoogleMap mGoogleMap;

    public MainActivity() {
        mSpiceManager = new SpiceManager(OpenWeatherMapService.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNameTextView = (TextView) findViewById(R.id.info_name_text_view);
        mCountryTextView = (TextView) findViewById(R.id.info_country_text_view);
        mCoordinateTextView = (TextView) findViewById(R.id.info_coordinate_text_view);
        mMainTextView = (TextView) findViewById(R.id.info_main_text_view);
        mDescriptionTextView = (TextView) findViewById(R.id.info_description_text_view);
        mWeatherImageView = (ImageView) findViewById(R.id.info_icon_image_view);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mSpiceManager.start(this);

        final SupportMapFragment fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.ac_main_fragment);
        fragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mGoogleMap = googleMap;
                mGoogleMap.setMyLocationEnabled(true);

                determineLocation();
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSpiceManager.shouldStop();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void getWeather(double latitude, double longitude) {
        mSpiceManager.execute(new GetWeatherRequest(latitude, longitude), new RequestListener<WeatherBody>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Log.v("TEST", spiceException.toString());
            }

            @Override
            public void onRequestSuccess(WeatherBody weatherBody) {
                Log.v("TEST", weatherBody.getName());
                mNameTextView.setText(getString(R.string.name_pattern, weatherBody.getName()));
                mCoordinateTextView.setText(getString(R.string.coordinates_pattern, weatherBody.getCoord().getLon(), weatherBody.getCoord().getLat()));
                mCountryTextView.setText(getString(R.string.country_pattern, weatherBody.getSys().getCountry()));

                Picasso.with(MainActivity.this).load(String.format("http://api.openweathermap.org/img/w/%s.png", weatherBody.getWeather().get(0).getIcon())).into(mWeatherImageView);

                mMainTextView.setText(weatherBody.getWeather().get(0).getMain());
                mDescriptionTextView.setText(weatherBody.getWeather().get(0).getDescription());

            }
        });
    }


    private void determineLocation() {

        final LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        final Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_FINE);

        try {
            locationManager.requestSingleUpdate(criteria, new LocationListener(), Looper.getMainLooper());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_refresh) {
            determineLocation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private final class LocationListener implements android.location.LocationListener {
        @Override
        public void onLocationChanged(Location location) {

            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 14);
            mGoogleMap.animateCamera(cameraUpdate);

            getWeather(location.getLatitude(), location.getLongitude());
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }
}
