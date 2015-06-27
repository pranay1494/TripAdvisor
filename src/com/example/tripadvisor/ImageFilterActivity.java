package com.example.tripadvisor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class ImageFilterActivity extends Activity {
	
	Spinner sizeSpinner;
	Spinner colorSpinner;
	Spinner typeSpinner;
	Button saveButton;
	EditText etSiteFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_android_filter);
		setupViews();
	}

	private void setupViews() {
		sizeSpinner = (Spinner) findViewById(R.id.spinnerSize);
		colorSpinner = (Spinner) findViewById(R.id.spinnerColorFilter);
		typeSpinner = (Spinner) findViewById(R.id.spinnerImageType);
		saveButton = (Button) findViewById(R.id.btnSave);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
		
	}
	
	public void onSave(View v) {
		
		InputMethodManager imm = (InputMethodManager)getSystemService(
			      Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(etSiteFilter.getWindowToken(), 0);
			
		String size = String.valueOf(sizeSpinner.getSelectedItem());
		if (size.equals("none")) {
			size = "";
		}
		String color = String.valueOf(colorSpinner.getSelectedItem());
		if (color.equals("none")) {
			color = "";
		}
		String type = String.valueOf(typeSpinner.getSelectedItem());
		if (type.equals("none")) {
			type = "";
		}
		String siteFilter = etSiteFilter.getText().toString();
		
		Intent returnIntent = new Intent();
		returnIntent.putExtra("size",size);
		returnIntent.putExtra("color",color);
		returnIntent.putExtra("type",type);
		returnIntent.putExtra("siteFilter",siteFilter);
		setResult(RESULT_OK,returnIntent);     
		finish();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.android_filter, menu);
		return true;
	}

}
