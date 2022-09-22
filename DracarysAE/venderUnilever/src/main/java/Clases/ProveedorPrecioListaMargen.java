package Clases;

public class ProveedorPrecioListaMargen 
{
	private int _proveedorId;
	private int _precioListaMargenId;
	private float _margen;
	
	public ProveedorPrecioListaMargen(){}

	public ProveedorPrecioListaMargen(int _proveedorId,
			int _precioListaMargenId, float _margen) 
	{
		super();
		this._proveedorId = _proveedorId;
		this._precioListaMargenId = _precioListaMargenId;
		this._margen = _margen;
	}

	public int get_proveedorId() {
		return _proveedorId;
	}

	public void set_proveedorId(int _proveedorId) {
		this._proveedorId = _proveedorId;
	}

	public int get_precioListaMargenId() {
		return _precioListaMargenId;
	}

	public void set_precioListaMargenId(int _precioListaMargenId) {
		this._precioListaMargenId = _precioListaMargenId;
	}

	public float get_margen() {
		return _margen;
	}

	public void set_margen(float _margen) {
		this._margen = _margen;
	}
}
