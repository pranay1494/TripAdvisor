package com.example.tripadvisor;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	
	//EditText etQuery;
	GridView gvResults;
//	Button btnSearch;
	//Button btnFilters;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultsArrayAdapter imageAdapter;
	
	String size;
	String color;
	String type;
	String siteFilter;
	String query;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b= getIntent().getExtras();
	     query=b.getString("search");
		setContentView(R.layout.activity_search);
		
		setupViews();
		onImageSearch();
		imageAdapter = new ImageResultsArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View parent, int position,
					long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
	}

	private void setupViews() {
	//	etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		//btnSearch = (Button) findViewById(R.id.btnSearch);
		//btnFilters = (Button) findViewById(R.id.btnFilters);
		
	}
	
	public void onFilter(View v) {
		Intent i = new Intent(getApplicationContext(), ImageFilterActivity.class);
		startActivityForResult(i, 1);
	}
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		  if (requestCode == 1) {

		     if(resultCode == RESULT_OK){      
		         size = data.getStringExtra("size"); 
		         color = data.getStringExtra("color");
		         type = data.getStringExtra("type");
		         siteFilter = data.getStringExtra("siteFilter");
		     }
		     if (resultCode == RESULT_CANCELED) {    
		         size = "";
		         color = "";
		         type = "";
		         siteFilter = "";
		     }
		  }
		}
	
	public void onImageSearch() {
	//	String query = "Akshardham";
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_LONG).show();
		
		//InputMethodManager imm = (InputMethodManager)getSystemService(
			//      Context.INPUT_METHOD_SERVICE);
		//	imm.hideSoftInputFromWindow(etQuery.getWindowToken(), 0);
			
		AsyncHttpClient client = new AsyncHttpClient();
		String queryString = "https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" 
				+ "start=" + 0 + "&v=1.0&q=" + Uri.encode(query);
		if (!isNullOrEmpty(size)){
			queryString = queryString + "&imgsz=" + size;
		}
		if (!isNullOrEmpty(color)){
			queryString = queryString + "&imgcolor=" + color;
		}
		if (!isNullOrEmpty(type)){
			queryString = queryString + "&imgtype=" + type;
		}
		if (!isNullOrEmpty(siteFilter)){
			queryString = queryString + "&as_sitesearch=" + siteFilter;
		}
		client.get(queryString, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults  = null;
				try {
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					Log.d("DEBUG", imageResults.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	boolean isNullOrEmpty(String s) {
		return (s == null || s.isEmpty());
	}

}
