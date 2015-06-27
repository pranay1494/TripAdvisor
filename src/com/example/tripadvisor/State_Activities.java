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

public class State_Activities extends AppCompatActivity {
	
	RecyclerView mRecyclerView;
	String[] activity_name;
	String[] activity_type;
	String[] activity_open_hrs;
	
	String[] activity_fair;
	String[] activity_photography;
	Activity_Card_Holder acd;
	String str;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle b= getIntent().getExtras();
		str= b.getString("state");
		
		setContentView(R.layout.activities);
DataBaseHelper myDbHelper = new DataBaseHelper(this);
	    
	    try {
			 
	 		myDbHelper.openDataBase();
	 
	 	}catch(SQLException sqle){
	        Log.i("opendataBase","unable to open database");
	 		throw sqle;
	 	}
	    
	    activity_name = new String[13];
	    activity_type = new String[13];
	    activity_open_hrs = new String[13];
	    activity_fair = new String[13];
	    activity_photography = new String[13];
	    
	    Cursor c = myDbHelper.getActivityName(str);
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
		
		
		Cursor c1 = myDbHelper.getActivityCategory(str);
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
		
		
		Cursor c2 = myDbHelper.getActivityOpenHrs(str);
	    int I_length2=c2.getCount();
		if( c2 != null && c2.moveToFirst());
		{
		Log.i("cursor c",Integer.toString(c2.getCount()));
		for(int i=0;i<I_length2;i++)
		{
		activity_open_hrs[i]=c2.getString(0);
		c2.moveToNext();
		}
		}
		c2.close();
		
		Cursor c3 = myDbHelper.getActivityFair(str);
	    int I_length3=c3.getCount();
		if( c3 != null && c3.moveToFirst());
		{
		Log.i("cursor c",Integer.toString(c3.getCount()));
		for(int i=0;i<I_length3;i++)
		{
		activity_fair[i]=c3.getString(0);
		c3.moveToNext();
		}
		}
		c3.close();
		
		Cursor c4 = myDbHelper.getActivityPhotography(str);
	    int I_length4=c4.getCount();
		if( c4 != null && c4.moveToFirst());
		{
		Log.i("cursor c",Integer.toString(c4.getCount()));
		for(int i=0;i<I_length4;i++)
		{
		activity_photography[i]=c4.getString(0);
		c4.moveToNext();
		}
		}
		c4.close();
		
		
		
		
		
	    mRecyclerView = (RecyclerView)findViewById(R.id.activitesView);
	    mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
	    mRecyclerView.setItemAnimator(new DefaultItemAnimator());
	    acd=new Activity_Card_Holder(I_length,str,activity_fair,activity_photography,activity_name,activity_type,activity_open_hrs,R.layout.activities_card, this);
	    mRecyclerView.setAdapter(acd);
	}
	
	

}
