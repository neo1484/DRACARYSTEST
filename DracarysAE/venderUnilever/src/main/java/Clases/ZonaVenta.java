package Clases;

public class ZonaVenta 
{
	private int _zonaVentaId;
	private String _nombre;
	
	public ZonaVenta(){}

	public ZonaVenta(int _zonaVentaId, String _nombre) 
	{
		super();
		this._zonaVentaId = _zonaVentaId;
		this._nombre = _nombre;
	}

	public int get_zonaVentaId() {
		return _zonaVentaId;
	}

	public void set_zonaVentaId(int _zonaVentaId) {
		this._zonaVentaId = _zonaVentaId;
	}

	public String get_nombre() {
		return _nombre;
	}

	public void set_nombre(String _nombre) {
		this._nombre = _nombre;
	}
	
	public String toString(){ return this._nombre;}
}
