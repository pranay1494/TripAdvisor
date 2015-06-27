package com.example.tripadvisor;

import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class RaboutFragment extends Fragment {

	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		String str = this.getArguments().get("state").toString();
  	  Log.i("state",str);
		
		  View v = inflater.inflate(R.layout.rabout, container, false);
		  
		  DataBaseHelper myDbHelper = new DataBaseHelper(this.getActivity());
		    
		    try {
				 
		 		myDbHelper.openDataBase();
		 
		 	}catch(SQLException sqle){
		        Log.i("opendataBase","unable to open database");
		 		throw sqle;
		 	}
		    
		    Cursor c = myDbHelper.getAbout(str);
		  //  int I_length=c.getCount();
			Log.i("cursor c",Integer.toString(c.getCount()));
			if( c != null && c.moveToFirst());
			{
			Log.i("cursor c",Integer.toString(c.getCount()));
			TextView tv=(TextView)v.findViewById(R.id.about_text);
			tv.setText(c.getString(0));
			tv.setMovementMethod(new ScrollingMovementMethod());
			
			}
			c.close();
		    
		  
		return v;
	}

}
