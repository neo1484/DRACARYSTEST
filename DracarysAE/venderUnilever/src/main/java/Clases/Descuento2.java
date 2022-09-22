package Clases;

public class Descuento2 
{
	private int _distribuidorId;
	private float _monto;
	
	public Descuento2(){}

	public Descuento2(int _distribuidorId, float _monto) {
		super();
		this._distribuidorId = _distribuidorId;
		this._monto = _monto;
	}

	public int get_distribuidorId() {
		return _distribuidorId;
	}

	public void set_distribuidorId(int _distribuidorId) {
		this._distribuidorId = _distribuidorId;
	}

	public float get_monto() {
		return _monto;
	}

	public void set_monto(float _monto) {
		this._monto = _monto;
	}
}
