package com.oberig.martovskiy.testtask.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.oberig.martovskiy.testtask.R;
import com.oberig.martovskiy.testtask.adapter.SectionsPagerAdapter;
import com.oberig.martovskiy.testtask.net.dto.WeatherBody;
import com.oberig.martovskiy.testtask.net.request.GetWeatherRequest;
import com.oberig.martovskiy.testtask.service.OpenWeatherMapService;
import com.octo.android.robospice.SpiceManager;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private final SpiceManager mSpiceManager;

    public MainActivity(){
        mSpiceManager = new SpiceManager(OpenWeatherMapService.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());


        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        mSpiceManager.start(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSpiceManager.shouldStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSpiceManager.execute(new GetWeatherRequest(51.5742958, -3.014301497), new RequestListener<WeatherBody>() {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Log.v("TEST", spiceException.toString());
            }

            @Override
            public void onRequestSuccess(WeatherBody weatherBody) {
                Log.v("TEST", weatherBody.getName());

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
