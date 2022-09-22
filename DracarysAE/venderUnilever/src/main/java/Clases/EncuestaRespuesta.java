package Clases;

public class EncuestaRespuesta 
{
	private int _id;
	private int _encuestaRespuestaId;
	private int _detalleId;
	private int _clienteId;
	private String _respuesta;
	private String _especificacion;
	private String _observacion;
	private int _empleadoId;
	private int _dia;
	private int _mes;
	private int _anio;
	
	public EncuestaRespuesta(){}

	public EncuestaRespuesta(int _id, int _encuestaRespuestaId, int _detalleId, int _clienteId, String _respuesta,
			String _especificacion, String _observacion, int _empleadoId, int _dia, int _mes, int _anio) 
	{
		super();
		this._id = _id;
		this._encuestaRespuestaId = _encuestaRespuestaId;
		this._detalleId = _detalleId;
		this._clienteId = _clienteId;
		this._respuesta = _respuesta;
		this._especificacion = _especificacion;
		this._observacion = _observacion;
		this._empleadoId = _empleadoId;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_encuestaRespuestaId() {
		return _encuestaRespuestaId;
	}

	public void set_encuestaRespuestaId(int _encuestaRespuestaId) {
		this._encuestaRespuestaId = _encuestaRespuestaId;
	}

	public int get_detalleId() {
		return _detalleId;
	}

	public void set_detalleId(int _detalleId) {
		this._detalleId = _detalleId;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public String get_respuesta() {
		return _respuesta;
	}

	public void set_respuesta(String _respuesta) {
		this._respuesta = _respuesta;
	}

	public String get_especificacion() {
		return _especificacion;
	}

	public void set_especificacion(String _especificacion) {
		this._especificacion = _especificacion;
	}

	public String get_observacion() {
		return _observacion;
	}

	public void set_observacion(String _observacion) {
		this._observacion = _observacion;
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
	
	
}
