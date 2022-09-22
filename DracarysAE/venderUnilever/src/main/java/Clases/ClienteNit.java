package Clases;

public class ClienteNit 
{
	private int _id;
	private int _clienteId;
	private String _nombreFactura;
	private String _nit;
	private int _empleadoId;
	private int _dia;
	private int _mes;
	private int _anio;
	private boolean _sincronizacion;
	private String _tipoNit;
	
	public ClienteNit(int _id, int _clienteId, String _nombreFactura,
			String _nit, int _empleadoId, int _dia, int _mes, int _anio,
			boolean _sincronizacion,String _tipoNit) {
		super();
		this._id = _id;
		this._clienteId = _clienteId;
		this._nombreFactura = _nombreFactura;
		this._nit = _nit;
		this._empleadoId = _empleadoId;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._sincronizacion = _sincronizacion;
		this._tipoNit = _tipoNit;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public String get_nombreFactura() {
		return _nombreFactura;
	}

	public void set_nombreFactura(String _nombreFactura) {
		this._nombreFactura = _nombreFactura;
	}

	public String get_nit() {
		return _nit;
	}

	public void set_nit(String _nit) {
		this._nit = _nit;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
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

	public boolean is_sincronizacion() {
		return _sincronizacion;
	}

	public void set_sincronizacion(boolean _sincronizacion) {
		this._sincronizacion = _sincronizacion;
	}

	public String get_tipoNit() {
		return _tipoNit;
	}

	public void set_tipoNit(String _tipoNit) {
		this._tipoNit = _tipoNit;
	}
}
