package com.android.helloweather.data;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

public class WeatherData implements Parcelable {

	private Bitmap image;
	private String imageUrl;
	private String location;
	private double windSpeed;
	private int humdity;
	private int currentTemp;
	private String currentStatus;
	private String time;
	private ArrayList<ForecastData> forecasts;
	
	public WeatherData()
	{
		/** 데이터의 초기화 */
		this.image=null;
		this.location=null;
		this.windSpeed=0;
		this.humdity=0;
		this.currentTemp=0;
		this.currentStatus=null;
		this.time=null;
		forecasts=new ArrayList<ForecastData>();
	}
	
	public WeatherData(Parcel in)
	{
		readFromParcel(in);
	}
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.location);
		dest.writeDouble(this.windSpeed);
		dest.writeInt(this.humdity);
		dest.writeInt(this.currentTemp);
		dest.writeString(this.currentStatus);
		dest.writeString(this.time);
	}
	
	private void readFromParcel(Parcel in)
	{
		this.location=in.readString();
		this.windSpeed=in.readDouble();
		this.humdity=in.readInt();
		this.currentTemp=in.readInt();
		this.currentStatus=in.readString();
		this.time=in.readString();
	}
	
	public static final Parcelable.Creator<WeatherData> CREATOR = new Parcelable.Creator<WeatherData>() {

		@Override
		public WeatherData createFromParcel(Parcel source) {
			return new WeatherData(source);
		}

		@Override
		public WeatherData[] newArray(int size) {
			return new WeatherData[size];
		}
	};
	
	/** SETTER */
	
	public void setImage(String imageSource) 
	{
		this.imageUrl=imageSource;
		URL imageURL;
		try {
			imageURL = new URL(imageUrl);
			HttpURLConnection conn = (HttpURLConnection)imageURL.openConnection();             
			BufferedInputStream bis = new BufferedInputStream(conn.getInputStream(), 10240);
			this.image=BitmapFactory.decodeStream(bis);
			bis.close();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setLocation(String location) 
	{
		this.location = location;
	}

	public void setWindSpeed(double windSpeed) 
	{
		this.windSpeed = windSpeed;
	}

	public void setHumdity(int humdity) 
	{
		this.humdity = humdity;
	}

	public void setCurrentTemp(int currentTemp) 
	{
		this.currentTemp = currentTemp;
	}
	
	public void setCurrentStatus(String status)
	{
		this.currentStatus=status;
	}
	
	public void setTime(String time)
	{
		this.time=time;
	}
	
	public void addForecast(ForecastData forecast)
	{
		this.forecasts.add(forecast);
	}
	
	/** GETTER */
	
	public Bitmap getImage() 
	{
		return image;
	}

	public String getLocation() 
	{
		return location;
	}

	public double getWindSpeed() 
	{
		return windSpeed;
	}

	public int getHumdity() 
	{
		return humdity;
	}

	public int getCurrentTemp() 
	{
		return currentTemp;
	}
	
	public String getCurrentStatus()
	{
		return currentStatus;
	}
	
	public String getDate()
	{
		return time;
	}

	public ArrayList<ForecastData> getForecasts()
	{
		return forecasts;
	}
}
