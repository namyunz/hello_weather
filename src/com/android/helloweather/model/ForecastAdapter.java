package com.android.helloweather.model;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.helloweather.R;
import com.android.helloweather.data.ForecastData;

public class ForecastAdapter extends ArrayAdapter<ForecastData> {

	private List<ForecastData> objects;
	private Context mContext;

	public ForecastAdapter(Context context, int textViewResourceId, List<ForecastData> objects) {
		super(context, textViewResourceId, objects);
		this.mContext=context;
		this.objects=objects;
	}

	@Override
	public int getCount() {
		return objects.size();
	}

	@Override
	public ForecastData getItem(int position) {
		return objects.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View mView=convertView;
		if(mView==null)
		{
			LayoutInflater mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			mView=mInflater.inflate(R.layout.forecastlistitem, null);
		}
		
		ForecastData element=objects.get(position);
		if(element!=null)
		{
			TextView date=(TextView)mView.findViewById(R.id.date_text);
			TextView status=(TextView)mView.findViewById(R.id.status_text);
			TextView temp=(TextView)mView.findViewById(R.id.temp_text);
			
			date.setText(element.getDate()+" "+element.getDay());
			status.setText(element.getStatus());
			temp.setText("Low: "+element.getLowTemp()+"¡ÆC High: "+element.getHighTemp()+"¡ÆC");
		}
		
		return mView;
	}
	
}
