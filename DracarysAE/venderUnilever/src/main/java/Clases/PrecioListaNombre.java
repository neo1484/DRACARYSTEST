package Clases;

public class PrecioListaNombre 
{
	private int _precioListaNombreId;
	private String _nombre;
	
	public PrecioListaNombre(){}

	public PrecioListaNombre(int _precioListaNombreId, String _nombre) 
	{
		super();
		this._precioListaNombreId = _precioListaNombreId;
		this._nombre = _nombre;
	}

	public int get_precioListaNombreId() {
		return _precioListaNombreId;
	}

	public void set_precioListaNombreId(int _precioListaNombreId) {
		this._precioListaNombreId = _precioListaNombreId;
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
