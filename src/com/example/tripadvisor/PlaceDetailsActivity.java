package com.example.tripadvisor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.HashMap;

import org.json.JSONObject;

import com.koushikdutta.ion.Ion;

import android.content.Intent;
import android.graphics.Typeface;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceDetailsActivity extends FragmentActivity {
	// WebView mWvPlaceDetails;

	TextView lbl_name;
	TextView lbl_address;
	TextView lbl_vicinity;
	TextView lbl_phone;
	TextView lbl_rating;
	TextView lbl_url;
	TextView lbl_web;
	ImageView img;
	double mLat = 0;
	double mLong = 0;
	RatingBar ratingBar;
	String reference;
	String calling;
	String formatted_phone;
	String dist;
	HashMap<String, String> llmPlace;
	Typeface tf ;
	Typeface tf1 ;
	protected ProgressBar mProgressBar;


	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.single_place);
		String fontPath = "fonts/KaushanScript-Regular.otf";
		String fontPath1 = "fonts/Sofia-Regular.otf";
	  //  ActionBar actionBar = getActionBar();
	    //sactionBar.show();
		lbl_name = (TextView) findViewById(R.id.name);
		lbl_address = (TextView) findViewById(R.id.address);
		lbl_phone = (TextView) findViewById(R.id.phone);
		lbl_rating = (TextView) findViewById(R.id.rating);
		lbl_url = (TextView) findViewById(R.id.url);
		lbl_web = (TextView) findViewById(R.id.website);
		img = (ImageView) findViewById(R.id.imageView1);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		tf = Typeface.createFromAsset(getAssets(), fontPath);
		tf1 = Typeface.createFromAsset(getAssets(), fontPath1);
		
		reference = getIntent().getStringExtra("reference");
		Intent i = getIntent();
		int position = i.getIntExtra("pos", 0);
		llmPlace = GlobalList.placentry.get(position);
		mLat = Double.parseDouble(llmPlace.get("lat"));
		mLong = Double.parseDouble(llmPlace.get("lng"));

		float[] distance = new float[1];
		// Location currentLocation =
		// PECApplication.getInstance().getLocationClient().getLastLocation();
		Location.distanceBetween(GlobalList.clat, GlobalList.clong, mLat, mLong, distance);
		float disInKm = distance[0] / (float) 1000;

		DecimalFormat df = new DecimalFormat("#.#");
		String formatted = df.format(disInKm);
		StringBuilder sb1 = new StringBuilder();
		sb1.append("-");
		sb1.append(formatted);
		sb1.append("Km");
		dist = sb1.toString();

		StringBuilder sb = new StringBuilder(
				"https://maps.googleapis.com/maps/api/place/details/json?");
		sb.append("placeid=" + reference);
		sb.append("&sensor=true");
		sb.append("&key=AIzaSyAbhl6HsWRtSSyPot-58p6i6YeXsHUNY6I");

		mProgressBar.setVisibility(View.VISIBLE);
		// Creating a new non-ui thread task to download Google place details
		PlacesTask placesTask = new PlacesTask();

		// Invokes the "doInBackground()" method of the class PlaceTask
		placesTask.execute(sb.toString());
		findViewById(R.id.action_a).setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		        Toast.makeText(PlaceDetailsActivity.this, "Clicked on Navigation", Toast.LENGTH_SHORT).show();
				sendToActionIntent();
		      }
		    });
		 
		 findViewById(R.id.action_b).setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		        Toast.makeText(PlaceDetailsActivity.this, "Clicked on Phone", Toast.LENGTH_SHORT).show();
		        call();
		      }
		    });
		 
		 findViewById(R.id.action_c).setOnClickListener(new OnClickListener() {
		      @Override
		      public void onClick(View v) {
		        Toast.makeText(PlaceDetailsActivity.this, "Clicked on More_Info", Toast.LENGTH_SHORT).show();
		        photo();
		      }
		    });
		 
	};

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);

			// Creating an http connection to communicate with url
			urlConnection = (HttpURLConnection) url.openConnection();

			// Connecting to url
			urlConnection.connect();

			// Reading data from url
			iStream = urlConnection.getInputStream();

			BufferedReader br = new BufferedReader(new InputStreamReader(
					iStream));

			StringBuffer sb = new StringBuffer();

			String line = "";
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}

			data = sb.toString();
			br.close();

		} catch (Exception e) {
			Log.d("Exception while downloading url", e.toString());
		} finally {
			iStream.close();
			urlConnection.disconnect();
		}

		return data;
	}

	/** A class, to download Google Place Details */
	private class PlacesTask extends AsyncTask<String, Integer, String> {

		String data = null;

		// Invoked by execute() method of this object
		@Override
		protected String doInBackground(String... url) {
			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(String result) {
			ParserTask parserTask = new ParserTask();

			// Start parsing the Google place details in JSON format
			// Invokes the "doInBackground()" method of the class ParseTask
			parserTask.execute(result);
		}
	}

	/** A class to parse the Google Place Details in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, HashMap<String, String>> {

		JSONObject jObject;

		// Invoked by execute() method of this object
		@Override
		protected HashMap<String, String> doInBackground(String... jsonData) {

			HashMap<String, String> hPlaceDetails = null;
			PlaceDetailsJSONParser placeDetailsJsonParser = new PlaceDetailsJSONParser();

			try {
				jObject = new JSONObject(jsonData[0]);

				// Start parsing Google place details in JSON format
				hPlaceDetails = placeDetailsJsonParser.parse(jObject);

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return hPlaceDetails;
		}

		// Executed after the complete execution of doInBackground() method
		@Override
		protected void onPostExecute(HashMap<String, String> hPlaceDetails) {

			mProgressBar.setVisibility(View.INVISIBLE);

			String name = hPlaceDetails.get("name").toUpperCase();
			String icon = hPlaceDetails.get("icon");
			String vicinity = hPlaceDetails.get("vicinity");
			String formatted_address = hPlaceDetails.get("formatted_address");
			formatted_phone = hPlaceDetails.get("formatted_phone").trim();
			calling = formatted_phone;
			String website = hPlaceDetails.get("website");
			String rating = hPlaceDetails.get("rating");
			String url = hPlaceDetails.get("url");
			lbl_name.setText(name);
			lbl_name.setTypeface(tf);
			lbl_address.setText(formatted_address);
			lbl_address.setTypeface(tf1);
			lbl_phone.setText(formatted_phone);
			lbl_phone.setTypeface(tf1);
			lbl_rating.setText("Rating "+rating);
			lbl_rating.setTypeface(tf1);
			lbl_url.setText(url);
			lbl_web.setText(website);
			Log.v("before", rating);
			if (!rating.equalsIgnoreCase("-NA-")) {
				Log.v("rating", rating);
				ratingBar = (RatingBar) findViewById(R.id.ratingBar);
				float f = Float.parseFloat(rating);
				Log.v("rating", rating);
				ratingBar.setRating(f);
			}
			//Picasso.with(getApplicationContext()).load(icon).into(img);
			Ion.with(img).placeholder(R.drawable.no).error(R.drawable.no).load(icon);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.single, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.menu_goto_action) {
			sendToActionIntent();
		}
		if (item.getItemId() == R.id.call) {
			call();
		}
		if (item.getItemId() == R.id.photo) {
			photo();
		}
		return false;
	}

	private void photo() {
		// TODO Auto-generated method stub
		DisplayMetrics dm = new DisplayMetrics();

		// Getting the screen display metrics
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// Creating a dialog fragment to display the photo
		PlaceDialogFragment dialogFragment = new PlaceDialogFragment(dm);

		// Getting a reference to Fragment Manager
		FragmentManager fm = getSupportFragmentManager();

		// Starting Fragment Transaction
		android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();

		// Adding the dialog fragment to the transaction
		ft.add(dialogFragment, "TAG");

		// Committing the fragment transaction
		ft.commit();

	}

	private void call() {
		try {
			Log.v("phone", calling);
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + formatted_phone));
			startActivity(callIntent);
		} catch (Exception activityException) {
			activityException.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void sendToActionIntent() {
		StringBuilder uri = new StringBuilder("geo:");
		uri.append(mLat);
		uri.append(",");
		uri.append(mLong);
		uri.append("?z=10");

		if (!llmPlace.get("place_name").equals("")) {
			uri.append("&q=" + URLEncoder.encode(llmPlace.get("place_name")));
		}

		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse(uri.toString()));
		startActivity(intent);
	}
}