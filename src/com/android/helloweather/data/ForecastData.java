package com.android.helloweather.data;

import android.os.Parcel;
import android.os.Parcelable;

public class ForecastData implements Parcelable {

	private String day;			// 요일
	private String date;		// 날짜
	private int lowTemp;		// 최저 온도
	private int highTemp; 		// 최고 온도
	private String status;		// 날씨
	
	public ForecastData(Parcel in)
	{
		ReadFromParcel(in);
	}
	
	public ForecastData(String day, String date, int low, int high, String status)
	{
		this.day=day;
		this.date=date;
		this.lowTemp=low;
		this.highTemp=high;
		this.status=status;
	}
	
	@Override
	public int describeContents() 
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(this.day);
		dest.writeString(this.date);
		dest.writeInt(this.lowTemp);
		dest.writeInt(this.highTemp);
		dest.writeString(this.status);
	}
	
	private void ReadFromParcel(Parcel in)
	{
		this.day=in.readString();
		this.date=in.readString();
		this.lowTemp=in.readInt();
		this.highTemp=in.readInt();
		this.status=in.readString();
	}

	public static final Parcelable.Creator<ForecastData> CREATOR = new Parcelable.Creator<ForecastData>() {

		@Override
		public ForecastData createFromParcel(Parcel source) {
			return new ForecastData(source);
		}

		@Override
		public ForecastData[] newArray(int size) {
			return new ForecastData[size];
		}
	};
	
	public String getDay() {
		return day;
	}

	public String getDate() {
		return date;
	}

	public int getLowTemp() {
		return lowTemp;
	}

	public int getHighTemp() {
		return highTemp;
	}

	public String getStatus() {
		return status;
	}
}
