package Clases;

public class AvanceDistribucion 
{
	private int _distribuidorId;
	private int _dia;
	private int _mes;
	private int _anio;
	private String _nombreDistribuidor;
	private int _noPreventas;
	private float _montoPreventas;
	private int _noVentas;
	private float _montoVentas;
	private String _tipoAvanceDistribucion;
	private String _rol;
	
	public AvanceDistribucion(){}

	public AvanceDistribucion(int _distribuidorId, int _dia, int _mes,
			int _anio, String _nombreDistribuidor, int _noPreventas,
			float _montoPreventas, int _noVentas, float _montoVentas,
			String _tipoAvanceDistribucion, String _rol) 
	{
		super();
		this._distribuidorId = _distribuidorId;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._nombreDistribuidor = _nombreDistribuidor;
		this._noPreventas = _noPreventas;
		this._montoPreventas = _montoPreventas;
		this._noVentas = _noVentas;
		this._montoVentas = _montoVentas;
		this._tipoAvanceDistribucion = _tipoAvanceDistribucion;
		this._rol = _rol;
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

	public String get_nombreDistribuidor() {
		return _nombreDistribuidor;
	}

	public void set_nombreDistribuidor(String _nombreDistribuidor) {
		this._nombreDistribuidor = _nombreDistribuidor;
	}

	public int get_noPreventas() {
		return _noPreventas;
	}

	public void set_noPreventas(int _noPreventas) {
		this._noPreventas = _noPreventas;
	}

	public float get_montoPreventas() {
		return _montoPreventas;
	}

	public void set_montoPreventas(float _montoPreventas) {
		this._montoPreventas = _montoPreventas;
	}

	public int get_noVentas() {
		return _noVentas;
	}

	public void set_noVentas(int _noVentas) {
		this._noVentas = _noVentas;
	}

	public float get_montoVentas() {
		return _montoVentas;
	}

	public void set_montoVentas(float _montoVentas) {
		this._montoVentas = _montoVentas;
	}

	public String get_tipoAvanceDistribucion() {
		return _tipoAvanceDistribucion;
	}

	public void set_tipoAvanceDistribucion(String _tipoAvanceDistribucion) {
		this._tipoAvanceDistribucion = _tipoAvanceDistribucion;
	}

	public String get_rol() {
		return _rol;
	}

	public void set_rol(String _rol) {
		this._rol = _rol;
	}

	public float get_eficiencia() {
		if(_montoPreventas>0)
		{
			return (_montoVentas/_montoPreventas)*100;
		}
		else
		{
			return 0;
		}
	}
}
