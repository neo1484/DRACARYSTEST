package Clases;

public class Consignacion 
{
	private int _rowId;
	private int _consignacionId;
	private int _distribuidorId;
	private int _dia;
	private int _mes;
	private int _anio;
	private int _almacenId;
	private int _precioListaId;
	private boolean _estadoincronizacion;
	
	public Consignacion(){}

	public Consignacion(int _rowId, int _consignacionId, int _distribuidorId,
			int _dia, int _mes, int _anio, int _almacenId, int _precioListaId,
			boolean _estadoincronizacion) 
	{
		super();
		this._rowId = _rowId;
		this._consignacionId = _consignacionId;
		this._distribuidorId = _distribuidorId;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._almacenId = _almacenId;
		this._precioListaId = _precioListaId;
		this._estadoincronizacion = _estadoincronizacion;
	}

	public int get_rowId() {
		return _rowId;
	}

	public void set_rowId(int _rowId) {
		this._rowId = _rowId;
	}

	public int get_consignacionId() {
		return _consignacionId;
	}

	public void set_consignacionId(int _consignacionId) {
		this._consignacionId = _consignacionId;
	}

	public int get_distribuidorId() {
		return _distribuidorId;
	}

	public void set_distribuidorId(int _distribuidorId) {
		this._distribuidorId = _distribuidorId;
	}

	public int get_dia() {
		return _dia;
	}

	public void set_dia(int _dia) {
		this._dia = _dia;
	}

	public int get_mes() {
		return _mes;
	}

	public void set_mes(int _mes) {
		this._mes = _mes;
	}

	public int get_anio() {
		return _anio;
	}

	public void set_anio(int _anio) {
		this._anio = _anio;
	}

	public int get_almacenId() {
		return _almacenId;
	}

	public void set_almacenId(int _almacenId) {
		this._almacenId = _almacenId;
	}

	public int get_precioListaId() {
		return _precioListaId;
	}

	public void set_precioListaId(int _precioListaId) {
		this._precioListaId = _precioListaId;
	}

	public boolean is_estadoincronizacion() {
		return _estadoincronizacion;
	}

	public void set_estadoincronizacion(boolean _estadoincronizacion) {
		this._estadoincronizacion = _estadoincronizacion;
	}
}
