package com.android.helloweather.data;

import java.io.BufferedInputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class WeatherData {

	private Bitmap image;
	private String location;
	private int minTemp;
	private int maxTemp;
	private int currentTemp;
	
	public WeatherData(String imgSource, String location, int minTemp, int maxTemp, int currentTemp)
	{
		try
		{
			URL imageURL = new URL(imgSource);
			HttpURLConnection conn = (HttpURLConnection)imageURL.openConnection();             
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), 10240);
			image=BitmapFactory.decodeStream(bis);
			bis.close();
		}
		catch(Exception e)
		{
			//TODO: Initialize image source
			image=null;
		}
		
		this.location=location;
		this.minTemp=minTemp;
		this.maxTemp=maxTemp;
		this.currentTemp=currentTemp;
	}
	

	public Bitmap getImage() {
		return image;
	}

	public String getLocation() {
		return location;
	}

	public int getMinTemp() {
		return minTemp;
	}

	public int getMaxTemp() {
		return maxTemp;
	}

	public int getCurrentTemp() {
		return currentTemp;
	}
}
