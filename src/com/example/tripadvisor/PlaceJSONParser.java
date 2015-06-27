package com.example.tripadvisor;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Location;
import android.util.Log;

public class PlaceJSONParser {

	// String mPlace[];
	// int k=0;
	// /** Receives a JSONObject and returns a list */
	public List<HashMap<String, String>> parse(JSONObject jObject) {

		JSONArray jPlaces = null;
		try {
			/** Retrieves all the elements in the 'places' array */
			jPlaces = jObject.getJSONArray("results");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		/**
		 * Invoking getPlaces with the array of json object where each json
		 * object represent a place
		 */
		return getPlaces(jPlaces);
	}

	private List<HashMap<String, String>> getPlaces(JSONArray jPlaces) {
		int placesCount = jPlaces.length();
		List<HashMap<String, String>> placesList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> place = null;
		// mPlace = new String[placesCount];
		/** Taking each place, parses and adds to list object */
		for (int i = 0; i < placesCount; i++) {
			try {
				/** Call getPlace with place JSON object to parse the place */
				place = getPlace((JSONObject) jPlaces.get(i));
				placesList.add(place);

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return placesList;
	}

	/** Parsing the Place JSON object */
	private HashMap<String, String> getPlace(JSONObject jPlace) {

		HashMap<String, String> place = new HashMap<String, String>();
		String placeName = "-NA-";
		String vicinity = "-NA-";
		String latitude = "";
		String longitude = "";
		String placeid = "";
		String rating = "-NA-";
		String url = "";
		String international_phone_number = "-NA-";
		String formatted_phone = "-NA-";
		String open="-NA-" ;
		try {
			// Extracting Place name, if available
			if (!jPlace.isNull("name")) {
				placeName = jPlace.getString("name");
				// mPlace[k++] = placeName;
			}
			if (!jPlace.isNull("international_phone_number")) {
				international_phone_number = jPlace
						.getString("international_phone_number");
			}
			Log.v("placephone", international_phone_number);
			if (!jPlace.isNull("vicinity")) {
				vicinity = jPlace.getString("vicinity");
			}
			if (!jPlace.isNull("rating")) {
				rating = jPlace.getString("rating");
			}
			if (!jPlace.isNull("formatted_phone_number")) {
				formatted_phone = jPlace.getString("formatted_phone_number");
			}

			if (!jPlace.isNull("icon")) {
				url = jPlace.getString("icon");
			}
			Log.v("url", url);
			
			latitude = jPlace.getJSONObject("geometry")
					.getJSONObject("location").getString("lat");
			longitude = jPlace.getJSONObject("geometry")
					.getJSONObject("location").getString("lng");

			//open = jPlace.getJSONObject("opening_hours").getString("open_now");
			Log.v("open", open);
			if (!jPlace.isNull("place_id")) {
				placeid = jPlace.getString("place_id");
			}
			double lat = Double.parseDouble(latitude);
			double lng = Double.parseDouble(longitude);
			float[] distance = new float[1];
			// Location currentLocation =
			// PECApplication.getInstance().getLocationClient().getLastLocation();
			Location.distanceBetween(GlobalList.clat, GlobalList.clong, lat,
					lng, distance);
			float disInKm = distance[0] / (float) 1000;

			DecimalFormat df = new DecimalFormat("#.#");
			String formatted = df.format(disInKm);
			StringBuilder sb = new StringBuilder();
			sb.append("-");
			sb.append(formatted);
			sb.append("Km");
			String dist = sb.toString();

			place.put("place_name", placeName);
			place.put("vicinity", vicinity);
			place.put("lat", latitude);
			place.put("lng", longitude);
			place.put("rating", rating);
			place.put("place_id", placeid);
			place.put("distance", dist);
			place.put("icon", url);
			place.put("phone", formatted_phone);
			place.put("international_phone_number", international_phone_number);
			place.put("open", open);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return place;
	}
}
