package Clases;

public class CierrePreventista 
{
	private int _id;
	private int _empleadoId;
	private int _anio;
	private int _mes;
	private int _dia;
	
	public CierrePreventista(int _id, int _empleadoId, int _anio, int _mes,
			int _dia) 
	{
		super();
		this._id = _id;
		this._empleadoId = _empleadoId;
		this._anio = _anio;
		this._mes = _mes;
		this._dia = _dia;
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
	};

}
