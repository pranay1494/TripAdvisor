package com.example.tripadvisor;

import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PlaceDetailsJSONParser {
	
	/** Receives a JSONObject and returns a list */
	public HashMap<String,String> parse(JSONObject jObject){		
		
		JSONObject jPlaceDetails = null;
		try {			
			/** Retrieves all the elements in the 'places' array */
			jPlaceDetails = jObject.getJSONObject("result");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		/** Invoking getPlaces with the array of json object
		 * where each json object represent a place
		 */
		return getPlaceDetails(jPlaceDetails);
	}
	
	
	/** Parsing the Place Details Object object */
	private HashMap<String, String> getPlaceDetails(JSONObject jPlaceDetails){
		
		
		HashMap<String, String> hPlaceDetails = new HashMap<String, String>();
		
		String name = "-NA-";
		String icon = "-NA-";
		String vicinity="-NA-";
		String latitude="";
		String longitude="";
		String formatted_address="-NA-";
		String formatted_phone="-NA-";
		String website="-NA-";
		String rating="-NA-";
		String international_phone_number="-NA-";
		String url="-NA-";
		
		try {

			GlobalList.place1 = new Place();
			// Extracting Place name, if available
			if(!jPlaceDetails.isNull("name")){
				name = jPlaceDetails.getString("name");
			}
			
			// Extracting Icon, if available
			if(!jPlaceDetails.isNull("icon")){
				icon = jPlaceDetails.getString("icon");
			}
			
			// Extracting Place Vicinity, if available
			if(!jPlaceDetails.isNull("vicinity")){
				vicinity = jPlaceDetails.getString("vicinity");
			}	
			
			// Extracting Place formatted_address, if available
			if(!jPlaceDetails.isNull("formatted_address")){
				formatted_address = jPlaceDetails.getString("formatted_address");
			}
			
			// Extracting Place formatted_phone, if available
			if(!jPlaceDetails.isNull("formatted_phone_number")){
				formatted_phone = jPlaceDetails.getString("formatted_phone_number");
			}	
			
			// Extracting website, if available
			if(!jPlaceDetails.isNull("website")){
				website = jPlaceDetails.getString("website");
			}	
			
			// Extracting rating, if available
			if(!jPlaceDetails.isNull("rating")){				
				rating = jPlaceDetails.getString("rating");
			}
			
			// Extracting rating, if available
			if(!jPlaceDetails.isNull("international_phone_number")){				
				international_phone_number = jPlaceDetails.getString("international_phone_number");
			}
			
			// Extracting url, if available
			if(!jPlaceDetails.isNull("url")){				
				url = jPlaceDetails.getString("url");
			}

			if(!jPlaceDetails.isNull("photos")){
				JSONArray photos = jPlaceDetails.getJSONArray("photos");
				GlobalList.place1.mPhotos = new Photo[photos.length()];
				for(int i=0;i<photos.length();i++){
					GlobalList.place1.mPhotos[i] = new Photo();
					GlobalList.place1.mPhotos[i].mWidth = ((JSONObject)photos.get(i)).getInt("width");
					GlobalList.place1.mPhotos[i].mHeight = ((JSONObject)photos.get(i)).getInt("height");
					GlobalList.place1.mPhotos[i].mPhotoReference = ((JSONObject)photos.get(i)).getString("photo_reference");
					JSONArray attributions = ((JSONObject)photos.get(i)).getJSONArray("html_attributions");
					GlobalList.place1.mPhotos[i].mAttributions = new Attribution[attributions.length()];
					for(int j=0;j<attributions.length();j++){
						GlobalList.place1.mPhotos[i].mAttributions[j] = new Attribution();
						GlobalList.place1.mPhotos[i].mAttributions[j].mHtmlAttribution = attributions.getString(j);
					}					
				}
			}

			
			latitude = jPlaceDetails.getJSONObject("geometry").getJSONObject("location").getString("lat");
			longitude = jPlaceDetails.getJSONObject("geometry").getJSONObject("location").getString("lng");
			
			
			hPlaceDetails.put("name", name);
			hPlaceDetails.put("icon", icon);			
			hPlaceDetails.put("vicinity", vicinity);
			hPlaceDetails.put("lat", latitude);
			hPlaceDetails.put("lng", longitude);
			hPlaceDetails.put("formatted_address", formatted_address);
			hPlaceDetails.put("formatted_phone", formatted_phone);
			hPlaceDetails.put("website", website);
			hPlaceDetails.put("rating", rating);
			hPlaceDetails.put("international_phone_number", international_phone_number);
			hPlaceDetails.put("url", url);
			
		} catch (JSONException e) {			
			e.printStackTrace();
		}
				
		return hPlaceDetails;
	}
}
