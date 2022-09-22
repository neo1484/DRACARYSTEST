package Clases;

public class MyLog 
{
	private int _id;
	private String _aplicacion;
	private String _actividad;
	private String _fecha;
	private String _tipoLog;
	private String _log;

	public MyLog(){}

	public MyLog(int _id, String _aplicacion, String _actividad, String _fecha,
			String _tipoLog, String _Log) 
	{
		super();
		this._id = _id;
		this._aplicacion = _aplicacion;
		this._actividad = _actividad;
		this._fecha = _fecha;
		this._tipoLog = _tipoLog;
		this._log = _Log;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String get_aplicacion() {
		return _aplicacion;
	}

	public void set_aplicacion(String _aplicacion) {
		this._aplicacion = _aplicacion;
	}

	public String get_actividad() {
		return _actividad;
	}

	public void set_actividad(String _actividad) {
		this._actividad = _actividad;
	}

	public String get_fecha() {
		return _fecha;
	}

	public void set_fecha(String _fecha) {
		this._fecha = _fecha;
	}

	public String get_tipoLog() {
		return _tipoLog;
	}

	public void set_tipoLog(String _tipoLog) {
		this._tipoLog = _tipoLog;
	}

	public String get_Log() {
		return _log;
	}

	public void set_Log(String _Log) {
		this._log = _Log;
	}
}
