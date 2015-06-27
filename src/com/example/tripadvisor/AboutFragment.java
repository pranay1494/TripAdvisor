package com.example.tripadvisor;



import java.net.URLEncoder;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class AboutFragment extends Fragment {

 //   private static final String ARG_COLOR = "color";

   // private int mColor;

  /*  public static 	AboutFragment newInstance() {
        AboutFragment fragment = new AboutFragment();
       // Bundle args = new Bundle();
      //  args.putInt(ARG_COLOR, );
        //fragment.setArguments(args);
        return fragment;
    }
*/
	
	String str;
    public AboutFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("aboutFragment","oncreate");
       // if (getArguments() != null) {
        //    mColor = getArguments().getInt(ARG_COLOR);
        //}
      
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	 Log.i("aboutFragment","oncreateview");
    	 
    	// String str = 
    	  str = this.getArguments().get("state").toString();
    	  Log.i("state",str);
    	 
        View v = inflater.inflate(R.layout.color_fragment, container, false);
        
        
 DataBaseHelper myDbHelper = new DataBaseHelper(this.getActivity());
	    
	    try {
			 
	 		myDbHelper.openDataBase();
	 
	 	}catch(SQLException sqle){
	        Log.i("opendataBase","unable to open database");
	 		throw sqle;
	 	}
	    
	    Cursor c = myDbHelper.getDuring(str);
	    int I_length=c.getCount();
		Log.i("cursor c",Integer.toString(c.getCount()));
		if( c != null && c.moveToFirst());
		{
		Log.i("cursor c",Integer.toString(c.getCount()));
		 LinearLayout ll1 = (LinearLayout) v.findViewById(R.id.best_visited_days);
		// LinearLayout ll1 = (LinearLayout) getLayoutInflater().inflate(R.layout.);
		LinearLayout ll2 = (LinearLayout) v.findViewById(R.id.best_visited_days2);
		LinearLayout ll3 = (LinearLayout) v.findViewById(R.id.best_visited_days3);
		LayoutParams layoutParams = new LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
	            LinearLayout.LayoutParams.WRAP_CONTENT);
		for(int i=0;i<I_length;i++)
		{
			TextView tv1 = new TextView(this.getActivity());
			 tv1.setId(i);
			 tv1.setText(c.getString(0));
			 tv1.setTextAppearance(getActivity(), android.R.style.TextAppearance_Medium);
			 tv1.setTextColor(Color.parseColor("#FFFFFF"));
			 tv1.setPadding(20,0,20,0);

			 
			if((i/3)==0)
					{
				 
				 if(i%3==0)
					// layoutParams.gravity = Gravity.CENTER;
				 tv1.setGravity(Gravity.CENTER);
				// tv1.setPadding(5,0,5,0);
				 if(i%3==1)
					 tv1.setGravity(Gravity.LEFT);
					 //layoutParams.gravity = Gravity.LEFT;
				 if(i%3==2)
					 tv1.setGravity(Gravity.RIGHT);
					// layoutParams.gravity = Gravity.RIGHT;
				 tv1.setLayoutParams(layoutParams);
				 ll1.addView(tv1);
				
				Log.i("adding textview dynamically","tv1"); 
				 
				
					}
			
			if((i/3)==1)
					{
				 if(i%3==0)
				 tv1.setGravity(Gravity.CENTER);
				 if(i%3==1)
					 tv1.setGravity(Gravity.LEFT);
				 if(i%3==2)
					 tv1.setGravity(Gravity.RIGHT);
				 tv1.setLayoutParams(layoutParams);
				 ll2.addView(tv1);
				
				Log.i("adding textview dynamically","tv1"); 
					}
			
			if((i/3)==2)
					{
				
				 if(i%3==0)
				 tv1.setGravity(Gravity.CENTER);
				 if(i%3==1)
					 tv1.setGravity(Gravity.LEFT);
				 if(i%3==2)
					 tv1.setGravity(Gravity.RIGHT);
				 tv1.setLayoutParams(layoutParams);
				 ll3.addView(tv1);
				
				Log.i("adding textview dynamically","tv1"); 
				
				
					}

			c.moveToNext();
		}
		}
		c.close();
		
		CardView activity=(CardView) v.findViewById(R.id.activity_card_view);
		activity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Toast.makeText(getActivity(), "Points Of Interest", Toast.LENGTH_SHORT).show();
				Intent i=new Intent(getActivity(),State_Activities.class);
				i.putExtra("state",str);
				startActivity(i);
				
			}
		});
		
		CardView hotels=(CardView) v.findViewById(R.id.hotels_card_view);
		hotels.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Toast.makeText(getActivity(), "Hotels in "+ str, Toast.LENGTH_SHORT).show();
				Intent i=new Intent(getActivity(),State_Hotels.class);
				i.putExtra("state",str);
				startActivity(i);
				
			}
		});
		
		CardView eatries=(CardView) v.findViewById(R.id.eatries_card_view);
		eatries.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Toast.makeText(getActivity(), "Eatries in "+ str, Toast.LENGTH_SHORT).show();
				
			}
		});
		
		CardView getting_here=(CardView) v.findViewById(R.id.getting_here_card_view);
		getting_here.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				Toast.makeText(getActivity(), "Route To " + str, Toast.LENGTH_SHORT).show();
				
				StringBuilder uri = new StringBuilder("geo:");
				
				uri.append(str);
				uri.append("?z=10");

				if (!str.equals("")) {
					uri.append("&q=" + URLEncoder.encode(str));
				}

				Intent intent = new Intent(Intent.ACTION_VIEW,
						Uri.parse(uri.toString()));
				startActivity(intent);
				
				
			}
		});
		
		
        
        //v.setBackgroundColor(mColor);

        return v;
    }
}
