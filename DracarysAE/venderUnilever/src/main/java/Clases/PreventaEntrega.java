package Clases;

public class PreventaEntrega 
{
	private int _anio;
	private int _dia;
	private boolean _estadoSincronizacion;
	private int _id;
	private int _mes;
	private int _motivoId;
	private int _preventaId;
	private int _tipoSincronizacion;
	private int _ventaId;
	
	public PreventaEntrega(){}

	public PreventaEntrega(int _anio, int _dia, boolean _estadoSincronizacion,
			int _id, int _mes, int _motivoId, int _preventaId,
			int _tipoSincronizacion, int _ventaId) {
		super();
		this._anio = _anio;
		this._dia = _dia;
		this._estadoSincronizacion = _estadoSincronizacion;
		this._id = _id;
		this._mes = _mes;
		this._motivoId = _motivoId;
		this._preventaId = _preventaId;
		this._tipoSincronizacion = _tipoSincronizacion;
		this._ventaId = _ventaId;
	}

	public int get_anio() {
		return _anio;
	}

	public void set_anio(int _anio) {
		this._anio = _anio;
	}

	public int get_dia() {
		return _dia;
	}

	public void set_dia(int _dia) {
		this._dia = _dia;
	}

	public boolean is_estadoSincronizacion() {
		return _estadoSincronizacion;
	}

	public void set_estadoSincronizacion(boolean _estadoSincronizacion) {
		this._estadoSincronizacion = _estadoSincronizacion;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_mes() {
		return _mes;
	}

	public void set_mes(int _mes) {
		this._mes = _mes;
	}

	public int get_motivoId() {
		return _motivoId;
	}

	public void set_motivoId(int _motivoId) {
		this._motivoId = _motivoId;
	}

	public int get_preventaId() {
		return _preventaId;
	}

	public void set_preventaId(int _preventaId) {
		this._preventaId = _preventaId;
	}

	public int get_tipoSincronizacion() {
		return _tipoSincronizacion;
	}

	public void set_tipoSincronizacion(int _tipoSincronizacion) {
		this._tipoSincronizacion = _tipoSincronizacion;
	}

	public int get_ventaId() {
		return _ventaId;
	}

	public void set_ventaId(int _ventaId) {
		this._ventaId = _ventaId;
	}
}
