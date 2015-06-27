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

public class About_Activity extends AppCompatActivity {

	String[] activity_name;
	String[] activity_type;
	String[] activity_open_hrs;
	String state_name;
	String place_name;
	String place_type;
	String place_open;
	String place_fair;
	String place_photo_fair;
	TextView plc_fairView;
	TextView plc_photo_fairView;
	TextView plc_nameView;
	TextView plc_typeView;
	TextView plc_openView;
	TextView plc_aboutView;
	ImageView plc_imgView;
	int imgid;
	String vidid;
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_descr);
		Bundle b= getIntent().getExtras();
		state_name= new String();
		state_name=b.getString("state_name");
		place_name = new String();
		place_name=b.getString("place_name");
		place_type = new String();
		place_type=b.getString("place_type");
		place_fair = new String();
		place_fair=b.getString("place_fair");
		place_photo_fair = new String();
		place_photo_fair=b.getString("place_photo_fair");
		place_open = new String();
		place_open =b.getString("place_open");
		imgid=b.getInt("place_photo");
		
		plc_nameView = (TextView) findViewById(R.id.plc_name);
		plc_typeView = (TextView) findViewById(R.id.plc_type);
		plc_openView = (TextView) findViewById(R.id.op_hrs_time);
		plc_aboutView = (TextView) findViewById(R.id.about_text);
		plc_imgView = (ImageView) findViewById(R.id.plc_image);
		plc_fairView = (TextView) findViewById(R.id.fair_text);
		plc_fairView.setText(place_fair);
		plc_photo_fairView = (TextView) findViewById(R.id.photography_text);
		plc_photo_fairView.setText(place_photo_fair);
		plc_imgView.setImageResource(imgid);
		
DataBaseHelper myDbHelper = new DataBaseHelper(this);
	    
	    try {
			 
	 		myDbHelper.openDataBase();
	 
	 	}catch(SQLException sqle){
	        Log.i("opendataBase","unable to open database");
	 		throw sqle;
	 	}
	    
	    Cursor c = myDbHelper.getActivityAbout(place_name);
	   // int I_length=c.getCount();
		if( c != null && c.moveToFirst());
		{
		Log.i("cursor c",Integer.toString(c.getCount()));
		
			Log.i("cursor c",c.getString(0));
	      plc_aboutView.setText(c.getString(0));
		
		}
		c.close();
		
		
		Cursor c1 = myDbHelper.getActivityVideo(place_name);
		   // int I_length=c.getCount();
			if( c1 != null && c1.moveToFirst());
			{
			Log.i("cursor c",Integer.toString(c1.getCount()));
			
				Log.i("cursor c",c1.getString(0));
		      
			vidid=c1.getString(0);
			}
			c1.close();
		
		
		
		plc_nameView.setText(place_name);
		plc_typeView.setText(place_type);
		plc_openView.setText(place_open);
		
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
				Intent i=new Intent(About_Activity.this,SearchActivity.class);
				StringBuilder sb = new StringBuilder();
				sb.append(place_name);
				sb.append(",");
				sb.append(state_name);
				i.putExtra("search",sb.toString());
				startActivity(i);
				
			}
		});
		
		
		findViewById(R.id.video).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Intent i = new Intent(About_Activity.this,VideoActivity.class);
				i.putExtra("video_id",vidid);
				startActivity(i);
				
			}
		});
	    
	}

	
}
