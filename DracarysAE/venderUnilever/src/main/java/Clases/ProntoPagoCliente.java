package Clases;

public class ProntoPagoCliente 
{
	private int _prontoPagoId;
	private int _clienteId;
	
	public ProntoPagoCliente() {}

	public ProntoPagoCliente(int _prontoPagoId, int _clienteId) 
	{
		super();
		this._prontoPagoId = _prontoPagoId;
		this._clienteId = _clienteId;
	}

	public int get_prontoPagoId() {
		return _prontoPagoId;
	}

	public void set_prontoPagoId(int _prontoPagoId) {
		this._prontoPagoId = _prontoPagoId;
	}

	public int get_clienteId() {
		return _clienteId;
	}

	public void set_clienteId(int _clienteId) {
		this._clienteId = _clienteId;
	}
}
