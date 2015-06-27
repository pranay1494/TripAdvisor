package com.example.tripadvisor;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;


import com.loopj.android.image.SmartImageView;

public class ImageDisplayActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_display);
		TextView tt = (TextView) findViewById(R.id.tt11);
		ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
		SmartImageView ivImage = (SmartImageView) findViewById(R.id.ivResult);
		ivImage.setImageUrl(result.getFullUrl());
		tt.setText("");
	}

}
