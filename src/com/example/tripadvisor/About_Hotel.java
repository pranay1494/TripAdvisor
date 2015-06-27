package com.example.tripadvisor;

import java.net.URLEncoder;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class About_Hotel extends AppCompatActivity {
	
	String state_name;
	String place_name;
	String place_type;
	TextView plc_nameView;
	TextView plc_typeView;
	TextView plc_addressView;
	TextView plc_roomsView;
	ImageView plc_imgView;
	int imgid;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotel_about);
		Bundle b= getIntent().getExtras();
		state_name= new String();
		state_name=b.getString("state_name");
		place_name = new String();
		place_name=b.getString("place_name");
		place_type = new String();
		place_type=b.getString("place_type");
		imgid=b.getInt("place_photo");
		
		plc_nameView = (TextView) findViewById(R.id.hotel_name);
		plc_nameView.setText(place_name);
		plc_typeView = (TextView) findViewById(R.id.hotel_type);
		plc_typeView.setText(place_type);
		plc_addressView =(TextView) findViewById(R.id.address_text);
		plc_roomsView = (TextView) findViewById(R.id.room_text);
		plc_imgView= (ImageView) findViewById(R.id.hotel_image);
		plc_imgView.setImageResource(imgid);
		
		
DataBaseHelper myDbHelper = new DataBaseHelper(this);
	    
	    try {
			 
	 		myDbHelper.openDataBase();
	 
	 	}catch(SQLException sqle){
	        Log.i("opendataBase","unable to open database");
	 		throw sqle;
	 	}
	    
	    Cursor c = myDbHelper.getHotelRooms(place_name);
		   // int I_length=c.getCount();
			if( c != null && c.moveToFirst());
			{
			Log.i("cursor c",Integer.toString(c.getCount()));
			
				Log.i("cursor c",c.getString(0));
		      plc_roomsView.setText(c.getString(0));
			
			}
			c.close();
			
			
			Cursor c1 = myDbHelper.getHotelAddress(place_name);
			   // int I_length=c.getCount();
				if( c1 != null && c1.moveToFirst());
				{
				Log.i("cursor c",Integer.toString(c1.getCount()));
				
					Log.i("cursor c",c1.getString(0));
			      
				plc_addressView.setText(c1.getString(0));
				}
				c1.close();
				
				
				findViewById(R.id.navigation).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						StringBuilder uri = new StringBuilder("geo:");
						uri.append(place_name);
						uri.append(",");
						uri.append(state_name);
						uri.append("?z=10");

						if (!place_name.equals("")) {
							uri.append("&q=" + URLEncoder.encode(place_name));
						}

						Intent intent = new Intent(Intent.ACTION_VIEW,
								Uri.parse(uri.toString()));
						startActivity(intent);
						
					}
				});
				
				findViewById(R.id.photos).setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent i=new Intent(About_Hotel.this,SearchActivity.class);
						StringBuilder sb = new StringBuilder();
						sb.append(place_name);
						sb.append(",");
						sb.append(state_name);
						i.putExtra("search",sb.toString());
						startActivity(i);
						
					}
				});
				
				
				
			    
			
		
	}
	
	

}
