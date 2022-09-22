package Clases;

public class MetaSap 
{
	private String _categoriaVendedor;
	private String _nivelVendedor;
	private String _tipo;
	private String _nivel;
	private String _objeto;
	private float _porcentaje;
	private float _monto;
	private int _cobertura;
	private float _montoVenta;
	private int _coberturaVenta;
	private float _avanceMonto;
	private float _avanceCobertura;
	
	public MetaSap(){}
	
	public MetaSap(String _categoriaVendedor, String _nivelVendedor, String _tipo, String _nivel, String _objeto,
			float _porcentaje, float _monto, int _cobertura, float _montoVenta, int _coberturaVenta, float _avanceMonto,
			float _avanceCobertura) {
		super();
		this._categoriaVendedor = _categoriaVendedor;
		this._nivelVendedor = _nivelVendedor;
		this._tipo = _tipo;
		this._nivel = _nivel;
		this._objeto = _objeto;
		this._porcentaje = _porcentaje;
		this._monto = _monto;
		this._cobertura = _cobertura;
		this._montoVenta = _montoVenta;
		this._coberturaVenta = _coberturaVenta;
		this._avanceMonto = _avanceMonto;
		this._avanceCobertura = _avanceCobertura;
	}

	public String get_categoriaVendedor() {
		return _categoriaVendedor;
	}

	public void set_categoriaVendedor(String _categoriaVendedor) {
		this._categoriaVendedor = _categoriaVendedor;
	}

	public String get_nivelVendedor() {
		return _nivelVendedor;
	}

	public void set_nivelVendedor(String _nivelVendedor) {
		this._nivelVendedor = _nivelVendedor;
	}

	public String get_tipo() {
		return _tipo;
	}

	public void set_tipo(String _tipo) {
		this._tipo = _tipo;
	}

	public String get_nivel() {
		return _nivel;
	}

	public void set_nivel(String _nivel) {
		this._nivel = _nivel;
	}

	public String get_objeto() {
		return _objeto;
	}

	public void set_objeto(String _objeto) {
		this._objeto = _objeto;
	}

	public float get_porcentaje() {
		return _porcentaje;
	}

	public void set_porcentaje(float _porcentaje) {
		this._porcentaje = _porcentaje;
	}

	public float get_monto() {
		return _monto;
	}

	public void set_monto(float _monto) {
		this._monto = _monto;
	}

	public int get_cobertura() {
		return _cobertura;
	}

	public void set_cobertura(int _cobertura) {
		this._cobertura = _cobertura;
	}

	public float get_montoVenta() {
		return _montoVenta;
	}

	public void set_montoVenta(float _montoVenta) {
		this._montoVenta = _montoVenta;
	}

	public int get_coberturaVenta() {
		return _coberturaVenta;
	}

	public void set_coberturaVenta(int _coberturaVenta) {
		this._coberturaVenta = _coberturaVenta;
	}

	public float get_avanceMonto() {
		return _avanceMonto;
	}

	public void set_avanceMonto(float _avanceMonto) {
		this._avanceMonto = _avanceMonto;
	}

	public float get_avanceCobertura() {
		return _avanceCobertura;
	}

	public void set_avanceCobertura(float _avanceCobertura) {
		this._avanceCobertura = _avanceCobertura;
	}
}
