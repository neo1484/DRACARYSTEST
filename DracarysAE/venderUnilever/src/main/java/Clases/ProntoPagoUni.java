package Clases;

public class ProntoPagoUni 
{
	private int _prontoPagoId;
	private String _descripcion;
	
	public ProntoPagoUni() {}
	
	public ProntoPagoUni(int _prontoPagoId, String _descripcion) 
	{
		super();
		this._prontoPagoId = _prontoPagoId;
		this._descripcion = _descripcion;
	}

	public int get_prontoPagoId() {
		return _prontoPagoId;
	}

	public void set_prontoPagoId(int _prontoPagoId) {
		this._prontoPagoId = _prontoPagoId;
	}

	public String get_descripcion() {
		return _descripcion;
	}

	public void set_descripcion(String _descripcion) {
		this._descripcion = _descripcion;
	}
}
