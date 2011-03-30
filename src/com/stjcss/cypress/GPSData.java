package com.stjcss.cypress;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;



public class GPSData extends MapActivity {

	MapView mapView;
	MapController mapController;
	MyLocationOverlay mOverlay;
	
	LocationManager locMgr;
	LocationListener locListener;
	
	
	
	@Override
	protected boolean isLocationDisplayed(){
		return mOverlay.isMyLocationEnabled();
	}
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.map);
	    
	    mapView = (MapView) findViewById(R.id.mapview);
	    mapView.setBuiltInZoomControls(true);
	    mapController = mapView.getController();
	    mapController.setZoom(14);    // this isn't 11 miles.
	    
	    mOverlay = new MyLocationOverlay(this,mapView);
	    mapView.getOverlays().add(mOverlay);
	    mapView.postInvalidate();
	    
	    locMgr = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	    
	    locListener = new LocationListener(){

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				showLocation(location);
				
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
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
				// TODO Auto-generated method stub
				
			}
	    };
	    
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		Location lastLoc = locMgr.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		showLocation(lastLoc);
		locMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
		
		
		mOverlay.enableMyLocation();
		mOverlay.runOnFirstFix(new Runnable(){
			public void run() {
				mapController.setCenter(mOverlay.getMyLocation());
			}
		});
	}
	@Override
	public void onPause(){
		super.onPause();
		
		locMgr.removeUpdates(locListener);
		mOverlay.disableMyLocation();
	}
	private void showLocation(Location location){
		if(location != null){
			double lat = location.getLatitude();
			double lng = location.getLongitude();
			GeoPoint myLocation = new GeoPoint((int)(lat*1000000),(int)(lng*1000000));
			
			Toast.makeText(getBaseContext(), "Latitude ["+lat+"] longitude ["+lng+"]", Toast.LENGTH_SHORT);
		}
	}
}