package com.android.helloweather.data;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import android.os.AsyncTask;
import android.util.Log;

public class WeatherParser extends AsyncTask<String, Integer, ArrayList<WeatherData>> {

	private URLConnection connection;
	private ArrayList<WeatherData> data;
	

	@Override
	protected ArrayList<WeatherData> doInBackground(String... params) {
		
		try
		{
			URL targetUrl=new URL(params[0]);
			connection=targetUrl.openConnection();
			data=new ArrayList<WeatherData>();
			
			InputStream iStream=connection.getInputStream();
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			XmlPullParser parser=factory.newPullParser();
			
			parser.setInput(iStream,"UTF-8");
			int eventType=parser.getEventType();
			
			while(eventType!=XmlPullParser.END_DOCUMENT)
			{
				switch(eventType)
				{
				case XmlPullParser.START_TAG:
				{
					String startTag=parser.getName();
					if(startTag.equals("yweather:location"))
					{
						Log.i("PARSER",parser.getAttributeValue(0));
						Log.i("PARSER",parser.getAttributeValue(1));
						Log.i("PARSER",parser.getAttributeValue(2));
					}
					else if(startTag.equals("yweather:condition"))
					{
						Log.i("PARSER",parser.getAttributeValue(0));
						Log.i("PARSER",parser.getAttributeValue(1));
						Log.i("PARSER",parser.getAttributeValue(2));
					}
					
					break;
				}
				case XmlPullParser.END_TAG:
					//Log.i("PARSER", "END: "+parser.getName());
					break;
				}
				
				eventType=parser.next();
			}
			
			return data;
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
		
		return null;
	}
}
