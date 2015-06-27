package com.example.tripadvisor;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;




import com.example.tripadvisor.Options.Selection;

public class MainActivity extends AppCompatActivity implements SlidingPaneLayout.PanelSlideListener,Selection {
	
	SlidingPaneLayout pane;
	Home myf;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// myf = new Home();
		
		 
		 
		pane = (SlidingPaneLayout) findViewById(R.id.sp);
		pane.setPanelSlideListener(this);
		pane.setParallaxDistance(200);
		pane.openPane();
	}

	@Override
	public void onPanelClosed(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPanelOpened(View arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPanelSlide(View arg0, float arg1) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
        }
        return super.onOptionsItemSelected(item);
        }

	@Override
	public void onSelection(int position) {
		// TODO Auto-generated method stub
		if(position==0)
		{
			Log.i("onSelection", "home");
		//	 myf = new Nearby_place();
            myf= new Home();
		FragmentTransaction transaction = getFragmentManager().beginTransaction();
			transaction.replace(R.id.home,myf,"home");
			transaction.commit();
		//	title="Nearby";
			pane.closePane();
			
		}
		
		if(position==1)
		{
			Log.i("onSelection", "capture the moment");
			
			//Favourites myf = new Favourites();

		//	FragmentTransaction transaction = getFragmentManager().beginTransaction();
			//transaction.replace(R.id.mainframe,myf,"favourites");
		//	transaction.commit();
			//title="My Favourites";
			//pane.closePane();
			
			Intent i = new Intent();
		    i.setClass(this,Camera.class);
		    startActivity(i);
		    pane.closePane();
		}
		
		if(position==2)
		{
			Log.i("onSelection", "Discover Places");
		//	 myf = new Nearby_place();

		//	FragmentTransaction transaction = getFragmentManager().beginTransaction();
			//transaction.replace(R.id.mainframe,myf,"nearby");
			//transaction.commit();
		//	title="Nearby";
			//pane.closePane();
			
			Intent i = new Intent();
		    i.setClass(this,Discover_Places.class);
		    startActivity(i);
		    pane.closePane();
		}
		
		if(position==3)
		{
			Log.i("onSelection", "Emergency ");
		//	 myf = new Nearby_place();

		//	FragmentTransaction transaction = getFragmentManager().beginTransaction();
			//transaction.replace(R.id.mainframe,myf,"nearby");
			//transaction.commit();
		//	title="Nearby";
			//pane.closePane();
			
		}
		
		
	}
}