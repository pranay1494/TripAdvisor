package com.example.tripadvisor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.tripadvisor/databases/";
 
    private static String DB_NAME = "tripadvisor.db";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DataBaseHelper(Context context) {
 
    	super(context, DB_NAME, null, 1);
        this.myContext = context;
    }	
    
    
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException{
 
    	boolean dbExist = checkDataBase();
 
    	if(dbExist){
    		//do nothing - database already exist
    	}else{
 
    		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
        	this.getReadableDatabase();
 
        	try {
 
    			copyDataBase();
 
    		} catch (IOException e) {
                Log.i("copydatabase","unsuccessful");
        		throw new Error("Error copying database");
 
        	}
    	}
 
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase(){
 
    	SQLiteDatabase checkDB = null;
 
    	try{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
 
    	}catch(SQLiteException e){
 
    		//database does't exist yet.
    		Log.i("checkDataBase", "database doesn't exist");
 
    	}
 
    	if(checkDB != null){
 
    		checkDB.close();
 
    	}
 
    	return checkDB != null ? true : false;
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{
 
    	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
 
    }
 
    public void openDataBase() throws SQLException{
 
    	//Open the database
        String myPath = DB_PATH + DB_NAME;
        try {
    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
           Log.i("opendatabase","cannot open database");
        }
    }
 
    @Override
	public synchronized void close() {
 
    	    if(myDataBase != null)
    		    myDataBase.close();
 
    	    super.close();
 
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) {
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public Cursor getInterest(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  myDataBase.rawQuery( "Select Idescr from CityInterest where _id IN (Select _id from City where CityName=?)",new String[] {str} );
	      return res;
	   }
	
	public Cursor getPartner(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery( "Select Pdescr from CityPartner where _id IN (Select _id from City where CityName=?)",new String[] {str});
	      return res1;
	   }
	
	public Cursor getDuring(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery( "select Ddescr from CityDuring where _id IN (select _id from City where cityName=?)",new String[] {str});
	      return res1;
	   }
	
	public Cursor getAbout(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery( "select Description from City where CityName=?",new String[] {str});
	      return res1;
	   }
	
	public Cursor getActivityName(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery("select name from activities where _id IN (select _id from City where cityName=?)",new String[] {str});
	      return res1;
	   }
	
	public Cursor getActivityCategory(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery("select category from activities where _id IN (select _id from City where cityName=?)",new String[] {str});
	      return res1;
	   }
	
	public Cursor getActivityOpenHrs(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery("select open from activities where _id IN (select _id from City where cityName=?)",new String[] {str});
	      return res1;
	   }
	
	public Cursor getActivityAbout(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery("select about from activities where name=?",new String[] {str});
	      return res1;

	}
	
	public Cursor getActivityFair(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery("select fair from activities where _id IN (select _id from City where cityName=?)",new String[] {str});
	      return res1;
	   }
	
	public Cursor getActivityPhotography(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery("select photography from activities where _id IN (select _id from City where cityName=?)",new String[] {str});
	      return res1;
	   }
	
	public Cursor getActivityVideo(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery("select video_id from activities where name=?",new String[] {str});
	      return res1;
	   }
	
	public Cursor getHotelName(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery("select name from Hotels where _id IN (select _id from City where cityName=?)",new String[] {str});
	      return res1;
	   }
	
	public Cursor getHotelType(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery("select type from Hotels where _id IN (select _id from City where cityName=?)",new String[] {str});
	      return res1;
	   }
	
	public Cursor getHotelRooms(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery("select rooms from Hotels where name=?",new String[] {str});
	      return res1;
	   }
	
	public Cursor getHotelAddress(String str){
		
	     // SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res1 =  myDataBase.rawQuery("select address from Hotels where name=?",new String[] {str});
	      return res1;
	   }
	
	
	
}
 
        // Add your public helper methods to access and get content from the database.
       // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
       // to you to create adapters for your views.