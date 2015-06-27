package com.example.tripadvisor;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class Custom_recycler_view extends RecyclerView {



	

	public Custom_recycler_view(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public Custom_recycler_view(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Custom_recycler_view(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean fling(int velocityX, int velocityY) {
		// TODO Auto-generated method stub
		
		velocityY *=5;
		
		return super.fling(velocityX, velocityY);
	}

}
