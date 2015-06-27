package com.example.tripadvisor;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Home extends Fragment {
	
	
	String[] homelist={"Capture The Moment","Discover Places"};

	Integer[] imgId = {
		      R.drawable.capture,
		      R.drawable.ic_action
		      };
	
	ListView hlist;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v=inflater.inflate(R.layout.home, container, true);
		
		CustomList adapter = new
		        CustomList(getActivity(),homelist,imgId);
		    hlist=(ListView)v.findViewById(R.id.listView1);
		        hlist.setAdapter(adapter);
		        hlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		                @Override
		                public void onItemClick(AdapterView<?> parent, View view,
		                                        int position, long id) {
		                	
		                    Toast.makeText(getActivity().getApplicationContext(), "You Clicked at " +homelist[+ position], Toast.LENGTH_SHORT).show();
		                  //  ( (Selection) getActivity()).onSelection(position);
		                    if(position==1)
		                    {
		                    	 Intent i = new Intent();
		        		    i.setClass(getActivity(),Discover_Places.class);
		        		    startActivity(i);
		                    }
		                    if(position==0)
		                    {
		                    	Intent i = new Intent();
			        		    i.setClass(getActivity(),Camera.class);
			        		    startActivity(i);
		                    }
		                    	
		                    
		                }
		            });
		
		return v;
	}

	
}
