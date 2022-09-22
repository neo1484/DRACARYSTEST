package Clases;

public class TipoPago 
{
	private int _tipoPagoId;
	private String _tipoPago;
	
	public TipoPago(){}

	public TipoPago(int _tipoPagoId, String _tipoPago) 
	{
		super();
		this._tipoPagoId = _tipoPagoId;
		this._tipoPago = _tipoPago;
	}

	public int get_tipoPagoId() {
		return _tipoPagoId;
	}

	public void set_tipoPagoId(int _tipoPagoId) {
		this._tipoPagoId = _tipoPagoId;
	}

	public String get_tipoPago() {
		return _tipoPago;
	}

	public void set_tipoPago(String _tipoPago) {
		this._tipoPago = _tipoPago;
	}
	
	public String toString()
	{
	    return this._tipoPago;
	}
}
