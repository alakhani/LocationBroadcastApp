package com.example.locationbroadcastapp;

//import com.example.locationmanapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements LocationListener 
{	
	//edit text for holding the phone number of the reciever 
	private EditText messageNumber;
	//creating a reference to the location manager
	LocationManager lm;
	//contants used As keys in the intent
	public final static String LONGITUDE_DATA = "com.example.locationbroadcastapp.LONGITUDE";
	public final static String LATITUDE_DATA = "com.example.locationbroadcastapp.LATITUDE";
	public final static String PHONE_NUM = "com.example.locationbroadcastapp.PHONE_NUM";
	String curr_lat=null;
	String curr_long=null;
	public void BroadCastLocation(View v)
	{
		//get the phone number from the edit text
		messageNumber=(EditText)findViewById(R.id.messageNumber);
		String _messageNumber=messageNumber.getText().toString();

		if(_messageNumber==null || _messageNumber =="" )
		{
			Toast.makeText(this, "Broadcast Intent Detected.",
					Toast.LENGTH_LONG).show();
		}
		else
		{
			//create an intent for broadcast
			Intent i = new Intent("LocationBroadCastIntent");
			//add the longitude data to intent
			i.putExtra(LONGITUDE_DATA, (CharSequence)curr_long);
			//add the latitude data to intent
			i.putExtra(LATITUDE_DATA,(CharSequence)curr_lat);
			//add the phone number to intent
			i.putExtra(PHONE_NUM,(CharSequence)_messageNumber);
			//set the action for the broadcast. This needs to be registered in the manifest file.
			i.setAction("com.example.locationbroadcastapp.LOCATION_INTENT");
			//send the broadcast
			sendBroadcast(i);
		}


	}

	@Override
	public void onProviderDisabled(String provider) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) 
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) 
	{
		// TODO Auto-generated method stub
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//initializing the location manager object
		lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();

		// Getting the name of the provider which is gps in this case
		String provider = lm.getBestProvider(criteria, false);
		
		if(provider!=null && !provider.equals(""))
		{ 
			// Get the location
			Location location = lm.getLastKnownLocation(provider);
			//set the location update frequency
			lm.requestLocationUpdates(provider, 20000, 1, this);

			if(location!=null)
				onLocationChanged(location);
			else
			{
				TextView tvLongitude = (TextView)findViewById(R.id.curr_longitude);
				tvLongitude.setText("Location can't be retrieved");
				Toast.makeText(getBaseContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
			}
		}
		else
		{
			Toast.makeText(getBaseContext(), "Oops something went wrong", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void onLocationChanged(Location location) 
	{
		// Getting TextView curr_longitude
		TextView tvLongitude = (TextView)findViewById(R.id.curr_longitude);

		// Getting TextView curr_latitude
		TextView tvLatitude = (TextView)findViewById(R.id.curr_latitude);

		// Setting Current Longitude
		tvLongitude.setText("Longitude:" + location.getLongitude());
		curr_long="Longitude:" + location.getLongitude();

		// Setting Current Latitude
		tvLatitude.setText("Latitude:" + location.getLatitude() );
		curr_lat="Latitude:" + location.getLatitude();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
