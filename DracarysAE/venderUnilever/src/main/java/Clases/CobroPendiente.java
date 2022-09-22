package Clases;

public class CobroPendiente 
{
	private int _ventaId;
	private int _clienteId;
	private String _clienteNombre;
	private String _fechaVenta;
	private float _monto;
	private String _fechaVencimiento;
	private int _diasMora;
	private float _saldo;
	
	public CobroPendiente(){}

	public CobroPendiente(int _ventaId, int _clienteId,
			String _clienteNombre, String _fechaVenta, float _monto,
			String _fechaVencimiento, int _diasMora,float _saldo)
	{
		super();
		this._ventaId = _ventaId;
		this._clienteId = _clienteId;
		this._clienteNombre = _clienteNombre;
		this._fechaVenta = _fechaVenta;
		this._monto = _monto;
		this._fechaVencimiento = _fechaVencimiento;
		this._diasMora = _diasMora;
		this._saldo = _saldo;
	}

	public int get_ventaId() {
		return _ventaId;
	}

	public void set_ventaId(int _ventaId) {
		this._ventaId = _ventaId;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public String get_clienteNombre() {
		return _clienteNombre;
	}

	public void set_clienteNombre(String _clienteNombre) {
		this._clienteNombre = _clienteNombre;
	}

	public String get_fechaVenta() {
		return _fechaVenta;
	}

	public void set_fechaVenta(String _fechaVenta) {
		this._fechaVenta = _fechaVenta;
	}

	public float get_monto() {
		return _monto;
	}

	public void set_monto(float _monto) {
		this._monto = _monto;
	}

	public String get_fechaVencimiento() {
		return _fechaVencimiento;
	}

	public void set_fechaVencimiento(String _fechaVencimiento) {
		this._fechaVencimiento = _fechaVencimiento;
	}

	public int get_diasMora() {
		return _diasMora;
	}

	public void set_diasMora(int _diasMora) {
		this._diasMora = _diasMora;
	}

	public float get_saldo() {
		return _saldo;
	}

	public void set_saldo(float _saldo) {
		this._saldo = _saldo;
	}
}
