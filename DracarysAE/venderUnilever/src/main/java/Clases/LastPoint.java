package Clases;

public class LastPoint 
{
	private int _empleadoId;
	private int _id;
	private double _latitud;
	private double _longitud;

	public LastPoint(){}

	public LastPoint(int _empleadoId, int _id, double _latitud, double _longitud) 
	{
		super();
		this._empleadoId = _empleadoId;
		this._id = _id;
		this._latitud = _latitud;
		this._longitud = _longitud;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public double get_latitud() {
		return _latitud;
	}

	public void set_latitud(double _latitud) {
		this._latitud = _latitud;
	}

	public double get_longitud() {
		return _longitud;
	}

	public void set_longitud(double _longitud) {
		this._longitud = _longitud;
	}
}
