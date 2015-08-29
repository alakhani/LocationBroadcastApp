package com.example.locationbroadcastapp;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.widget.Toast;

public class LocationBroadcastReceiver extends BroadcastReceiver 
{
	//constant to be used when extracting data from the intent
	public final static String LONGITUDE_DATA = "com.example.locationbroadcastapp.LONGITUDE";
	public final static String LATITUDE_DATA = "com.example.locationbroadcastapp.LATITUDE";
	public final static String PHONE_NUM = "com.example.locationbroadcastapp.PHONE_NUM";

	@Override
	public void onReceive(Context context, Intent intent)
	{
		//indicate that the broadcast has been recieved using a toast
		Toast.makeText(context, "Location Broadcasted.",
				Toast.LENGTH_LONG).show();
		//extract the latitude, logitude and the phone number data from the intent
		CharSequence longData = intent.getCharSequenceExtra(LONGITUDE_DATA);
		CharSequence latData = intent.getCharSequenceExtra(LATITUDE_DATA);
		CharSequence phoneNum = intent.getCharSequenceExtra(PHONE_NUM);
		//create a msseage text to send
		String messageText = latData+ " "+longData;
		//create a sms manager object and pass in the required parameters
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phoneNum.toString(), null, messageText, null, null);
	}

}
