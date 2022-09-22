package Clases;

public class DraRebateSaldo 
{
	private int _clienteId;
	private float _saldo;
	private float _maxDescuento;
	
	public DraRebateSaldo() { }
	
	public DraRebateSaldo(int _clienteId, float _saldo, float _maxDescuento) {
		super();
		this._clienteId = _clienteId;
		this._saldo = _saldo;
		this._maxDescuento = _maxDescuento;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}

	public float get_saldo() {
		return _saldo;
	}

	public void set_saldo(float _saldo) {
		this._saldo = _saldo;
	}

	public float get_maxDescuento() {
		return _maxDescuento;
	}

	public void set_maxDescuento(float _maxDescuento) {
		this._maxDescuento = _maxDescuento;
	}	
}
