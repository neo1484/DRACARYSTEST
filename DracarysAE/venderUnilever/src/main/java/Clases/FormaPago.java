package Clases;

public class FormaPago 
{
	private int _formaPagoId;
	private String _formaPago;
	
	public FormaPago(){}

	public FormaPago(int _formaPagoId, String _formaPago) 
	{
		super();
		this._formaPagoId = _formaPagoId;
		this._formaPago = _formaPago;
	}

	public int get_formaPagoId() {
		return _formaPagoId;
	}

	public void set_formaPagoId(int _formaPagoId) {
		this._formaPagoId = _formaPagoId;
	}

	public String get_formaPago() {
		return _formaPago;
	}

	public void set_formaPago(String _formaPago) {
		this._formaPago = _formaPago;
	}
	
	public String toString()
	{
		return this._formaPago;
	}

}
