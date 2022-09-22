package Clases;

public class ProntoPagoJerarquia
{
	private int _prontoPagoId;
	private int _jerarquia;
	private int _jerarquiaId;
	private float _descuento;
	
	public ProntoPagoJerarquia() {}

	public ProntoPagoJerarquia(int _prontoPagoId, String jerarquia, int _jerarquiaId, float _descuento)
	{
		super();
		this._prontoPagoId = _prontoPagoId;
		this._jerarquia = _jerarquiaId;
		this._jerarquiaId = _jerarquiaId;
		this._descuento = _descuento;
	}

	public int get_prontoPagoId() {
		return _prontoPagoId;
	}

	public void set_prontoPagoId(int _prontoPagoId) {
		this._prontoPagoId = _prontoPagoId;
	}

	public int get_jerarquia() {
		return _jerarquia;
	}

	public void set_jerarquia(int _jerarquia) {
		this._jerarquia = _jerarquia;
	}

	public int get_jerarquiaId() {
		return _jerarquiaId;
	}

	public void set_jerarquiaId(int _jerarquiaId) {
		this._jerarquiaId = _jerarquiaId;
	}

	public float get_descuento() {
		return _descuento;
	}

	public void set_descuento(float _descuento) {
		this._descuento = _descuento;
	}
}
