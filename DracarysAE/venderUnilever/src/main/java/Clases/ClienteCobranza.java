package Clases;

public class ClienteCobranza 
{
	private int _rowId;
	private int _clienteId;
	private String _nombre;
	private String _fecha;
	private float _monto;
	private float _saldo;
	private String _nroFactura;
	private int _ventaId;
	private double _latitud;
	private double _longitud;
	private int _serverId;
	
	public ClienteCobranza(){}

	public ClienteCobranza(int _rowId,int _clienteId, String _nombre,String _fecha, float _monto, float _saldo, String _nroFactura, int _ventaId,
			double _latitud, double _longitud, int _serverId) {
		super();
		this._rowId = _rowId;
		this._clienteId = _clienteId;
		this._nombre = _nombre;
		this._fecha = _fecha;
		this._monto = _monto;
		this._saldo = _saldo;
		this._nroFactura = _nroFactura;
		this._ventaId = _ventaId;
		this._latitud = _latitud;
		this._longitud = _longitud;
		this._serverId = _serverId;
	}

	public int get_rowId() {
		return _rowId;
	}

	public void set_rowId(int _rowId) {
		this._rowId = _rowId;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}

	public String get_fecha() {
		return _fecha;
	}

	public void set_fecha(String _fecha) {
		this._fecha = _fecha;
	}

	public float get_monto() {
		return _monto;
	}

	public void set_monto(float _monto) {
		this._monto = _monto;
	}

	public float get_saldo() {
		return _saldo;
	}

	public void set_saldo(float _saldo) {
		this._saldo = _saldo;
	}

	public String get_nroFactura() {
		return _nroFactura;
	}

	public void set_nroFactura(String _nroFactura) {
		this._nroFactura = _nroFactura;
	}

	public int get_ventaId() {
		return _ventaId;
	}

	public void set_ventaId(int _ventaId) {
		this._ventaId = _ventaId;
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

	public int get_serverId() {
		return _serverId;
	}

	public void set_serverId(int _serverId) {
		this._serverId = _serverId;
	}
}
