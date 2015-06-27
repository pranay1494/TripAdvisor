package com.example.tripadvisor;

import java.util.HashMap;
import java.util.List;

import com.koushikdutta.ion.Ion;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
	private List<HashMap<String, String>> movieItems;
 
    public CustomListAdapter(Activity activity) {
        this.activity = activity;
        this.movieItems = GlobalList.placentry;
    }
 
    @Override
    public int getCount() {
        return movieItems.size();
    }
 
    @Override
    public Object getItem(int location) {
        return movieItems.get(location);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
 
    	String fontPath = "fonts/Pacifico.ttf";
    	 
    	 
        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);
        //convertView.setBackgroundColor(Color.BLACK);
//        if (imageLoader == null)
//            imageLoader = AppController.getInstance().getImageLoader();
//        NetworkImageView thumbNail = (NetworkImageView) convertView
//                .findViewById(R.id.thumbnail);
        ImageView thumbNail =(ImageView) convertView.findViewById(R.id.thumbnail);
        
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView rating = (TextView) convertView.findViewById(R.id.rating);
        TextView genre = (TextView) convertView.findViewById(R.id.genre);
        TextView year = (TextView) convertView.findViewById(R.id.releaseYear);
 
        //Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        // getting movie data for the row
        HashMap<String, String> m = movieItems.get(position);
 
        // thumbnail image
        //thumbNail.setImageUrl(m.getThumbnailUrl(), imageLoader);
        Log.v("url",m.get("icon"));
      // Picasso.with(activity).load(m.get("icon")).into(thumbNail);
        // title
        Ion.with(thumbNail).placeholder(R.drawable.no).error(R.drawable.no).load(m.get("icon"));
        title.setText(m.get("place_name"));
        //title.setTextColor(Color.YELLOW); 
        // rating
        rating.setText("Rating: " + m.get("rating"));
        //rating.setTextColor(Color.YELLOW); 
        // genre
        //Log.v("wow", m.get("open"));
        genre.setText("");
         
        // release year
        year.setText(m.get("distance"));
      //  year.setTextColor(Color.BLUE);
        return convertView;
    }
 
}