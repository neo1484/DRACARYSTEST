package Clases;

public class Proveedor 
{
	private int _proveedorId;
	private String _nombre;

	public Proveedor(){}

	public Proveedor(int _proveedorId, String _nombre) 
	{
		super();
		this._proveedorId = _proveedorId;
		this._nombre = _nombre;
	}

	public int get_proveedorId() {
		return _proveedorId;
	}

	public void set_proveedorId(int _proveedorId) {
		this._proveedorId = _proveedorId;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}
	
	public String toString()
	{
	    return this._nombre;
	}

}
