package Clases;

public class SincronizacionDatos 
{
	private int _idEmpleado;
	private int _dia;
	private int _mes;
	private int _anio;
	private int _rol;
	
	public SincronizacionDatos(){}

	public SincronizacionDatos(int _idEmpleado, int _dia, int _mes, int _anio,
			int _rol) 
	{
		super();
		this._idEmpleado = _idEmpleado;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._rol = _rol;
	}

	public int get_idEmpleado() {
		return _idEmpleado;
	}

	public void set_idEmpleado(int _idEmpleado) {
		this._idEmpleado = _idEmpleado;
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

	public int get_rol() {
		return _rol;
	}

	public void set_rol(int _rol) {
		this._rol = _rol;
	}

}
