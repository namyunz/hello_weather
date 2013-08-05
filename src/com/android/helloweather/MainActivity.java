package com.android.helloweather;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.android.helloweather.data.ForecastData;
import com.android.helloweather.data.WeatherData;
import com.android.helloweather.model.MainWeatherAdapter;

public class MainActivity extends SherlockActivity {

	private ActionBar mActionBar;
	private ListView mListView;
	private WeatherParser parser=new WeatherParser();
	
	private ArrayList<WeatherData> weatherData=new ArrayList<WeatherData>();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainlayout);
        
        mActionBar=getSupportActionBar();
        mActionBar.setTitle(R.string.app_name);
        
        mListView=(ListView)findViewById(R.id.weather_list);
        mListView.setAdapter(new MainWeatherAdapter(getApplicationContext(), 
        											R.layout.main_list_item, 
        											new ArrayList<WeatherData>()));
        WeatherListViewClickListener clickListener=new WeatherListViewClickListener();
        mListView.setOnItemClickListener(clickListener);
        
        String[] arg={"http://weather.yahooapis.com/forecastrss?w=1132599&u=c",
        			  "http://weather.yahooapis.com/forecastrss?w=2345968&u=c",
        			  "http://weather.yahooapis.com/forecastrss?w=2345963&u=c",
        			  "http://weather.yahooapis.com/forecastrss?w=23701285&u=c"};
        parser.execute(arg);
    }

	@Override
	public boolean onCreateOptionsMenu(com.actionbarsherlock.view.Menu menu) {
		MenuInflater inflater=getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.action_add:
			// Do something...
			return true;
		case R.id.action_settings:
			// Do something...
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private class WeatherListViewClickListener implements OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parentView, View clickedView, int position, long id) 
		{
			Intent mIntent=new Intent(getApplicationContext(), DetailWeatherAcitivity.class);
			mIntent.putExtra("Weather", weatherData.get(position));
			mIntent.putExtra("WeatherImage", weatherData.get(position).getImage());
			mIntent.putParcelableArrayListExtra("Forecast", weatherData.get(position).getForecasts());
			startActivity(mIntent);
		}
	}
		
	private class WeatherParser extends AsyncTask<String, WeatherData, ArrayList<WeatherData>> {

		private URLConnection connection;
		
		@Override
		protected ArrayList<WeatherData> doInBackground(String... params) 
		{
			for (String url : params) {
				try
				{
					URL targetUrl=new URL(url);
					connection=targetUrl.openConnection();
					
					InputStream iStream=connection.getInputStream();
					XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
					XmlPullParser parser=factory.newPullParser();
					
					parser.setInput(iStream,"UTF-8");
					int eventType=parser.getEventType();
					
					boolean isDescription=false;
					boolean isItem=false;
					
					WeatherData element=null;
					
					while(eventType!=XmlPullParser.END_DOCUMENT)
					{
						switch(eventType)
						{
							case XmlPullParser.START_TAG:
							{
								String startTag=parser.getName();
								if(startTag.equals("yweather:location"))
								{
									element=new WeatherData();
									// Location Data
									element.setLocation(parser.getAttributeValue(0));
								}
								else if(startTag.equals("yweather:wind"))
								{
									element.setWindSpeed(Double.parseDouble(parser.getAttributeValue(2)));
								}
								else if(startTag.equals("yweather:atmosphere"))
								{
									element.setHumdity(Integer.parseInt(parser.getAttributeValue(0)));
								}
								else if(startTag.equals("yweather:condition"))
								{
									element.setCurrentStatus(parser.getAttributeValue(0));
									element.setCurrentTemp(Integer.parseInt(parser.getAttributeValue(2)));
									element.setTime(parser.getAttributeValue(3));
								}
								else if(startTag.equals("item"))
								{
									isItem=true;
								}
								else if(isItem&&startTag.equals("description"))
								{
									isDescription=true;
								}
								else if(startTag.equals("yweather:forecast"))
								{
									ForecastData forecast=new ForecastData(parser.getAttributeValue(0), 
																		   parser.getAttributeValue(1), 
																		   Integer.parseInt(parser.getAttributeValue(2)), 
																		   Integer.parseInt(parser.getAttributeValue(3)), 
																		   parser.getAttributeValue(4));
									element.addForecast(forecast);
								}
								
								break;
							}
							case XmlPullParser.TEXT:
							{
								if(isDescription)
								{
									String rawHtml=parser.getText();
									
									int start=rawHtml.indexOf("http://");
									int end=rawHtml.indexOf(".gif");
									if(start!=-1&&end!=-1)
									{
										String result=rawHtml.substring(start, end+4);
										element.setImage(result);
										
										isDescription=false;
									}
								}
								break;
							}
							case XmlPullParser.END_TAG:
							{
								String endTag=parser.getName();
								if(endTag.equals("item"))
								{
									publishProgress(element);
									isItem=false;
								}
								break;
							}
						}
						
						eventType=parser.next();
					}
				}
				catch (MalformedURLException e) 
				{
					Log.e("PARSE", "MAL "+e.getMessage());
				} 
				catch (IOException e) 
				{
					Log.e("PARSE","IO1 "+e.getMessage());
				}
				catch (XmlPullParserException e) 
				{
					Log.e("PARSE","Xml Parser Error");
				}
			}
			
			return null;
		}
		

		@Override
		protected void onProgressUpdate(WeatherData... values) {
			if(values[0]!=null)
			{
				((MainWeatherAdapter)mListView.getAdapter()).add(values[0]);
				weatherData.add(values[0]);
			}
		}

		@Override
		protected void onPostExecute(ArrayList<WeatherData> result) {
			Toast.makeText(getApplicationContext(), "Done!", Toast.LENGTH_SHORT).show();
		}
	}
	
}
