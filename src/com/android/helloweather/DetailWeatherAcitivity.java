package com.android.helloweather;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.android.helloweather.data.ForecastData;
import com.android.helloweather.data.WeatherData;
import com.android.helloweather.model.ForecastAdapter;

public class DetailWeatherAcitivity extends SherlockActivity {

	private ActionBar mActionBar;
	private WeatherData weatherData;
	private ArrayList<ForecastData> forecastData;
	private Bitmap image;
	
	private ListView forecastList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailweatherlayout);
		
		mActionBar=getSupportActionBar();
		Intent mIntent=getIntent();
		Bundle bundle=mIntent.getExtras();
		weatherData=bundle.getParcelable("Weather");
		image=bundle.getParcelable("WeatherImage");
		forecastData=mIntent.getParcelableArrayListExtra("Forecast");
		
		mActionBar.setTitle(weatherData.getLocation());
        mActionBar.setDisplayHomeAsUpEnabled(true);
        
        ImageView weatherImage=(ImageView)findViewById(R.id.weather_icon);
        weatherImage.setImageBitmap(image);
        
		TextView currentStatus=(TextView)findViewById(R.id.current_status_text);
		currentStatus.setText(weatherData.getCurrentStatus());
		
		TextView currentTemp=(TextView)findViewById(R.id.current_temp_text);
		currentTemp.setText(weatherData.getCurrentTemp()+"¡ÆC");
		
		TextView time=(TextView)findViewById(R.id.time_text);
		time.setText(weatherData.getDate());
		
		TextView detail=(TextView)findViewById(R.id.detail_text);
		detail.setText("Humidity: "+weatherData.getHumdity()+" Windspeed: "+weatherData.getWindSpeed()+"km/h");
		
		forecastList=(ListView)findViewById(R.id.forecast_list);
		ForecastAdapter mAdapter=new ForecastAdapter(getApplicationContext(), R.layout.forecastlistitem, forecastData);
		forecastList.setAdapter(mAdapter);
	}
	
}
