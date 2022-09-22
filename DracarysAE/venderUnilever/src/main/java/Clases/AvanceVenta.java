package Clases;

public class AvanceVenta 
{
	private int _id;
	private int _vendedorId;
	private int _dia;
	private int _mes;
	private int _anio;
	private String _nombreVendedor;
	private float _presupuesto;
	private float _avance;
	private float _tendencia;
	private float _cobertura;
	private String _tipoAvance;
	private String _rol;
	private String _nombreProveedor;
	private int _noPreventas;
	private float _coberturaPorcentaje;
	
	public AvanceVenta(){}

	public AvanceVenta(int _id, int _vendedorId, int _dia, int _mes, int _anio,
			String _nombreVendedor, float _presupuesto, float _avance,
			float _tendencia, float _cobertura, String _tipoAvance,String _rol,
			String _nombreProveedor,int _noPreventas,float _coberturaPorcentaje) 
	{
		super();
		this._id = _id;
		this._vendedorId = _vendedorId;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._nombreVendedor = _nombreVendedor;
		this._presupuesto = _presupuesto;
		this._avance = _avance;
		this._tendencia = _tendencia;
		this._cobertura = _cobertura;
		this._tipoAvance = _tipoAvance;
		this._rol = _rol;
		this._nombreProveedor = _nombreProveedor;
		this._noPreventas = _noPreventas;
		this._coberturaPorcentaje = _coberturaPorcentaje;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public int get_vendedorId() {
		return _vendedorId;
	}

	public void set_vendedorId(int _vendedorId) {
		this._vendedorId = _vendedorId;
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

	public String get_nombreVendedor() {
		return _nombreVendedor;
	}

	public void set_nombreVendedor(String _nombreVendedor) {
		this._nombreVendedor = _nombreVendedor;
	}

	public float get_presupuesto() {
		return _presupuesto;
	}

	public void set_presupuesto(float _presupuesto) {
		this._presupuesto = _presupuesto;
	}

	public float get_avance() {
		return _avance;
	}

	public void set_avance(float _avance) {
		this._avance = _avance;
	}

	public float get_tendencia() {
		return _tendencia;
	}

	public void set_tendencia(float _tendencia) {
		this._tendencia = _tendencia;
	}

	public float get_cobertura() {
		return _cobertura;
	}

	public void set_cobertura(float _cobertura) {
		this._cobertura = _cobertura;
	}

	public String get_tipoAvance() {
		return _tipoAvance;
	}

	public void set_tipoAvance(String _tipoAvance) {
		this._tipoAvance = _tipoAvance;
	}

	public String get_rol() {
		return _rol;
	}

	public void set_rol(String _rol) {
		this._rol = _rol;
	}

	public String get_nombreProveedor() {
		return _nombreProveedor;
	}

	public void set_nombreProveedor(String _nombreProveedor) {
		this._nombreProveedor = _nombreProveedor;
	}

	public int get_noPreventas() {
		return _noPreventas;
	}

	public void set_noPreventas(int _noPreventas) {
		this._noPreventas = _noPreventas;
	}

	public float get_coberturaPorcentaje() {
		return _coberturaPorcentaje;
	}

	public void set_coberturaPorcentaje(float _coberturaPorcentaje) {
		this._coberturaPorcentaje = _coberturaPorcentaje;
	}	
}
