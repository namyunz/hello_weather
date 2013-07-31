package com.android.helloweather.model;

import java.util.List;

import com.android.helloweather.R;
import com.android.helloweather.data.WeatherData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MainWeatherAdapter extends ArrayAdapter<WeatherData> {

	private List<WeatherData> objects;
	private Context mContext;

	public MainWeatherAdapter(Context context, int textViewResourceId, List<WeatherData> objects) {
		super(context, textViewResourceId, objects);
		this.mContext=context;
		this.objects=objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView=convertView;
		if(mView==null)
		{
			LayoutInflater mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mInflater.inflate(R.layout.main_list_item, null);
		}
		else
		{
			WeatherData element=objects.get(position);
			if(element!=null)
			{
				ImageView weather_icon=(ImageView)mView.findViewById(R.id.weather_icon);
				TextView location=(TextView)mView.findViewById(R.id.location_text);
				TextView detail=(TextView)mView.findViewById(R.id.detail_text);
				TextView current=(TextView)mView.findViewById(R.id.current_text);
				
				location.setText(element.getLocation());
				detail.setText("Max: "+element.getMaxTemp()+" Min: "+element.getMinTemp());
				current.setText(element.getCurrentTemp());
			}
		}
		
		return mView;
	}
}
