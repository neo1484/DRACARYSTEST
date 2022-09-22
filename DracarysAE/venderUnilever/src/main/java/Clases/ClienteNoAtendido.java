package Clases;

public class ClienteNoAtendido 
{
	private int _id;
	private int _empleadoId;
	private int _clienteId;
	private int _motivoId;
	private int _dia;
	private int _mes;
	private int _anio;
	private int _hora;
	private int _minuto;
	private double _latitud;
	private double _longitud;
	private boolean _sincronizacion;
	
	public ClienteNoAtendido(){}

	public ClienteNoAtendido(int _id, int _empleadoId, int _clienteId,
			int _motivoId, int _dia, int _mes, int _anio, int _hora, 
			int _minuto,double _latitud,double _longitud,boolean _sincronizacion) 
	{
		super();
		this._id = _id;
		this._empleadoId = _empleadoId;
		this._clienteId = _clienteId;
		this._motivoId = _motivoId;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._hora = _hora;
		this._minuto = _minuto;
		this._latitud = _latitud;
		this._longitud = _longitud;
		this._sincronizacion = _sincronizacion;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_empleadoId() {
		return _empleadoId;
	}

	public void set_empleadoId(int _empleadoId) {
		this._empleadoId = _empleadoId;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public int get_motivoId() {
		return _motivoId;
	}

	public void set_motivoId(int _motivoId) {
		this._motivoId = _motivoId;
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

	public int get_hora() {
		return _hora;
	}

	public void set_hora(int _hora) {
		this._hora = _hora;
	}

	public int get_minuto() {
		return _minuto;
	}

	public void set_minuto(int _minuto) {
		this._minuto = _minuto;
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

	public boolean is_sincronizacion() {
		return _sincronizacion;
	}

	public void set_sincronizacion(boolean _sincronizacion) {
		this._sincronizacion = _sincronizacion;
	}
}
