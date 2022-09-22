package Clases;

public class VendedorZonaVenta 
{
	private int _zonaVentaId;
	private String _rutaId;
	
	public VendedorZonaVenta(){}

	public VendedorZonaVenta(int _zonaVentaId, String _rutaId) 
	{
		super();
		this._zonaVentaId = _zonaVentaId;
		this._rutaId = _rutaId;
	}

	public int get_zonaVentaId() {
		return _zonaVentaId;
	}

	public void set_zonaVentaId(int _zonaVentaId) {
		this._zonaVentaId = _zonaVentaId;
	}

	public String get_rutaId() {
		return _rutaId;
	}

	public void set_rutaId(String _rutaId) {
		this._rutaId = _rutaId;
	}
	
	public String toString()
	{
	    return this._rutaId;
	}
}
