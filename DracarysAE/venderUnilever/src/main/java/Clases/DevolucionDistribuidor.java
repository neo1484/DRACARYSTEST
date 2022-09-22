package Clases;

public class DevolucionDistribuidor 
{
	private int _id;
	private int _almacenDevolucionId;
	private int _distribuidorId;
	private int _anio;
	private int _mes;
	private int _dia;
	private int _estadoId;
	private boolean _estadoSincronizacion;
	
	public DevolucionDistribuidor(){}

	public DevolucionDistribuidor(int _id, int _almacenDevolucionId,
			int _distribuidorId, int _anio, int _mes, int _dia, int _estadoId,
			boolean _estadoSincronizacion) 
	{
		super();
		this._id = _id;
		this._almacenDevolucionId = _almacenDevolucionId;
		this._distribuidorId = _distribuidorId;
		this._anio = _anio;
		this._mes = _mes;
		this._dia = _dia;
		this._estadoId = _estadoId;
		this._estadoSincronizacion = _estadoSincronizacion;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_almacenDevolucionId() {
		return _almacenDevolucionId;
	}

	public void set_almacenDevolucionId(int _almacenDevolucionId) {
		this._almacenDevolucionId = _almacenDevolucionId;
	}

	public int get_distribuidorId() {
		return _distribuidorId;
	}

	public void set_distribuidorId(int _distribuidorId) {
		this._distribuidorId = _distribuidorId;
	}

	public int get_anio() {
		return _anio;
	}

	public void set_anio(int _anio) {
		this._anio = _anio;
	}

	public int get_mes() {
		return _mes;
	}

	public void set_mes(int _mes) {
		this._mes = _mes;
	}

	public int get_dia() {
		return _dia;
	}

	public void set_dia(int _dia) {
		this._dia = _dia;
	}

	public int get_estadoId() {
		return _estadoId;
	}

	public void set_estadoId(int _estadoId) {
		this._estadoId = _estadoId;
	}

	public boolean is_estadoSincronizacion() {
		return _estadoSincronizacion;
	}

	public void set_estadoSincronizacion(boolean _estadoSincronizacion) {
		this._estadoSincronizacion = _estadoSincronizacion;
	}
}
