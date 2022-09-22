package Clases;

public class AvanceVentaVendedor 
{
	private int _vendedorId;
	private int _dia;
	private int _mes;
	private int _anio;
	private String _nombreVendedor;
	private float _presupuesto;
	private float _avance;
	private float _tendencia;
	private int _cobertura;
	private String _objeto;
	private float _coberturaPorcentaje;
	
	public AvanceVentaVendedor(){}

	public AvanceVentaVendedor(int _vendedorId, int _dia, int _mes, int _anio, String _nombreVendedor,
			float _presupuesto, float _avance, float _tendencia, int _cobertura, String _objeto,
			float _coberturaPorcentaje) 
	{
		super();
		this._vendedorId = _vendedorId;
		this._dia = _dia;
		this._mes = _mes;
		this._anio = _anio;
		this._nombreVendedor = _nombreVendedor;
		this._presupuesto = _presupuesto;
		this._avance = _avance;
		this._tendencia = _tendencia;
		this._cobertura = _cobertura;
		this._objeto = _objeto;
		this._coberturaPorcentaje = _coberturaPorcentaje;
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

	public int get_cobertura() {
		return _cobertura;
	}

	public void set_cobertura(int _cobertura) {
		this._cobertura = _cobertura;
	}

	public String get_objeto() {
		return _objeto;
	}

	public void set_objeto(String _objeto) {
		this._objeto = _objeto;
	}

	public float get_coberturaPorcentaje() {
		return _coberturaPorcentaje;
	}

	public void set_coberturaPorcentaje(float _coberturaPorcentaje) {
		this._coberturaPorcentaje = _coberturaPorcentaje;
	}
}
