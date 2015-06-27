package com.example.tripadvisor;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class State_Hotels extends AppCompatActivity {
	
	String[] activity_name;
	String[] activity_type;
	String str;
	RecyclerView mRecyclerView;
	Hotel_card_holder acd;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hotels);
		
		Bundle b= getIntent().getExtras();
		str= b.getString("state");
		
DataBaseHelper myDbHelper = new DataBaseHelper(this);
	    
	    try {
			 
	 		myDbHelper.openDataBase();
	 
	 	}catch(SQLException sqle){
	        Log.i("opendataBase","unable to open database");
	 		throw sqle;
	 	}
	    
	    activity_name = new String[10];
	    activity_type = new String[10];
	    
	    
	    Cursor c = myDbHelper.getHotelName(str);
	    int I_length=c.getCount();
		if( c != null && c.moveToFirst());
		{
		Log.i("cursor c",Integer.toString(c.getCount()));
		for(int i=0;i<I_length;i++)
		{
			Log.i("cursor c",c.getString(0));
		activity_name[i]=c.getString(0);
		Log.i("cursor c",activity_name[i]);
		c.moveToNext();
		}
		}
		c.close();
		
		
		Cursor c1 = myDbHelper.getHotelType(str);
	    int I_length1=c1.getCount();
		if( c1 != null && c1.moveToFirst());
		{
		Log.i("cursor c",Integer.toString(c1.getCount()));
		for(int i=0;i<I_length1;i++)
		{
		activity_type[i]=c1.getString(0);
		c1.moveToNext();
		}
		}
		c1.close();
		
		
		mRecyclerView = (RecyclerView)findViewById(R.id.hotelsView);
	    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
	    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
	    acd=new Hotel_card_holder(I_length,str,activity_name,activity_type,R.layout.hotel_card, this);
	    mRecyclerView.setAdapter(acd);
	    
	}
	
	

}
