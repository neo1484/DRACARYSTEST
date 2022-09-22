package Clases;

public class UltimaCoordenada 
{
	private int _clienteId;
	private double _latitud;
	private double _longitud;
	
	public UltimaCoordenada(int _clienteId, double _latitud, double _longitud) 
	{
		super();
		this._clienteId = _clienteId;
		this._latitud = _latitud;
		this._longitud = _longitud;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
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
