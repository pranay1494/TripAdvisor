package com.example.tripadvisor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerOpenListener;

@SuppressWarnings("deprecation")
public class NearbyActivity extends Activity implements OnClickListener,
		OnDrawerOpenListener {

	@SuppressWarnings("deprecation")
	SlidingDrawer sd;
	int d;
	Button b1;
	Button b2;
	Button b3;
	Button b4;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nearby);

		sd = (SlidingDrawer) findViewById(R.id.slidingDrawer1);
		sd.setOnDrawerOpenListener(this);
		b1 = (Button) findViewById(R.id.Activities);
		b2 = (Button) findViewById(R.id.Eatries);
		b3 = (Button) findViewById(R.id.ATM);
		b4 = (Button) findViewById(R.id.Shopping);
		b1.setOnClickListener(this);
		b2.setOnClickListener(this);
		b3.setOnClickListener(this);
		b4.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		if (arg0.getId() == R.id.Activities) {
			// d = 1;
			Intent in = new Intent(getApplicationContext(), MainActivity.class);
			in.putExtra("type", 1);
			startActivity(in);
		} else if (arg0.getId() == R.id.Eatries) {
			// d = 2;
			Intent in = new Intent(getApplicationContext(), MainActivity.class);
			in.putExtra("type", 2);
			startActivity(in);

		} else if (arg0.getId() == R.id.ATM) {
			Intent in = new Intent(getApplicationContext(), MainActivity.class);
			in.putExtra("type", 3);
			startActivity(in);

		} else {
			Intent in = new Intent(getApplicationContext(), MainActivity.class);
			in.putExtra("type", 4);
			startActivity(in);

		}
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	public void onDrawerOpened() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.nearby, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.cab)
		{
			Intent in = new Intent(getApplicationContext(), MainActivity.class);
			in.putExtra("type", 5);
			startActivity(in);
		}
		return false;
	}
}
