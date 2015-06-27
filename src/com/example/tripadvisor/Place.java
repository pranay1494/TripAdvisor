package com.example.tripadvisor;

import android.os.Parcel;
import android.os.Parcelable;

public class Place implements Parcelable{
	// Latitude of the place
	// Photos of the place
	// Photo is a Parcelable class
	Photo[] mPhotos={};
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}		
	
	/** Writing Place object data to Parcel */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelableArray(mPhotos, 0);	
	}
	
	public Place(){		
	}
	
	/** Initializing Place object from Parcel object */
	private Place(Parcel in){
		this.mPhotos = (Photo[])in.readParcelableArray(Photo.class.getClassLoader());				
	}
	
	
	/** Generates an instance of Place class from Parcel */
	public static final Parcelable.Creator<Place> CREATOR = new Parcelable.Creator<Place>(){
		@Override
		public Place createFromParcel(Parcel source) {			
			return new Place(source);
		}

		@Override
		public Place[] newArray(int size) {
			// TODO Auto-generated method stub
			return null;
		}		
	};
}