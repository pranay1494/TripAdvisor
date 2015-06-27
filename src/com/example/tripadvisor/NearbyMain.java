package com.example.tripadvisor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;

public class NearbyMain extends AppCompatActivity implements LocationListener,
		ConnectionCallbacks, OnConnectionFailedListener {

	String type;
	GoogleMap mGoogleMap;
	Spinner mSprPlaceType;
	String[] mPlaceType = null;
	String[] mPlaceTypeName = null;

	protected GoogleApiClient mGoogleApiClient;

	TextView text;

	/**
	 * Represents a geographical location.
	 */
	protected Location mLastLocation;

	double mLatitude = 0;
	double mLongitude = 0;
	String mPlacenames[];
	HashMap<String, String> lmPlace;
	HashMap<String, String> hhmPlace;

	JSONObject jObject;
	String mPlace[];
	int k = 0;
	protected MenuItem mMenu;
	private ListView listView;
	private CustomListAdapter adapter;
	int d;
	Toolbar toolbar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.listentry);
		

        toolbar = (Toolbar) findViewById(R.id.tool_bar); 
        setSupportActionBar(toolbar);   
        
		int status = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(getBaseContext());
		text = (TextView) findViewById(R.id.heloo);
		if (status != ConnectionResult.SUCCESS) {

			int requestCode = 10;
			Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, this,
					requestCode);
			dialog.show();

		} else {

			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if ((networkInfo != null && networkInfo.isConnected())) {
				// Toast.makeText(this, "Data is Enabled in your device",
				// Toast.LENGTH_SHORT).show();
			} else {
				showDataDisabledAlertToUser();
			}
		}
		statusCheck();
		Intent lelo = getIntent();
		d = lelo.getIntExtra("type", 2);
		if (d == 1 && GlobalList.filter == 0) {
			type = "hindu_temple|mosque";
		} else if (d == 2 && GlobalList.filter == 0) {
			type = "bar|restaurant";
		} else if (d == 3 && GlobalList.filter == 0) {
			type = "bank|atm";
		} else if (d == 4 && GlobalList.filter == 0) {
			type = "shopping_mall|clothes";
		} else if (d == 5 && GlobalList.filter == 0) {
			type = "taxi_stand|car_rental";
			
		//	mMenu.setEnabled(false);
		} else {
			type = GlobalList.type;
			GlobalList.filter = 0;
		}
		buildGoogleApiClient();
		listView = (ListView) findViewById(R.id.list);

	}

	public void statusCheck() {
		final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			buildAlertMessageNoGps();

		}

	}

	private void buildAlertMessageNoGps() {
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(
				"Your GPS seems to be disabled, do you want to enable it?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(final DialogInterface dialog,
									final int id) {
								startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton("No", new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();

	}

	private void showDataDisabledAlertToUser() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder
				.setMessage(
						"Data is disabled in your device. Would you like to enable it?")
				.setCancelable(false)
				.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent callDataSettingIntent = new Intent(
										android.provider.Settings.ACTION_SETTINGS);
								startActivity(callDataSettingIntent);
							}
						});
		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});
		AlertDialog alert = alertDialogBuilder.create();
		alert.show();
	}

	protected synchronized void buildGoogleApiClient() {
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(LocationServices.API).build();
	}

	/** A method to download json data from url */
	private String downloadUrl(String strUrl) throws IOException {
		String data = "";
		InputStream iStream = null;
		HttpURLConnection urlConnection = null;
		try {
			URL url = new URL(strUrl);
			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.connect();
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

	@Override
	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();
	}

	@Override
	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		mLastLocation = LocationServices.FusedLocationApi
				.getLastLocation(mGoogleApiClient);
		if (mLastLocation != null) {
			mLatitude = mLastLocation.getLatitude();
			mLongitude = mLastLocation.getLongitude();
			GlobalList.clat = mLatitude;
			GlobalList.clong = mLongitude;
		} else {
		}

		
		StringBuilder sb = new StringBuilder(
				"https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
		sb.append("location=" + mLatitude + "," + mLongitude);
		sb.append("&radius=5000");
		sb.append("&types=" + type);
		sb.append("&sensor=true");
		sb.append("&key=AIzaSyAbhl6HsWRtSSyPot-58p6i6YeXsHUNY6I");

		// Creating a new non-ui thread task to download Google place json
		// data
		PlacesTask placesTask = new PlacesTask();

		// Invokes the "doInBackground()" method of the class PlaceTask
		placesTask.execute(sb.toString());

	}

	private class PlacesTask extends AsyncTask<String, Integer, String> {
		String data = null;

		@Override
		protected String doInBackground(String... url) {
			try {
				data = downloadUrl(url[0]);
			} catch (Exception e) {
				Log.d("Background Task", e.toString());
			}
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			ParserTask parserTask = new ParserTask();

			parserTask.execute(result);
		}

	}

	/** A class to parse the Google Places in JSON format */
	private class ParserTask extends
			AsyncTask<String, Integer, List<HashMap<String, String>>> {
		@Override
		protected List<HashMap<String, String>> doInBackground(
				String... jsonData) {
			PlaceJSONParser placeJsonParser = new PlaceJSONParser();

			try {
				jObject = new JSONObject(jsonData[0]);
				GlobalList.placentry = placeJsonParser.parse(jObject);

			} catch (Exception e) {
				Log.d("Exception", e.toString());
			}
			return GlobalList.placentry;
		}

		@Override
		protected void onPostExecute(List<HashMap<String, String>> list) {
			// mMenu.setVisible(true);
			updatelist();
		}

	}

	public void updatelist() {
		adapter = new CustomListAdapter(this);
		listView.setAdapter(adapter);
		int num = listView.getCount();
		Log.v("count", Integer.toString(num));
		if(num>0)
		text.setText("");
		else
			text.setEnabled(true);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				hhmPlace = GlobalList.placentry.get(arg2);
				String reference = hhmPlace.get("place_id");
				Intent in = new Intent(getApplicationContext(),
						PlaceDetailsActivity.class);

				in.putExtra("reference", reference);
				in.putExtra("pos", arg2);
				startActivity(in);
			}
		});
	}

	// @Override
	// protected void onListItemClick(ListView l, View v, int position, long id)
	// {
	// // TODO Auto-generated method stub
	// super.onListItemClick(l, v, position, id);
	// hhmPlace = GlobalList.placentry.get(position);
	// String reference = hhmPlace.get("place_id");
	// Intent in = new Intent(getApplicationContext(),
	// PlaceDetailsActivity.class);
	//
	// in.putExtra("reference", reference);
	// in.putExtra("pos", position);
	// startActivity(in);
	// }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nearby_main, menu);
		mMenu = menu.getItem(1);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.mapimg) {
			Intent i = new Intent("com.example.tripadvisor.LISTENTRY");
			startActivity(i);
		}
		if (item.getItemId() == R.id.action_find) {
			Intent i = new Intent(NearbyMain.this,
					GridViewExampleActivity.class);
			Log.v("vald", d + "");
			i.putExtra("hello", d);
			startActivity(i);
		}
		return false;
	}

	// @Override
	// protected void onActivityResult(int requestCode, int resultCode, Intent
	// data) {
	// // TODO Auto-generated method stub
	// super.onActivityResult(requestCode, resultCode, data);
	// if(requestCode==2)
	// {
	// type = data.getStringExtra("filter");
	// }
	// }
	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionSuspended(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		if (GlobalList.filter == 99) {
			type = GlobalList.type;
			GlobalList.filter = 0;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// type=GlobalList.type;
	}
}
