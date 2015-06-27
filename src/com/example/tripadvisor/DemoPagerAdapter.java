package com.example.tripadvisor;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
//import android.support.v4.app.FragmentActivity;

public class DemoPagerAdapter extends FragmentPagerAdapter {

    private int pagerCount = 2;
    String state;
	 AboutFragment fragment;
	 RaboutFragment fragment2;

  //  private Random random = new Random();

    public DemoPagerAdapter(FragmentManager fm,String st) {
        super(fm);
        state=st;
    }

    

	@Override public Fragment getItem(int i) {
		if(i==0)
		{
		  fragment = new AboutFragment();
		 Bundle bundle=new Bundle();
	        //put bundle data here
		 bundle.putString("state",state);
	        fragment.setArguments(bundle);
	        return fragment;
		}
		else
		{
	 fragment2 = new RaboutFragment();
		 Bundle bundle=new Bundle();
	        //put bundle data here
		 bundle.putString("state",state);
	        fragment2.setArguments(bundle);
	        return fragment2;
		}
		
	        
    }

    @Override public int getCount() {
        return pagerCount;
    }
}