package Clases;

import android.location.Location;
import android.location.LocationManager;

public class DatosGPS 
{
	private boolean _estado;
	private Location _location;
	private LocationManager _locationManager;
	
	public DatosGPS(){}

	public DatosGPS(boolean _estado, Location _location,
			LocationManager _locationManager) 
	{
		super();
		this._estado = _estado;
		this._location = _location;
		this._locationManager = _locationManager;
	}

	public boolean is_estado() {
		return _estado;
	}

	public void set_estado(boolean _estado) {
		this._estado = _estado;
	}

	public Location get_location() {
		return _location;
	}

	public void set_location(Location _location) {
		this._location = _location;
	}

	public LocationManager get_locationManager() {
		return _locationManager;
	}

	public void set_locationManager(LocationManager _locationManager) {
		this._locationManager = _locationManager;
	}
}
