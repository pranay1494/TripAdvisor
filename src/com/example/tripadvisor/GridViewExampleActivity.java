package com.example.tripadvisor;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class GridViewExampleActivity extends Activity {
	/** Called when the activity is first created. */

	private GridviewAdapter mAdapter;
	private ArrayList<String> listCountry;
	private ArrayList<Integer> listFlag;

	private GridView gridView;
	int d;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		//Intent in = new Intent();
		d = getIntent().getIntExtra("hello", 1);
		Log.v("vlue", Integer.toString(d));
		if (d == 2)
			prepareList();
		else if (d == 1)
			prepareList1();
		else if (d == 3)
			prepareList2();
		else if (d == 4)
			prepareList3();
		// prepared arraylist and passed it to the Adapter class
		mAdapter = new GridviewAdapter(this, listCountry, listFlag);

		gridView = (GridView) findViewById(R.id.gridView1);
		gridView.setAdapter(mAdapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// Toast.makeText(GridViewExampleActivity.this,
				// Integer.toString(position), Toast.LENGTH_SHORT).show();
				if (d == 1) {
					GlobalList.type = GlobalList.activities[position];
				} else if (d == 2) {

					GlobalList.type = GlobalList.eatries[position];
				} else if (d == 3) {

					GlobalList.type = GlobalList.atm[position];
				} else if (d == 4) {

					GlobalList.type = GlobalList.shopping[position];
				}
				Log.v("selected", GlobalList.type);
				GlobalList.filter=99;
				finish();
			}
		});
	//	finish();
	}

	public void prepareList() {
		listCountry = new ArrayList<String>();

		listCountry.add("Resturant");
		listCountry.add("Bakery");
		listCountry.add("Cafe");
		listCountry.add("Bar");

		listFlag = new ArrayList<Integer>();
		listFlag.add(R.drawable.rest);
		listFlag.add(R.drawable.bakery);
		listFlag.add(R.drawable.cafe1);
		listFlag.add(R.drawable.bar);
	}

	public void prepareList1() {
		listCountry = new ArrayList<String>();

		listCountry.add("Amusement Park");
		listCountry.add("Aquarium");
		listCountry.add("Art Gallery");
		listCountry.add("Bowling Alley");
		listCountry.add("Camping ground");
		listCountry.add("Casino");
		listCountry.add("Church");
		listCountry.add("Hindu Temple");
		listCountry.add("Mosque");
		listCountry.add("Museum");
		listCountry.add("Night Club");
		listCountry.add("Zoo");
		listFlag = new ArrayList<Integer>();
		listFlag.add(R.drawable.park);
		listFlag.add(R.drawable.aquarium);
		listFlag.add(R.drawable.art);
		listFlag.add(R.drawable.bowling);
		listFlag.add(R.drawable.camping);
		listFlag.add(R.drawable.casino);
		listFlag.add(R.drawable.church);
		listFlag.add(R.drawable.hindu);
		listFlag.add(R.drawable.mosque);
		listFlag.add(R.drawable.museum);
		listFlag.add(R.drawable.night);
		listFlag.add(R.drawable.zoo);
	}

	public void prepareList2() {
		listCountry = new ArrayList<String>();

		listCountry.add("Atm");
		listCountry.add("Bank");

		listFlag = new ArrayList<Integer>();
		listFlag.add(R.drawable.atmm);
		listFlag.add(R.drawable.no);
	}

	public void prepareList3() {
		listCountry = new ArrayList<String>();

		listCountry.add("Shopping Malls");
		listCountry.add("Clothes");
		listCountry.add("Shoes");
		listCountry.add("Supermarket");
		listCountry.add("Jewllery");

		listFlag = new ArrayList<Integer>();
		listFlag.add(R.drawable.mall);
		listFlag.add(R.drawable.clothes);
		listFlag.add(R.drawable.shoes);
		listFlag.add(R.drawable.no);
		listFlag.add(R.drawable.ring);

	}

}