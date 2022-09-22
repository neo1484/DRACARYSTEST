package Clases;

public class ProductoPOPCosto 
{
	private int _costoId;
	private int _productoId;
	private float _costo;
	
	public ProductoPOPCosto(){}

	public ProductoPOPCosto(int _costoId, int _productoId, float _costo) 
	{
		super();
		this._costoId = _costoId;
		this._productoId = _productoId;
		this._costo = _costo;
	}

	public int get_costoId() {
		return _costoId;
	}

	public void set_costoId(int _costoId) {
		this._costoId = _costoId;
	}

	public int get_productoId() {
		return _productoId;
	}

	public void set_productoId(int _productoId) {
		this._productoId = _productoId;
	}

	public float get_costo() {
		return _costo;
	}

	public void set_costo(float _costo) {
		this._costo = _costo;
	}
}
