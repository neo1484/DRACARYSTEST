package Clases;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class Ubicacion extends Service implements LocationListener {
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10L;
	private static final long MIN_TIME_BW_UPDATES = 60000L;

	private final Context mContext;
	protected LocationManager locationManager = null;
	;
	Location location = null;

	boolean canGetLocation = false;
	boolean isGPSEnabled = false;
	boolean isNetworkEnabled = false;

	double latitude;
	double longitude;

	public Ubicacion(Context contexto) throws Exception {
		mContext = contexto;
		getLocation();
	}

	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	public double getLatitude() {
		if (location != null) {
			latitude = location.getLatitude();
		}
		return this.latitude;
	}

	public Location getLocation() throws Exception {
		locationManager = ((LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE));
		isGPSEnabled = locationManager.isProviderEnabled("gps");
		isNetworkEnabled = locationManager.isProviderEnabled("network");

		if ((isGPSEnabled) || (isNetworkEnabled)) {
			canGetLocation = true;

			if ((isGPSEnabled) && (location == null)) {
				if (ActivityCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
						&& ActivityCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					return null;
				}
				locationManager.requestLocationUpdates("gps", MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
				Log.i("App", "GPS habilitado");

				if (locationManager != null) {
					location = locationManager.getLastKnownLocation("gps");

					if (location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();
					}
				}
			}

			if ((isNetworkEnabled) && (location == null)) {
				if (ActivityCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
						&& ActivityCompat.checkSelfPermission((Activity)mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					return null;
				}
				locationManager.requestLocationUpdates("network", MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
				Log.i("App", "Ubicacion encontrada por red telefonica.");
          
				if (locationManager != null)
				{
					location = locationManager.getLastKnownLocation("network");
					
					if (location != null)
					{
						latitude = location.getLatitude();
						longitude = location.getLongitude();
					}
				}
			}
		}
		
		return location;
	}
	  
	public double getLongitude()
	{
	    if(location != null)
	    {
	    	this.longitude = location.getLongitude();
	    }
	    return longitude;
	}
	  
	public void onLocationChanged(Location paramLocation) {}
	  
	public void onProviderDisabled(String paramString) {}
	  
	public void onProviderEnabled(String paramString) {}
	    
	public void stopUsingGPS()
	{
		if(locationManager != null) 
		{
			locationManager.removeUpdates(this);
	    }
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {}

	@Override
	public IBinder onBind(Intent intent) 
	{
		return null;
	}
}
