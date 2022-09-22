package Clases;

public class Fecha 
{
	private int _anio;
	private int _dia;
	private int _diaSemana;
	private int _hora;
	private int _mes;
	private int _minuto;
	private int _segundo;
	
	public Fecha(){}
	
	public Fecha(int _anio, int _dia, int _diaSemana, int _hora, int _mes,
			int _minuto, int _segundo) 
	{
		super();
		this._anio = _anio;
		this._dia = _dia;
		this._diaSemana = _diaSemana;
		this._hora = _hora;
		this._mes = _mes;
		this._minuto = _minuto;
		this._segundo = _segundo;
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

	public int get_diaSemana() {
		return _diaSemana;
	}

	public void set_diaSemana(int _diaSemana) {
		this._diaSemana = _diaSemana;
	}

	public int get_hora() {
		return _hora;
	}

	public void set_hora(int _hora) {
		this._hora = _hora;
	}

	public int get_mes() {
		return _mes;
	}

	public void set_mes(int _mes) {
		this._mes = _mes;
	}

	public int get_minuto() {
		return _minuto;
	}

	public void set_minuto(int _minuto) {
		this._minuto = _minuto;
	}

	public int get_segundo() {
		return _segundo;
	}

	public void set_segundo(int _segundo) {
		this._segundo = _segundo;
	}
}
